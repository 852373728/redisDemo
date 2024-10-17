package com.qilin.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class VoucherOrderDto implements Serializable {

    private Long id;

    /**
     * 下单的用户id
     */
    private Long userId;

    /**
     * 购买的代金券id
     */
    private Long voucherId;
}
