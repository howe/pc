package cn.com.chanyue.web.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.Blowfish;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.LoginSessionManager;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.member.LoginLogs;
import cn.com.chanyue.entity.member.RealAuth;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.service.chong.BuyService;
import cn.com.chanyue.service.member.LoginLogsService;
import cn.com.chanyue.service.member.RealAuthService;
import cn.com.chanyue.service.member.UsersService;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.session.SessionProvider;

/**
 * 用户中心相关
 * 
 * @author Howe
 * 
 */
@Controller
public class UserAct {

	/**
	 * 用户首页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/user/index.jspx" }, method = { RequestMethod.GET })
	public String index(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/index.jspx";

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		LoginLogs loginLogs = loginLogsService.queryPreLogin(user.getId());
		RealAuth realAuth = realAuthService.queryRealAuth(user.getId());
		model.put("realAuth", realAuth);
		model.put("loginLogs", loginLogs);

		model.addAttribute("gravatar",
				CodeCryption.encode("MD5", user.getEmail()));

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER,
				TemplateConstants.TPL_USER_INDEX);
	}

	/**
	 * 修改密码页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/user/modifypswd.jspx" }, method = { RequestMethod.GET })
	public String modifypswd(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/modifypswd.jspx";
		

		Users user = loginSessionManager.getCurrentUser(request);
		
		model.put("user", user);
		RealAuth realAuth = realAuthService.queryRealAuth(user.getId());
		model.put("realAuth", realAuth);
		LoginLogs loginLogs = loginLogsService.queryPreLogin(user.getId());
		model.put("loginLogs", loginLogs);

		model.addAttribute("gravatar",
				CodeCryption.encode("MD5", user.getEmail()));

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER,
				TemplateConstants.TPL_MODIFYPSWD);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/user/modifypswd.jspx" }, method = { RequestMethod.POST })
	public String modifypswd(HttpServletRequest request,
			HttpServletResponse response) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/modifypswd.jspx";

		String pswd = RequestUtils.getQueryParam(request, "pswd");
		String password = RequestUtils.getQueryParam(request, "password");

		Users user = new Users();
		user.setEmail(loginSessionManager.getCurrentUser(request).getEmail());
		user.setPassword(password);
		user = usersService.login(user);
		if (user != null && StringUtils.isNotBlank(pswd)) {
			String salt = ToolUtility.getRandomString(6);
			user.setSalt(salt);
			user.setPassword(CodeCryption.encode("MD5",
					CodeCryption.encode("MD5", pswd) + salt));
			usersService.modifyPassword(user);
			session.logout(request, response);
			
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("email", user.getEmail()));
//			params.add(new BasicNameValuePair("salt", salt));
//			params.add(new BasicNameValuePair("password", CodeCryption.encode("MD5",
//					CodeCryption.encode("MD5", pswd) + salt)));
//			params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5", user.getEmail() + Dataconverter.KEY)));
//			HttpClientTool.post("http://120.27.39.71:8099/update/pswd", params);
			
			return "redirect:/login.jspx?returnUrl=/user/index.jspx";
		} else
			return "redirect:/user/modifypswd.jspx";
	}

	/**
	 * 实名认证页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/user/realauth.jspx" }, method = { RequestMethod.GET })
	public String realauth(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/realauth.jspx";

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);

		model.addAttribute("gravatar",
				CodeCryption.encode("MD5", user.getEmail()));
		
		RealAuth realAuth = realAuthService.queryRealAuth(user.getId());
		model.put("realAuth", realAuth);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils
				.getTplPath(request, site.getSolutionPath(),
						TemplateConstants.TPLDIR_MEMBER,
						TemplateConstants.TPL_REALAUTH);
	}

	@RequestMapping(value = { "/user/realauth.jspx" }, method = { RequestMethod.POST })
	public String realauth(HttpServletRequest request,
			HttpServletResponse response) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/realauth.jspx";

		String realName = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "realname")) ? "" : RequestUtils.getQueryParam(
				request, "realname");
		String idCard = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "idcard")) ? "" : RequestUtils.getQueryParam(
				request, "idcard");
		
		if (ToolUtility.verifyIdCard(idCard) && ToolUtility.verifyChinese(realName)) {
			RealAuth realAuth = new RealAuth();
			realAuth.setUid(loginSessionManager.getCurrentUser(request).getId());
			realAuth.setRealName(realName);
			realAuth.setIdCard(idCard);
			realAuthService.addRealAuth(realAuth);
			realAuthService.updateRealAuth(loginSessionManager.getCurrentUser(request).getId());
			
//			List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("realname", CodeCryption.encode("URL", realName)));
//			params.add(new BasicNameValuePair("uid", loginSessionManager.getCurrentUser(request).getId().toString()));
//			params.add(new BasicNameValuePair("idcard", idCard));
//			params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5", loginSessionManager.getCurrentUser(request).getId().toString() + Dataconverter.KEY)));
//			HttpClientTool.post("http://120.27.39.71:8099/sync/realauth", params);
			
			return "redirect:/user/realauth.jspx";
		} else
			return "redirect:/user/realauth.jspx";
	}

	/**
	 * 用户资料页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/user/profile.jspx" }, method = { RequestMethod.GET })
	public String profile(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/profile.jspx";

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER, TemplateConstants.TPL_PROFILE);
	}

	/**
	 * 社交帐号绑定
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/user/social.jspx" }, method = { RequestMethod.GET })
	public String social(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/social.jspx";

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER, TemplateConstants.TPL_SOCIAL);
	}

	/**
	 * 充值流水
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/user/trade.jspx" }, method = { RequestMethod.GET })
	public String trade(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/trade.jspx";

//		Integer page = StringUtils.isBlank(RequestUtils.getQueryParam(request,
//				"page")) ? 1 : Integer.parseInt(RequestUtils.getQueryParam(
//				request, "page"));
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		
		List<Order> trades = buyService.getOrderList(user.getId());
		model.put("trades", trades);

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER, TemplateConstants.TPL_TRADE);
	}
	
	@RequestMapping(value = { "/user/detail.jspx" }, method = { RequestMethod.GET })
	public String detail(HttpServletRequest request, ModelMap model) {
		
		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
						request, "id"));

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/detail.jspx?id="+id;

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		
		Order order = new Order();
		order.setId(id);
		order.setUid(user.getId());
		order = buyService.getDetail(order);
		order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
		order.setPswd(Blowfish.decode(order.getPswd(), order.getGid().toString()));
		model.put("order", order);
		if(order.getType().equals("安卓首充号"))
			model.addAttribute("hao",
					buyService.getHaoByOrderId(order.getId()).getRemark());
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER, TemplateConstants.TPL_DETAIL);
	}
	
	@RequestMapping(value = { "/user/info.jspx" }, method = { RequestMethod.GET })
	public String info(HttpServletRequest request, ModelMap model) {
		
		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
						request, "id"));

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/info.jspx?id="+id;

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		
		Order order = new Order();
		order.setId(id);
		order.setUid(user.getId());
		order = buyService.getDetail(order);
		order.setAccount(Blowfish.decode(order.getAccount(), order.getGid().toString()));
		order.setPswd(Blowfish.decode(order.getPswd(), order.getGid().toString()));
		model.put("order", order);
		
		Payment payment = buyService.getPaymentInfo(order.getId());
		model.put("payment", payment);
		
		if(order.getType().equals("安卓首充号"))
			model.addAttribute("hao",
					buyService.getHaoByOrderId(order.getId()).getRemark());

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER, TemplateConstants.TPL_INFO);
	}
	
	@RequestMapping(value = { "/user/verifyemail.jspx" }, method = { RequestMethod.GET })
	public String verifyemail(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/verifyemail.jspx";

		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		
		model.addAttribute("gravatar",
				CodeCryption.encode("MD5", user.getEmail()));

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER, TemplateConstants.TPL_VERIFY_EMAIL);
	}

	@RequestMapping(value = { "/user/loginLogs.jspx" }, method = { RequestMethod.GET })
	public String loginLogs(HttpServletRequest request, ModelMap model) {

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/user/loginLogs.jspx";

		Integer page = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"page")) ? 1 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "page"));
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_MEMBER,
				TemplateConstants.TPL_LOGINLOGS);
	}

	@Autowired
	private LoginSessionManager loginSessionManager;
	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private BuyService buyService;
	@Autowired
	private RealAuthService realAuthService;
	@Autowired
	private LoginLogsService loginLogsService;
}
