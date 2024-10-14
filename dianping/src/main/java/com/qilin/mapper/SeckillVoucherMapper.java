package com.qilin.mapper;

import com.qilin.entities.SeckillVoucher;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

public interface SeckillVoucherMapper extends Mapper<SeckillVoucher> {
    boolean decreaseStock(Map<String, Object> params);
}