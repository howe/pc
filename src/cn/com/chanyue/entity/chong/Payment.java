package cn.com.chanyue.entity.chong;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付表
 * 
 * @author Howe
 *
 */
public class Payment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * UID
	 */
	private Integer uid;
	/**
	 * 订单编号
	 */
	private Integer orderId;
	/**
	 * 订单金额
	 */
	private Double total;
	/**
	 * 三方支付单号
	 */
	private String tsn;
	/**
	 * 银行类型
	 */
	private String bankType;
	/**
	 * 银行订单号
	 */
	private String bankBillno;
	/**
	 * 提交IP
	 */
	private String ip;
	/**
	 * 支付返回编码
	 */
	private String backCode;
	/**
	 * 实际支付金额
	 */
	private Double actual;
	/**
	 * 支付网关
	 */
	private String payGate;
	/**
	 * 支付方式
	 */
	private String payWay;
	/**
	 * 支付方式名称
	 */
	private String payName;
	/**
	 * 手续费
	 */
	private Double factorage;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 支付返回时间
	 */
	private Date backTime;
	/**
	 * 支付状态
	 */
	private String status;
	/**
	 * 备注
	 */
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getTsn() {
		return tsn;
	}

	public void setTsn(String tsn) {
		this.tsn = tsn;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public Double getActual() {
		return actual;
	}

	public void setActual(Double actual) {
		this.actual = actual;
	}

	public String getPayGate() {
		return payGate;
	}

	public void setPayGate(String payGate) {
		this.payGate = payGate;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Double getFactorage() {
		return factorage;
	}

	public void setFactorage(Double factorage) {
		this.factorage = factorage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankBillno() {
		return bankBillno;
	}

	public void setBankBillno(String bankBillno) {
		this.bankBillno = bankBillno;
	}
}
