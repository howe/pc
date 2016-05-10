package cn.com.chanyue.entity.chong;

import java.io.Serializable;
import java.util.Date;

/**
 * 首充号表
 * 
 * @author Howe
 *
 */
public class Hao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 运营商ID
	 */
	private Integer oid;
	/**
	 * 游戏ID
	 */
	private Integer gid;
	private Integer uid;
	private Integer orderId;
	private String gName;
	private String oName;
	private String aName;
	private String sName;
	private String num;
	private String remark;
	/**
	 * 区ID
	 */
	private Integer aid;
	/**
	 * 服ID
	 */
	private Integer sid;
	/**
	 * 游戏帐号
	 */
	private String account;
	/**
	 * 帐号密码
	 */
	private String pswd;
	/**
	 * 生成时间
	 */
	private Date creatTime;

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
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

	public String getGName() {
		return gName;
	}

	public void setGName(String gName) {
		this.gName = gName;
	}

	public String getOName() {
		return oName;
	}

	public void setOName(String oName) {
		this.oName = oName;
	}

	public String getAName() {
		return aName;
	}

	public void setAName(String aName) {
		this.aName = aName;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}