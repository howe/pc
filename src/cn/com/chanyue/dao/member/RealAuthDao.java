package cn.com.chanyue.dao.member;

import cn.com.chanyue.dao.MyBatisRepository;
import cn.com.chanyue.entity.member.RealAuth;

@MyBatisRepository
public interface RealAuthDao {

	RealAuth queryRealAuth(Integer uid);
	
	void addRealAuth(RealAuth realAuth);
	
	void updateRealAuth(Integer uid);
}