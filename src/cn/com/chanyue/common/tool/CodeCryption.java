package cn.com.chanyue.common.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/** 
 * 加解密工具
 * @author howe
 *
 */
public class CodeCryption {

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 加密字符串
	 * 
	 * @param algorithm
	 *            BASE64 MD5 SHA1 URL
	 * @param str
	 */
	public static String encode(String algorithm, String str) {
		if (StringUtils.isBlank(str))
			return null;		
		try {
			if(algorithm.toUpperCase().equals("URL"))
				return URLEncoder.encode(str, "UTF-8");
			if (algorithm.toUpperCase().equals("BASE64"))
				return encodeBase64(str);
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 * 
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);

		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}

		return buf.toString();
	}

	/**
	 * BASE64编码
	 * 
	 * @param str
	 * @return
	 */
	private static String encodeBase64(String str) {
		if (StringUtils.isBlank(str))
			return null;
		try {
			return new String(
					org.apache.commons.codec.binary.Base64.encodeBase64(str
							.getBytes("UTF-8"))).replaceAll("\r", "")
					.replaceAll("\n", "");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

	}
	private static String encodeUrl(String str) {
		if (StringUtils.isBlank(str))
			return null;
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * BASE64解码
	 * 
	 * @param str
	 * @return
	 */
	private static String decodeBase64(String str) {
		if (StringUtils.isBlank(str))
			return null;
		try {
			return new String(
					org.apache.commons.codec.binary.Base64.decodeBase64(str
							.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密字符串
	 * 
	 * @param algorithm
	 *            BASE64 URL
	 * @param str
	 * @return
	 */
	public static String decode(String algorithm, String str) {

		if (StringUtils.isBlank(str))
			return null;
		if (algorithm.toUpperCase().equals("BASE64"))
			return decodeBase64(str);
		else if (algorithm.toUpperCase().equals("URL"))
			return encodeUrl(str);
		else
			return null;
	}

	public static void main(String[] args){

		System.out.println("MD5  :" + CodeCryption.encode("MD5", "123456"));
		System.out.println("SHA1 :" + CodeCryption.encode("SHA1", "123456"));
		System.out.println("BASE64 :"
				+ CodeCryption.encode("BASE64", "123456"));
		System.out.println("BASE64 :" + decode("BASE64", "MTIzNDU2"));
		System.out.println("URL :" + encode("URL", "||||||||||"));
		System.out.println("URL :" + decode("URL", "%e8%92%8b%e6%b5%a9"));
		System.out.println("Blowfish :" + Blowfish.encode("howechiang@gmail+++++----.com", "12345678910"));
		System.out.println("Blowfish :" + Blowfish.decode("011d00aa25946a7b37adfbb63d6748de47f80b0c3e3d5f14e97244086daf896ad4647a", "12345678910"));
	}

}
