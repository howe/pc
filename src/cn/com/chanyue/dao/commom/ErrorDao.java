package cn.com.chanyue.dao.commom;

import cn.com.chanyue.dao.MyBatisRepository;
import cn.com.chanyue.entity.common.Error;

@MyBatisRepository
public interface ErrorDao {
	
	Error queryError(String code);
}