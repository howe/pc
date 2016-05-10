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
public class WeixinAct {

	@RequestMapping(value = { "/social/weixinBacktrack.jspx" })
	public String backtrack(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String code = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"code")) ? "" : RequestUtils.getQueryParam(request, "code");
		String state = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"state")) ? "" : RequestUtils.getQueryParam(request, "state");
		if (code.equals("") && !state.equals("fakafa"))
			return "redirect:/error-777.html";
		else {
			String tmp = HttpClientTool
					.get("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=wx9d8839e5c620b070&secret=e1431b65fddb486fb6bccc7312b75175&code="
							+ code);

			if (tmp.indexOf("access_token") >= 0 && tmp != null) {
				String token = ToolUtility.getValueFromJson(tmp,
						"access_token", "expires_in");
				String openid = ToolUtility.getValueFromJson(tmp, "openid",
						"scope");

				tmp = HttpClientTool
						.get("https://api.weixin.qq.com/sns/userinfo?access_token="
								+ token + "&openid=" + openid);

				if (tmp.indexOf("openid") >= 0 && tmp != null) {

					SocialBind socialBind = new SocialBind();
					String openId = ToolUtility.getValueFromJson(tmp, "unionid", "}");
					socialBind.setOpenId(openId);
					socialBind.setAppId("weixin");
					socialBind.setToken(token);
					socialBind = usersService.getSocialBind(socialBind);

					String nickName = ToolUtility.getValueFromJson(tmp,
							"nickname", "sex");
					String avatar = ToolUtility.getValueFromJson(tmp, "headimgurl", "privilege").replaceAll("/\\", "/");
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
							String url = ToolUtility.getCookieValue(request,
									"url");
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
						user.setEmail(openId + "@quicklogin.weixin");
						user.setQq(null);
						user.setRegIP(ip);
						user.setSafeCode(null);
						user.setSalt("fakafa");
						user.setAnswer(null);
						user.setQuestion(null);
						usersService.insert(user);

						socialBind = new SocialBind();
						socialBind.setOpenId(openId);
						socialBind.setAppId("weixin");
						socialBind.setToken(token);
						socialBind.setUid(user.getId());
						usersService.insertSocialBind(socialBind);

						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("email", CodeCryption
								.encode("URL", openId + "@quicklogin.weixin")));
						params.add(new BasicNameValuePair("regip", ip));
						params.add(new BasicNameValuePair("id", user.getId()
								.toString()));
						params.add(new BasicNameValuePair("salt", "fakafa"));
						params.add(new BasicNameValuePair("openid", openId));
						params.add(new BasicNameValuePair("appid", "weixin"));
						params.add(new BasicNameValuePair("safecode", ""));
						params.add(new BasicNameValuePair("token", token));
						params.add(new BasicNameValuePair("mailverify", "1"));
						params.add(new BasicNameValuePair("password",
								"QuickLogin"));
						params.add(new BasicNameValuePair("sign", CodeCryption
								.encode("MD5", user.getId().toString() + openId
										+ "@quicklogin.weixin" + Dataconverter.KEY)));
						HttpClientTool.post(
								"http://120.27.39.71:8099/sync/social", params);

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

	@RequestMapping(value = { "/login/weixin.jspx" })
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		return "redirect:https://open.weixin.qq.com/connect/qrconnect?appid=wx9d8839e5c620b070&redirect_uri=http%3A%2F%2Fwww.fakafa.com%2Fsocial%2FweixinBacktrack.jspx&response_type=code&scope=snsapi_login&state=fakafa";
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginLogsService loginLogsService;
}
