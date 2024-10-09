package com.qilin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String name;
    private Integer age;
    private List<UserAddress> userAddressList;

}
