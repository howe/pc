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
public class QQAct {

	@RequestMapping(value = { "/social/qqBacktrack.jspx" })
	public String backtrack(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String code = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"code")) ? "" : RequestUtils.getQueryParam(request, "code");
		if (code.equals(""))
			return "redirect:/error-777.html";
		else {
			String tmp = HttpClientTool
					.get("https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=101172292&client_secret=de1a6276a961fa49569a83d9375d0bd1&code="
							+ code
							+ "&redirect_uri=http://www.fakafa.com/social/qqBacktrack.jspx");
			if (tmp.indexOf("access_token") >= 0 && tmp != null) {
				String token = tmp.substring(tmp.indexOf("access_token") + 13,
						tmp.indexOf("&"));
				tmp = HttpClientTool
						.get("https://graph.qq.com/oauth2.0/me?access_token="
								+ token);
				if (tmp.indexOf("openid") >= 0 && tmp != null) {
					SocialBind socialBind = new SocialBind();
					String openId = ToolUtility.getValueFromJson(tmp, "openid",
							"}").toUpperCase();
					socialBind.setOpenId(openId);
					socialBind.setAppId("qq");
					socialBind.setToken(token);
					socialBind = usersService.getSocialBind(socialBind);

					tmp = HttpClientTool
							.get("https://graph.qq.com/user/get_user_info?access_token="
									+ token
									+ "&oauth_consumer_key=101172292&openid="
									+ openId);
					String nickName = ToolUtility.getValueFromJson(tmp,
							"nickname", "gender");
					String avatar = ToolUtility.getValueFromJson(tmp,
							"figureurl_qq_1", "figureurl_qq_2").replaceAll("/\\", "/");
					String ip = ToolUtility.getIpAddr(request);
					if (socialBind != null) {
						Users user = usersService.getById(socialBind.getUid());
						if (user != null) {

							LoginLogs logs = new LoginLogs();
							logs.setIp(ip);
							logs.setUid(user.getId());
							loginLogsService.insertLoginLogs(logs);
							user.setNickName(nickName);
							user.setAvatar(avatar);
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
						user.setEmail(openId + "@quicklogin.qq");
						user.setQq(null);
						user.setRegIP(ip);
						user.setSafeCode(null);
						user.setSalt("fakafa");
						user.setAnswer(null);
						user.setQuestion(null);
						usersService.insert(user);

						socialBind = new SocialBind();
						socialBind.setOpenId(openId);
						socialBind.setAppId("qq");
						socialBind.setToken(token);
						socialBind.setUid(user.getId());
						usersService.insertSocialBind(socialBind);

						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("email", CodeCryption
								.encode("URL", openId + "@quicklogin.qq")));
						params.add(new BasicNameValuePair("regip", ip));
						params.add(new BasicNameValuePair("id", user.getId()
								.toString()));
						params.add(new BasicNameValuePair("salt", "fakafa"));
						params.add(new BasicNameValuePair("openid", openId));
						params.add(new BasicNameValuePair("appid", "qq"));
						params.add(new BasicNameValuePair("safecode", ""));
						params.add(new BasicNameValuePair("token", token));
						params.add(new BasicNameValuePair("mailverify", "1"));
						params.add(new BasicNameValuePair("password",
								"QuickLogin"));
						params.add(new BasicNameValuePair("sign", CodeCryption
								.encode("MD5", user.getId().toString() + openId
										+ "@quicklogin.qq" + Dataconverter.KEY)));
						HttpClientTool.post(
								"http://120.27.39.71:8099/sync/social",
								params);

						LoginLogs logs = new LoginLogs();
						logs.setIp(ip);
						logs.setUid(user.getId());
						loginLogsService.insertLoginLogs(logs);
						user.setNickName(nickName);
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
					return "redirect:/error-777.html";
			} else
				return "redirect:/error-777.html";
		}
	}

	@RequestMapping(value = { "/login/qq.jspx" })
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101172292&scope=get_user_info&redirect_uri=http://www.fakafa.com/social/qqBacktrack.jspx";
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginLogsService loginLogsService;
}
