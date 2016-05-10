package cn.com.chanyue.entity.chong;

import java.io.Serializable;

/**
 * 运行平台表
 * 
 * @author Howe
 *
 */
public class Platform implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Integer id;
	/**
	 * 运行平台
	 */
	private String pName;
	/**
	 * 父级ID
	 */
	private Integer pid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPName() {
		return pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
}
