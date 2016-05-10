package cn.com.chanyue.pay;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.DateUtil;
import cn.com.chanyue.common.tool.Pkipair;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.service.chong.BuyService;

/**
 * 快钱支付
 * 
 * @author Howe
 *
 */
@Controller
public class Kuaiqian {

	/**
	 * 商户号
	 */
	public final static String MERCHANTACCTID = "1002401553601";
	/**
	 * 密钥
	 */
	public final static String KEY = "6LH9UQGDZZGJZRA5";
	public final static String K = "562008";

	/**
	 * 支付接口
	 */
	public final static String PAYURL = "https://www.99bill.com/gateway/recvMerchantInfoAction.htm";

	/**
	 * 字符集
	 */
	public final static String INPUTCHARSET = "1";

	/**
	 * 支付返回地址
	 */
	public final static String PAGEURL = "http://www.fakafa.com/kuaiqian/return.jspx";

	/**
	 * 接收充值通知接口
	 */
	public final static String BGURL = "http://www.fakafa.com/kuaiqian/notify.jspx";

	/**
	 * 签名方式
	 */
	public final static String SIGNTYPE = "4";
	/**
	 * 币种
	 */
	public final static String FEE_TYPE = "1";
	/**
	 * 接口版本
	 */
	public final static String VERSION = "v2.0";
	/**
	 * 显示语言
	 */
	public final static String LANGUAGE = "1";

	/**
	 * 
	 * @param order
	 * @param payway
	 * @param ip
	 * @return
	 */
	public String create(Order order, Payway payway, Payment payment) {

		// 字符集
		String inputCharset = INPUTCHARSET;
		// 接叐支付结果的页面地址
		String pageUrl = PAGEURL;
		// 服务器接叐支付结果的后台地址
		String bgUrl = BGURL;
		// 网关版本
		String version = VERSION;
		// 网关页面显示诧言种类
		String language = LANGUAGE;
		// 签名类型
		String signType = SIGNTYPE;
		// 人民币账号
		String merchantAcctId = MERCHANTACCTID;
		// 付款人IP
		String payerIP = payment.getIp();
		// 商户订单号
		Integer orderId = payment.getId();
		// 商户订单金额
		Integer orderAmount = (int) (payment.getTotal() * 100);
		// 商户订单提交时间
		String orderTime = DateUtil.dateToString(new Date(),
				DateUtil.FORMAT_FOUR);
		// 商品名称
		String productName = order.getType() + order.getFace() + "元（"
				+ order.getStandard() + "）×" + order.getQuantity()
				+ order.getGName() + order.getPName() + "-" + order.getOName()
				+ "-" + order.getAName() + order.getNum() + "区【"
				+ order.getSName() + "】";
		// 扩展字段1
		Integer ext1 = order.getId();
		// 支付方式
		String payType = payway.getCode();
		// 同一订单禁止重复提交标志
		String redoFlag = "1";
		// 交易超时时间 秒
		String orderTimeOut = "1800";
		
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("inputCharset=").append(inputCharset);
		sbuffer.append("&pageUrl=").append(pageUrl);
		sbuffer.append("&bgUrl=").append(bgUrl);
		sbuffer.append("&version=").append(version);
		sbuffer.append("&language=").append(language);
		sbuffer.append("&signType=").append(signType);
		sbuffer.append("&merchantAcctId=").append(merchantAcctId);
		sbuffer.append("&payerIP=").append(payerIP);
		sbuffer.append("&orderId=").append(orderId);
		sbuffer.append("&orderAmount=").append(orderAmount);
		sbuffer.append("&orderTime=").append(orderTime);
		sbuffer.append("&productName=").append(CodeCryption.encode("URL", productName));
		sbuffer.append("&ext1=").append(ext1);
		sbuffer.append("&payType=").append(payType);
		sbuffer.append("&redoFlag=").append(redoFlag);
		sbuffer.append("&orderTimeOut=").append(orderTimeOut);
		
		return "/redirect.jspx?code="				
		+ CodeCryption.encode(
				"BASE64",
				PAYURL
						+ "?"
						+ sbuffer.toString()
						+ "&signMsg="
						+ Pkipair.encode(sbuffer.toString()));
	}

	@Autowired
	private BuyService buyService;
}
