package com.qilin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {

    private String id;
    private String userId;
    private String province;
    private String city;
    private String area;
    private String detailedPosition;


}
