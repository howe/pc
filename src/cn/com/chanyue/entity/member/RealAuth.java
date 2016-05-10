package cn.com.chanyue.entity.member;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实名信息表
 * 
 * @author Howe
 * 
 */
public class RealAuth implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * UID
	 */
	private Integer uid;
	/**
	 * 真实名
	 */
	private String realName;
	/**
	 * 身份证号码
	 */
	private String idCard;
	/**
	 * 实名状态
	 */
	private String status;
	/**
	 * 提交时间
	 */
	private Date addTime;
	/**
	 * 验证时间
	 */
	private Date authTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

}
