package com.qilin.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 表名：tb_voucher_order
*/
@Table(name = "tb_voucher_order")
public class VoucherOrder {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 下单的用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 购买的代金券id
     */
    @Column(name = "voucher_id")
    private Long voucherId;

    /**
     * 支付方式 1：余额支付；2：支付宝；3：微信
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     */
    private Byte status;

    /**
     * 下单时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 核销时间
     */
    @Column(name = "use_time")
    private Date useTime;

    /**
     * 退款时间
     */
    @Column(name = "refund_time")
    private Date refundTime;

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
     * 获取下单的用户id
     *
     * @return userId - 下单的用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置下单的用户id
     *
     * @param userId 下单的用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取购买的代金券id
     *
     * @return voucherId - 购买的代金券id
     */
    public Long getVoucherId() {
        return voucherId;
    }

    /**
     * 设置购买的代金券id
     *
     * @param voucherId 购买的代金券id
     */
    public void setVoucherId(Long voucherId) {
        this.voucherId = voucherId;
    }

    /**
     * 获取支付方式 1：余额支付；2：支付宝；3：微信
     *
     * @return payType - 支付方式 1：余额支付；2：支付宝；3：微信
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置支付方式 1：余额支付；2：支付宝；3：微信
     *
     * @param payType 支付方式 1：余额支付；2：支付宝；3：微信
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     *
     * @return status - 订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     *
     * @param status 订单状态，1：未支付；2：已支付；3：已核销；4：已取消；5：退款中；6：已退款
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取下单时间
     *
     * @return createTime - 下单时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置下单时间
     *
     * @param createTime 下单时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付时间
     *
     * @return payTime - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取核销时间
     *
     * @return useTime - 核销时间
     */
    public Date getUseTime() {
        return useTime;
    }

    /**
     * 设置核销时间
     *
     * @param useTime 核销时间
     */
    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    /**
     * 获取退款时间
     *
     * @return refundTime - 退款时间
     */
    public Date getRefundTime() {
        return refundTime;
    }

    /**
     * 设置退款时间
     *
     * @param refundTime 退款时间
     */
    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
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