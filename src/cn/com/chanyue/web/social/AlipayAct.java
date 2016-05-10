package cn.com.chanyue.web.social;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.Dataconverter;
import cn.com.chanyue.common.tool.HttpClientTool;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.member.LoginLogs;
import cn.com.chanyue.entity.member.SocialBind;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.service.member.LoginLogsService;
import cn.com.chanyue.service.member.UsersService;

import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.session.SessionProvider;

@Controller
public class AlipayAct {

	@RequestMapping(value = { "/social/alipayBacktrack.jspx" })
	public String backtrack(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String notify_id = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "notify_id")) ? "" : RequestUtils.getQueryParam(
				request, "notify_id");
		String user_id = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "user_id")) ? "" : RequestUtils.getQueryParam(request,
				"user_id");
		String token = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"token")) ? "" : RequestUtils.getQueryParam(request, "token");
		String real_name = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "real_name")) ? "" : RequestUtils.getQueryParam(
				request, "real_name");
		if (StringUtils.isBlank(notify_id) || StringUtils.isBlank(user_id))
			return "redirect:/error-777.html";
		else {
			String tmp = HttpClientTool
					.get("https://mapi.alipay.com/gateway.do?service=notify_verify&partner=2088711159125952&notify_id="
							+ CodeCryption.encode("URL", notify_id));
			if (tmp.toLowerCase().equals("true") && !StringUtils.isBlank(tmp)) {
				String openId = user_id;
				SocialBind socialBind = new SocialBind();
				socialBind.setOpenId(openId);
				socialBind.setAppId("alipay");
				socialBind.setToken(token);
				socialBind = usersService.getSocialBind(socialBind);
				String ip = ToolUtility.getIpAddr(request);
				if (socialBind != null) {
					Users user = usersService.getById(socialBind.getUid());
					if (user != null) {

						LoginLogs logs = new LoginLogs();
						logs.setIp(ip);
						logs.setUid(user.getId());
						loginLogsService.insertLoginLogs(logs);

						user.setNickName(real_name);
						session.setAttribute(request, response,
								TemplateConstants.AUTHEN_KEY, user);
						String url = ToolUtility.getCookieValue(request, "url");
						ToolUtility.deleteCookie(request, response, "url");
						if (StringUtils.isBlank(url))
							return "redirect:/";
						else
							return "redirect:" + url;
					} else
						return "redirect:/error-505.html";
				} else {

					Users user = new Users();
					user.setPassword("QuickLogin");
					user.setMailVerify('1');
					user.setMobile(null);
					user.setEmail(openId + "@quicklogin.alipay");
					user.setQq(null);
					user.setRegIP(ip);
					user.setSafeCode(null);
					user.setSalt("fakafa");
					user.setAnswer(null);
					user.setQuestion(null);
					usersService.insert(user);

					socialBind = new SocialBind();
					socialBind.setOpenId(openId);
					socialBind.setAppId("alipay");
					socialBind.setToken(token);
					socialBind.setUid(user.getId());
					usersService.insertSocialBind(socialBind);

					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("email", CodeCryption
							.encode("URL", openId + "@quicklogin.alipay")));
					params.add(new BasicNameValuePair("regip", ip));
					params.add(new BasicNameValuePair("id", user.getId()
							.toString()));
					params.add(new BasicNameValuePair("safecode", ""));
					params.add(new BasicNameValuePair("salt", "fakafa"));
					params.add(new BasicNameValuePair("openid", openId));
					params.add(new BasicNameValuePair("appid", "alipay"));
					params.add(new BasicNameValuePair("token", token));
					params.add(new BasicNameValuePair("mailverify", "1"));
					params.add(new BasicNameValuePair("password", "QuickLogin"));
					params.add(new BasicNameValuePair("sign", CodeCryption
							.encode("MD5", user.getId().toString() + openId
									+ "@quicklogin.alipay" + Dataconverter.KEY)));
					HttpClientTool
							.post("http://120.27.39.71:8099/sync/social",
									params);

					LoginLogs logs = new LoginLogs();
					logs.setIp(ip);
					logs.setUid(user.getId());
					loginLogsService.insertLoginLogs(logs);
					user.setNickName(real_name);
					session.setAttribute(request, response,
							TemplateConstants.AUTHEN_KEY, user);

					String url = ToolUtility.getCookieValue(request, "url");
					ToolUtility.deleteCookie(request, response, "url");
					if (StringUtils.isBlank(url))
						return "redirect:/";
					else
						return "redirect:" + url;
				}
			} else
				return "redirect:/error-505.html";
		}
	}

	@RequestMapping(value = { "/login/alipay.jspx" })
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return "redirect:/redirect.jspx?code=aHR0cHM6Ly9tYXBpLmFsaXBheS5jb20vZ2F0ZXdheS5kbz90YXJnZXRfc2VydmljZT11c2VyLmF1dGgucXVpY2subG9naW4mc2lnbl90eXBlPU1ENSZyZXR1cm5fdXJsPWh0dHA6Ly93d3cuOTEwODUuY29tL3NvY2lhbC9hbGlwYXlCYWNrdHJhY2suanNweCZzaWduPWMwMjQxMGM0ZjJmYjdmYWI4MzRkZmQ0ZWI3ZWM1NGMzJnNlcnZpY2U9YWxpcGF5LmF1dGguYXV0aG9yaXplJnBhcnRuZXI9MjA4ODcxMTE1OTEyNTk1MiZfaW5wdXRfY2hhcnNldD11dGYtOA==";
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginLogsService loginLogsService;
}
