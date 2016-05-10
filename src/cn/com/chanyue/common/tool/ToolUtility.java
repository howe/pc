package cn.com.chanyue.common.tool;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 工具类
 * 
 * @author Howe
 * 
 */
public class ToolUtility {

	/**
	 * 验证URL格式
	 * 
	 * @param url
	 * @return
	 */
	public static boolean verifyUrl(String url) {

		String regex = "^(http|https|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern patt = Pattern.compile(regex);
		Matcher matcher = patt.matcher(url);
		boolean isMatch = matcher.matches();
		if (!isMatch)
			return false;
		else
			return true;
	}
	
	public static boolean verifyQuotes(String str){
		
		if(str.indexOf("\'")>=0 || str.indexOf("\"")>=0 || StringUtils.isBlank(str))
			return true;
		else
			return false;
	}
	
	public static boolean verifyKefuQQ(String qq){
		
		switch (qq) {
		case "40001fakafa":		
			return true;
		case "800065308":		
			return true;
		case "fakafa3355":		
			return true;
		case "fakafa3366":		
			return true;
		case "fakafa3377":		
			return true;
		default:
			return false;
		}
	}

	/**
	 * 获取IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		ip = StringUtils.isBlank(ip) ? "0.0.0.0" : ip;
		if (ip.indexOf(",") > 0)
			ip = ip.substring(0, ip.indexOf(","));
		return ip;
	}

	/**
	 * 获取COOKIE
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			return null;
		for (Cookie ck : cookies) {
			if (StringUtils.equalsIgnoreCase(name, ck.getName()))
				return ck.getValue();
		}
		return null;
	}

	/**
	 * 设置COOKIE
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 * @param all_sub_domain
	 */
	public static void setCookie(HttpServletRequest request,
			HttpServletResponse response, String name, String value,
			int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(".fakafa.com");

		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void deleteCookie(HttpServletRequest request,
			HttpServletResponse response, String name) {
		setCookie(request, response, name, "", 0);
	}

	/**
	 * 获取用户访问URL中的根域名
	 * 
	 * @param host
	 * @return
	 */
	public static String getDomainOfServerName(String host) {
		if (isIPAddr(host))
			return null;
		String[] names = StringUtils.split(host, '.');
		int len = names.length;
		if (len == 1)
			return null;
		if (len == 3)
			return makeup(names[len - 2], names[len - 1]);
		if (len > 3) {
			String dp = names[len - 2];
			if (dp.equalsIgnoreCase("com") || dp.equalsIgnoreCase("gov")
					|| dp.equalsIgnoreCase("net") || dp.equalsIgnoreCase("edu")
					|| dp.equalsIgnoreCase("org"))
				return makeup(names[len - 3], names[len - 2], names[len - 1]);
			else
				return makeup(names[len - 2], names[len - 1]);
		}
		return host;
	}

	/**
	 * 判断字符串是否是一个IP地址
	 * 
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddr(String addr) {
		if (StringUtils.isEmpty(addr))
			return false;
		String[] ips = StringUtils.split(addr, '.');
		if (ips.length != 4)
			return false;
		try {
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
					&& ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e) {
		}
		return false;
	}

	private static String makeup(String... ps) {
		StringBuilder s = new StringBuilder();
		for (int idx = 0; idx < ps.length; idx++) {
			if (idx > 0)
				s.append('.');
			s.append(ps[idx]);
		}
		return s.toString();
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isBlank(str.trim()))
			return false;
		else {
			Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
			return pattern.matcher(str).matches();
		}
	}

	/**
	 * SQL过滤
	 * 
	 * @param sql
	 * @return
	 */
	public static String TransactSQLInjection(String sql) {
		return sql.replaceAll(".*([';]+|(--)+).*", "").replaceAll(" ", "");
	}

	/**
	 * 比对时间差
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static boolean contrastTime(Date before, Date after) {

		if (before == null || after == null) {
			return false;
		} else {
			return after.after(before);
		}
	}

	/**
	 * 生成随机数字
	 * 
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getRandomNumber(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			buffer.append(base.charAt(number));
		}
		return buffer.toString();
	}

	/**
	 * 生成随机字符
	 * 
	 * @param length
	 *            长度
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			buffer.append(base.charAt(number));
		}
		return buffer.toString();
	}

	/**
	 * 判断EMAIL格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean verifyEmail(String email) {

		if (StringUtils.isBlank(email.trim()) || email.length() < 5)
			// #如果帐号小于5位，则肯定不可能为邮箱帐号eg: x@x.x
			return false;
		if (!email.contains("@")) // 判断是否含有@符号
			return false;// 没有@则肯定不是邮箱
		String[] tmp = email.split("@");
		if (tmp.length != 2) // # 数组长度不为2则包含2个以上的@符号，不为邮箱帐号
			return false;
		if (tmp[0].length() <= 0) // #@前段为邮箱用户名，自定义的话至少长度为1，其他暂不验证
			return false;
		if (tmp[1].length() < 3 || !tmp[1].contains("."))
			// # @后面为域名，位数小于3位则不为有效的域名信息
			// #如果后端不包含.则肯定不是邮箱的域名信息
			return false;
		else {
			if (tmp[1].substring(tmp[1].length() - 1).equals("."))
				// # 最后一位不能为.结束
				return false;
			String[] domain = tmp[1].split("\\.");
			// #将域名拆分 tm-sp.com 或者 .com.cn.xxx
			for (String str : domain) {
				if (str.length() <= 0)
					return false;
			}

		}
		return true;
	}

	/**
	 * 判断手机号码格式
	 * 
	 * @param mobi
	 * @return
	 */
	public static boolean verifyMobile(String mobi) {

		if (StringUtils.isBlank(mobi.trim()))
			return false;
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(145)|(170)|(147)|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(mobi);
		return matcher.matches();
	}

	/**
	 * 判断QQ格式
	 * 
	 * @param qq
	 * @return
	 */
	public static boolean verifyQQ(String qq) {
		if (StringUtils.isBlank(qq.trim()))
			return false;
		else if (qq.length() < 5 || qq.length() > 11)
			return false;
		else
			return isNumeric(qq);
	}

	/**
	 * 判断是否是汉字
	 * 
	 * @param str
	 *            字符
	 * @return
	 */
	public static boolean verifyChinese(String str) {
		if (StringUtils.isBlank(str.trim()))
			return false;
		for (int i = 0; i < str.length(); i++) {
			if (isChinese(str.charAt(i)))
				return true;
		}
		return false;
	}

	private static boolean isChinese(char a) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(a);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断身份证号码
	 * 
	 * @param idCard
	 *            身份证号码
	 * @return
	 */
	public static boolean verifyIdCard(String idCard) {
		if (StringUtils.isBlank(idCard.trim()))
			return false;
		if (idCard.length() == 15)
			idCard = uptoeighteen(idCard);
		if (idCard.length() != 18)
			return false;
		String verify = idCard.substring(17, 18);
		if (verify.equals(getVerify(idCard)))
			return true;
		return false;
	}

	// wi =2(n-1)(mod 11);加权因子
	static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
			2, 1 };
	// 校验码
	static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	static int[] ai = new int[18];

	// 15位转18位
	private static String uptoeighteen(String fifteen) {
		StringBuffer eighteen = new StringBuffer(fifteen);
		eighteen = eighteen.insert(6, "19");
		return eighteen.toString();
	}

	// 计算最后一位校验值
	private static String getVerify(String eighteen) {
		int remain = 0;
		if (eighteen.length() == 18)
			eighteen = eighteen.substring(0, 17);
		if (eighteen.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eighteen.substring(i, i + 1);
				ai[i] = Integer.valueOf(k);
			}
			for (int i = 0; i < 17; i++) {
				sum += wi[i] * ai[i];
			}
			remain = sum % 11;
		}
		return remain == 2 ? "X" : String.valueOf(vi[remain]);

	}

	/**
	 * 取Json字段值
	 * 
	 * @param jsonStr
	 *            JSON
	 * @param field
	 *            取值字段
	 * @param tailField
	 *            后一个字段 取值字段为结束字段填"}"
	 * @return
	 */
	public static String getValueFromJson(String jsonStr, String field,
			String tailField) {
		if (StringUtils.isBlank(jsonStr.trim())
				|| StringUtils.isBlank(field.trim())
				|| StringUtils.isBlank(tailField.trim()))
			return null;
		else {
			jsonStr = jsonStr.replaceAll("\"", "").replaceAll(" ", "");
			return jsonStr.substring(
					jsonStr.indexOf(field) + field.length() + 1,
					jsonStr.indexOf(tailField)).replaceAll(",", "");
		}
	}

	/**
	 * 取XML里字段值
	 * 
	 * @param xmlStr
	 *            XML
	 * @param field
	 *            取值字段
	 * @return 字段值
	 */
	public static String getValueFromXML(String xmlStr, String field) {

		if (xmlStr.indexOf("<" + field + ">") >= 0) {
			xmlStr = xmlStr.substring(
					xmlStr.indexOf("<" + field + ">") + field.length() + 2,
					xmlStr.indexOf("</" + field + ">"));
			if (field.equals("msg")) {
				xmlStr = replacebrack(xmlStr);
			}
		} else
			xmlStr = null;
		return xmlStr;
	}

	private static String replacebrack(String str) {
		str = str.replaceAll("<", "");
		str = str.replaceAll(">", "");
		return str;
	}

}