package cn.com.chanyue.dao.chong;

import java.util.List;

import cn.com.chanyue.dao.MyBatisRepository;
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

@MyBatisRepository
public interface BuyDao {
	
	Integer getDiscount(Par par);
	List<GS> getGS1(String gName);
	List<GS> getGS21(String gName);
	List<GS> getGS22(String gName);
	List<GS> getGS23(String gName);
	List<GS> getGS24(String gName);
	List<GS> getGS25(String gName);
	List<GS> getGS3(String gName);
	List<GS> getGS4(String gName);
	List<GS> getGS5(String gName);

	Game getGameById(Integer id);
	Game getGameByName(Game game);
	List<Game> getGameList(Integer pid);
	List<Game> getGameHotList(Integer pid);
	List<Game> getGameSortList(Game game);
	List<Game> getGameListIndex(Integer pid);
	
	Operator getOperatorById(Integer id);
	List<Operator> getOperatorList(Integer gid);
	List<Operator> getOperatorListF(Integer gid);
	
	Par getParById(Integer id);
	List<Par> getParList(Par par);
	
	Platform getPlatformById(Integer id);	
	List<Platform> getPlatformList();
	
	Server getServerById(Integer id);
	List<Server> getServerList(Server server);
	
	Area getAreaById(Integer id);
	List<Area> getAreaList(Area area);
	
	Hao getHaoById(Integer id);
	Hao getHaoByOrderId(Integer orderId);
	Hao getHao(Hao hao);
	
	Order getOrderById(Integer id);
	Order getDetail(Order order);
	List<Order> getOrderList(Integer id);
	
	Integer insertOrder(Order order);
	Integer insertHao(Hao hao);
	
	Integer insertPayment(Payment payment);
	
	Payment getPaymentById(Integer id);
	
	Payment verifyPayment(String tsn);
	
	Payway getPaywayById(Integer id);
	
	void paymentOrder(Payment payment);
	
	Payment getPaymentInfo(Integer orderId);
	List<Game> getGameListByPlid(Integer plid);
	List<Game> getGameSitemap();
	List<Game> search(String gName);
}
