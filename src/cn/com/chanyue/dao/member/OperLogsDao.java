package cn.com.chanyue.dao.member;

import java.util.List;

import cn.com.chanyue.dao.MyBatisRepository;
import cn.com.chanyue.entity.member.OperLogs;

@MyBatisRepository
public interface OperLogsDao {

	Integer getOperTotalNum(Integer uid);
	
	List<OperLogs> getOperLogs(Integer page);
	
	void insertOperLogs(OperLogs operLogs);
}
