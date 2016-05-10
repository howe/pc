package cn.com.chanyue.entity.member;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码表
 * 
 * @author Howe
 * 
 */
public class Captcha implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * UID
	 */
	private Integer uid;
	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 验证类型
	 */
	private String type;
	/**
	 * 接收方
	 */
	private String receive;
	/**
	 * 验证方式
	 */
	private String mode;
	/**
	 * 发送时间
	 */
	private Date sendTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

}
