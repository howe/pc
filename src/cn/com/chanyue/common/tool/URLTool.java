package cn.com.chanyue.common.tool;

/**
 * 网址长短管理
 * 
 * @author Howe
 * 
 */
public class URLTool {

	private static final String TOKEN = "1E05F045ED4FB9E99F93C513A9B47165";

	/**
	 * 长网址缩短
	 * 
	 * @param urlLong
	 * @return
	 */
	public static String long2Short(String urlLong) {

		if (urlLong.equals(""))
			return null;
		else {
			String tmp = HttpClientTool.get("http://ib5.cn/api/short?token="
					+ TOKEN + "&url=" + CodeCryption.encode("URL", urlLong));
			if (tmp.indexOf("result") >= 0)
				return ToolUtility.getValueFromJson(tmp, "shortUrl", "}");
			else
				return null;
		}
	}

	/**
	 * 长网址缩短
	 * 
	 * @param urlLong
	 * @param key
	 * @return
	 */
	public static String long2Short(String urlLong, String key) {

		if (urlLong.equals(""))
			return null;
		else {
			String tmp = HttpClientTool.get("http://ib5.cn/api/short?token="
					+ TOKEN + "&key=" + key + "&url="
					+ CodeCryption.encode("URL", urlLong));
			if (tmp.indexOf("result") >= 0)
				return ToolUtility.getValueFromJson(tmp, "shortUrl", "}");
			else
				return null;
		}
	}

	/**
	 * 短网址还原
	 * 
	 * @param urlShort
	 * @return
	 */
	public static String short2Long(String urlShort) {

		if (urlShort.equals(""))
			return null;
		else {
			String tmp = HttpClientTool.get("http://ib5.cn/api/long?token="
					+ TOKEN + "&url=" + CodeCryption.encode("URL", urlShort));
			if (tmp.indexOf("result") >= 0) {
				return ToolUtility.getValueFromJson(tmp, "longUrl", "}");
			} else
				return null;
		}
	}

	public static final void main(String args[]) {

//		 System.out.println(long2Short("http://www.qq.com/", "qq"));
//		 System.out.println(short2Long("http://ib5.cn/yMfe2u"));

	}

}
