package cn.com.chanyue.entity.common;

import java.io.Serializable;

/**
 * 错误编码表
 * 
 * @author Howe
 * 
 */
public class Error implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 错误代码
	 */
	private String code;
	/**
	 * 说明
	 */
	private String explanation;
	/**
	 * 备注
	 */
	private String remark;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
