package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 游戏服表
 * 
 * @author Howe
 *
 */
public class Server implements Serializable {

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
	/**
	 * 区ID
	 */
	private Integer aid;
	/**
	 * 区服编码
	 */
	private String num;
	/**
	 * 服务器名称
	 */
	private String sName;

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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String sName) {
		this.sName = sName;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}
}
