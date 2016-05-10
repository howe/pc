package cn.com.chanyue.entity.common;

/***
 * 邮件实体类
 * 
 * @author yangq(qi.yang.cn@gmail.com)
 */
public class Email {

	/** 发件人 */
	private String from;

	/** 发件人密码 */
	private String pwd;

	/** 收件人组 */
	private String[] recipients;

	/** 主题 */
	private String subject;
	
	/**
	 * 主题描述
	 */
	private String headValue;
	
	/**
	 * 主题名称
	 */
	private String headName;

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getHeadValue() {
		return headValue;
	}

	public void setHeadValue(String headValue) {
		this.headValue = headValue;
	}

	/** 内容类型 */
	private String contentType = "html";

	/** 内容 */
	private String content;

	/** 附件组 */
	private String[] file;
	
	/**
	 * 接收邮件地址
	 */
	private String[] receives = new String[] {"fakafa<info@fakafa.com>"};
	

	public String[] getReceives() {
		return receives;
	}

	public void setReceives(String[] receives) {
		this.receives = receives;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getFile() {
		return file;
	}

	public void setFile(String[] file) {
		this.file = file;
	}

}
