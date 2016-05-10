package cn.com.chanyue.web.chong;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.Blowfish;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.Dataconverter;
import cn.com.chanyue.common.tool.HttpClientTool;
import cn.com.chanyue.common.tool.LoginSessionManager;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.pay.Alipay;
import cn.com.chanyue.pay.Bankpay;
import cn.com.chanyue.pay.Cardpay;
import cn.com.chanyue.pay.Kuaiqian;
import cn.com.chanyue.pay.Tenpay;
import cn.com.chanyue.pay.Unionpay;
import cn.com.chanyue.service.chong.BuyService;
import cn.com.chanyue.service.member.UsersService;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;

@Controller
public class PaymentAct {

	/**
	 * 财付通返回
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/tenpay/return.jspx" })
	public String tenpayReturn(HttpServletRequest request, ModelMap model) {

		// 商家数据包

		String attach = RequestUtils.getQueryParam(request, "attach");
		// 银行订单号

		String bank_billno = RequestUtils.getQueryParam(request, "bank_billno");
		// 付款银行

		String bank_type = RequestUtils.getQueryParam(request, "bank_type");
		// 折扣价格

		String discount = RequestUtils.getQueryParam(request, "discount");
		// 币种

		String fee_type = RequestUtils.getQueryParam(request, "fee_type");
		// 字符集

		String input_charset = RequestUtils.getQueryParam(request,
				"input_charset");
		// 通知ID

		String notify_id = RequestUtils.getQueryParam(request, "notify_id");
		// 商户订单号

		String out_trade_no = RequestUtils.getQueryParam(request,
				"out_trade_no");
		// 商户号

		String partner = RequestUtils.getQueryParam(request, "partner");
		// 支付结果信息

		String pay_info = RequestUtils.getQueryParam(request, "pay_info");
		// 物品费用

		String product_fee = RequestUtils.getQueryParam(request, "product_fee");
		// 签名方式

		String sign_type = RequestUtils.getQueryParam(request, "sign_type");
		// 支付完成时间

		String time_end = RequestUtils.getQueryParam(request, "time_end");
		// 总金额

		String total_fee = RequestUtils.getQueryParam(request, "total_fee");
		// 交易模式

		String trade_mode = RequestUtils.getQueryParam(request, "trade_mode");
		// 交易状态

		String trade_state = RequestUtils.getQueryParam(request, "trade_state");
		// 财付通订单号

		String transaction_id = RequestUtils.getQueryParam(request,
				"transaction_id");
		// 物流费用

		String transport_fee = RequestUtils.getQueryParam(request,
				"transport_fee");
		// 签名

		String sign = RequestUtils.getQueryParam(request, "sign");

		StringBuffer sb = new StringBuffer();
		sb.append("attach=").append(attach);
		if (!StringUtils.isBlank(bank_billno))
			sb.append("&bank_billno=").append(bank_billno);
		sb.append("&bank_type=").append(bank_type);
		if (!StringUtils.isBlank(discount))
			sb.append("&discount=").append(discount);
		sb.append("&fee_type=").append(fee_type);
		if (!StringUtils.isBlank(input_charset))
			sb.append("&input_charset=").append(input_charset);
		sb.append("&notify_id=").append(notify_id);
		sb.append("&out_trade_no=").append(out_trade_no);
		sb.append("&partner=").append(partner);
		if (!StringUtils.isBlank(pay_info))
			sb.append("&pay_info=").append(pay_info);
		if (!StringUtils.isBlank(product_fee))
			sb.append("&product_fee=").append(product_fee);
		if (!StringUtils.isBlank(sign_type))
			sb.append("&sign_type=").append(sign_type);
		sb.append("&time_end=").append(time_end);
		sb.append("&total_fee=").append(total_fee);
		sb.append("&trade_mode=").append(trade_mode);
		sb.append("&trade_state=").append(trade_state);
		sb.append("&transaction_id=").append(transaction_id);
		if (!StringUtils.isBlank(transport_fee))
			sb.append("&transport_fee=").append(transport_fee);
		sb.append("&key=").append(Tenpay.KEY);
		String v = CodeCryption.encode("MD5", sb.toString()).toUpperCase();

		if (!sign.equals(v) || !partner.equals(Tenpay.PARTNER))
			return "redirect:/error-222.html";

		 Map<String, Object> parms = new Tenpay().verify(notify_id);
		 if (!parms.get("time_end").equals(time_end)
		 || !parms.get("partner").equals(partner)
		 || !parms.get("out_trade_no").equals(out_trade_no)
		 || !parms.get("total_fee").equals(total_fee)
		 || !parms.get("bank_type").equals(bank_type)
		 || !parms.get("transaction_id").equals(transaction_id)
		 || !parms.get("trade_state").equals(trade_state))
		 return "redirect:/error-998.html";

		Order order = buyService.getOrderById(Integer.parseInt(attach));
		order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
		model.put("order", order);

		model.addAttribute("transaction_id", transaction_id);
		model.addAttribute("pay_info", pay_info);
		model.addAttribute("bank_type",
				Dataconverter.changeTenpayBankType(bank_type));
		model.addAttribute("total", Double.parseDouble(total_fee) / 100);
		model.addAttribute("trade_state", trade_state.equals("0") ? "成功" : "失败");

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_PAYMENT,
				TemplateConstants.TPL_TENPAY_RETURN);
	}
	
	/**
	 * 支付宝返回
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/alipay/return.jspx" })
	public String alipayReturn(HttpServletRequest request, ModelMap model) {

		// 商品描述

		String body = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "body"));
		// 买家支付宝账号

		String buyer_email = CodeCryption.decode("URL",RequestUtils.getQueryParam(request, "buyer_email"));
		// 买家支付宝用户号

		String buyer_id = RequestUtils.getQueryParam(request, "buyer_id");
		// 接口名称

		String exterface = RequestUtils.getQueryParam(request, "exterface");
		// 公用回传参数

		String extra_common_param = RequestUtils.getQueryParam(request, "extra_common_param");
		// 字符集

		String is_success = RequestUtils.getQueryParam(request, "is_success");
		// 通知校验ID

		String notify_id = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "notify_id"));
		// 通知时间

		String notify_time = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "notify_time"));
		// 通知类型

		String notify_type = RequestUtils.getQueryParam(request, "notify_type");
		// 商户网站唯一订单号

		String out_trade_no = RequestUtils.getQueryParam(request, "out_trade_no");
		// 支付类型

		String payment_type = RequestUtils.getQueryParam(request, "payment_type");
		// 卖家支付宝账号

		String seller_email = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "seller_email"));
		// 卖家支付宝账户号

		String seller_id = RequestUtils.getQueryParam(request, "seller_id");
		// 商品名称

		String subject = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "subject"));
		// 交易金额

		String total_fee = RequestUtils.getQueryParam(request, "total_fee");
		// 支付宝交易号

		String trade_no = RequestUtils.getQueryParam(request, "trade_no");
		// 交易状态

		String trade_status = RequestUtils.getQueryParam(request, "trade_status");
		// 签名方式

		String sign_type = RequestUtils.getQueryParam(request, "sign_type");
		// 签名

		String sign = RequestUtils.getQueryParam(request, "sign");

//		StringBuffer sb = new StringBuffer();

//		sb.append("body=").append(body);

//		if (!StringUtils.isBlank(buyer_email))

//			sb.append("&buyer_email=").append(buyer_email);

//		if (!StringUtils.isBlank(buyer_id))

//			sb.append("&buyer_id=").append(buyer_id);

//		if (!StringUtils.isBlank(exterface))

//			sb.append("&exterface=").append(exterface);

//		if (!StringUtils.isBlank(extra_common_param))

//			sb.append("&extra_common_param=").append(extra_common_param);

//		sb.append("&is_success=").append(is_success);

//		if (!StringUtils.isBlank(notify_id))

//			sb.append("&notify_id=").append(notify_id);

//		if (!StringUtils.isBlank(notify_time))

//			sb.append("&notify_time=").append(notify_time);

//		if (!StringUtils.isBlank(notify_type))

//			sb.append("&notify_type=").append(notify_type);

//		if (!StringUtils.isBlank(out_trade_no))

//			sb.append("&out_trade_no=").append(out_trade_no);

//		if (!StringUtils.isBlank(payment_type))

//			sb.append("&payment_type=").append(payment_type);

//		if (!StringUtils.isBlank(seller_email))

//			sb.append("&seller_email=").append(seller_email);

//		if (!StringUtils.isBlank(seller_id))

//			sb.append("&seller_id=").append(seller_id);

//		if (!StringUtils.isBlank(subject))

//			sb.append("&subject=").append(subject);

//		if (!StringUtils.isBlank(total_fee))

//			sb.append("&total_fee=").append(total_fee);

//		if (!StringUtils.isBlank(trade_no))

//			sb.append("&trade_no=").append(trade_no);

//		if (!StringUtils.isBlank(trade_status))

//		sb.append(Alipay.KEY);

//		String v = CodeCryption.encode("MD5", sb.toString());


		if (!seller_id.equals(Alipay.PID))
			return "redirect:/error-222.html";
//

//		boolean verify = new Alipay().verify(notify_id);

//		System.out.println("verify: " + verify);

//		 if (verify)

//			 return "redirect:/error-998.html";

		
		if(!is_success.equals("T"))
			return "redirect:/error-998.html";
		
		Order order = buyService.getOrderById(Integer.parseInt(extra_common_param));
		order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
		model.put("order", order);

		model.addAttribute("trade_no", trade_no);
		model.addAttribute("buyer_email", buyer_email);
		model.addAttribute("total", total_fee);
		model.addAttribute("trade_status", trade_status.equals("TRADE_SUCCESS") ? "成功" : "失败");

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_PAYMENT,
				TemplateConstants.TPL_ALIPAY_RETURN);
	}
	
	/**
	 * 支付宝返回
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/bankpay/return.jspx" })
	public String bankPayReturn(HttpServletRequest request, ModelMap model) {

		// 网银流水
		String bank_seq_no = RequestUtils.getQueryParam(request, "bank_seq_no");
				
		// 商品描述
		String body = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "body"));
		
		// 买家支付宝账号
		String buyer_email = CodeCryption.decode("URL",RequestUtils.getQueryParam(request, "buyer_email"));
		
		// 买家支付宝用户号
		String buyer_id = RequestUtils.getQueryParam(request, "buyer_id");
		
		// 接口名称
		String exterface = RequestUtils.getQueryParam(request, "exterface");
		
		// 公用回传参数
		String extra_common_param = RequestUtils.getQueryParam(request, "extra_common_param");
		
		// 字符集
		String is_success = RequestUtils.getQueryParam(request, "is_success");
		
		// 通知校验ID
		String notify_id = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "notify_id"));
		
		// 通知时间
		String notify_time = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "notify_time"));
		
		// 通知类型
		String notify_type = RequestUtils.getQueryParam(request, "notify_type");
		
		// 商户网站唯一订单号
		String out_trade_no = RequestUtils.getQueryParam(request, "out_trade_no");
		
		// 支付类型
		String payment_type = RequestUtils.getQueryParam(request, "payment_type");
		
		// 卖家支付宝账号
		String seller_email = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "seller_email"));
		
		// 卖家支付宝账户号
		String seller_id = RequestUtils.getQueryParam(request, "seller_id");
		
		// 商品名称
		String subject = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "subject"));
		
		// 交易金额
		String total_fee = RequestUtils.getQueryParam(request, "total_fee");
		
		// 支付宝交易号
		String trade_no = RequestUtils.getQueryParam(request, "trade_no");
		// 交易状态
		String trade_status = RequestUtils.getQueryParam(request, "trade_status");
		
		// 签名方式
		String sign_type = RequestUtils.getQueryParam(request, "sign_type");
		
		// 签名
		String sign = RequestUtils.getQueryParam(request, "sign");

//		StringBuffer sb = new StringBuffer();

//		sb.append("body=").append(body);

//		if (!StringUtils.isBlank(buyer_email))

//			sb.append("&buyer_email=").append(buyer_email);

//		if (!StringUtils.isBlank(buyer_id))

//			sb.append("&buyer_id=").append(buyer_id);

//		if (!StringUtils.isBlank(exterface))

//			sb.append("&exterface=").append(exterface);

//		if (!StringUtils.isBlank(extra_common_param))

//			sb.append("&extra_common_param=").append(extra_common_param);

//		sb.append("&is_success=").append(is_success);

//		if (!StringUtils.isBlank(notify_id))

//			sb.append("&notify_id=").append(notify_id);

//		if (!StringUtils.isBlank(notify_time))

//			sb.append("&notify_time=").append(notify_time);

//		if (!StringUtils.isBlank(notify_type))

//			sb.append("&notify_type=").append(notify_type);

//		if (!StringUtils.isBlank(out_trade_no))

//			sb.append("&out_trade_no=").append(out_trade_no);

//		if (!StringUtils.isBlank(payment_type))

//			sb.append("&payment_type=").append(payment_type);

//		if (!StringUtils.isBlank(seller_email))

//			sb.append("&seller_email=").append(seller_email);

//		if (!StringUtils.isBlank(seller_id))

//			sb.append("&seller_id=").append(seller_id);

//		if (!StringUtils.isBlank(subject))

//			sb.append("&subject=").append(subject);

//		if (!StringUtils.isBlank(total_fee))

//			sb.append("&total_fee=").append(total_fee);

//		if (!StringUtils.isBlank(trade_no))

//			sb.append("&trade_no=").append(trade_no);

//		if (!StringUtils.isBlank(trade_status))

//		sb.append(Alipay.KEY);

//		String v = CodeCryption.encode("MD5", sb.toString());


		if (!seller_id.equals(Alipay.PID))
			return "redirect:/error-222.html";
//

//		boolean verify = new Alipay().verify(notify_id);

//		System.out.println("verify: " + verify);

//		 if (verify)

//			 return "redirect:/error-998.html";

		
		if(!is_success.equals("T"))
			return "redirect:/error-998.html";
		
		Order order = buyService.getOrderById(Integer.parseInt(extra_common_param));
		order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
		model.put("order", order);

		Payment payment = buyService.getPaymentById(Integer.parseInt(out_trade_no));
		model.put("payment", payment);
		model.addAttribute("trade_no", trade_no);
		model.addAttribute("bank_seq_no", bank_seq_no);
		model.addAttribute("buyer_email", buyer_email);
		model.addAttribute("total", total_fee);
		model.addAttribute("trade_status", trade_status.equals("TRADE_SUCCESS") ? "成功" : "失败");

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_PAYMENT,
				TemplateConstants.TPL_BANKPAY_RETURN);
	}
	
	/**
	 * 银联在线返回
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/unionpay/return.jspx" })
	public String unionpayReturn(HttpServletRequest request, ModelMap model) {

		// 商家数据包

		String attach = RequestUtils.getQueryParam(request, "attach");
		// 银行订单号

		String bank_billno = RequestUtils.getQueryParam(request, "bank_billno");
		// 付款银行

		String bank_type = RequestUtils.getQueryParam(request, "bank_type");
		// 折扣价格

		String discount = RequestUtils.getQueryParam(request, "discount");
		// 币种

		String fee_type = RequestUtils.getQueryParam(request, "fee_type");
		// 字符集

		String input_charset = RequestUtils.getQueryParam(request,
				"input_charset");
		// 通知ID

		String notify_id = RequestUtils.getQueryParam(request, "notify_id");
		// 商户订单号

		String out_trade_no = RequestUtils.getQueryParam(request,
				"out_trade_no");
		// 商户号

		String partner = RequestUtils.getQueryParam(request, "partner");
		// 支付结果信息

		String pay_info = RequestUtils.getQueryParam(request, "pay_info");
		// 物品费用

		String product_fee = RequestUtils.getQueryParam(request, "product_fee");
		// 签名方式

		String sign_type = RequestUtils.getQueryParam(request, "sign_type");
		// 支付完成时间

		String time_end = RequestUtils.getQueryParam(request, "time_end");
		// 总金额

		String total_fee = RequestUtils.getQueryParam(request, "total_fee");
		// 交易模式

		String trade_mode = RequestUtils.getQueryParam(request, "trade_mode");
		// 交易状态

		String trade_state = RequestUtils.getQueryParam(request, "trade_state");
		// 财付通订单号

		String transaction_id = RequestUtils.getQueryParam(request,
				"transaction_id");
		// 物流费用

		String transport_fee = RequestUtils.getQueryParam(request,
				"transport_fee");
		// 签名

		String sign = RequestUtils.getQueryParam(request, "sign");

		StringBuffer sb = new StringBuffer();
		sb.append("attach=").append(attach);
		if (!StringUtils.isBlank(bank_billno))
			sb.append("&bank_billno=").append(bank_billno);
		sb.append("&bank_type=").append(bank_type);
		if (!StringUtils.isBlank(discount))
			sb.append("&discount=").append(discount);
		sb.append("&fee_type=").append(fee_type);
		if (!StringUtils.isBlank(input_charset))
			sb.append("&input_charset=").append(input_charset);
		sb.append("&notify_id=").append(notify_id);
		sb.append("&out_trade_no=").append(out_trade_no);
		sb.append("&partner=").append(partner);
		if (!StringUtils.isBlank(pay_info))
			sb.append("&pay_info=").append(pay_info);
		if (!StringUtils.isBlank(product_fee))
			sb.append("&product_fee=").append(product_fee);
		if (!StringUtils.isBlank(sign_type))
			sb.append("&sign_type=").append(sign_type);
		sb.append("&time_end=").append(time_end);
		sb.append("&total_fee=").append(total_fee);
		sb.append("&trade_mode=").append(trade_mode);
		sb.append("&trade_state=").append(trade_state);
		sb.append("&transaction_id=").append(transaction_id);
		if (!StringUtils.isBlank(transport_fee))
			sb.append("&transport_fee=").append(transport_fee);
		sb.append("&key=").append(Tenpay.KEY);
		String v = CodeCryption.encode("MD5", sb.toString()).toUpperCase();

		if (!sign.equals(v) || !partner.equals(Tenpay.PARTNER))
			return "redirect:/error-222.html";

		 Map<String, Object> parms = new Tenpay().verify(attach);
		 if (!parms.get("time_end").equals(time_end)
		 || !parms.get("partner").equals(partner)
		 || !parms.get("out_trade_no").equals(out_trade_no)
		 || !parms.get("total_fee").equals(total_fee)
		 || !parms.get("bank_type").equals(bank_type)
		 || !parms.get("transaction_id").equals(transaction_id)
		 || !parms.get("trade_state").equals(trade_state))
		 return "redirect:/error-998.html";

		Order order = buyService.getOrderById(Integer.parseInt(attach));
		order.setAccount(Blowfish.decode(order.getAccount(), order.getUid()
				+ "" + order.getGid()));
		model.put("order", order);

		model.addAttribute("transaction_id", transaction_id);
		model.addAttribute("pay_info", pay_info);
		model.addAttribute("bank_type",
				Dataconverter.changeTenpayBankType(bank_type));
		model.addAttribute("total", Double.parseDouble(total_fee) / 100);
		model.addAttribute("trade_state", trade_state.equals("0") ? "成功" : "失败");

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_PAYMENT,
				TemplateConstants.TPL_UNIONPAY_RETURN);
	}

	@RequestMapping(value = { "/tenpay/notify.jspx" })
	public void tenpayNotify(HttpServletRequest request, HttpServletResponse response) {

		try {
			
			// 商家数据包

			String attach = RequestUtils.getQueryParam(request, "attach");
			// 银行订单号

			String bank_billno = RequestUtils.getQueryParam(request,
					"bank_billno");
			// 付款银行

			String bank_type = RequestUtils.getQueryParam(request, "bank_type");
			// 折扣价格

			String discount = RequestUtils.getQueryParam(request, "discount");
			// 币种

			String fee_type = RequestUtils.getQueryParam(request, "fee_type");
			// 字符集

			String input_charset = RequestUtils.getQueryParam(request,
					"input_charset");
			// 通知ID

			String notify_id = RequestUtils.getQueryParam(request, "notify_id");
			// 商户订单号

			String out_trade_no = RequestUtils.getQueryParam(request,
					"out_trade_no");
			// 商户号

			String partner = RequestUtils.getQueryParam(request, "partner");
			// 支付结果信息

			String pay_info = RequestUtils.getQueryParam(request, "pay_info");
			// 物品费用

			String product_fee = RequestUtils.getQueryParam(request,
					"product_fee");
			// 签名方式

			String sign_type = RequestUtils.getQueryParam(request, "sign_type");
			// 支付完成时间

			String time_end = RequestUtils.getQueryParam(request, "time_end");
			// 总金额

			String total_fee = RequestUtils.getQueryParam(request, "total_fee");
			// 交易模式

			String trade_mode = RequestUtils.getQueryParam(request,
					"trade_mode");
			// 交易状态

			String trade_state = RequestUtils.getQueryParam(request,
					"trade_state");
			// 财付通订单号

			String transaction_id = RequestUtils.getQueryParam(request,
					"transaction_id");
			// 物流费用

			String transport_fee = RequestUtils.getQueryParam(request,
					"transport_fee");
			// 签名

			String sign = RequestUtils.getQueryParam(request, "sign");
			
			StringBuffer sb = new StringBuffer();
			sb.append("attach=").append(attach);
			if (!StringUtils.isBlank(bank_billno))
				sb.append("&bank_billno=").append(bank_billno);
			sb.append("&bank_type=").append(bank_type);
			if (!StringUtils.isBlank(discount))
				sb.append("&discount=").append(discount);
			sb.append("&fee_type=").append(fee_type);
			if (!StringUtils.isBlank(input_charset))
				sb.append("&input_charset=").append(input_charset);
			sb.append("&notify_id=").append(notify_id);
			sb.append("&out_trade_no=").append(out_trade_no);
			sb.append("&partner=").append(partner);
			if (!StringUtils.isBlank(pay_info))
				sb.append("&pay_info=").append(pay_info);
			if (!StringUtils.isBlank(product_fee))
				sb.append("&product_fee=").append(product_fee);
			if (!StringUtils.isBlank(sign_type))
				sb.append("&sign_type=").append(sign_type);
			sb.append("&time_end=").append(time_end);
			sb.append("&total_fee=").append(total_fee);
			sb.append("&trade_mode=").append(trade_mode);
			sb.append("&trade_state=").append(trade_state);
			sb.append("&transaction_id=").append(transaction_id);
			if (!StringUtils.isBlank(transport_fee))
				sb.append("&transport_fee=").append(transport_fee);
			sb.append("&key=").append(Tenpay.KEY);
			String v = CodeCryption.encode("MD5", sb.toString()).toUpperCase();
			String msg = "";
			if (!sign.equals(v) || !partner.equals(Tenpay.PARTNER))
				msg = "fail";
			Map<String, Object> parms = new Tenpay().verify(notify_id);
			if (!parms.get("attach").equals(attach)
					|| !parms.get("time_end").equals(time_end)
					|| !parms.get("partner").equals(partner)
					|| !parms.get("out_trade_no").equals(out_trade_no)
					|| !parms.get("total_fee").equals(total_fee)
					|| !parms.get("bank_type").equals(bank_type)
					|| !parms.get("transaction_id").equals(transaction_id)
					|| !parms.get("trade_state").equals(trade_state)) {
				msg = "fail";
			} else {

				if (buyService.verifyPayment(transaction_id) == null && trade_state.equals("0")) {
					Payment payment = new Payment();
					payment.setBankBillno(bank_billno);
					payment.setId(Integer.parseInt(out_trade_no));
					payment.setBankType(Dataconverter.changeTenpayBankType(bank_type));
					payment.setBackCode(trade_state);
					payment.setOrderId(Integer.parseInt(attach));
					payment.setRemark(pay_info);
					payment.setActual(Double.parseDouble(total_fee)/100);
					payment.setTsn(transaction_id);
					buyService.paymentOrder(payment);
					
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("orderid", attach));
					params.add(new BasicNameValuePair("id", out_trade_no));
					params.add(new BasicNameValuePair("bankbillno", bank_billno));
					params.add(new BasicNameValuePair("banktype", CodeCryption.encode("URL", Dataconverter.changeTenpayBankType(bank_type))));
					params.add(new BasicNameValuePair("backcode", trade_state));
					params.add(new BasicNameValuePair("tsn", transaction_id));
					params.add(new BasicNameValuePair("actual", String.valueOf(Double.parseDouble(total_fee)/100)));
					params.add(new BasicNameValuePair("remark", CodeCryption.encode("URL", pay_info)));
					params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5", out_trade_no + attach + Dataconverter.KEY)));
					HttpClientTool.post("http://120.27.39.71:8099/payment/order", params);
				}
				msg = "success";
			}
			ResponseUtils.renderText(response, msg);
		} catch (Exception e) {
			ResponseUtils.renderText(response, "fail");
		}
	}
	
	@RequestMapping(value = { "/alipay/notify.jspx" })
	public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {

		try {

			// 通知时间
			String notify_time = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "notify_time"));
			// 通知类型
			String notify_type = RequestUtils.getQueryParam(request,
					"notify_type");
			// 通知校验ID
			String notify_id = RequestUtils.getQueryParam(request, "notify_id");
			// 签名方式
			String sign_type = RequestUtils.getQueryParam(request, "sign_type");
			// 签名
			String sign = RequestUtils.getQueryParam(request, "sign");
			// 商户网站唯一订单号
			String out_trade_no = RequestUtils.getQueryParam(request,
					"out_trade_no");
			// 商品名称
			String subject = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "subject"));
			// 支付类型
			String payment_type = RequestUtils.getQueryParam(request,
					"payment_type");
			// 支付宝交易号
			String trade_no = RequestUtils.getQueryParam(request, "trade_no");
			// 交易状态
			String trade_status = RequestUtils.getQueryParam(request, "trade_status");
			// 交易创建时间
			String gmt_create = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_create"));
			// 交易付款时间
			String gmt_payment = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_payment"));
			// 交易关闭时间
			String gmt_close = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_close"));
			// 退款状态
			String refund_status = RequestUtils.getQueryParam(request, "refund_status");
			// 退款时间
			String gmt_refund = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_refund"));
			// 卖家支付宝账号
			String seller_email = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "seller_email"));
			// 买家支付宝账号
			String buyer_email = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "buyer_email"));
			// 卖家支付宝账户号
			String seller_id = RequestUtils.getQueryParam(request,
					"seller_id");
			// 买家支付宝账户号
			String buyer_id = RequestUtils.getQueryParam(request, "buyer_id");
			// 商品单价
			String price = RequestUtils.getQueryParam(request, "price");
			// 交易金额
			String total_fee = RequestUtils.getQueryParam(request, "total_fee");
			// 购买数量
			String quantity = RequestUtils.getQueryParam(request, "quantity");
			// 商品描述
			String body = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "body"));
			// 折扣
			String discount = RequestUtils.getQueryParam(request, "discount");
			// 是否调整总价
			String is_total_fee_adjust = RequestUtils.getQueryParam(request, "is_total_fee_adjust");
			// 是否使用红包买家
			String use_coupon = RequestUtils.getQueryParam(request, "use_coupon");
			// 公用回传参数
			String extra_common_param = RequestUtils.getQueryParam(request, "extra_common_param");
			// 支付渠道组合信息
			String out_channel_type = RequestUtils.getQueryParam(request, "out_channel_type");
			// 支付金额组合信息
			String out_channel_amount = RequestUtils.getQueryParam(request, "out_channel_amount");
			// 实际支付渠道
			String out_channel_inst = RequestUtils.getQueryParam(request, "out_channel_inst");
			// 是否扫码支付
			String business_scene = RequestUtils.getQueryParam(request, "business_scene");

			StringBuffer sb = new StringBuffer();
			sb.append("body=").append(body);
			if (!StringUtils.isBlank(business_scene))
				sb.append("&business_scene=").append(business_scene);
			if (!StringUtils.isBlank(buyer_email))
				sb.append("&buyer_email=").append(buyer_email);
			if (!StringUtils.isBlank(buyer_id))
				sb.append("&buyer_id=").append(buyer_id);
			if (!StringUtils.isBlank(discount))
				sb.append("&discount=").append(discount);
			if (!StringUtils.isBlank(extra_common_param))
				sb.append("&extra_common_param=").append(extra_common_param);
			if (!StringUtils.isBlank(gmt_close))
				sb.append("&gmt_close=").append(gmt_close);
			if (!StringUtils.isBlank(gmt_create))
				sb.append("&gmt_create=").append(gmt_create);
			if (!StringUtils.isBlank(gmt_payment))
				sb.append("&gmt_payment=").append(gmt_payment);
			if (!StringUtils.isBlank(gmt_refund))
				sb.append("&gmt_refund=").append(gmt_refund);
			if (!StringUtils.isBlank(is_total_fee_adjust))
				sb.append("&is_total_fee_adjust=").append(is_total_fee_adjust);
			sb.append("&notify_id=").append(notify_id);
			sb.append("&notify_time=").append(notify_time);
			sb.append("&notify_type=").append(notify_type);
			if (!StringUtils.isBlank(out_channel_amount))
				sb.append("&out_channel_amount=").append(out_channel_amount);
			if (!StringUtils.isBlank(out_channel_inst))
				sb.append("&out_channel_inst=").append(out_channel_inst);
			if (!StringUtils.isBlank(out_channel_type))
				sb.append("&out_channel_type=").append(out_channel_type);
			if (!StringUtils.isBlank(out_trade_no))
				sb.append("&out_trade_no=").append(out_trade_no);
			if (!StringUtils.isBlank(payment_type))
				sb.append("&payment_type=").append(payment_type);
			if (!StringUtils.isBlank(price))
				sb.append("&price=").append(price);
			if (!StringUtils.isBlank(quantity))
				sb.append("&quantity=").append(quantity);
			if (!StringUtils.isBlank(refund_status))
				sb.append("&refund_status=").append(refund_status);
			if (!StringUtils.isBlank(seller_email))
				sb.append("&seller_email=").append(seller_email);
			if (!StringUtils.isBlank(seller_id))
				sb.append("&seller_id=").append(seller_id);
			if (!StringUtils.isBlank(subject))
				sb.append("&subject=").append(subject);
			if (!StringUtils.isBlank(total_fee))
				sb.append("&total_fee=").append(total_fee);
			if (!StringUtils.isBlank(trade_no))
				sb.append("&trade_no=").append(trade_no);
			if (!StringUtils.isBlank(trade_status))
				sb.append("&trade_status=").append(trade_status);
			if (!StringUtils.isBlank(use_coupon))
				sb.append("&use_coupon=").append(use_coupon);
			sb.append(Alipay.KEY);
			String v = CodeCryption.encode("MD5", sb.toString());
			String msg = "";
			if (!sign.equals(v) || !seller_id.equals(Alipay.PID))
				msg = "fail";
			if (new Alipay().verify(notify_id)) {	

				if (buyService.verifyPayment(trade_no) == null && trade_status.equals("TRADE_SUCCESS")) {
					Payment payment = new Payment();
					payment.setBankBillno(buyer_email);
					payment.setId(Integer.parseInt(out_trade_no));
					payment.setBankType("支付宝账号");
					payment.setBackCode(trade_status);
					payment.setOrderId(Integer.parseInt(extra_common_param));
					payment.setRemark(null);
					payment.setActual(Double.parseDouble(total_fee));
					payment.setTsn(trade_no);
					buyService.paymentOrder(payment);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("orderid",
							extra_common_param));
					params.add(new BasicNameValuePair("id", out_trade_no));
					params.add(new BasicNameValuePair("actual", total_fee));
					params.add(new BasicNameValuePair("bankbillno", CodeCryption
							.encode("URL", buyer_email)));
					params.add(new BasicNameValuePair("banktype", CodeCryption
							.encode("URL", "支付宝账号")));
					params.add(new BasicNameValuePair("backcode", trade_status));
					params.add(new BasicNameValuePair("tsn", trade_no));
					params.add(new BasicNameValuePair("remark", null));
					params.add(new BasicNameValuePair("sign", CodeCryption
							.encode("MD5", out_trade_no + extra_common_param
									+ Dataconverter.KEY)));
					HttpClientTool.post(
							"http://120.27.39.71:8099/payment/order",
							params);
				}
				msg = "success";
			} else 
				msg = "fail";
			ResponseUtils.renderText(response, msg);
		} catch (Exception e) {
			ResponseUtils.renderText(response, "fail");
		}
	}
	
	@RequestMapping(value = { "/bankpay/notify.jspx" })
	public void bankpayNotify(HttpServletRequest request, HttpServletResponse response) {

		try {

			// 通知时间
			String notify_time = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "notify_time"));
			// 通知类型
			String notify_type = RequestUtils.getQueryParam(request,
					"notify_type");
			// 通知校验ID
			String notify_id = RequestUtils.getQueryParam(request, "notify_id");
			// 签名方式
			String sign_type = RequestUtils.getQueryParam(request, "sign_type");
			// 网银流水
			String bank_seq_no = RequestUtils.getQueryParam(request, "bank_seq_no");
			// 签名
			String sign = RequestUtils.getQueryParam(request, "sign");
			// 商户网站唯一订单号
			String out_trade_no = RequestUtils.getQueryParam(request,
					"out_trade_no");
			// 商品名称
			String subject = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "subject"));
			// 支付类型
			String payment_type = RequestUtils.getQueryParam(request,
					"payment_type");
			// 支付宝交易号
			String trade_no = RequestUtils.getQueryParam(request, "trade_no");
			// 交易状态
			String trade_status = RequestUtils.getQueryParam(request, "trade_status");
			// 交易创建时间
			String gmt_create = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_create"));
			// 交易付款时间
			String gmt_payment = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_payment"));
			// 交易关闭时间
			String gmt_close = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_close"));
			// 退款状态
			String refund_status = RequestUtils.getQueryParam(request, "refund_status");
			// 退款时间
			String gmt_refund = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "gmt_refund"));
			// 卖家支付宝账号
			String seller_email = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "seller_email"));
			// 买家支付宝账号
			String buyer_email = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "buyer_email"));
			// 卖家支付宝账户号
			String seller_id = RequestUtils.getQueryParam(request,
					"seller_id");
			// 买家支付宝账户号
			String buyer_id = RequestUtils.getQueryParam(request, "buyer_id");
			// 商品单价
			String price = RequestUtils.getQueryParam(request, "price");
			// 交易金额
			String total_fee = RequestUtils.getQueryParam(request, "total_fee");
			// 购买数量
			String quantity = RequestUtils.getQueryParam(request, "quantity");
			// 商品描述
			String body = CodeCryption.decode("URL", RequestUtils.getQueryParam(request, "body"));
			// 折扣
			String discount = RequestUtils.getQueryParam(request, "discount");
			// 是否调整总价
			String is_total_fee_adjust = RequestUtils.getQueryParam(request, "is_total_fee_adjust");
			// 是否使用红包买家
			String use_coupon = RequestUtils.getQueryParam(request, "use_coupon");
			// 公用回传参数
			String extra_common_param = RequestUtils.getQueryParam(request, "extra_common_param");
			// 支付渠道组合信息
			String out_channel_type = RequestUtils.getQueryParam(request, "out_channel_type");
			// 支付金额组合信息
			String out_channel_amount = RequestUtils.getQueryParam(request, "out_channel_amount");
			// 实际支付渠道
			String out_channel_inst = RequestUtils.getQueryParam(request, "out_channel_inst");
			// 是否扫码支付
			String business_scene = RequestUtils.getQueryParam(request, "business_scene");

			StringBuffer sb = new StringBuffer();
			sb.append("bank_seq_no=").append(bank_seq_no);
			sb.append("&body=").append(body);
			if (!StringUtils.isBlank(business_scene))
				sb.append("&business_scene=").append(business_scene);
			if (!StringUtils.isBlank(buyer_email))
				sb.append("&buyer_email=").append(buyer_email);
			if (!StringUtils.isBlank(buyer_id))
				sb.append("&buyer_id=").append(buyer_id);
			if (!StringUtils.isBlank(discount))
				sb.append("&discount=").append(discount);
			if (!StringUtils.isBlank(extra_common_param))
				sb.append("&extra_common_param=").append(extra_common_param);
			if (!StringUtils.isBlank(gmt_close))
				sb.append("&gmt_close=").append(gmt_close);
			if (!StringUtils.isBlank(gmt_create))
				sb.append("&gmt_create=").append(gmt_create);
			if (!StringUtils.isBlank(gmt_payment))
				sb.append("&gmt_payment=").append(gmt_payment);
			if (!StringUtils.isBlank(gmt_refund))
				sb.append("&gmt_refund=").append(gmt_refund);
			if (!StringUtils.isBlank(is_total_fee_adjust))
				sb.append("&is_total_fee_adjust=").append(is_total_fee_adjust);
			sb.append("&notify_id=").append(notify_id);
			sb.append("&notify_time=").append(notify_time);
			sb.append("&notify_type=").append(notify_type);
			if (!StringUtils.isBlank(out_channel_amount))
				sb.append("&out_channel_amount=").append(out_channel_amount);
			if (!StringUtils.isBlank(out_channel_inst))
				sb.append("&out_channel_inst=").append(out_channel_inst);
			if (!StringUtils.isBlank(out_channel_type))
				sb.append("&out_channel_type=").append(out_channel_type);
			if (!StringUtils.isBlank(out_trade_no))
				sb.append("&out_trade_no=").append(out_trade_no);
			if (!StringUtils.isBlank(payment_type))
				sb.append("&payment_type=").append(payment_type);
			if (!StringUtils.isBlank(price))
				sb.append("&price=").append(price);
			if (!StringUtils.isBlank(quantity))
				sb.append("&quantity=").append(quantity);
			if (!StringUtils.isBlank(refund_status))
				sb.append("&refund_status=").append(refund_status);
			if (!StringUtils.isBlank(seller_email))
				sb.append("&seller_email=").append(seller_email);
			if (!StringUtils.isBlank(seller_id))
				sb.append("&seller_id=").append(seller_id);
			if (!StringUtils.isBlank(subject))
				sb.append("&subject=").append(subject);
			if (!StringUtils.isBlank(total_fee))
				sb.append("&total_fee=").append(total_fee);
			if (!StringUtils.isBlank(trade_no))
				sb.append("&trade_no=").append(trade_no);
			if (!StringUtils.isBlank(trade_status))
				sb.append("&trade_status=").append(trade_status);
			if (!StringUtils.isBlank(use_coupon))
				sb.append("&use_coupon=").append(use_coupon);
			sb.append(Alipay.KEY);
			String v = CodeCryption.encode("MD5", sb.toString());
			String msg = "";
			if (!sign.equals(v) || !seller_id.equals(Alipay.PID))
				msg = "fail";
			if (new Alipay().verify(notify_id)) {	

				if (buyService.verifyPayment(trade_no) == null && trade_status.equals("TRADE_SUCCESS")) {
					Payment payment = new Payment();
					payment.setBankBillno(bank_seq_no);
					payment.setId(Integer.parseInt(out_trade_no));
					payment.setBankType("支付宝网关");
					payment.setBackCode(trade_status);
					payment.setOrderId(Integer.parseInt(extra_common_param));
					payment.setRemark(null);
					payment.setActual(Double.parseDouble(total_fee));
					payment.setTsn(trade_no);
					buyService.paymentOrder(payment);
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("orderid",
							extra_common_param));
					params.add(new BasicNameValuePair("id", out_trade_no));
					params.add(new BasicNameValuePair("actual", total_fee));
					params.add(new BasicNameValuePair("bankbillno", bank_seq_no));
					params.add(new BasicNameValuePair("banktype", CodeCryption
							.encode("URL", "支付宝网关")));
					params.add(new BasicNameValuePair("backcode", trade_status));
					params.add(new BasicNameValuePair("tsn", trade_no));
					params.add(new BasicNameValuePair("remark", null));
					params.add(new BasicNameValuePair("sign", CodeCryption
							.encode("MD5", out_trade_no + extra_common_param
									+ Dataconverter.KEY)));
					HttpClientTool.post(
							"http://120.27.39.71:8099/payment/order",
							params);
				}
				msg = "success";
			} else 
				msg = "fail";
			ResponseUtils.renderText(response, msg);
		} catch (Exception e) {
			ResponseUtils.renderText(response, "fail");
		}
	}

	@RequestMapping(value = { "/payment.jspx" }, method = { RequestMethod.POST })
	public String payment(HttpServletRequest request,
			HttpServletResponse response) {

		Integer orderId = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "orderid")) ? 0 : Integer.parseInt(RequestUtils
				.getQueryParam(request, "orderid"));
		Integer payId = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"payid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "payid"));
		Order order = buyService.getOrderById(orderId);
		Payway payway = buyService.getPaywayById(payId);
		String ip = ToolUtility.getIpAddr(request);
		if (order == null || payway == null)
			return "redirect:/error-998.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/payment.jspx?id="
					+ order.getId();

		String url = "http://www.fakafa.com/";

		Payment payment = new Payment();
		payment.setOrderId(order.getId());
		payment.setUid(order.getUid());
		payment.setIp(ip);
		payment.setPayName(payway.getName());
		payment.setPayWay(payway.getCode());
		payment.setPayGate(payway.getGate());
		payment.setTotal(order.getQuantity() * order.getPrice());
		buyService.insertPayment(payment);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("orderid", order.getId().toString()));
		params.add(new BasicNameValuePair("uid", order.getUid().toString()));
		params.add(new BasicNameValuePair("payway", payway.getCode()));
		params.add(new BasicNameValuePair("ip", ip));
		params.add(new BasicNameValuePair("id", payment.getId().toString()));
		params.add(new BasicNameValuePair("total", String.valueOf(order.getQuantity()*order.getPrice())));
		params.add(new BasicNameValuePair("paygate", payway.getGate()));
		params.add(new BasicNameValuePair("payname", CodeCryption.encode("URL", payway.getName())));		
		params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5", payment.getId().toString() + order.getUid().toString() + Dataconverter.KEY)));
		HttpClientTool.post("http://120.27.39.71:8099/sync/payment", params);

		if (payway.getGate().equals("TENPAY"))
			url = new Tenpay().create(order, payway, payment);
		else if (payway.getGate().equals("ALIPAY"))
			url = new Alipay().create(order, payway, payment);
		else if (payway.getGate().equals("BANKPAY"))
			url = new Bankpay().create(order, payway, payment);
		else if (payway.getGate().equals("99BILL"))
			url = new Kuaiqian().create(order, payway, payment);
		else if (payway.getGate().equals("UNIONPAY"))
			url = new Unionpay().create(order, payway, payment);
		else if (payway.getGate().equals("CARDPAY"))
			url = new Cardpay().create(order, payway, payment);
		else
			url = "http://www.fakafa.com/";

		return "redirect:" + url;
	}

	@RequestMapping(value = { "/payment.jspx" }, method = { RequestMethod.GET })
	public String payment(HttpServletRequest request, ModelMap model) {

		Integer oderId = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "id")) ? 0 : Integer.parseInt(RequestUtils
				.getQueryParam(request, "id"));
		Order order = buyService.getOrderById(oderId);

		if (order == null)
			return "redirect:/error-404.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/payment.jspx?id=" + oderId;
		
		if (order.getStatus().equals("1"))
			return "redirect:/user/trade.jspx";

		Users user = loginSessionManager.getCurrentUser(request);
		if (!order.getUid().equals(user.getId()))
			return "redirect:/error-555.html";
		order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
		model.put("order", order);
		if(order.getType().equals("安卓首充号"))
			model.addAttribute("hao",
					buyService.getHaoByOrderId(order.getId()).getRemark());
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils
				.getTplPath(request, site.getSolutionPath(),
						TemplateConstants.TPLDIR_PAYMENT,
						TemplateConstants.TPL_BUY_PAYMENT);
	}

	@Autowired
	private BuyService buyService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginSessionManager loginSessionManager;
}