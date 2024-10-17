package com.qilin.service;

import com.qilin.dto.Result;
import com.qilin.entities.SeckillVoucher;
import com.qilin.entities.Voucher;
import com.qilin.mapper.SeckillVoucherMapper;
import com.qilin.mapper.VoucherMapper;
import com.qilin.utils.RedisGlobalPrefixKey;
import com.qilin.vo.VoucherVO;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class VoucherService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private VoucherMapper voucherMapper;

    @Resource
    private SeckillVoucherMapper seckillVoucherMapper;



    @Transactional
    public Result saveVoucherAndSeckill(VoucherVO voucherVO) {

        Voucher voucher = new Voucher();
        voucher.setShopId(1l);
        voucher.setTitle("200元代金券");
        voucher.setSubTitle("周一至周日均可使用");
        voucher.setRules("全场通用");
        voucher.setPayValue(200l);
        voucher.setActualValue(150L);
        voucher.setStatus((byte) 1);
        voucher.setType((byte) 1);
        voucher.setCreateTime(new Date());
        voucher.setUpdateTime(new Date());

        voucherMapper.insertSelective(voucher);

        SeckillVoucher seckillVoucher = new SeckillVoucher();
        seckillVoucher.setVoucherId(voucher.getId());
        seckillVoucher.setBeginTime(new Date());
        seckillVoucher.setStock(200);
        Instant instant =  LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
        seckillVoucher.setEndTime(new Date(instant.toEpochMilli()));
        seckillVoucher.setCreateTime(new Date());
        seckillVoucher.setUpdateTime(new Date());
        seckillVoucherMapper.insert(seckillVoucher);

        stringRedisTemplate.opsForValue().set(RedisGlobalPrefixKey.SECKILL_STOCK+seckillVoucher.getVoucherId(),seckillVoucher.getStock()+"");

        return Result.ok("添加完成");
    }
}
