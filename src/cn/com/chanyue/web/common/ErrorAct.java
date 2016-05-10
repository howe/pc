package cn.com.chanyue.web.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.service.common.ErrorService;
import cn.com.chanyue.entity.common.Error;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;

@Controller
public class ErrorAct {

	@RequestMapping(value = { "/error.jspx" }, method = { RequestMethod.GET })
	public String error(HttpServletRequest request, ModelMap model) {

		Integer code = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"code")) ? 404 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "code"));
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Error error = errorServicer.queryError(code.toString());
		if (error == null)
			return "redirect:/error-404.html";
		model.put("error", error);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON, TemplateConstants.TPL_ERROR);
	}

	@RequestMapping(value = { "/redirect.jspx" }, method = { RequestMethod.GET })
	public String redirect(HttpServletRequest request, ModelMap model) {

		String code = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"code")) ? "aHR0cDovL3d3dy45MTA4NS5jb20v" : RequestUtils
				.getQueryParam(request, "code").replaceAll(" ", "+");
		
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		String url = CodeCryption.decode("BASE64", code);
		model.addAttribute("url", url);
		return FrontUtils
				.getTplPath(request, site.getSolutionPath(),
						TemplateConstants.TPLDIR_COMMON,
						TemplateConstants.TPL_REDIRECT);
	}

	@Autowired
	private ErrorService errorServicer;
}
