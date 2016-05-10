package cn.com.chanyue.entity.member;

import java.io.Serializable;
import java.util.Date;

/**
* 社交绑定表
*/
public class SocialBind implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * UID
	 */
	private Integer uid;
	/**
	 * OPENID
	 */
	private String openId;
	/**
	 * 社会化标识 weibo qq weixin
	 */
	private String appId;
	/**
	 * 绑定时间
	 */
	private Date bindDate;
	
	private String nickName;
	
	private String token;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Date getBindDate() {
		return bindDate;
	}
	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}