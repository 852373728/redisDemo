package com.qilin.service;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qilin.config.RedissonConfig;
import com.qilin.dto.Result;
import com.qilin.dto.VoucherOrderDto;
import com.qilin.entities.SeckillVoucher;
import com.qilin.entities.Voucher;
import com.qilin.entities.VoucherOrder;
import com.qilin.mapper.SeckillVoucherMapper;
import com.qilin.mapper.VoucherMapper;
import com.qilin.mapper.VoucherOrderMapper;
import com.qilin.utils.RedisDistributedLock;
import com.qilin.utils.RedisGlobalPrefixKey;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigInteger;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class VoucherOrderService implements InitializingBean {

    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Resource
    private SeckillVoucherMapper seckillVoucherMapper;

    @Resource
    private IdGenerate idGenerate;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedissonClient redissonClient;


    public Result createVoucherOrder(Long voucherId, Long userId) {

        SeckillVoucher seckillVoucher = seckillVoucherMapper.selectByPrimaryKey(voucherId);
        Date now = new Date();
        if (seckillVoucher.getBeginTime().after(now)) {
            return Result.fail("秒杀尚未开始");
        }
        if (seckillVoucher.getEndTime().before(now)) {
            return Result.fail("秒杀已经结束");
        }

        if (seckillVoucher.getStock() <= 0) {
            return Result.fail("库存不足");
        }


        VoucherOrderService voucherOrderServiceBean = applicationContext.getBean(VoucherOrderService.class);
        //单体应用下的锁，利用jvm的synchronized
//        synchronized (userId.toString().intern()) {
//            return voucherOrderServiceBean.validAndInsert(voucherId, userId);
//        }
        //分布式锁
//        RedisDistributedLock redisDistributedLock = new RedisDistributedLock(userId.toString(),stringRedisTemplate);
//        boolean b = redisDistributedLock.tryLock(1200);
        RLock lock = redissonClient.getLock("distributedLock:" + userId.toString());
        boolean b = lock.tryLock();
        if (!b) {
            return Result.fail("获取锁失败");
        }
        try {
            return voucherOrderServiceBean.validAndInsert(voucherId, userId);
        } finally {
            lock.unlock();
        }

    }




    @Transactional(rollbackFor = Exception.class)
    public Result validAndInsert(Long voucherId, Long userId) {

        Example example = new Example(VoucherOrder.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("voucherId", voucherId);
        List<VoucherOrder> voucherOrders = voucherOrderMapper.selectByExample(example);
        if (!voucherOrders.isEmpty()) {
            return Result.fail("不能重复下单");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("voucherId", voucherId);
        boolean b = seckillVoucherMapper.decreaseStock(params);
        if (!b) {
            return Result.fail("库存不足");
        }

        VoucherOrder voucherOrder = new VoucherOrder();
        BigInteger id = idGenerate.getGloballyUniqueId("order");
        voucherOrder.setId(id.longValue());
        voucherOrder.setVoucherId(voucherId);
        voucherOrder.setUserId(userId);
        voucherOrder.setPayType((byte) 1);
        voucherOrder.setStatus((byte) 1);
        voucherOrder.setCreateTime(new Date());
        voucherOrder.setUpdateTime(new Date());
        voucherOrderMapper.insert(voucherOrder);
        log.info("插入成功");
        return Result.ok(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result validAndInsert(Long voucherId, Long userId,Long orderId) {

        Example example = new Example(VoucherOrder.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("voucherId", voucherId);
        List<VoucherOrder> voucherOrders = voucherOrderMapper.selectByExample(example);
        if (!voucherOrders.isEmpty()) {
            return Result.fail("不能重复下单");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("voucherId", voucherId);
        boolean b = seckillVoucherMapper.decreaseStock(params);
        if (!b) {
            return Result.fail("库存不足");
        }

        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setId(orderId);
        voucherOrder.setVoucherId(voucherId);
        voucherOrder.setUserId(userId);
        voucherOrder.setPayType((byte) 1);
        voucherOrder.setStatus((byte) 1);
        voucherOrder.setCreateTime(new Date());
        voucherOrder.setUpdateTime(new Date());
        voucherOrderMapper.insert(voucherOrder);
        log.info("插入成功");
        return Result.ok(orderId);
    }

    private  static final String STREAM_KEY = "seckillOrderQueue";
    private  static final String STREAM_GROUP1 = "seckillOrderGroup1";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Override
    public void afterPropertiesSet(){
        StreamOperations<String, Object, Object> streamOperations = stringRedisTemplate.opsForStream();
        if (BooleanUtil.isFalse(stringRedisTemplate.hasKey(STREAM_KEY))) {
            String group = streamOperations.createGroup(STREAM_KEY, ReadOffset.from("0"), STREAM_GROUP1);
            log.info("{}队列key创建成功",STREAM_KEY);
            System.out.println(group);
            StreamInfo.XInfoStream info = streamOperations.info(STREAM_KEY);
            System.out.println(info);
        }
        executorService.execute(()->{
            VoucherOrderService voucherOrderServiceBean = applicationContext.getBean(VoucherOrderService.class);
            while (true) {
                try {

                    List<MapRecord<String, Object, Object>> info = streamOperations.read(
                            Consumer.from(STREAM_GROUP1, "consumer1"),
                            StreamReadOptions.empty().count(1),
                            StreamOffset.create(STREAM_KEY, ReadOffset.from("0"))
                    );
                    if (info==null || info.isEmpty()) {
                        log.info("没有待处理的消息");
                        break;
                    }
                    MapRecord<String, Object, Object> objectRecord = info.get(0);
                    Map<Object, Object> value = objectRecord.getValue();
                    ObjectMapper objectMapper = new ObjectMapper();
                    VoucherOrderDto voucherOrder = objectMapper.readValue(objectMapper.writeValueAsString(value), VoucherOrderDto.class);
                    voucherOrderServiceBean.validAndInsert(voucherOrder.getVoucherId(),voucherOrder.getUserId(),voucherOrder.getId());
                    streamOperations.acknowledge(STREAM_KEY, STREAM_GROUP1, objectRecord.getId());

                }catch (Exception e1){

                    log.error("发生了异常",e1);
                    break;
                }
            }
        });

        executorService.execute(()->{
            VoucherOrderService voucherOrderServiceBean = applicationContext.getBean(VoucherOrderService.class);
            while (true) {
                try {

                    List<MapRecord<String, Object, Object>> info = streamOperations.read(
                            Consumer.from(STREAM_GROUP1, "consumer1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                            StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed())
                    );
                    if (info==null || info.isEmpty()) {
                        log.info("暂无消费信息");
                        continue;
                    }
                    MapRecord<String, Object, Object> objectRecord = info.get(0);
                    Map<Object, Object> value = objectRecord.getValue();
                    ObjectMapper objectMapper = new ObjectMapper();
                    VoucherOrderDto voucherOrder = objectMapper.readValue(objectMapper.writeValueAsString(value), VoucherOrderDto.class);
                    voucherOrderServiceBean.validAndInsert(voucherOrder.getVoucherId(),voucherOrder.getUserId(),voucherOrder.getId());
                    streamOperations.acknowledge(STREAM_KEY, STREAM_GROUP1, objectRecord.getId());

                }catch (Exception e){
                    log.error("消费异常",e);
                    while (true) {
                        try {

                            List<MapRecord<String, Object, Object>> info = streamOperations.read(
                                    Consumer.from(STREAM_GROUP1, "consumer1"),
                                    StreamReadOptions.empty().count(1),
                                    StreamOffset.create(STREAM_KEY, ReadOffset.from("0"))
                            );
                            if (info==null || info.isEmpty()) {
                                log.info("没有找到异常队列里的消息");
                                break;
                            }
                            MapRecord<String, Object, Object> objectRecord = info.get(0);
                            Map<Object, Object> value = objectRecord.getValue();
                            ObjectMapper objectMapper = new ObjectMapper();
                            VoucherOrderDto voucherOrder = objectMapper.readValue(objectMapper.writeValueAsString(value), VoucherOrderDto.class);
                            voucherOrderServiceBean.validAndInsert(voucherOrder.getVoucherId(),voucherOrder.getUserId(),voucherOrder.getId());
                            streamOperations.acknowledge(STREAM_KEY, STREAM_GROUP1, objectRecord.getId());

                        }catch (Exception e1){

                            log.error("处理异常时，又发生了异常",e1);
                            break;
                        }
                    }

                }
            }
        });

    }




    private static final DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();

    static {
        defaultRedisScript.setLocation(new ClassPathResource("seckillStock.lua"));
        defaultRedisScript.setResultType(Long.class);
    }

    public Result asyncCreateVoucherOrder(Long voucherId, Long userId) {
        BigInteger order = idGenerate.getGloballyUniqueId("order");

        Long execute = stringRedisTemplate.execute(defaultRedisScript, Arrays.asList(RedisGlobalPrefixKey.SECKILL_STOCK,STREAM_KEY), voucherId.toString(), userId.toString(),order.toString());
        int i = execute.intValue();
        if (i != 0) {
            return i == 1 ? Result.fail("库存不足") : Result.fail("不可重复下单");
        }


        return Result.ok("抢单成功");
    }


}
