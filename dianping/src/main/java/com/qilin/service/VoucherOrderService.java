package com.qilin.service;

import com.qilin.dto.Result;
import com.qilin.entities.SeckillVoucher;
import com.qilin.entities.Voucher;
import com.qilin.entities.VoucherOrder;
import com.qilin.mapper.SeckillVoucherMapper;
import com.qilin.mapper.VoucherMapper;
import com.qilin.mapper.VoucherOrderMapper;
import com.qilin.utils.RedisDistributedLock;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class VoucherOrderService {

    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Resource
    private VoucherMapper voucherMapper;

    @Resource
    private SeckillVoucherMapper seckillVoucherMapper;

    @Resource
    private IdGenerate idGenerate;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private StringRedisTemplate stringRedisTemplate;




    public Result createVoucherOrder(Long voucherId,Long userId) {

        SeckillVoucher seckillVoucher = seckillVoucherMapper.selectByPrimaryKey(voucherId);
        Date now = new Date();
        if (seckillVoucher.getBeginTime().after(now)){
            return Result.fail("秒杀尚未开始");
        }
        if (seckillVoucher.getEndTime().before(now)){
            return Result.fail("秒杀已经结束");
        }

        if (seckillVoucher.getStock()<=0){
            return Result.fail("库存不足");
        }


        VoucherOrderService voucherOrderServiceBean = applicationContext.getBean(VoucherOrderService.class);
        //单体应用下的锁，利用jvm的synchronized
//        synchronized (userId.toString().intern()) {
//            return voucherOrderServiceBean.validAndInsert(voucherId, userId);
//        }
        //分布式锁
        RedisDistributedLock redisDistributedLock = new RedisDistributedLock(userId.toString(),stringRedisTemplate);
        boolean b = redisDistributedLock.tryLock(1200);
        if (!b){
            return Result.fail("获取锁失败");
        }
        try {
            return voucherOrderServiceBean.validAndInsert(voucherId, userId);
        }finally {
            redisDistributedLock.unlock();
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public Result validAndInsert(Long voucherId, Long userId) {
        Example example = new Example(VoucherOrder.class);
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("voucherId", voucherId);
        List<VoucherOrder> voucherOrders = voucherOrderMapper.selectByExample(example);
        if (!voucherOrders.isEmpty()){
            return Result.fail("不能重复下单");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("voucherId",voucherId);
        boolean b = seckillVoucherMapper.decreaseStock(params);
        if (!b){
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


}
