package cn.com.chanyue.common.tool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import cn.com.chanyue.pay.Kuaiqian;

/**
 * 快钱加密类
 * @author Howe
 *
 */
public class Pkipair {
	
	
	/**
	 * 加密
	 * @param signMsg
	 * @return
	 */
	public static String encode(String signMsg) {

		String base64 = "";
		try {
			// 密钥仓库
			KeyStore ks = KeyStore.getInstance("PKCS12");

			// 读取密钥仓库
			FileInputStream ksfis = new FileInputStream("/home/webroot/99bill/99bill-rsa.pfx");
			System.out.println(ksfis.toString());
			
			// 读取密钥仓库（相对路径）
//			String file = Pkipair.class.getResource("99bill-rsa.pfx").getPath().replaceAll("%20", " ");
//			System.out.println(file);
//			
//			FileInputStream ksfis = new FileInputStream(file);
			
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);

			char[] keyPwd = Kuaiqian.K.toCharArray();
			System.out.println(Kuaiqian.KEY);
			ks.load(ksbufin, keyPwd);
			// 从密钥仓库得到私钥
			PrivateKey priK = (PrivateKey) ks.getKey("test-alias", keyPwd);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(signMsg.getBytes("utf-8"));
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			base64 = encoder.encode(signature.sign());
			
		} catch(FileNotFoundException e){
			System.out.println("文件找不到");
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("test = "+base64);
		return base64.replaceAll("\n", "");
	}
	
	/**
	 * 效验
	 * @param val
	 * @param msg
	 * @return
	 */
	public static boolean verify(String val, String msg) {
		boolean flag = false;
		try {
			//获得文件(绝对路径)
			InputStream inStream = new FileInputStream("/home/webroot/99bill/99bill.cert.rsa.cer");
			
			//获得文件(相对路径)
//			String file = Pkipair.class.getResource("99bill.cert.rsa.cer").toURI().getPath();
//			System.out.println(file);
//			FileInputStream inStream = new FileInputStream(file);
			
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
			//获得公钥
			PublicKey pk = cert.getPublicKey();
			//签名
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initVerify(pk);
			signature.update(val.getBytes());
			//解码
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			System.out.println(new String(decoder.decodeBuffer(msg)));
			flag = signature.verify(decoder.decodeBuffer(msg));
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("no");
		} 
		return flag;
	}
}