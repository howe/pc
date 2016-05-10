package cn.com.chanyue.entity.chong;

import java.io.Serializable;
import java.util.Date;

/**
 * 游戏表
 * 
 * @author Howe
 *
 */
public class Game implements Serializable {

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
	private String pinyin;
	/**
	 * 运行平台编码
	 */
	private Integer plid;
	private String pName;
	/**
	 * 拼音首字母
	 */
	private String py;
	/**
	 * 购买页模板代码
	 */
	private Integer template;
	/**
	 * 是否热门游戏
	 */
	private String isHot;
	private String isTop;
	/**
	 * 游戏开发商
	 */
	private String developers;
	/**
	 * 添加时间
	 */
	private Date addDate;
	/**
	 * 上架状态
	 */
	private String shelf;
	/**
	 * 首充
	 */
	private String t1;
	/**
	 * 首充代充
	 */
	private String t2;
	/**
	 * 代充
	 */
	private String t3;
	/**
	 * 金币 元宝
	 */
	private String t4;
	/**
	 * 月卡 点卡
	 */
	private String t5;
	
	/**
	 * 充值方式ID
	 */
	private Integer cid;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public String getT3() {
		return t3;
	}

	public void setT3(String t3) {
		this.t3 = t3;
	}

	public String getT4() {
		return t4;
	}

	public void setT4(String t4) {
		this.t4 = t4;
	}

	public String getT5() {
		return t5;
	}

	public void setT5(String t5) {
		this.t5 = t5;
	}

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
	
	public String getPName() {
		return pName;
	}

	public void setPName(String pName) {
		this.pName = pName;
	}

	public Integer getPlid() {
		return plid;
	}

	public void setPlid(Integer plid) {
		this.plid = plid;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public Integer getTemplate() {
		return template;
	}

	public void setTemplate(Integer template) {
		this.template = template;
	}

	public String getIsHot() {
		return isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public String getDevelopers() {
		return developers;
	}

	public void setDevelopers(String developers) {
		this.developers = developers;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}
