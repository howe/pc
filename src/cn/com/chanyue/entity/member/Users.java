package cn.com.chanyue.entity.member;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * 
 * @author Howe
 * 
 */
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * UID
	 */
	private Integer id;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * 安全码
	 */
	private String safeCode;
	/**
	 * 邮件验证标识
	 */
	private char mailVerify;
	/**
	 * 手机验证标识
	 */
	private char mobiVerify;
	/**
	 * 注册IP
	 */
	private String regIP;
	/**
	 * 注册时间
	 */
	private Date regDate;
	/**
	 * 登录次数
	 */
	private Integer loginTimes;
	/**
	 * 登录错误次数
	 */
	private Integer errorTimes;
	/**
	 * 加密钥
	 */
	private String salt;
	/**
	 * 密保问题
	 */
	private String question;
	/**
	 * 密保答案
	 */
	private String answer;
	
	private String nickName;
	
	private String avatar;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public char getMailVerify() {
		return mailVerify;
	}

	public void setMailVerify(char mailVerify) {
		this.mailVerify = mailVerify;
	}

	public char getMobiVerify() {
		return mobiVerify;
	}

	public void setMobiVerify(char mobiVerify) {
		this.mobiVerify = mobiVerify;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Integer getErrorTimes() {
		return errorTimes;
	}

	public void setErrorTimes(Integer errorTimes) {
		this.errorTimes = errorTimes;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSafeCode() {
		return safeCode;
	}

	public void setSafeCode(String safeCode) {
		this.safeCode = safeCode;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
