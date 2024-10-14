package com.qilin.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：tb_voucher
*/
@Table(name = "tb_voucher")
public class Voucher {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 商铺id
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 代金券标题
     */
    private String title;

    /**
     * 副标题
     */
    @Column(name = "sub_title")
    private String subTitle;

    /**
     * 使用规则
     */
    private String rules;

    /**
     * 支付金额，单位是分。例如200代表2元
     */
    @Column(name = "pay_value")
    private Long payValue;

    /**
     * 抵扣金额，单位是分。例如200代表2元
     */
    @Column(name = "actual_value")
    private Long actualValue;

    /**
     * 0,普通券；1,秒杀券
     */
    private Byte type;

    /**
     * 1,上架; 2,下架; 3,过期
     */
    private Byte status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商铺id
     *
     * @return shopId - 商铺id
     */
    public Long getShopId() {
        return shopId;
    }

    /**
     * 设置商铺id
     *
     * @param shopId 商铺id
     */
    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    /**
     * 获取代金券标题
     *
     * @return title - 代金券标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置代金券标题
     *
     * @param title 代金券标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取副标题
     *
     * @return subTitle - 副标题
     */
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * 设置副标题
     *
     * @param subTitle 副标题
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * 获取使用规则
     *
     * @return rules - 使用规则
     */
    public String getRules() {
        return rules;
    }

    /**
     * 设置使用规则
     *
     * @param rules 使用规则
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
     * 获取支付金额，单位是分。例如200代表2元
     *
     * @return payValue - 支付金额，单位是分。例如200代表2元
     */
    public Long getPayValue() {
        return payValue;
    }

    /**
     * 设置支付金额，单位是分。例如200代表2元
     *
     * @param payValue 支付金额，单位是分。例如200代表2元
     */
    public void setPayValue(Long payValue) {
        this.payValue = payValue;
    }

    /**
     * 获取抵扣金额，单位是分。例如200代表2元
     *
     * @return actualValue - 抵扣金额，单位是分。例如200代表2元
     */
    public Long getActualValue() {
        return actualValue;
    }

    /**
     * 设置抵扣金额，单位是分。例如200代表2元
     *
     * @param actualValue 抵扣金额，单位是分。例如200代表2元
     */
    public void setActualValue(Long actualValue) {
        this.actualValue = actualValue;
    }

    /**
     * 获取0,普通券；1,秒杀券
     *
     * @return type - 0,普通券；1,秒杀券
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置0,普通券；1,秒杀券
     *
     * @param type 0,普通券；1,秒杀券
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取1,上架; 2,下架; 3,过期
     *
     * @return status - 1,上架; 2,下架; 3,过期
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置1,上架; 2,下架; 3,过期
     *
     * @param status 1,上架; 2,下架; 3,过期
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return updateTime - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}