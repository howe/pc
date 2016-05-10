package cn.com.chanyue.service.chong;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chanyue.dao.chong.BuyDao;
import cn.com.chanyue.entity.chong.Area;
import cn.com.chanyue.entity.chong.GS;
import cn.com.chanyue.entity.chong.Game;
import cn.com.chanyue.entity.chong.Hao;
import cn.com.chanyue.entity.chong.Operator;
import cn.com.chanyue.entity.chong.Order;
import cn.com.chanyue.entity.chong.Par;
import cn.com.chanyue.entity.chong.Payment;
import cn.com.chanyue.entity.chong.Payway;
import cn.com.chanyue.entity.chong.Platform;
import cn.com.chanyue.entity.chong.Server;

@Component
@Transactional(readOnly = true)
public class BuyService {

	@Autowired
	private BuyDao dao;

	public Integer getDiscount(Par par){
		return dao.getDiscount(par);
	}
	
	public List<Game> getGameListByPlid(Integer plid){
		return dao.getGameListByPlid(plid);
	}
	
	public Payment getPaymentInfo(Integer orderId){
		return dao.getPaymentInfo(orderId);
	}
	
	public List<Game> search(String gName){
		return dao.search(gName);
	}
	
	public List<GS> getGS1(String gName) {
		return dao.getGS1(gName);
	}

	public List<GS> getGS21(String gName) {
		return dao.getGS21(gName);
	}

	public List<GS> getGS22(String gName) {
		return dao.getGS22(gName);
	}
	
	public List<Game> getGameSitemap() {
		return dao.getGameSitemap();
	}

	public List<GS> getGS23(String gName) {
		return dao.getGS23(gName);
	}

	public List<GS> getGS24(String gName) {
		return dao.getGS24(gName);
	}

	public List<GS> getGS25(String gName) {
		return dao.getGS25(gName);
	}

	public List<GS> getGS3(String gName) {
		return dao.getGS1(gName);
	}

	public List<GS> getGS4(String gName) {
		return dao.getGS1(gName);
	}

	public List<GS> getGS5(String gName) {
		return dao.getGS1(gName);
	}

	// 通过ID查询游戏信息
	public Game getGameById(Integer id) {
		return dao.getGameById(id);
	}

	public Game getGameByName(Game game) {
		return dao.getGameByName(game);
	}

	public List<Game> getGameList(Integer plid) {
		return dao.getGameList(plid);
	}
	
	public List<Game> getGameSortList(Game game) {
		return dao.getGameSortList(game);
	}
	
	public List<Game> getGameHotList(Integer plid) {
		return dao.getGameHotList(plid);
	}

	public List<Game> getGameListIndex(Integer pid) {
		return dao.getGameListIndex(pid);
	}

	public Par getParById(Integer id) {
		return dao.getParById(id);
	}

	public Hao getHaoById(Integer id) {
		return dao.getHaoById(id);
	}

	public Hao getHaoByOrderId(Integer orderId) {
		return dao.getHaoByOrderId(orderId);
	}

	public Hao getHao(Hao hao) {
		return dao.getHao(hao);
	}

	public List<Par> getParList(Par par) {
		return dao.getParList(par);
	}

	public Operator getOperatorById(Integer id) {
		return dao.getOperatorById(id);
	}

	public List<Operator> getOperatorList(Integer gid) {
		return dao.getOperatorList(gid);
	}

	public List<Operator> getOperatorListF(Integer gid) {
		return dao.getOperatorListF(gid);
	}

	public Server getServerById(Integer id) {
		return dao.getServerById(id);
	}

	public Area getAreaById(Integer id) {
		return dao.getAreaById(id);
	}

	public List<Server> getServerList(Server server) {
		return dao.getServerList(server);
	}

	public Platform getPlatformById(Integer id) {
		return dao.getPlatformById(id);
	}

	public List<Platform> getPlatformList() {
		return dao.getPlatformList();
	}

	public List<Area> getAreaList(Area area) {
		return dao.getAreaList(area);
	}

	public Order getOrderById(Integer id) {
		return dao.getOrderById(id);
	}

	public Order getDetail(Order order) {
		return dao.getDetail(order);
	}

	public List<Order> getOrderList(Integer id) {
		return dao.getOrderList(id);
	}

	public Integer insertOrder(Order order) {
		return dao.insertOrder(order);
	}

	public Integer insertHao(Hao hao) {
		return dao.insertHao(hao);
	}

	public Integer insertPayment(Payment payment) {
		return dao.insertPayment(payment);
	}

	public Payment getPaymentById(Integer id) {
		return dao.getPaymentById(id);
	}
	
	public Payment verifyPayment(String tsn) {
		return dao.verifyPayment(tsn);
	}

	public Payway getPaywayById(Integer id) {
		return dao.getPaywayById(id);
	}

	public void paymentOrder(Payment payment) {
		dao.paymentOrder(payment);
	}
}