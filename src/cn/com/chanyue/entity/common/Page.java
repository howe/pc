package cn.com.chanyue.entity.common;

import java.io.Serializable;

/**
 * 分页类
 * 
 * @author Howe
 * 
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 总数据数
	 */
	private Integer total;
	/**
	 * 分页数
	 */
	private Integer pageNum;
	/**
	 * 每页数据数
	 */
	private Integer num;
	/**
	 * UID
	 */
	private Integer uid;
	/**
	 * TMP
	 */
	private Integer tmp;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getTmp() {
		tmp = (pageNum - 1) * num;
		return tmp;
	}

}
