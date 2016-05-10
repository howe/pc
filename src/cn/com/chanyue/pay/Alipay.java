package cn.com.chanyue.pay;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.HttpClientTool;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.service.chong.BuyService;

/**
 * 支付宝支付
 * 
 * @author Howe
 *
 */
@Controller
public class Alipay {

	/**
	 * 合作者身份ID
	 */
	public final static String PID = "2088711159125952";
	/**
	 * 密钥
	 */
	public final static String KEY = "txo7iogedapq0ea337szm46y5ys2wwmo";

	public final static String INPUT_CHARSET = "utf-8";

	public final static String SIGN_TYPE = "MD5";

	/**
	 * 页面跳转同步通知页面路径
	 */
	public final static String RETURN_URL = "http://www.fakafa.com/alipay/return.jspx";
	/**
	 * 服务器异步通知页面路径
	 */
	public final static String NOTIFY_URL = "http://www.fakafa.com/alipay/notify.jspx";

	public final static String ALIPAY_GATEWAY = "https://mapi.alipay.com/gateway.do";

	/**
	 * 
	 * @param order
	 * @param payway
	 * @param ip
	 * @return
	 */
	public String create(Order order, Payway payway, Payment payment) {

		// 参数编码字符集
		String _input_charset = INPUT_CHARSET;
		// 防钓鱼时间戳
		String anti_phishing_key = queryTimestamp();
		// 商品描述
		String body = order.getType() + order.getFace() + "元("
				+ order.getStandard() + ")×" + order.getQuantity() + "-"
				+ order.getGName() + order.getPName() + "-" + order.getOName()
				+ "-" + order.getAName() + order.getNum() + "服("
				+ order.getSName() + ")";
		// 客户端IP
		String exter_invoke_ip = payment.getIp();
		// 公用回传参数
		Integer extra_common_param = order.getId();
		// 超时时间
		String it_b_pay = "30m";
		// 服务器异步通知页面路径
		String notify_url = NOTIFY_URL;
		// 商户网站唯一订单号
		Integer out_trade_no = payment.getId();
		// 合作者身份ID
		String partner = PID;
		// 支付类型
		String payment_type = "1";
		// 页面跳转同步通知页面路径
		String return_url = RETURN_URL;
		// 卖家支付宝用户号
		String seller_id = PID;
		// 接口名称
		String service = "create_direct_pay_by_user";
		// 商品展示网址
		String show_url = "http://www.fakafa.com/game-" + order.getGid()
				+ ".html";
//		// 签名方式
//		String sign_type = SIGN_TYPE;
		// 商品名称
		String subject = "fakafa订单：" + order.getId();
		// 交易金额
		Double total_fee = payment.getTotal();

		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("_input_charset=").append(_input_charset);
		sbuffer.append("&anti_phishing_key=").append(anti_phishing_key);
		sbuffer.append("&body=").append(body);
		sbuffer.append("&exter_invoke_ip=").append(exter_invoke_ip);
		sbuffer.append("&extra_common_param=").append(extra_common_param);
		sbuffer.append("&it_b_pay=").append(it_b_pay);
		sbuffer.append("&notify_url=").append(notify_url);
		sbuffer.append("&out_trade_no=").append(out_trade_no);
		sbuffer.append("&partner=").append(partner);
		sbuffer.append("&payment_type=").append(payment_type);
		sbuffer.append("&return_url=").append(return_url);
		sbuffer.append("&seller_id=").append(seller_id);
		sbuffer.append("&service=").append(service);
		sbuffer.append("&show_url=").append(show_url);
		sbuffer.append("&subject=").append(subject);
		sbuffer.append("&total_fee=").append(total_fee);

		return "/redirect.jspx?code="
				+ CodeCryption.encode(
						"BASE64",
						ALIPAY_GATEWAY
								+ "?"
								+ sbuffer.toString()
								+ "&sign="
								+ CodeCryption.encode(
										"MD5",
										sbuffer.append(KEY)
												.toString()));
	}
	
	private String queryTimestamp(){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("service", "query_timestamp"));
		params.add(new BasicNameValuePair("partner", PID));
		params.add(new BasicNameValuePair("_input_charset", INPUT_CHARSET));
		String tmp = HttpClientTool.post(ALIPAY_GATEWAY, params);
		return ToolUtility.getValueFromXML(tmp, "encrypt_key");
	}
	
	
	public boolean verify(String id) {
		
		// 接口版本
		String service = "notify_verify";
		// 商户号
		String partner = PID;
		// 通知ID
		String notify_id = id;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("notify_id", notify_id));
		params.add(new BasicNameValuePair("partner", partner));
		params.add(new BasicNameValuePair("service", service));
		return Boolean.parseBoolean(HttpClientTool.post(ALIPAY_GATEWAY, params));
	}

	@Autowired
	private BuyService buyService;
}
