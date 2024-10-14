package com.qilin.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：tb_seckill_voucher
 * 表注释：秒杀优惠券表，与优惠券是一对一关系
*/
@Table(name = "tb_seckill_voucher")
public class SeckillVoucher {
    /**
     * 关联的优惠券的id
     */
    @Id
    @Column(name = "voucher_id")
    private Long voucherId;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 生效时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 失效时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取关联的优惠券的id
     *
     * @return voucherId - 关联的优惠券的id
     */
    public Long getVoucherId() {
        return voucherId;
    }

    /**
     * 设置关联的优惠券的id
     *
     * @param voucherId 关联的优惠券的id
     */
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
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
     * 获取生效时间
     *
     * @return beginTime - 生效时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置生效时间
     *
     * @param beginTime 生效时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取失效时间
     *
     * @return endTime - 失效时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置失效时间
     *
     * @param endTime 失效时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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