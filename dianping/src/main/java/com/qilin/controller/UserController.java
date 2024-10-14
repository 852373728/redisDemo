package com.qilin.controller;

import cn.hutool.core.util.RandomUtil;
import com.qilin.utils.ResponseResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/sendCode")
    public ResponseResult sendCode(@RequestParam("phone") String phone, HttpSession session){

        String code = RandomUtil.randomNumbers(6);
        return new ResponseResult(true,"",code);
    }

}
