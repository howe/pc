package cn.com.chanyue.dao.member;

import cn.com.chanyue.dao.MyBatisRepository;
import cn.com.chanyue.entity.member.Captcha;
import cn.com.chanyue.entity.member.SocialBind;
import cn.com.chanyue.entity.member.Users;

@MyBatisRepository
public interface UsersDao {

	Users getById(Integer id);
	
	Users login(Users user);
	
	Integer insert(Users user);
	
	void lastLogin(Users user);
	
	void modifyPassword(Users user);
	
	void validEmail(Integer id);
	
	Users getByEmail(String email);
	
	Users getByUserName(String userName);
	
	Users getByQQ(String qq);
	
	Users getByMobile(String mobile);
	
	Users isExistEmail(String email);
	
	Users isExistQQ(String qq);
	
	Users isExistMobile(String mobile);
	
	void insertCaptcha(Captcha captcha);
	
	void deleteCaptcha(Captcha captcha);
	
	Captcha validCaptcha(Captcha captcha);

	void insertSocialBind(SocialBind socialBind);

	void updateToken(SocialBind socialBind);

	SocialBind getSocialBind(SocialBind socialBind);
}
