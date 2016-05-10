package cn.com.chanyue.web.chong;

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

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.common.tool.Blowfish;
import cn.com.chanyue.common.tool.CodeCryption;
import cn.com.chanyue.common.tool.Dataconverter;
import cn.com.chanyue.common.tool.HttpClientTool;
import cn.com.chanyue.common.tool.LoginSessionManager;
import cn.com.chanyue.common.tool.ToolUtility;
import cn.com.chanyue.entity.chong.Area;
import cn.com.chanyue.entity.chong.Game;
import cn.com.chanyue.entity.chong.Hao;
import cn.com.chanyue.entity.chong.Operator;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Par;
import cn.com.chanyue.entity.chong.Server;
import cn.com.chanyue.entity.member.Users;
import cn.com.chanyue.service.chong.BuyService;
import cn.com.chanyue.service.member.UsersService;

import com.alibaba.fastjson.JSON;
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
public class BuyAct {

	/**
	 * 苹果代充
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/ibuy.jspx" }, method = { RequestMethod.GET })
	public String ibuy(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 1)
			return "redirect:/apple.html";

		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? null : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Integer aid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"aid")) ? null : Integer.parseInt(RequestUtils.getQueryParam(
				request, "aid"));
		Integer sid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sid")) ? null : Integer.parseInt(RequestUtils.getQueryParam(
				request, "sid"));
		Integer pid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"pid")) ? null : Integer.parseInt(RequestUtils.getQueryParam(
				request, "pid"));

		Order order = new Order();
		order.setSid(sid);
		order.setPid(pid);
		order.setOid(oid);
		order.setAid(aid);
		model.put("order", order);

//		StringBuffer buffer = new StringBuffer();
//		buffer.append("/ibuy.jspx?id=").append(id);
//		if (sid != null)
//			buffer.append("&sid=").append(sid);
//		if (aid != null)
//			buffer.append("&aid=").append(aid);
//		if (oid != null)
//			buffer.append("&oid=").append(oid);
//		if (pid != null)
//			buffer.append("&pid=").append(pid);
//		
//		if (!loginSessionManager.checkLogin(request)){
//			ToolUtility.setCookie(request, response, "url", buffer.toString(), -1);
//			return "redirect:/login.jspx?returnUrl=" + buffer.toString();
//		}
		
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_IBUY);
	}

	@RequestMapping(value = { "/wbuy.jspx" }, method = { RequestMethod.GET })
	public String wbuy(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 4)
			return "redirect:/web.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/wbuy.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_WBUY);
	}

	@RequestMapping(value = { "/obuy.jspx" }, method = { RequestMethod.GET })
	public String obuy(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 3)
			return "redirect:/online.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/obuy.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_OBUY);
	}

	/**
	 * 安卓首充号
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/abuy1.jspx" }, method = { RequestMethod.GET })
	public String abuy1(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (game.getT1().equals("0"))
			return "redirect:/abuy3.jspx?id=" + id;

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy1.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorListF(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_ABUY1);
	}

	/**
	 * 安卓首充号代充
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/abuy2.jspx" }, method = { RequestMethod.GET })
	public String abuy2(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (game.getT2().equals("0"))
			return "redirect:/abuy3.jspx?id=" + id;

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy2.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_ABUY2);
	}

	/**
	 * 安卓代充
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/abuy3.jspx" }, method = { RequestMethod.GET })
	public String abuy3(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (game.getT3().equals("0"))
			return "redirect:/android.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy3.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_ABUY3);
	}

	/**
	 * 安卓金币元宝
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/abuy4.jspx" }, method = { RequestMethod.GET })
	public String abuy4(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (game.getT4().equals("0"))
			return "redirect:/abuy3.jspx?id=" + id;

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy4.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_ABUY4);
	}

	/**
	 * 安卓金币点卡
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/abuy5.jspx" }, method = { RequestMethod.GET })
	public String abuy5(HttpServletRequest request, ModelMap model) {

		Integer id = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"id")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "id"));
		Game game = buyService.getGameById(id);

		if (game == null)
			return "redirect:/error-404.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (game.getT5().equals("0"))
			return "redirect:/abuy3.jspx?id=" + id;

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy5.jspx?id=" + id;
		List<Operator> operators = buyService.getOperatorList(game.getId());
		model.put("operators", operators);
		model.put("game", game);
		Users user = loginSessionManager.getCurrentUser(request);
		model.put("user", user);
		CmsSite site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TemplateConstants.TPLDIR_BUY, TemplateConstants.TPL_BUY_ABUY5);
	}

	@RequestMapping(value = { "/ibuy.jspx" }, method = { RequestMethod.POST })
	public String ibuy(HttpServletRequest request, HttpServletResponse response) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		Game game = buyService.getGameById(gid);
		Integer sid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "sid"));
		Server sever = buyService.getServerById(sid);
		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		
		Operator operator = buyService.getOperatorById(oid);
		Integer pid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"pid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "pid"));
		Par par = buyService.getParById(pid);
		Integer aid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"aid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "aid"));
		Area area = buyService.getAreaById(aid);
		Integer quantity = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "quantity")) ? 0 : Integer.parseInt(RequestUtils
				.getQueryParam(request, "quantity"));
		String account = RequestUtils.getQueryParam(request, "account");
		String juese = RequestUtils.getQueryParam(request, "juese");
		String qq = ToolUtility.verifyQQ(RequestUtils.getQueryParam(request,
				"qq")) ? RequestUtils.getQueryParam(request, "qq") : "";
		String mobile = ToolUtility.verifyMobile(RequestUtils.getQueryParam(
				request, "mobile")) ? RequestUtils.getQueryParam(request,
				"mobile") : "";
		String pswd = request.getParameter("pswd");

		if (game == null || sever == null || operator == null || par == null
				|| area == null || quantity > 11 || quantity < 0
				|| StringUtils.isBlank(account) || StringUtils.isBlank(pswd))

			return "redirect:/error-505.html";

		if (game.getPlid() != 1)
			return "redirect:/apple.html";

		StringBuffer buffer = new StringBuffer();
		buffer.append("/ibuy.jspx?id=").append(gid);
		if (sid != null)
			buffer.append("&sid=").append(sid);
		if (aid != null)
			buffer.append("&aid=").append(aid);
		if (oid != null)
			buffer.append("&oid=").append(oid);
		if (pid != null)
			buffer.append("&pid=").append(pid);
		
		if (!loginSessionManager.checkLogin(request)){
			ToolUtility.setCookie(request, response, "url", buffer.toString(), -1);
			return "redirect:/login.jspx?returnUrl=" + buffer.toString();
		}

		Users user = loginSessionManager.getCurrentUser(request);

		String source = "fakafa";
		
		Order order = new Order();
		order.setAccount(Blowfish.encode(account, game.getId().toString()));
		order.setPswd(Blowfish.encode(pswd, game.getId().toString()));
		order.setGid(game.getId());
		order.setGName(game.getGName());
		order.setUid(user.getId());
		order.setSid(sever.getId());
		order.setSName(sever.getSName());
		order.setMobile(mobile);
		order.setQq(qq);
		order.setSource(source);
		order.setJuese(juese);
		order.setAid(area.getId());
		order.setAName(area.getAName());
		order.setFace(par.getFace());
		order.setQuantity(quantity);
		order.setOName(operator.getOName());
		order.setOid(operator.getId());
		order.setStandard(par.getStandard());
		order.setPid(par.getId());
		order.setIp(ToolUtility.getIpAddr(request));
		order.setNum(sever.getNum());
		order.setPName(game.getPName());
		order.setPlid(1);
		order.setType("官方代充");
		order.setPrice(par.getPrice());
		buyService.insertOrder(order);
		Integer id = order.getId();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id.toString()));
		params.add(new BasicNameValuePair("qq", qq));
		params.add(new BasicNameValuePair("plid", "1"));
		params.add(new BasicNameValuePair("account", Blowfish.encode(account,
				game.getId().toString())));
		params.add(new BasicNameValuePair("pswd", Blowfish.encode(pswd, game
				.getId().toString())));
		params.add(new BasicNameValuePair("gid", game.getId().toString()));
		params.add(new BasicNameValuePair("gname", CodeCryption.encode("URL",
				game.getGName())));
		params.add(new BasicNameValuePair("uid", user.getId().toString()));
		params.add(new BasicNameValuePair("sid", sever.getId().toString()));
		params.add(new BasicNameValuePair("sname", CodeCryption.encode("URL",
				sever.getSName())));
		params.add(new BasicNameValuePair("source", CodeCryption.encode("URL",
				source)));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("aid", area.getId().toString()));
		params.add(new BasicNameValuePair("aname", CodeCryption.encode("URL",
				area.getAName())));
		params.add(new BasicNameValuePair("face", par.getFace().toString()));
		params.add(new BasicNameValuePair("quantity", quantity.toString()));
		params.add(new BasicNameValuePair("juese", juese));
		params.add(new BasicNameValuePair("oname", CodeCryption.encode("URL",
				operator.getOName())));
		params.add(new BasicNameValuePair("oid", operator.getId().toString()));
		params.add(new BasicNameValuePair("ip", ToolUtility.getIpAddr(request)));
		params.add(new BasicNameValuePair("pid", par.getId().toString()));
		params.add(new BasicNameValuePair("standard", CodeCryption.encode(
				"URL", par.getStandard())));
		params.add(new BasicNameValuePair("num", sever.getNum()));
		params.add(new BasicNameValuePair("pname", CodeCryption.encode("URL",
				game.getPName())));
		params.add(new BasicNameValuePair("type", CodeCryption.encode("URL",
				"官方代充")));
		params.add(new BasicNameValuePair("price", par.getPrice().toString()));
		params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5",
				id.toString() + user.getId().toString() + Dataconverter.KEY)));
		String tmp = HttpClientTool.post(
				"http://120.27.39.71:8099/sync/orderi", params);
		System.out.println(tmp);
		return "redirect:/payment.jspx?id=" + id;
	}

	@RequestMapping(value = { "/abuy1.jspx" }, method = { RequestMethod.POST })
	public String abuy1(HttpServletRequest request, HttpServletResponse response) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		Game game = buyService.getGameById(gid);
		Integer sid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "sid"));
		Server sever = buyService.getServerById(sid);
		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Operator operator = buyService.getOperatorById(oid);
		Integer pid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"pid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "pid"));
		Par par = buyService.getParById(pid);
		Integer aid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"aid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "aid"));
		Area area = buyService.getAreaById(aid);
		Integer quantity = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "quantity")) ? 0 : Integer.parseInt(RequestUtils
				.getQueryParam(request, "quantity"));
		String account = RequestUtils.getQueryParam(request, "account");
		String qq = ToolUtility.verifyQQ(RequestUtils.getQueryParam(request,
				"qq")) ? RequestUtils.getQueryParam(request, "qq") : "";
		String mobile = ToolUtility.verifyMobile(RequestUtils.getQueryParam(
				request, "mobile")) ? RequestUtils.getQueryParam(request,
				"mobile") : "";
		String pswd = request.getParameter("pswd");

		if (game == null || sever == null || operator == null || par == null
				|| area == null || quantity > 11 || quantity < 0)

			return "redirect:/error-505.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy1.jspx?id=" + gid;

		Users user = loginSessionManager.getCurrentUser(request);

		Order order = new Order();
		order.setAccount(Blowfish.encode(account, game.getId().toString()));
		order.setPswd(Blowfish.encode(pswd, game.getId().toString()));
		order.setGid(game.getId());
		order.setGName(game.getGName());
		order.setUid(user.getId());
		order.setSid(sever.getId());
		order.setSName(sever.getSName());
		order.setMobile(mobile);
		order.setQq(qq);
		order.setAid(area.getId());
		order.setAName(area.getAName());
		order.setFace(par.getFace());
		order.setQuantity(quantity);
		order.setOName(operator.getOName());
		order.setOid(operator.getId());
		order.setStandard(par.getStandard());
		order.setPid(par.getId());
		order.setIp(ToolUtility.getIpAddr(request));
		order.setNum(sever.getNum());
		order.setPName(game.getPName());
		order.setPlid(2);
		order.setType("安卓首充号");
		order.setStandard(par.getStandard());
		order.setPrice(par.getPrice());
		buyService.insertOrder(order);
		Integer id = order.getId();

		Hao hao = new Hao();
		hao.setAid(area.getId());
		hao.setAName(area.getAName());
		hao.setUid(user.getId());
		hao.setSid(sever.getId());
		hao.setOid(operator.getId());
		hao.setSName(sever.getSName());
		hao.setNum(sever.getNum());
		hao.setOrderId(id);
		hao.setGid(game.getId());
		hao.setGName(game.getGName());
		hao.setOName(operator.getOName());
		StringBuffer remark = new StringBuffer();
		String sex = RequestUtils.getQueryParam(request, "sex");
		if (!StringUtils.isBlank(sex))
			remark.append("角色性别（").append(sex).append("）; ");
		String guojia = RequestUtils.getQueryParam(request, "guojia");
		if (!StringUtils.isBlank(guojia))
			remark.append("所属国家（").append(guojia).append("）; ");
		String zhiye = RequestUtils.getQueryParam(request, "zhiye");
		if (!StringUtils.isBlank(zhiye))
			remark.append("角色职业（").append(zhiye).append("）; ");
		String menpai = RequestUtils.getQueryParam(request, "menpai");
		if (!StringUtils.isBlank(menpai))
			remark.append("所属门派（").append(menpai).append("）; ");
		String xingxiang = RequestUtils.getQueryParam(request, "xingxiang");
		if (!StringUtils.isBlank(xingxiang))
			remark.append("角色形象（").append(xingxiang).append("）; ");
		String chongwu = RequestUtils.getQueryParam(request, "chongwu");
		if (!StringUtils.isBlank(chongwu))
			remark.append("初始宠物（").append(chongwu).append("）; ");
		String juese1 = RequestUtils.getQueryParam(request, "juese1");
		if (!StringUtils.isBlank(juese1))
			remark.append("角色名称1（").append(juese1).append("）; ");
		String juese2 = RequestUtils.getQueryParam(request, "juese2");
		if (!StringUtils.isBlank(juese2))
			remark.append("角色名称2（").append(juese2).append("）; ");
		String juese3 = RequestUtils.getQueryParam(request, "juese3");
		if (!StringUtils.isBlank(juese3))
			remark.append("角色名称3（").append(juese3).append("）; ");
		hao.setRemark(remark.toString());
		buyService.insertHao(hao);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id.toString()));
		params.add(new BasicNameValuePair("qq", qq));
		params.add(new BasicNameValuePair("plid", "2"));
		params.add(new BasicNameValuePair("gid", game.getId().toString()));
		params.add(new BasicNameValuePair("gname", CodeCryption.encode("URL",
				game.getGName())));
		params.add(new BasicNameValuePair("uid", user.getId().toString()));
		params.add(new BasicNameValuePair("sid", sever.getId().toString()));
		params.add(new BasicNameValuePair("sname", CodeCryption.encode("URL",
				sever.getSName())));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("aid", area.getId().toString()));
		params.add(new BasicNameValuePair("aname", CodeCryption.encode("URL",
				area.getAName())));
		params.add(new BasicNameValuePair("face", par.getFace().toString()));
		params.add(new BasicNameValuePair("quantity", quantity.toString()));
		params.add(new BasicNameValuePair("oname", CodeCryption.encode("URL",
				operator.getOName())));
		params.add(new BasicNameValuePair("oid", operator.getId().toString()));
		params.add(new BasicNameValuePair("ip", ToolUtility.getIpAddr(request)));
		params.add(new BasicNameValuePair("pid", par.getId().toString()));
		params.add(new BasicNameValuePair("standard", CodeCryption.encode(
				"URL", par.getStandard())));
		params.add(new BasicNameValuePair("num", sever.getNum()));
		params.add(new BasicNameValuePair("pname", CodeCryption.encode("URL",
				game.getPName())));
		params.add(new BasicNameValuePair("type", CodeCryption.encode("URL",
				"安卓首充号")));
		params.add(new BasicNameValuePair("price", par.getPrice().toString()));
		params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5",
				id.toString() + user.getId().toString() + Dataconverter.KEY)));
		params.add(new BasicNameValuePair("remark", CodeCryption.encode("URL",
				remark.toString())));
		String tmp = HttpClientTool.post(
				"http://120.27.39.71:8099/sync/ordera1", params);
		System.out.println(tmp);
		return "redirect:/payment.jspx?id=" + id;
	}

	@RequestMapping(value = { "/abuy2.jspx" }, method = { RequestMethod.POST })
	public String abuy2(HttpServletRequest request, HttpServletResponse response) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		Game game = buyService.getGameById(gid);
		Integer sid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "sid"));
		Server sever = buyService.getServerById(sid);
		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Operator operator = buyService.getOperatorById(oid);
		Integer pid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"pid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "pid"));
		Par par = buyService.getParById(pid);
		Integer aid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"aid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "aid"));
		Area area = buyService.getAreaById(aid);
		Integer quantity = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "quantity")) ? 0 : Integer.parseInt(RequestUtils
				.getQueryParam(request, "quantity"));
		String account = RequestUtils.getQueryParam(request, "account");
		String qq = ToolUtility.verifyQQ(RequestUtils.getQueryParam(request,
				"qq")) ? RequestUtils.getQueryParam(request, "qq") : "";
		String mobile = ToolUtility.verifyMobile(RequestUtils.getQueryParam(
				request, "mobile")) ? RequestUtils.getQueryParam(request,
				"mobile") : "";
		String pswd = request.getParameter("pswd");

		if (game == null || sever == null || operator == null || par == null
				|| area == null || quantity > 11 || quantity < 0
				|| StringUtils.isBlank(account) || StringUtils.isBlank(pswd))

			return "redirect:/error-505.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy2.jspx?id=" + gid;

		Users user = loginSessionManager.getCurrentUser(request);

		Order order = new Order();
		order.setAccount(Blowfish.encode(account, game.getId().toString()));
		order.setPswd(Blowfish.encode(pswd, game.getId().toString()));
		order.setGid(game.getId());
		order.setGName(game.getGName());
		order.setUid(user.getId());
		order.setSid(sever.getId());
		order.setSName(sever.getSName());
		order.setMobile(mobile);
		order.setQq(qq);
		order.setAid(area.getId());
		order.setAName(area.getAName());
		order.setFace(par.getFace());
		order.setQuantity(quantity);
		order.setOName(operator.getOName());
		order.setOid(operator.getId());
		order.setStandard(par.getStandard());
		order.setPid(par.getId());
		order.setIp(ToolUtility.getIpAddr(request));
		order.setNum(sever.getNum());
		order.setPName(game.getPName());
		order.setPlid(2);
		order.setType("安卓首充代充");
		order.setStandard(par.getStandard());
		order.setPrice(par.getPrice());
		buyService.insertOrder(order);
		Integer id = order.getId();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id.toString()));
		params.add(new BasicNameValuePair("qq", qq));
		params.add(new BasicNameValuePair("plid", "2"));
		params.add(new BasicNameValuePair("account", Blowfish.encode(account,
				game.getId().toString())));
		params.add(new BasicNameValuePair("pswd", Blowfish.encode(pswd, game
				.getId().toString())));
		params.add(new BasicNameValuePair("gid", game.getId().toString()));
		params.add(new BasicNameValuePair("gname", CodeCryption.encode("URL",
				game.getGName())));
		params.add(new BasicNameValuePair("uid", user.getId().toString()));
		params.add(new BasicNameValuePair("sid", sever.getId().toString()));
		params.add(new BasicNameValuePair("sname", CodeCryption.encode("URL",
				sever.getSName())));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("aid", area.getId().toString()));
		params.add(new BasicNameValuePair("aname", CodeCryption.encode("URL",
				area.getAName())));
		params.add(new BasicNameValuePair("face", par.getFace().toString()));
		params.add(new BasicNameValuePair("quantity", quantity.toString()));
		params.add(new BasicNameValuePair("oname", CodeCryption.encode("URL",
				operator.getOName())));
		params.add(new BasicNameValuePair("oid", operator.getId().toString()));
		params.add(new BasicNameValuePair("ip", ToolUtility.getIpAddr(request)));
		params.add(new BasicNameValuePair("pid", par.getId().toString()));
		params.add(new BasicNameValuePair("standard", CodeCryption.encode(
				"URL", par.getStandard())));
		params.add(new BasicNameValuePair("num", sever.getNum()));
		params.add(new BasicNameValuePair("pname", CodeCryption.encode("URL",
				game.getPName())));
		params.add(new BasicNameValuePair("type", CodeCryption.encode("URL",
				"安卓首充代充")));
		params.add(new BasicNameValuePair("price", par.getPrice().toString()));
		params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5",
				id.toString() + user.getId().toString() + Dataconverter.KEY)));
		HttpClientTool.post("http://120.27.39.71:8099/sync/ordera2",
				params);
		return "redirect:/payment.jspx?id=" + id;
	}

	@RequestMapping(value = { "/abuy3.jspx" }, method = { RequestMethod.POST })
	public String abuy3(HttpServletRequest request, HttpServletResponse response) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		Game game = buyService.getGameById(gid);
		Integer sid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"sid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "sid"));
		Server sever = buyService.getServerById(sid);
		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Operator operator = buyService.getOperatorById(oid);
		Integer pid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"pid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "pid"));
		Par par = buyService.getParById(pid);
		Integer aid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"aid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "aid"));
		Area area = buyService.getAreaById(aid);
		Integer quantity = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "quantity")) ? 0 : Integer.parseInt(RequestUtils
				.getQueryParam(request, "quantity"));
		String account = RequestUtils.getQueryParam(request, "account");
		String qq = ToolUtility.verifyQQ(RequestUtils.getQueryParam(request,
				"qq")) ? RequestUtils.getQueryParam(request, "qq") : "";
		String mobile = ToolUtility.verifyMobile(RequestUtils.getQueryParam(
				request, "mobile")) ? RequestUtils.getQueryParam(request,
				"mobile") : "";
		String pswd = request.getParameter("pswd");

		if (game == null || sever == null || operator == null || par == null
				|| area == null || quantity > 11 || quantity < 0
				|| StringUtils.isBlank(account) || StringUtils.isBlank(pswd))

			return "redirect:/error-505.html";

		if (game.getPlid() != 2)
			return "redirect:/android.html";

		if (!loginSessionManager.checkLogin(request))
			return "redirect:/login.jspx?returnUrl=/abuy3.jspx?id=" + gid;

		Users user = loginSessionManager.getCurrentUser(request);

		Order order = new Order();
		order.setAccount(Blowfish.encode(account, game.getId().toString()));
		order.setPswd(Blowfish.encode(pswd, game.getId().toString()));
		order.setGid(game.getId());
		order.setGName(game.getGName());
		order.setUid(user.getId());
		order.setSid(sever.getId());
		order.setSName(sever.getSName());
		order.setMobile(mobile);
		order.setQq(qq);
		order.setAid(area.getId());
		order.setAName(area.getAName());
		order.setFace(par.getFace());
		order.setQuantity(quantity);
		order.setOName(operator.getOName());
		order.setOid(operator.getId());
		order.setStandard(par.getStandard());
		order.setPid(par.getId());
		order.setIp(ToolUtility.getIpAddr(request));
		order.setNum(sever.getNum());
		order.setPName(game.getPName());
		order.setPlid(2);
		order.setType("安卓代充");
		order.setStandard(par.getStandard());
		order.setPrice(par.getPrice());
		buyService.insertOrder(order);
		Integer id = order.getId();

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id.toString()));
		params.add(new BasicNameValuePair("qq", qq));
		params.add(new BasicNameValuePair("plid", "2"));
		params.add(new BasicNameValuePair("account", Blowfish.encode(account,
				game.getId().toString())));
		params.add(new BasicNameValuePair("pswd", Blowfish.encode(pswd, game
				.getId().toString())));
		params.add(new BasicNameValuePair("gid", game.getId().toString()));
		params.add(new BasicNameValuePair("gname", CodeCryption.encode("URL",
				game.getGName())));
		params.add(new BasicNameValuePair("uid", user.getId().toString()));
		params.add(new BasicNameValuePair("sid", sever.getId().toString()));
		params.add(new BasicNameValuePair("sname", CodeCryption.encode("URL",
				sever.getSName())));
		params.add(new BasicNameValuePair("mobile", mobile));
		params.add(new BasicNameValuePair("aid", area.getId().toString()));
		params.add(new BasicNameValuePair("aname", CodeCryption.encode("URL",
				area.getAName())));
		params.add(new BasicNameValuePair("face", par.getFace().toString()));
		params.add(new BasicNameValuePair("quantity", quantity.toString()));
		params.add(new BasicNameValuePair("oname", CodeCryption.encode("URL",
				operator.getOName())));
		params.add(new BasicNameValuePair("oid", operator.getId().toString()));
		params.add(new BasicNameValuePair("ip", ToolUtility.getIpAddr(request)));
		params.add(new BasicNameValuePair("pid", par.getId().toString()));
		params.add(new BasicNameValuePair("standard", CodeCryption.encode(
				"URL", par.getStandard())));
		params.add(new BasicNameValuePair("num", sever.getNum()));
		params.add(new BasicNameValuePair("pname", CodeCryption.encode("URL",
				game.getPName())));
		params.add(new BasicNameValuePair("type", CodeCryption.encode("URL",
				"安卓代充")));
		params.add(new BasicNameValuePair("price", par.getPrice().toString()));
		params.add(new BasicNameValuePair("sign", CodeCryption.encode("MD5",
				id.toString() + user.getId().toString() + Dataconverter.KEY)));
		HttpClientTool.post("http://120.27.39.71:8099/sync/ordera3",
				params);
		return "redirect:/payment.jspx?id=" + id;
	}

	/**
	 * 取服
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getServerList.jspx" })
	public void getServerList(HttpServletRequest request,
			HttpServletResponse response) {

		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		Integer aid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"aid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "aid"));

		Server server = new Server();
		server.setGid(gid);
		server.setOid(oid);
		server.setAid(aid);
		List<Server> servers = buyService.getServerList(server);
		String json = JSON.toJSONString(servers);
		ResponseUtils.renderText(response, json);
	}

	/**
	 * 取区
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getAreaList.jspx" })
	public void getAreaList(HttpServletRequest request,
			HttpServletResponse response) {

		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));

		Area area = new Area();
		area.setGid(gid);
		area.setOid(oid);
		List<Area> areas = buyService.getAreaList(area);
		String json = JSON.toJSONString(areas);
		ResponseUtils.renderText(response, json);
	}

	/**
	 * 取面值
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getParList.jspx" })
	public void getParList(HttpServletRequest request,
			HttpServletResponse response) {

		Integer oid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"oid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "oid"));
		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		Integer tid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"tid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "tid"));

		Par par = new Par();
		par.setGid(gid);
		par.setOid(oid);
		par.setTid(tid);
		List<Par> pars = buyService.getParList(par);
		String json = JSON.toJSONString(pars);
		ResponseUtils.renderText(response, json);
	}

	@RequestMapping(value = { "/getGameList.jspx" })
	public void getGameList(HttpServletRequest request,
			HttpServletResponse response) {

		Integer plid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"plid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "plid"));

		List<Game> games = buyService.getGameListByPlid(plid);
		String json = JSON.toJSONString(games);
		ResponseUtils.renderText(response, json);
	}

	@RequestMapping(value = { "/getOperatorList.jspx" })
	public void getOperatorList(HttpServletRequest request,
			HttpServletResponse response) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));

		List<Operator> operators = buyService.getOperatorList(gid);
		String json = JSON.toJSONString(operators);
		ResponseUtils.renderText(response, json);
	}

	/**
	 * 取首充号
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "/getSCH.jspx" })
	public void getSCH(HttpServletRequest request, HttpServletResponse response) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		String account = StringUtils.isBlank(RequestUtils.getQueryParam(
				request, "account")) ? "" : RequestUtils.getQueryParam(request,
				"account");

		Hao hao = new Hao();
		hao.setGid(gid);
		hao.setAccount(Blowfish.encode(account, gid.toString()));
		hao = buyService.getHao(hao);
		String json = JSON.toJSONString(hao);
		ResponseUtils.renderText(response, json);
	}

	@RequestMapping(value = { "/verifySCH.jspx" })
	public void verifySCH(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		Integer gid = StringUtils.isBlank(RequestUtils.getQueryParam(request,
				"gid")) ? 0 : Integer.parseInt(RequestUtils.getQueryParam(
				request, "gid"));
		String account = RequestUtils.getQueryParam(request, "account");
		String msg = "{\"data\":{\"error\":\"未登录\"}}".toString();
		if (!StringUtils.isBlank(account)) {
			Hao hao = new Hao();
			hao.setGid(gid);
			hao.setAccount(Blowfish.encode(account, gid.toString()));
			hao = buyService.getHao(hao);
			if (hao != null)
				msg = "{\"data\":{\"ok\":\"\"}}".toString();
			else
				msg = "{\"data\":{\"error\":\"没有首充记录\"}}".toString();
		} else
			msg = "{\"data\":{\"error\":\"帐号不能为空\"}}".toString();
		ResponseUtils.renderText(response, msg);
	}

	@Autowired
	private SessionProvider session;
	@Autowired
	private BuyService buyService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private LoginSessionManager loginSessionManager;
}