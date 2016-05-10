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
public class QihuAct {

	@RequestMapping(value = { "/social/qihuBacktrack.jspx" })
	public String backtrack(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String code = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"code")) ? "" : RequestUtils.getQueryParam(request, "code");
		if (code.equals(""))
			return "redirect:/error-777.html";
		else {
			String tmp = HttpClientTool
					.httpsGet("https://openapi.360.cn/oauth2/access_token?client_id=5ba4c54cc1b5bb245de93f36ee61669d&client_secret=db31a9e4c29f7480fa9887a02a7972ac&code="
							+ code
							+ "&grant_type=authorization_code&redirect_uri=http%3A%2F%2Fwww.fakafa.com%2Fsocial%2F360Backtrack.jspx");

			if (tmp.indexOf("access_token") >= 0 && tmp != null) {

				String access_token = ToolUtility.getValueFromJson(tmp, "access_token", "expires_in");
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("access_token", access_token));
				tmp = HttpClientTool.httpsPost(
						"https://openapi.360.cn/user/me.json", params);
				
				if (tmp.indexOf("id") >= 0 || tmp == null) {

					SocialBind socialBind = new SocialBind();
					String openId = ToolUtility.getValueFromJson(tmp, "id", "name");
					socialBind.setOpenId(openId);
					socialBind.setAppId("360");
					socialBind.setToken(access_token);
					socialBind = usersService.getSocialBind(socialBind);			
					String ip = ToolUtility.getIpAddr(request);
					String nickName = ToolUtility.getValueFromJson(tmp,
							"name", "avatar");
					String avatar = ToolUtility.getValueFromJson(tmp, "avatar", "}");
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
						user.setEmail(openId + "@quicklogin.360");
						user.setQq(null);
						user.setRegIP(ip);
						user.setSafeCode(null);
						user.setSalt("fakafa");
						user.setAnswer(null);
						user.setQuestion(null);
						usersService.insert(user);

						socialBind = new SocialBind();
						socialBind.setOpenId(openId);
						socialBind.setAppId("360");
						socialBind.setToken(access_token);
						socialBind.setUid(user.getId());
						usersService.insertSocialBind(socialBind);

						params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("email", CodeCryption
								.encode("URL", openId + "@quicklogin.360")));
						params.add(new BasicNameValuePair("regip", ip));
						params.add(new BasicNameValuePair("id", user.getId()
								.toString()));
						params.add(new BasicNameValuePair("salt", "fakafa"));
						params.add(new BasicNameValuePair("openid", openId));
						params.add(new BasicNameValuePair("safecode", ""));
						params.add(new BasicNameValuePair("appid", "360"));
						params.add(new BasicNameValuePair("token", access_token));
						params.add(new BasicNameValuePair("mailverify", "1"));
						params.add(new BasicNameValuePair("password",
								"QuickLogin"));
						params.add(new BasicNameValuePair("sign", CodeCryption
								.encode("MD5", user.getId().toString() + openId
										+ "@quicklogin.360" + Dataconverter.KEY)));
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

	@RequestMapping(value = { "/login/qihu.jspx" })
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		return "redirect:https://openapi.360.cn/oauth2/authorize?client_id=5ba4c54cc1b5bb245de93f36ee61669d&response_type=code&redirect_uri=http://www.fakafa.com/social/qihuBacktrack.jspx&scope=basic&display=default";
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginLogsService loginLogsService;
}
