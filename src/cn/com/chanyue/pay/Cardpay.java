package cn.com.chanyue.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.service.chong.BuyService;

/**
 * 卡支付
 * @author Howe
 *
 */
@Controller
public class Cardpay {

	/**
	 * 
	 * @param order
	 * @param payway
	 * @param ip
	 * @return
	 */
	public String create(Order order, Payway payway, Payment payment){
		return "";
	}
	
	@Autowired
	private BuyService buyService;
}
