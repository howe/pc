package cn.com.chanyue.web.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.entity.chong.Game;
import cn.com.chanyue.service.chong.BuyService;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;

@Controller
public class SiteMapAct {
	
	/**
	 * SiteMap
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/siteMap.jspx")
	public String siteMap(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		model.addAttribute("date", dateFormat.format(date));
		List<Game> games = buyService.getGameSitemap();
		model.put("games", games);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_COMMON, TemplateConstants.TPL_SITEMAP);
	}
	
	@RequestMapping(value = "/sitemap.jspx")
	public String sitemap(HttpServletRequest request, ModelMap model) {
		
		List<Game> games = buyService.getGameSitemap();
		model.put("games", games);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_SITEMAP);
	}
	
	@Autowired
	private BuyService buyService;
}
