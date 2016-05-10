package cn.com.chanyue.web.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.LoginSessionManager;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.member.LoginLogs;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.service.member.LoginLogsService;
import cn.com.chanyue.service.member.UsersService;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.session.SessionProvider;

/**
 * 登录相关
 * 
 * @author Howe
 * 
 */
@Controller
public class LoginAct {

	/**
	 * 登录页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/login.jspx" }, method = { RequestMethod.GET })
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		if (loginSessionManager.checkLogin(request))
			return "redirect:/user/index.html";

		String returnUrl = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "returnUrl")) ? "/user/index.jspx" : RequestUtils
				.getQueryParam(request, "returnUrl");
		returnUrl = returnUrl.indexOf("＼") < 0 ? returnUrl : "";
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute("returnUrl", returnUrl);
		}

		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON, TemplateConstants.TPL_LOGIN);
	}

	@RequestMapping(value = { "/login.jspx" }, method = { RequestMethod.POST })
	public String login(HttpServletRequest request, HttpServletResponse response) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		email = !ToolUtility.verifyEmail(email) ? "" : email;

		String password = RequestUtils.getQueryParam(request, "password");

		String returnUrl = RequestUtils.getQueryParam(request, "returnUrl");
		if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password)) {

			Users user = new Users();
			user.setEmail(email);
			user.setPassword(password);
			user = usersService.login(user);
			if (user != null) {

				String ip = ToolUtility.getIpAddr(request);
				String salt = ToolUtility.getRandomString(6);
				user.setSalt(salt);
				user.setPassword(CodeCryption.encode("MD5",
						CodeCryption.encode("MD5", password) + salt));
				usersService.lastLogin(user);

				LoginLogs logs = new LoginLogs();
				logs.setIp(ip);
				logs.setUid(user.getId());
				loginLogsService.insertLoginLogs(logs);

				session.setAttribute(request, response,
						TemplateConstants.AUTHEN_KEY, user);

				String url = ToolUtility.getCookieValue(request, "url");
				ToolUtility.deleteCookie(request, response, "url");

				if (StringUtils.isNotBlank(url))
					return "redirect:" + url;
				else if(StringUtils.isNotBlank(returnUrl))
					return "redirect:" + returnUrl;
				else
					return "redirect:/user/index.jspx";
			} else
				return "redirect:/login.jspx?code=1";
		} else
			return "redirect:/login.jspx?code=0";
	}

	@RequestMapping(value = { "/verifyEmail.jspx" })
	public void verifyEmail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		String msg = "{\"data\":{\"error\":\"电子邮件格式不正确\"}}".toString();
		if (!StringUtils.isBlank(email)) {
			if (ToolUtility.verifyEmail(email)) {
				Users user = usersService.getByEmail(email);
				if (user != null)
					msg = "{\"data\":{\"ok\":\"\"}}".toString();
				else
					msg = "{\"data\":{\"error\":\"帐号不存在\"}}".toString();
			} else
				msg = "{\"data\":{\"error\":\"电子邮件格式错误\"}}".toString();
		} else
			msg = "{\"data\":{\"error\":\"电子邮件不能为空\"}}".toString();
		ResponseUtils.renderText(response, msg);
	}

	@RequestMapping(value = { "/checkPassword.jspx" })
	public void checkPassword(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		String password = RequestUtils.getQueryParam(request, "password");
		String msg = "{\"data\":{\"error\":\"电子邮件格式不正确\"}}".toString();
		if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password)) {
			Users user = new Users();
			user.setEmail(email);
			user.setPassword(password);
			user = usersService.login(user);
			if (user != null)
				msg = "{\"data\":{\"ok\":\"\"}}".toString();
			else
				msg = "{\"data\":{\"error\":\"帐号或密码错误\"}}".toString();
		} else
			msg = "{\"data\":{\"error\":\"电子邮件或密码不能为空\"}}".toString();
		ResponseUtils.renderText(response, msg);
	}

	@RequestMapping(value = { "/verifyPassword.jspx" })
	public void verifyPassword(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		String password = RequestUtils.getQueryParam(request, "pswd");
		String msg = "{\"data\":{\"error\":\"未登录\"}}".toString();
		if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password)) {
			Users user = new Users();
			user.setEmail(email);
			user.setPassword(password);
			user = usersService.login(user);
			if (user != null)
				msg = "{\"data\":{\"ok\":\"\"}}".toString();
			else
				msg = "{\"data\":{\"error\":\"当前密码错误\"}}".toString();
		} else
			msg = "{\"data\":{\"error\":\"当前密码不能为空\"}}".toString();
		ResponseUtils.renderText(response, msg);
	}

	@RequestMapping(value = { "/checkpassword.jspx" })
	public void checkpassword(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		String password = RequestUtils.getQueryParam(request, "password");
		String msg = "0".toString();
		if (!StringUtils.isBlank(email) && !StringUtils.isBlank(password)) {
			Users user = new Users();
			user.setEmail(email);
			user.setPassword(password);
			user = usersService.login(user);
			if (user != null)
				msg = "1".toString();
			else
				msg = "0".toString();
		} else
			msg = "0".toString();
		ResponseUtils.renderText(response, msg);
	}

	@RequestMapping(value = "/logout.jspx")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		session.logout(request, response);
		return "redirect:/";
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginLogsService loginLogsService;
	@Autowired
	private LoginSessionManager loginSessionManager;
}