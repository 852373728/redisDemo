package com.qilin.controller;


import com.qilin.dto.Result;
import com.qilin.service.VoucherOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/voucher-order")
public class VoucherOrderController {

    @Resource
    private VoucherOrderService voucherOrderService;
    @PostMapping("/seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId, @RequestParam("userId") Long userId) {
        Result voucherOrder = voucherOrderService.createVoucherOrder(voucherId,userId);
        return voucherOrder;
    }


    @PostMapping("/asyncSeckillVoucher/{id}")
    public Result asyncSeckillVoucher(@PathVariable("id") Long voucherId, @RequestParam("userId") Long userId) {
        Result voucherOrder = voucherOrderService.asyncCreateVoucherOrder(voucherId,userId);
        return voucherOrder;
    }
}
