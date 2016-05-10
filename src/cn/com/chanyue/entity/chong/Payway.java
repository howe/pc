package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 支付方式表
 * 
 * @author Howe
 *
 */
public class Payway implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 支付方式
	 */
	private String name;
	/**
	 * 支付网关
	 */
	private String gate;
	/**
	 * 支付编码
	 */
	private String code;
	/**
	 * 费率
	 */
	private Double rate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
}
