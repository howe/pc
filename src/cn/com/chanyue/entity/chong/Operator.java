package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 游戏运营商表
 * 
 * @author Howe
 *
 */
public class Operator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 运营商名称
	 */
	private String oName;
	/**
	 * 游戏ID
	 */
	private Integer gid;
	/**
	 * 是否支持首充
	 */
	private String isF;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOName() {
		return oName;
	}

	public void setOName(String oName) {
		this.oName = oName;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getIsF() {
		return isF;
	}

	public void setIsF(String isF) {
		this.isF = isF;
	}
}
