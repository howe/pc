package cn.com.chanyue.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chanyue.dao.commom.ErrorDao;
import cn.com.chanyue.entity.common.Error;

@Component
@Transactional(readOnly = true)
public class ErrorService {

	@Autowired
	private ErrorDao dao;

	public Error queryError(String code) {
		return dao.queryError(code);
	}
}
