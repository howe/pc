package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 面值表
 * 
 * @author Howe
 *
 */
public class Par implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 面值
	 */
	private Integer face;
	/**
	 * 描述
	 */
	private String standard;
	/**
	 * 游戏ID
	 */
	private Integer gid;
	/**
	 * 运营商ID
	 */
	private Integer oid;
	private Integer tid;
	/**
	 * 售价
	 */
	private Double price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFace() {
		return face;
	}

	public void setFace(Integer face) {
		this.face = face;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}
	
	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}
}