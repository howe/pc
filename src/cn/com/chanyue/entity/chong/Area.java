package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 游戏区表
 * 
 * @author Howe
 *
 */
public class Area implements Serializable {

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
	 * 区名称
	 */
	private String aName;

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

	public String getAName() {
		return aName;
	}

	public void setAName(String aName) {
		this.aName = aName;
	}
}
