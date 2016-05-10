package cn.com.chanyue.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chanyue.dao.member.UsersDao;
import cn.com.chanyue.entity.member.Captcha;
import cn.com.chanyue.entity.member.SocialBind;
import cn.com.chanyue.entity.member.Users;

@Component
@Transactional(readOnly = true)
public class UsersService {

	@Autowired
	private UsersDao dao;

	// 通过UID查询用户信息
	public Users getById(Integer uid) {
		return dao.getById(uid);
	}

	// 用户登录
	public Users login(Users user) {
		return dao.login(user);
	}

	// 用户成功登录后重置登录密钥与加密串
	public void lastLogin(Users user) {
		dao.lastLogin(user);
	}

	// 更新用户密码
	public void modifyPassword(Users user) {
		dao.modifyPassword(user);
	}

	// 注册用户
	public Integer insert(Users user) {
		return dao.insert(user);
	}

	// 通过EMAIL查询用户
	public Users getByEmail(String email) {
		return dao.getByEmail(email);
	}
	
	public Users getByUserName(String userName) {
		return dao.getByUserName(userName);
	}

	// 通过QQ查询用户
	public Users getByQQ(String qq) {
		return dao.getByQQ(qq);
	}

	// 通过手机号码查询用户
	public Users getByMobile(String mobile) {
		return dao.getByMobile(mobile);
	}

	// 验证EMAIL是否存在
	public Users isExistEmail(String email) {
		return dao.isExistEmail(email);
	}

	// 验证QQ是否存在
	public Users isExistQQ(String qq) {
		return dao.isExistQQ(qq);
	}

	// 验证手机号码是否存在
	public Users isExistMobile(String mobile) {
		return dao.isExistMobile(mobile);
	}
	
	public void insertCaptcha(Captcha captcha){
		dao.insertCaptcha(captcha);
	}
	
	public Captcha validCaptcha(Captcha captcha){
		return dao.validCaptcha(captcha);
	}
	
	public void validEmail(Integer id){
		dao.validEmail(id);
	}
	
	public void deleteCaptcha(Captcha captcha){
		dao.deleteCaptcha(captcha);
	}
	
	public void insertSocialBind(SocialBind socialBind){
		dao.insertSocialBind(socialBind);
	}
	
	public void updateToken(SocialBind socialBind){
		dao.updateToken(socialBind);
	}
	
	public SocialBind getSocialBind(SocialBind socialBind){
		return dao.getSocialBind(socialBind);
	}
}
