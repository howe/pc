package cn.com.chanyue.entity.chong;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 * 
 * @author Howe
 *
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 用户ID
	 */
	private Integer uid;
	/**
	 * 提交IP
	 */
	private String ip;
	private String juese;
	/**
	 * 订单来源
	 */
	private String source;
	/**
	 * 充值类型
	 */
	private String type;
	/**
	 * 价格ID
	 */
	private Integer pid;
	/**
	 * 运行平台编码
	 */
	private Integer plid;
	/**
	 * 运营平台
	 */
	private String pName;
	/**
	 * 面值
	 */
	private Integer face;
	/**
	 * 游戏ID
	 */
	private Integer gid;
	/**
	 * 游戏名称
	 */
	private String gName;
	/**
	 * 服编号
	 */
	private String num;
	/**
	 * 运营商ID
	 */
	private Integer oid;
	/**
	 * 运营商名称
	 */
	private String oName;
	/**
	 * 区ID
	 */
	private Integer aid;
	/**
	 * 区名称
	 */
	private String aName;
	/**
	 * 服ID
	 */
	private Integer sid;
	/**
	 * 服务器名称
	 */
	private String sName;
	/**
	 * 商品规格
	 */
	private String standard;
	/**
	 * 单价
	 */
	private Double price;
	/**
	 * 购买数量
	 */
	private Integer quantity;
	/**
	 * 充值帐号
	 */
	private String account;
	/**
	 * 帐号密码
	 */
	private String pswd;
	/**
	 * 联系手机
	 */
	private String mobile;
	/**
	 * 联系QQ
	 */
	private String qq;
	/**
	 * 下单时间
	 */
	private Date orderTime;
	/**
	 * 完成时间
	 */
	private Date finishTime;
	/**
	 * 支付成功时间
	 */
	private Date payTime;
	/**
	 * 订单状态
	 * 0未付款
	 * 1已付款
	 * 2已完成
	 * 3已取消
	 * 4已撤单
	 */
	private String status;
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPName() {
		return pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGName() {
		return gName;
	}

	public void setGName(String gName) {
		this.gName = gName;
	}

	public Integer getOid() {
		return oid;
	}
	
	public Integer getAid() {
		return aid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}
	
	public void setAid(Integer aid) {
		this.aid = aid;
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

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getFace() {
		return face;
	}

	public void setFace(Integer face) {
		this.face = face;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPlid() {
		return plid;
	}

	public void setPlid(Integer plid) {
		this.plid = plid;
	}
	public String getJuese() {
		return juese;
	}
	public void setJuese(String juese) {
		this.juese = juese;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}