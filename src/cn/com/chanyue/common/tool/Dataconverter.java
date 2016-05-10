package cn.com.chanyue.common.tool;

/**
 * 数据转换
 * 
 * @author Howe
 *
 */
public class Dataconverter {
	
	public static final String KEY = "db4867ebb07003926a0d607d5ec719db";
	
	public static String getLeft(String sort){
		
		switch (sort) {
		case "A":	
			return "134px";
		case "B":	
			return "164px";
		case "C":	
			return "194px";
		case "D":	
			return "224px";
		case "E":	
			return "254px";
		case "F":	
			return "284px";
		case "G":	
			return "314px";
		case "H":	
			return "344px";
		case "I":	
			return "374px";
		case "J":	
			return "404px";
		case "K":	
			return "434px";
		case "L":	
			return "464px";
		case "M":	
			return "494px";
		case "N":	
			return "524px";
		case "O":	
			return "554px";
		case "P":	
			return "584px";
		case "Q":	
			return "614px";
		case "R":	
			return "644px";
		case "S":	
			return "674px";
		case "T":	
			return "704px";
		case "U":	
			return "734px";
		case "V":	
			return "764px";
		case "W":	
			return "794px";
		case "X":	
			return "824px";
		case "Y":	
			return "854px";
		case "Z":	
			return "884px";
		default:
			return "42px";
		}
		
	}
	
	public static String plid2Url(Integer id){
		switch (id){
			case 1:
			return "/apple.html";
			case 2:
			return "/android.html";
			case 3:
			return "/online.html";
			case 4:
			return "/web.html";
			case 5:
			return "/wp.html";
			default:
			return "/";
		}
	}
	
	public static String plid2Buy(String id){
		
		switch (id) {
		case "1":
			return "/ibuy.jspx";
		case "2":
			return "/abuy3.jspx";
		case "3":
			return "/wbuy.jspx";
		case "4":
			return "/obuy.jspx";
		case "5":
			return "/wpbuy.jspx";
		default:
			return "/";
		}
	}
	
	public static Integer plid2Name(String name){		
		switch (name){
			case "苹果版":
			return 1;
			case "安卓版":
			return 2;
			case "端游":
			return 3;
			case "页游":
			return 4;
			case "WP版":
			return 5;
			default:
			return null;
		}
	}

	public static String changeTenpayBankType(String bank_type) {

		switch (bank_type.toUpperCase()) {
		case "BL":
			return "余额支付";
		case "ICBC":
			return "工商银行";
		case "CCB":
			return "建设银行";
		case "ABC":
			return "农业银行";
		case "CMB":
			return "招商银行";
		case "SPDB":
			return "上海浦发银行";
		case "SDB":
			return "深圳发展银行";
		case "CIB":
			return "兴业银行";
		case "BOB":
			return "北京银行";
		case "CEB":
			return "光大银行";
		case "CMBC":
			return "民生银行";
		case "CITIC":
			return "中信银行";
		case "GDB":
			return "广东发展银行";
		case "PAB":
			return "平安银行";			
		case "BOC":
			return "中国银行";
		case "COMM":
			return "交通银行";
		case "NJCB":
			return "南京银行";			
		case "NBCB":
			return "宁波银行";
		case "SRCB":
			return "上海农商";
		case "BEA":
			return "东亚银行";
		case "POSTGC":
			return "邮政储蓄";
		case "ICBCB2B":
			return "工商银行（企业版）";
		case "CMBB2B":
			return "招商银行（企业版）";
		case "CCBB2B":
			return "建设银行（企业版）";
		case "ABCB2B":
			return "农业银行（企业版）";
		case "SPDBB2B":
			return "浦发银行（企业版）";
		case "CEBB2B":
			return "光大银行（企业版）";
		default:
			return "未知";
		}
	}
	
	public static String changeKuaiqianBankType(String bankId) {

		switch (bankId.toUpperCase()) {
		case "BL":
			return "余额支付";
		case "GZCB":
			return "广州银行";
		case "CBHB":
			return "渤海银行";
		case "BJRCB":
			return "北京农商银行";
		case "ICBC":
			return "工商银行";
		case "CCB":
			return "建设银行";
		case "ABC":
			return "农业银行";
		case "CMB":
			return "招商银行";
		case "SPDB":
			return "上海浦发银行";
		case "SDB":
			return "深圳发展银行";
		case "CIB":
			return "兴业银行";
		case "HXB":
			return "华夏银行";
		case "BOB":
			return "北京银行";
		case "CEB":
			return "光大银行";
		case "CMBC":
			return "民生银行";
		case "CITIC":
			return "中信银行";
		case "GDB":
			return "广东发展银行";
		case "PAB":
			return "平安银行";			
		case "BOC":
			return "中国银行";
		case "BCOM":
			return "交通银行";
		case "NJCB":
			return "南京银行";			
		case "HSB":
			return "徽商银行";
		case "CZB":
			return "浙商银行";
		case "HZB":
			return "杭州银行";
		case "NBCB":
			return "宁波银行";
		case "SRCB":
			return "上海农商";
		case "UPOP":
			return "银联在线支付";
		case "BEA":
			return "东亚银行";
		case "JSB":
			return "江苏银行";
		case "DLB":
			return "大连银行";
		case "SHB":
			return "上海银行";
		case "PSBC":
			return "邮政储蓄";
		case "ICBC_B2B":
			return "工商银行（企业版）";
		case "CMB_B2B":
			return "招商银行（企业版）";
		case "CCB_B2B":
			return "建设银行（企业版）";
		case "ABC_B2B":
			return "农业银行（企业版）";
		case "BOC_B2B":
			return "中国银行（企业版）";
		case "BCOM_B2B":
			return "交通银行（企业版）";
		case "CIB_B2B":
			return "兴业银行（企业版）";
		case "SPDB_B2B":
			return "浦发银行（企业版）";
		case "CEB_B2B":
			return "光大银行（企业版）";
		default:
			return "未知";
		}
	}
}
