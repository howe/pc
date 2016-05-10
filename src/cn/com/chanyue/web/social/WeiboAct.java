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
public class WeiboAct {

	@RequestMapping(value = { "/social/weiboBacktrack.jspx" })
	public String backtrack(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String code = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"code")) ? "" : RequestUtils.getQueryParam(request, "code");

		if (code.equals(""))
			return "redirect:/error-777.html";
		else {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("client_id", "2470702678"));
			params.add(new BasicNameValuePair("client_secret",
					"eedeea133fd72692a773c8d69813240d"));
			params.add(new BasicNameValuePair("grant_type",
					"authorization_code"));
			params.add(new BasicNameValuePair("redirect_uri",
					"http://www.fakafa.com"));
			params.add(new BasicNameValuePair("code", code));
			String tmp = HttpClientTool.post(
					"https://api.weibo.com/oauth2/access_token", params);
			if (tmp.indexOf("uid") >= 0 && tmp != null) {
				
				String access_token = ToolUtility.getValueFromJson(tmp,
						"access_token", "remind_in");
				SocialBind socialBind = new SocialBind();
				String openId = ToolUtility.getValueFromJson(tmp, "uid", "}");
				socialBind.setOpenId(openId);
				socialBind.setAppId("weibo");
				socialBind.setToken(access_token);
				socialBind = usersService.getSocialBind(socialBind);
				tmp = HttpClientTool.get("https://api.weibo.com/2/users/show.json?source=2470702678&access_token="+access_token+"&uid="+openId);
				String avatar = ToolUtility.getValueFromJson(tmp, "profile_image_url", "cover_image").replaceAll("http:", "");
				String nickName = ToolUtility.getValueFromJson(tmp.replaceAll("\"", ""), "screen_name", ",name");

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
					user.setEmail(openId + "@quicklogin.weibo");
					user.setQq(null);
					user.setRegIP(ip);
					user.setSafeCode(null);
					user.setSalt("fakafa");
					user.setAnswer(null);
					user.setQuestion(null);
					usersService.insert(user);

					socialBind = new SocialBind();
					socialBind.setOpenId(openId);
					socialBind.setAppId("weibo");
					socialBind.setToken(access_token);
					socialBind.setUid(user.getId());
					usersService.insertSocialBind(socialBind);

					params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("email", CodeCryption
							.encode("URL", openId + "@quicklogin.weibo")));
					params.add(new BasicNameValuePair("regip", ip));
					params.add(new BasicNameValuePair("id", user.getId()
							.toString()));
					params.add(new BasicNameValuePair("salt", "fakafa"));
					params.add(new BasicNameValuePair("openid", openId));
					params.add(new BasicNameValuePair("appid", "weibo"));
					params.add(new BasicNameValuePair("token", access_token));
					params.add(new BasicNameValuePair("safecode", ""));
					params.add(new BasicNameValuePair("mailverify", "1"));
					params.add(new BasicNameValuePair("password", "QuickLogin"));
					params.add(new BasicNameValuePair("sign", CodeCryption
							.encode("MD5", user.getId().toString() + openId
									+ "@quicklogin.weibo" + Dataconverter.KEY)));
					HttpClientTool
							.post("http://120.27.39.71:8099/sync/social",
									params);

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
				}
			} else
				return "redirect:/error-777.html";
		}
	}

	@RequestMapping(value = { "/login/weibo.jspx" })
	public String login(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		return "redirect:https://api.weibo.com/oauth2/authorize?client_id=2470702678&response_type=code&redirect_uri=http://www.fakafa.com/social/weiboBacktrack.jspx";
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginLogsService loginLogsService;
}
