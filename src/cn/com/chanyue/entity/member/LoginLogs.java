package cn.com.chanyue.entity.member;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录日志表
 * 
 * @author Howe
 * 
 */
public class LoginLogs implements Serializable {

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
	 * 登录IP
	 */
	private String ip;
	/**
	 * 登录时间
	 */
	private Date loginDate;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
