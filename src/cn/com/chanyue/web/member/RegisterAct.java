package cn.com.chanyue.web.member;

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
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.chanyue.common.EmailTemplate;
import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.Dataconverter;
import cn.com.chanyue.common.tool.EmailTransport;
import cn.com.chanyue.common.tool.HttpClientTool;
import cn.com.chanyue.common.tool.LoginSessionManager;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.common.Email;
import cn.com.chanyue.entity.member.Captcha;
import cn.com.chanyue.entity.member.OperLogs;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.service.member.LoginLogsService;
import cn.com.chanyue.service.member.OperLogsService;
import cn.com.chanyue.service.member.RealAuthService;
import cn.com.chanyue.service.member.UsersService;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.session.SessionProvider;

/**
 * 注册相关
 * 
 * @author Howe
 * 
 */
@Controller
public class RegisterAct {

	@RequestMapping(value = { "/register.jspx" }, method = { RequestMethod.GET })
	public String register(HttpServletRequest request, ModelMap model) {

		if (loginSessionManager.checkLogin(request))
			return "redirect:/user/index.html";

		String returnUrl = RequestUtils.getQueryParam(request, "returnUrl");
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		if (!StringUtils.isBlank(returnUrl)) {
			model.addAttribute("returnUrl", returnUrl);
		}
		return FrontUtils
				.getTplPath(request, site.getSolutionPath(),
						TemplateConstants.TPLDIR_COMMON,
						TemplateConstants.TPL_REGISTER);
	}

	@RequestMapping(value = { "/findpswd.jspx" }, method = { RequestMethod.GET })
	public String findpswd(HttpServletRequest request, ModelMap model) {

		if (loginSessionManager.checkLogin(request))
			return "redirect:/user/index.html";

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils
				.getTplPath(request, site.getSolutionPath(),
						TemplateConstants.TPLDIR_COMMON,
						TemplateConstants.TPL_FINDPSWD);
	}

	@RequestMapping(value = { "/validEmail.jspx" }, method = { RequestMethod.GET })
	public String validEmail(HttpServletRequest request, ModelMap model) {

		String key = RequestUtils.getQueryParam(request, "key").replaceAll("#",
				"");
		key = CodeCryption.decode("BASE64", key);

		if (key.indexOf("uid") >= 0 && key.indexOf("code") >= 0
				&& key.indexOf("email") >= 0) {
			String uid = key.substring(key.indexOf("uid") + 4,
					key.indexOf("code") - 1);
			String code = key.substring(key.indexOf("code") + 5,
					key.indexOf("type") - 1);
			String type = key.substring(key.indexOf("type") + 5,
					key.indexOf("email") - 1);
			String email = key.substring(key.indexOf("email") + 6);
			Captcha captcha = new Captcha();
			captcha.setCode(code);
			captcha.setType(type);
			captcha.setMode("email");
			captcha.setReceive(email);
			captcha.setUid(Integer.parseInt(uid));
			captcha = usersService.validCaptcha(captcha);

			if (captcha != null) {
				model.addAttribute("msg", "1");
				usersService.validEmail(Integer.parseInt(uid));
				usersService.deleteCaptcha(captcha);

//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("uid", uid));
//				params.add(new BasicNameValuePair("sign", CodeCryption.encode(
//						"MD5", uid + Dataconverter.KEY)));
//				HttpClientTool.post(
//						"http://120.27.39.71:8099/update/validemail",
//						params);

				OperLogs logs = new OperLogs();
				logs.setUid(Integer.parseInt(uid));
				logs.setIp(ToolUtility.getIpAddr(request));
				logs.setAction("完成验证邮箱（" + email + "）");
				operLogsService.insertOperLogs(logs);
			} else
				model.addAttribute("msg", "0");
		} else
			model.addAttribute("msg", "0");
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);

		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON,
				TemplateConstants.TPL_VALID_EMAIL);
	}

	@RequestMapping(value = { "/register/jump.jspx" }, method = { RequestMethod.GET })
	public String registerJump(HttpServletRequest request, ModelMap model) {

		String code = RequestUtils.getQueryParam(request, "code");
		String email = RequestUtils.getQueryParam(request, "email")
				.toLowerCase();
		String uid = RequestUtils.getQueryParam(request, "uid");
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		model.addAttribute("code", code);
		model.addAttribute("email", email);
		model.addAttribute("uid", uid);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON,
				TemplateConstants.TPL_REGISTER_JUMP);
	}

	@RequestMapping(value = { "/findpswd/jump.jspx" }, method = { RequestMethod.GET })
	public String findpswdJump(HttpServletRequest request, ModelMap model) {

		String email = RequestUtils.getQueryParam(request, "email")
				.toLowerCase();
		String uid = RequestUtils.getQueryParam(request, "uid");
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		model.addAttribute("email", email);
		model.addAttribute("uid", uid);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON,
				TemplateConstants.TPL_FINDPSWD_JUMP);
	}

	@RequestMapping(value = { "/register.jspx" }, method = { RequestMethod.POST })
	public String register(HttpServletRequest request,
			HttpServletResponse response) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		String password = RequestUtils.getQueryParam(request, "password");
		String salt = ToolUtility.getRandomString(6);
		// String safeCode = RequestUtils.getQueryParam(request, "safecode");
		// String question = RequestUtils.getQueryParam(request, "question");
		// String answer = RequestUtils.getQueryParam(request, "answer");
		if (!StringUtils.isBlank(email) && ToolUtility.verifyEmail(email)
				&& !StringUtils.isBlank(password)) {
			try {

				String ip = ToolUtility.getIpAddr(request);
				Users user = new Users();
				user.setEmail(email.toLowerCase());
				user.setSalt(salt);
				user.setRegIP(ip);
				user.setAnswer(null);
				user.setQuestion(null);
				user.setMailVerify('0');
				user.setPassword(CodeCryption.encode("MD5",
						CodeCryption.encode("MD5", password) + salt));
				// user.setSafeCode(CodeCryption.encode("MD5", safeCode
				// + "@CHANYUE"));
				user.setSafeCode(null);
				usersService.insert(user);

				String code = ToolUtility.getRandomNumber(6);
				Captcha captcha = new Captcha();
				captcha.setUid(user.getId());
				captcha.setCode(code);
				captcha.setReceive(email);
				captcha.setMode("email");
				captcha.setType("register");
				usersService.insertCaptcha(captcha);

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("email", CodeCryption
						.encode("URL", email)));
				params.add(new BasicNameValuePair("regip", ip));
				params.add(new BasicNameValuePair("id", user.getId().toString()));
				params.add(new BasicNameValuePair("salt", salt));
				params.add(new BasicNameValuePair("safecode", ""));
				params.add(new BasicNameValuePair("mailverify", "0"));
				params.add(new BasicNameValuePair("password", CodeCryption
						.encode("MD5", CodeCryption.encode("MD5", password)
								+ salt)));
				params.add(new BasicNameValuePair("sign", CodeCryption.encode(
						"MD5", user.getId().toString() + email
								+ Dataconverter.KEY)));
				HttpClientTool.post("http://120.27.39.71:8099/sync/user",
						params);

				String valid = CodeCryption
						.encode("BASE64",
								"uid="
										+ user.getId()
										+ "&code="
										+ CodeCryption.encode(
												"MD5",
												CodeCryption
														.encode("MD5", code)
														+ code)
										+ "&type=register&email=" + email);

				Email mail = new Email();
				mail.setFrom(EmailTemplate.VALIDMAIL_FROM);
				mail.setPwd(EmailTemplate.VALIDMAIL_PWD);
				mail.setRecipients(new String[] { email });
				mail.setSubject(EmailTemplate.VALIDMAIL_SUBJECT);
				mail.setHeadValue(EmailTemplate.VALIDMAIL_HEADVALUE);
				mail.setHeadName(EmailTemplate.HEADNAME);
				mail.setContent(EmailTemplate.VALIDMAIL.replaceAll("@VALID",
						valid));
				EmailTransport.sendMailBySMTP(mail);

				session.setAttribute(request, response,
						TemplateConstants.AUTHEN_KEY,
						usersService.getById(user.getId()));
				return "redirect:/user/index.jspx";
			} catch (Exception e) {
				return "redirect:/register.html";
			}
		}
		return "redirect:/register.html";

	}

	@RequestMapping(value = { "/findpswd.jspx" }, method = { RequestMethod.POST })
	public String findpswd(HttpServletRequest request,
			HttpServletResponse response) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		Users user = usersService.getByEmail(email);
		if (user != null) {
			try {

				String salt = ToolUtility.getRandomString(6);
				String password = ToolUtility.getRandomNumber(6);

				user.setSalt(salt);
				user.setPassword(CodeCryption.encode("MD5",
						CodeCryption.encode("MD5", password) + salt));
				usersService.modifyPassword(user);

//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("email", email));
//				params.add(new BasicNameValuePair("salt", salt));
//				params.add(new BasicNameValuePair("password", CodeCryption
//						.encode("MD5", CodeCryption.encode("MD5", password)
//								+ salt)));
//				params.add(new BasicNameValuePair("sign", CodeCryption.encode(
//						"MD5", email + Dataconverter.KEY)));
//				HttpClientTool.post(
//						"http://120.27.39.71:8099/update/pswd", params);

				Email mail = new Email();
				mail.setFrom(EmailTemplate.RESETPASSWORD_FROM);
				mail.setPwd(EmailTemplate.RESETPASSWORD_PWD);
				mail.setRecipients(new String[] { email });
				mail.setSubject(EmailTemplate.RESETPASSWORD_SUBJECT);
				mail.setHeadValue(EmailTemplate.RESETPASSWORD_HEADVALUE);
				mail.setHeadName(EmailTemplate.HEADNAME);
				mail.setContent(EmailTemplate.RESETPASSWORD.replaceAll(
						"@PASSWORD", password));
				EmailTransport.sendMailBySMTP(mail);

				return "redirect:/findpswd_s.html";
			} catch (Exception e) {
				return "redirect:/findpswd.html";
			}
		}
		return "redirect:/findpswd.html";

	}
	
	@RequestMapping(value = { "/findpswd_s.jspx" }, method = { RequestMethod.GET })
	public String findpswd_s(HttpServletRequest request, ModelMap model) {

		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON,
				TemplateConstants.TPL_FINDPSWD_S);
	}

	@RequestMapping(value = { "/checkEmail.jspx" })
	public void checkEmail(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String email = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "email")) ? RequestUtils.getQueryParam(request,
				"email").toLowerCase() : "";
		String msg = "{\"data\":{\"error\":\"电子邮件格式不正确\"}}".toString();
		if (!StringUtils.isBlank(email)) {
			if (ToolUtility.verifyEmail(email)) {
				Users user = usersService.getByEmail(email);

				if (user != null)
					msg = "{\"data\":{\"error\":\"电子邮件已注册\"}}".toString();
				else
					msg = "{\"data\":{\"ok\":\"可以注册\"}}".toString();
			} else
				msg = "{\"data\":{\"error\":\"电子邮件格式错误\"}}".toString();

		} else {
			msg = "{\"data\":{\"error\":\"电子邮件不能为空\"}}".toString();
		}
		ResponseUtils.renderText(response, msg);
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private UsersService usersService;
	@Autowired
	private RealAuthService realAuthService;
	@Autowired
	private OperLogsService operLogsService;
	@Autowired
	private LoginLogsService loginLogsService;
	@Autowired
	private LoginSessionManager loginSessionManager;
}
