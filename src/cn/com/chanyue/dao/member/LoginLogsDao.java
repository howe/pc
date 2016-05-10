package cn.com.chanyue.dao.member;

import java.util.List;

import cn.com.chanyue.dao.MyBatisRepository;
import cn.com.chanyue.entity.common.Page;
import cn.com.chanyue.entity.member.LoginLogs;

@MyBatisRepository
public interface LoginLogsDao {

	Integer getLoginTotalNum(Integer uid);
	
	List<LoginLogs> getLoginLogs(Page page);
	
	void insertLoginLogs(LoginLogs loginLogs);
	
	LoginLogs queryPreLogin(Integer uid);
}
