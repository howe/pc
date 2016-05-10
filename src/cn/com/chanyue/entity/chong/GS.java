package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 游戏展示页
 * 
 * @author Howe
 *
 */
public class GS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 游戏名称
	 */
	private String gName;
	/**
	 * 运行平台编码
	 */
	private Integer plid;
	private String pName;
	private Integer oid;
	private String oName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGName() {
		return gName;
	}
	public void setGName(String gName) {
		this.gName = gName;
	}
	public Integer getPlid() {
		return plid;
	}
	public void setPlid(Integer plid) {
		this.plid = plid;
	}
	public String getPName() {
		return pName;
	}
	public void setPName(String pName) {
		this.pName = pName;
	}
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getOName() {
		return oName;
	}
	public void setOName(String oName) {
		this.oName = oName;
	}
}
