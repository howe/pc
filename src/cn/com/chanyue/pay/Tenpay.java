package cn.com.chanyue.pay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.DateUtil;
import cn.com.chanyue.common.tool.HttpClientTool;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.service.chong.BuyService;

/**
 * 财付通支付
 * 
 * @author Howe
 *
 */
@Controller
public class Tenpay {

	/**
	 * 商户号
	 */
	public final static String PARTNER = "1217400601";
	/**
	 * 密钥
	 */
	public final static String KEY = "4b42e363906353abacde82abffe73971";
	/**
	 * 字符集
	 */
	public final static String INPUT_CHARSET = "UTF-8";
	/**
	 * 签名方式
	 */
	public final static String SIGN_TYPE = "MD5";
	/**
	 * 币种
	 */
	public final static String FEE_TYPE = "1";
	/**
	 * 接口版本
	 */
	public final static String SERVICE_VERSION = "1.0";
	/**
	 * 支付返回地址
	 */
	public final static String RETURN_URL = "http://www.fakafa.com/tenpay/return.jspx";
	/**
	 * 接收充值通知接口
	 */
	public final static String NOTIFY_URL = "http://www.fakafa.com/tenpay/notify.jspx";
	/**
	 * 支付接口
	 */
	public final static String PAY_URL = "https://gw.tenpay.com/gateway/pay.htm";
	/**
	 * 验证接口
	 */
	public final static String VERIFY_URL = "https://gw.tenpay.com/gateway/verifynotifyid.xml";
	/**
	 * 查询接口
	 */
	public final static String QUERY_URL = "https://gw.tenpay.com/gateway/normalorderquery.xml";

	/**
	 * 
	 * @param order
	 * @param payway
	 * @param ip
	 * @return
	 */
	public String create(Order order, Payway payway, Payment payment) {

		// 附加数据
		Integer attach = order.getId();
		// 银行类型
		String bank_type = payway.getCode();
		// 商品描述
		String body = order.getType() + order.getFace() + "元("
				+ order.getStandard() + ")×" + order.getQuantity() + "-"
				+ order.getGName() + order.getPName() + "-" + order.getOName() + "-" + order.getAName()
				+ order.getNum() + "服(" + order.getSName() + ")";
		// 币种
		String fee_type = FEE_TYPE;
		// 字符集
		String input_charset = INPUT_CHARSET;
		// 通知URL
		String notify_url = NOTIFY_URL;
		// 商户订单号
		Integer out_trade_no = payment.getId();
		// 商户号
		String partner = PARTNER;
		// 返回URL
		String return_url = RETURN_URL;
		// 接口版本
		String service_version = SERVICE_VERSION;
		// 签名方式
		String sign_type = SIGN_TYPE;
		// 用户IP
		String spbill_create_ip = payment.getIp();
		// 交易结束时间
		String time_expire = DateUtil.timestampToString(DateUtil
				.getTimestamp() + 1800, DateUtil.FORMAT_FOUR);
		// 交易起始时间
		String time_start = DateUtil.dateToString(new Date(), DateUtil.FORMAT_FOUR);
		// 总金额
		Integer total_fee = (int) (payment.getTotal() * 100);

		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("attach=").append(attach);
		sbuffer.append("&bank_type=").append(bank_type);
		sbuffer.append("&body=").append(body);
		sbuffer.append("&fee_type=").append(fee_type);
		sbuffer.append("&input_charset=").append(input_charset);
		sbuffer.append("&notify_url=").append(notify_url);
		sbuffer.append("&out_trade_no=").append(out_trade_no);
		sbuffer.append("&partner=").append(partner);
		sbuffer.append("&return_url=").append(return_url);
		sbuffer.append("&service_version=").append(service_version);
		sbuffer.append("&sign_type=").append(sign_type);
		sbuffer.append("&spbill_create_ip=").append(spbill_create_ip);
		sbuffer.append("&time_expire=").append(time_expire);
		sbuffer.append("&time_start=").append(time_start);
		sbuffer.append("&total_fee=").append(total_fee);

		return "/redirect.jspx?code="
				+ CodeCryption.encode(
						"BASE64",
						PAY_URL
								+ "?"
								+ sbuffer.toString()
								+ "&sign="
								+ CodeCryption.encode(
										"MD5",
										sbuffer.append("&key=").append(KEY)
												.toString()).toUpperCase());
	}

	/**
	 * 
	 * @param notify_id
	 * @return
	 */
	public Map<String, Object> verify(String id) {
		Map<String, Object> parms = new HashMap<String, Object>();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 签名方式
		String sign_type = SIGN_TYPE;
		// 接口版本
		String service_version = SERVICE_VERSION;
		// 字符集
		String input_charset = INPUT_CHARSET;
		// 商户号
		String partner = PARTNER;
		// 通知ID
		String notify_id = id;
		
		StringBuffer sb = new StringBuffer();
		sb.append("input_charset=").append(input_charset);
		sb.append("&notify_id=").append(notify_id);
		sb.append("&partner=").append(partner);
		sb.append("&service_version=").append(service_version);
		sb.append("&sign_type=").append(sign_type);
		sb.append("&key=").append(KEY);
		String sign = CodeCryption.encode("MD5", sb.toString()).toUpperCase();
		params.add(new BasicNameValuePair("input_charset", input_charset));
		params.add(new BasicNameValuePair("notify_id", notify_id));
		params.add(new BasicNameValuePair("partner", partner));
		params.add(new BasicNameValuePair("service_version", service_version));
		params.add(new BasicNameValuePair("sign_type", sign_type));
		params.add(new BasicNameValuePair("sign", sign));
		
		String xml = HttpClientTool.post(VERIFY_URL, params);
		if(ToolUtility.getValueFromXML(xml, "retcode").equals("0")){
			parms.put("attach", ToolUtility.getValueFromXML(xml, "attach"));
			parms.put("time_end", ToolUtility.getValueFromXML(xml, "time_end"));
			parms.put("partner", ToolUtility.getValueFromXML(xml, "partner"));
			parms.put("trade_state", ToolUtility.getValueFromXML(xml, "trade_state"));
			parms.put("transaction_id", ToolUtility.getValueFromXML(xml, "transaction_id"));
			parms.put("out_trade_no", ToolUtility.getValueFromXML(xml, "out_trade_no"));
			parms.put("total_fee", ToolUtility.getValueFromXML(xml, "total_fee"));
			parms.put("bank_type", ToolUtility.getValueFromXML(xml, "bank_type"));
		}
		return parms;
	}
	
	/**
	 * 
	 * @param paymentId
	 * @return
	 */
	public Map<String, Object> query(String id) {
		Map<String, Object> parms = new HashMap<String, Object>();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		// 签名方式
		String sign_type = SIGN_TYPE;
		// 接口版本
		String service_version = SERVICE_VERSION;
		// 字符集
		String input_charset = INPUT_CHARSET;
		// 商户号
		String partner = PARTNER;
		// 通知ID
		String out_trade_no = id;
		
		StringBuffer sb = new StringBuffer();
		sb.append("input_charset=").append(input_charset);
		sb.append("&out_trade_no=").append(out_trade_no);
		sb.append("&partner=").append(partner);
		sb.append("&service_version=").append(service_version);
		sb.append("&sign_type=").append(sign_type);
		sb.append("&key=").append(KEY);
		String sign = CodeCryption.encode("MD5", sb.toString()).toUpperCase();
		params.add(new BasicNameValuePair("input_charset", input_charset));
		params.add(new BasicNameValuePair("out_trade_no", out_trade_no));
		params.add(new BasicNameValuePair("partner", partner));
		params.add(new BasicNameValuePair("service_version", service_version));
		params.add(new BasicNameValuePair("sign_type", sign_type));
		params.add(new BasicNameValuePair("sign", sign));
		
		String xml = HttpClientTool.post(QUERY_URL, params);
		if(ToolUtility.getValueFromXML(xml, "retcode").equals("0")){
			parms.put("time_end", ToolUtility.getValueFromXML(xml, "time_end"));
			parms.put("partner", ToolUtility.getValueFromXML(xml, "partner"));
			parms.put("trade_state", ToolUtility.getValueFromXML(xml, "trade_state"));
			parms.put("transaction_id", ToolUtility.getValueFromXML(xml, "transaction_id"));
			parms.put("out_trade_no", ToolUtility.getValueFromXML(xml, "out_trade_no"));
			parms.put("total_fee", ToolUtility.getValueFromXML(xml, "total_fee"));
			parms.put("bank_type", ToolUtility.getValueFromXML(xml, "bank_type"));
		}
		return parms;
	}

	@Autowired
	private BuyService buyService;
}
