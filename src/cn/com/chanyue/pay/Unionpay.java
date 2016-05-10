package cn.com.chanyue.pay;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

import cn.com.chanyue.common.tool.DateUtil;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.service.chong.BuyService;

/**
 * 银联在线支付
 * @author Howe
 *
 */
@Controller
public class Unionpay {

	// 商户号码
	public final static String MERID = "898320148160182";
	public final static String KEY = "WIWU4YUI4YI245TC45GYGW4UET8734";
	// 版本号
	public final static String VERSION = "5.0.0";
	// 字符集编码
	public final static String ENCODING = "UTF-8";
	// 前台接收地址
	public final static String FRONTURL = "http://www.fakafa.com/unionpay/return.jspx";
	// 后台接收地址
	public final static String BACKURL = "http://www.fakafa.com/unionpay/notify.jspx";

	/**
	 * 
	 * @param order
	 * @param payway
	 * @param ip
	 * @return
	 */
	public String create(Order order, Payway payway, Payment payment){
		
		SDKConfig.getConfig().loadPropertiesFromSrc();
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		String version = VERSION;
		data.put("version", version);
		// 字符集编码
		String encoding = ENCODING;
		data.put("encoding", encoding);
		// 签名方法
		String signMethod = "01";
		data.put("signMethod", signMethod);
		// 交易类型
		String txnType = "01";
		data.put("txnType", txnType);
		// 交易子类型
		String txnSubType = "01";
		data.put("txnSubType", txnSubType);
		// 业务类型
		String bizType = "000201";
		data.put("bizType", bizType);
		// 渠道类型
		String channelType = "07";
		data.put("channelType", channelType);
		// 前台接收地址
		String frontUrl = FRONTURL;
		data.put("frontUrl", frontUrl);
		// 后台接收地址
		String backUrl = BACKURL;
		data.put("backUrl", backUrl);
		// 接入类型
		String accessType = "0";
		data.put("accessType", accessType);
		// 商户号码
		String merId = MERID;
		data.put("merId", merId);
		// 订单号
		Integer orderId = payment.getId();
		data.put("orderId", orderId.toString());
		// 订单发送时间
		String txnTime = DateUtil.dateToString(new Date(), DateUtil.FORMAT_FOUR);
		data.put("txnTime", txnTime);
		// 交易金额 分
		Integer txnAmt = (int) (payment.getTotal() * 100);
		data.put("txnAmt", txnAmt.toString());
		// 交易币种
		String currencyCode = "156";
		data.put("currencyCode", currencyCode);
		// 订单超时时间
		String orderTimeout = "1800000";
		data.put("orderTimeout", orderTimeout);
		// 持卡人IP
		String customerIp = payment.getIp();
		data.put("customerIp", customerIp);
		String signature = "";
		
		String html = createHtml(requestFrontUrl, data);
		System.out.println(html);

		Map<String, String> resmap = submitUrl(data, requestFrontUrl);

		System.out.println(resmap.toString());
		return "/";
	}
	
	public static String createHtml(String action, Map<String, String> hiddens) {
		StringBuffer sf = new StringBuffer();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + action
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			Set<Entry<String, String>> set = hiddens.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		return sf.toString();
	}

	public static Map<String, String> submitUrl(
			Map<String, String> submitFromData,String requestUrl) {
		String resultString = "";
		System.out.println("requestUrl====" + requestUrl);
		System.out.println("submitFromData====" + submitFromData.toString());
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
		try {
			int status = hc.send(submitFromData, ENCODING);
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, ENCODING)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
			// 打印返回报文
			System.out.println("打印返回报文：" + resultString);
		}
		return resData;
	}
	
	@Autowired
	private BuyService buyService;
}
