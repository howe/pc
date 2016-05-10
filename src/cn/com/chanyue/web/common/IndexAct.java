package cn.com.chanyue.web.common;

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
import cn.com.chanyue.common.tool.Dataconverter;
import cn.com.chanyue.common.tool.LoginSessionManager;
import cn.com.chanyue.common.tool.PinyinUtil;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.chong.GS;
import cn.com.chanyue.entity.chong.Game;
import cn.com.chanyue.entity.chong.Par;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.service.chong.BuyService;

import com.jeecms.cms.entity.main.CmsSite;
import com.jeecms.cms.web.CmsUtils;
import com.jeecms.cms.web.FrontUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;

@Controller
public class IndexAct {

	/**
	 * 首页
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		List<Game> apples = buyService.getGameListIndex(1);
		List<Game> androids = buyService.getGameListIndex(2);
		model.put("apples", apples);
		model.put("androids", androids);
		
		List<Game> onlines = buyService.getGameListIndex(3);
		model.put("onlines", onlines);
		
		List<Game> appleList = buyService.getGameListByPlid(1);
		List<Game> androidList = buyService.getGameListByPlid(2);
		List<Game> onlineList = buyService.getGameListByPlid(3);
		model.put("appleList", appleList);
		model.put("androidList", androidList);
		model.put("onlineList", onlineList);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_INDEX);
	}
	
	@RequestMapping(value = { "/verifyKefuQQ.jspx" })
	public void verifyKefuQQ(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String qq = ToolUtility.verifyEmail(RequestUtils.getQueryParam(
				request, "qq")) ? RequestUtils.getQueryParam(request,
				"qq") : "";
		String msg = "{\"data\":{\"error\":\"请填写QQ号码\"}}".toString();
		if (!StringUtils.isBlank(qq)) {
			if (ToolUtility.verifyKefuQQ(qq))
				msg = "{\"data\":{\"ok\":\"fakafa官方服务QQ\"}}".toString();
			else
				msg = "{\"data\":{\"error\":\"非fakafa官方服务QQ，谨防上当受骗。\"}}".toString();
		} else
			msg = "{\"data\":{\"error\":\"请填写QQ号码\"}}".toString();
		ResponseUtils.renderText(response, msg);
	}

	/**
	 * WEBLOGIC的默认路径
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index.jhtml", method = RequestMethod.GET)
	public String indexForWeblogic(HttpServletRequest request, ModelMap model) {
		return index(request, model);
	}

	@RequestMapping(value = "/android.jspx", method = RequestMethod.GET)
	public String android(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		List<Game> games = buyService.getGameHotList(2);
		model.put("games", games);
		model.addAttribute("sort", "HOT");
		model.addAttribute("left", "42px");
		model.addAttribute("width", "68px");
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_ANDROID);
	}

	@RequestMapping(value = "/android/sort.jspx", method = RequestMethod.GET)
	public String androidSort(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		String sort = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sort")) ? "" : RequestUtils.getQueryParam(request, "sort")
				.toUpperCase();
		Game game = new Game();
		game.setPy(sort);
		game.setPlid(2);
		List<Game> games = buyService.getGameSortList(game);
		model.put("games", games);
		model.addAttribute("sort", sort);
		model.addAttribute("left", Dataconverter.getLeft(sort));
		model.addAttribute("width", "30px");
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_ANDROID);
	}

	@RequestMapping(value = "/apple/sort.jspx", method = RequestMethod.GET)
	public String appleSort(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		String sort = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sort")) ? "" : RequestUtils.getQueryParam(request, "sort")
				.toUpperCase();
		Game game = new Game();
		game.setPy(sort);
		game.setPlid(1);
		List<Game> games = buyService.getGameSortList(game);
		model.put("games", games);
		model.addAttribute("sort", sort);
		model.addAttribute("left", Dataconverter.getLeft(sort));
		model.addAttribute("width", "30px");
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_APPLE);
	}

	@RequestMapping(value = "/online.jspx", method = RequestMethod.GET)
	public String online(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		List<Game> games = buyService.getGameList(3);
		model.put("games", games);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_ONLINE);
	}

	@RequestMapping(value = "/game.jspx", method = RequestMethod.GET)
	public String game(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		String gName = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gname")) ? "" : RequestUtils.getQueryParam(request, "gname")
				.trim();
		String plName = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"plname")) ? "" : RequestUtils.getQueryParam(request, "plname")
				.trim();
		Game game = new Game();
		game.setGName(gName);
		game.setPlid(Dataconverter.plid2Name(plName));
		game = buyService.getGameByName(game);
		if (game == null)
			return "redirect:/error-404.html";
		if (game.getPlid() == 1) {
			List<GS> gses = buyService.getGS1(gName);
			model.put("gses", gses);
			Par par = new Par();
			par.setGid(game.getId());
			par.setTid(1);
			model.addAttribute("discount1", buyService.getDiscount(par));
		} else if (game.getPlid() == 2) {
			List<GS> gs1es = buyService.getGS21(gName);
			model.put("gs1es", gs1es);
			Par par = new Par();
			par.setGid(game.getId());
			par.setTid(2);
			model.addAttribute("discount2", buyService.getDiscount(par));
			List<GS> gs2es = buyService.getGS22(gName);
			model.put("gs2es", gs2es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(3);
			model.addAttribute("discount3", buyService.getDiscount(par));
			List<GS> gs3es = buyService.getGS23(gName);
			model.put("gs3es", gs3es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(4);
			model.addAttribute("discount4", buyService.getDiscount(par));
			List<GS> gs4es = buyService.getGS24(gName);
			model.put("gs4es", gs4es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(5);
			model.addAttribute("discount5", buyService.getDiscount(par));
			List<GS> gs5es = buyService.getGS25(gName);
			model.put("gs5es", gs5es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(6);
			model.addAttribute("discount6", buyService.getDiscount(par));
		} else if (game.getPlid() == 3) {
			List<GS> gses = buyService.getGS3(gName);
			model.put("gses", gses);
		} else if (game.getPlid() == 4) {
			List<GS> gses = buyService.getGS4(gName);
			model.put("gses", gses);
		} else if (game.getPlid() == 5) {
			List<GS> gses = buyService.getGS5(gName);
			model.put("gses", gses);
		} else {
			List<GS> gses = buyService.getGS1(gName);
			model.put("gses", gses);
		}
		model.put("game", game);
		model.addAttribute("url", Dataconverter.plid2Url(game.getPlid()));
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_GAME);
	}

	@RequestMapping(value = "/game/id.jspx", method = RequestMethod.GET)
	public String gameById(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);
		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() == 1) {
			List<GS> gses = buyService.getGS1(game.getGName());
			model.put("gses", gses);
			Par par = new Par();
			par.setGid(game.getId());
			par.setTid(1);
			model.addAttribute("discount1", buyService.getDiscount(par));
		} else if (game.getPlid() == 2) {
			List<GS> gs1es = buyService.getGS21(game.getGName());
			model.put("gs1es", gs1es);
			Par par = new Par();
			par.setGid(game.getId());
			par.setTid(2);
			model.addAttribute("discount2", buyService.getDiscount(par));
			List<GS> gs2es = buyService.getGS22(game.getGName());
			model.put("gs2es", gs2es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(3);
			model.addAttribute("discount3", buyService.getDiscount(par));
			List<GS> gs3es = buyService.getGS23(game.getGName());
			model.put("gs3es", gs3es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(4);
			model.addAttribute("discount4", buyService.getDiscount(par));
			List<GS> gs4es = buyService.getGS24(game.getGName());
			model.put("gs4es", gs4es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(5);
			model.addAttribute("discount5", buyService.getDiscount(par));
			List<GS> gs5es = buyService.getGS25(game.getGName());
			model.put("gs5es", gs5es);
			par = new Par();
			par.setGid(game.getId());
			par.setTid(6);
			model.addAttribute("discount6", buyService.getDiscount(par));
		} else if (game.getPlid() == 3) {
			List<GS> gses = buyService.getGS3(game.getGName());
			model.put("gses", gses);
		} else if (game.getPlid() == 4) {
			List<GS> gses = buyService.getGS4(game.getGName());
			model.put("gses", gses);
		} else if (game.getPlid() == 5) {
			List<GS> gses = buyService.getGS5(game.getGName());
			model.put("gses", gses);
		} else {
			List<GS> gses = buyService.getGS1(game.getGName());
			model.put("gses", gses);
		}
		model.put("game", game);
		model.addAttribute("url", Dataconverter.plid2Url(game.getPlid()));
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_GAME);
	}

	@RequestMapping(value = "/apple.jspx", method = RequestMethod.GET)
	public String apple(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		List<Game> games = buyService.getGameHotList(1);
		model.put("games", games);
		model.addAttribute("sort", "HOT");
		model.addAttribute("left", "42px");
		model.addAttribute("width", "68px");
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_APPLE);
	}

	@RequestMapping(value = "/web.jspx", method = RequestMethod.GET)
	public String web(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		List<Game> games = buyService.getGameList(4);
		model.put("games", games);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_WEB);
	}
	
	
	@RequestMapping(value = "/search.jspx", method = RequestMethod.GET)
	public String search(HttpServletRequest request, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		
		String gName = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gname")) ? "" : RequestUtils.getQueryParam(request, "gname")
				.trim();
		
		if (StringUtils.isBlank(gName))
			return "redirect:/error-404.html";
		
		String pinyin = PinyinUtil.getPinYin(gName);
		
		List<Game> games = buyService.search(pinyin);
		model.put("games", games);
		if(games.isEmpty())
			return "redirect:/error-404.html";
		
		model.addAttribute("gName", gName);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_INDEX, TemplateConstants.TPL_SEARCH);
	}
	

	@RequestMapping(value = "/game/redirect.jspx", method = RequestMethod.GET)
	public String web(HttpServletRequest request, HttpServletResponse response) {

		String plid = RequestUtils.getQueryParam(request, "plid");
		String gid = RequestUtils.getQueryParam(request, "gid");
		String oid = RequestUtils.getQueryParam(request, "oid");
		String aid = RequestUtils.getQueryParam(request, "aid");
		String pid = RequestUtils.getQueryParam(request, "pid");
		String sid = RequestUtils.getQueryParam(request, "sid");
		return "redirect:" + Dataconverter.plid2Buy(plid) + "?id=" + gid
				+ "&oid=" + oid + "&aid=" + aid + "&sid=" + sid + "&pid=" + pid;
	}

	@Autowired
	private BuyService buyService;
	@Autowired
	private LoginSessionManager loginSessionManager;
}
