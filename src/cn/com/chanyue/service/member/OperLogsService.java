package cn.com.chanyue.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chanyue.dao.member.OperLogsDao;
import cn.com.chanyue.entity.member.OperLogs;

@Component
@Transactional(readOnly = true)
public class OperLogsService {

	@Autowired
	private OperLogsDao dao;
	
	// 查询日志总数
	public Integer getOperTotalNum(Integer uid){
		return dao.getOperTotalNum(uid);
	}
	
	// 按页数查询
	public List<OperLogs> getOperLogs(Integer page){
		return dao.getOperLogs(page);
	}
	
	//插入操作日志
	public void insertOperLogs(OperLogs operLogs){
		dao.insertOperLogs(operLogs);
	}
}
