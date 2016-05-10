package cn.com.chanyue.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chanyue.dao.member.RealAuthDao;
import cn.com.chanyue.entity.member.RealAuth;

@Component
@Transactional(readOnly = true)
public class RealAuthService {

	@Autowired
	private RealAuthDao dao;
	
	public RealAuth queryRealAuth(Integer uid){
		return dao.queryRealAuth(uid);
	}
	
	public void addRealAuth(RealAuth realAuth){
		dao.addRealAuth(realAuth);
	}
	
	public void updateRealAuth(Integer uid){
		dao.updateRealAuth(uid);
	}
}
