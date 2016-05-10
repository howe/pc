package cn.com.chanyue.entity.member;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户操作日志表
 * 
 * @author Howe
 * 
 */
public class OperLogs implements Serializable {

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
	 * 操作IP
	 */
	private String ip;
	/**
	 * 操作时间
	 */
	private Date operDate;
	/**
	 * 动作
	 */
	private String action;

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

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
