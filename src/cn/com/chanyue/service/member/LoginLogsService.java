package cn.com.chanyue.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chanyue.dao.member.LoginLogsDao;
import cn.com.chanyue.entity.common.Page;
import cn.com.chanyue.entity.member.LoginLogs;

@Component
@Transactional(readOnly = true)
public class LoginLogsService {

	@Autowired
	private LoginLogsDao dao;
	
	// 查询日志总数
	public Integer getLoginTotalNum(Integer uid){
		return dao.getLoginTotalNum(uid);
	}
	
	// 按页数查询
	public List<LoginLogs> getLoginLogs(Page page){
		return dao.getLoginLogs(page);
	}
		
	//插入用户登录日志
	public void insertLoginLogs(LoginLogs loginLogs){
		dao.insertLoginLogs(loginLogs);
	}
	
	public LoginLogs queryPreLogin(Integer uid){
		return dao.queryPreLogin(uid);
	}
}
