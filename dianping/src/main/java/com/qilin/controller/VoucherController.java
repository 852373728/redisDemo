package com.qilin.controller;

import com.qilin.dto.Result;
import com.qilin.entities.Voucher;
import com.qilin.service.VoucherService;
import com.qilin.vo.VoucherVO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private VoucherService voucherService;


    @PostMapping("/saveVoucherAndSeckill")
    public Result saveVoucherAndSeckill(@RequestBody VoucherVO voucherVO)
    {
        return voucherService.saveVoucherAndSeckill(voucherVO);
    }



}
