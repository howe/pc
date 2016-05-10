package cn.com.chanyue.common.tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.chanyue.common.TemplateConstants;
import cn.com.chanyue.entity.member.Users;

import com.jeecms.common.web.session.SessionProvider;

@Component
public class LoginSessionManager {
    @Autowired
    SessionProvider sessionProvider;
    
    /**
     * 根据登录信息初始化session
     * @param request
     * @param userMaster
     */
    public void initSession(HttpServletRequest request, Users users){
        sessionProvider.setAttribute(request, null, TemplateConstants.AUTHEN_KEY, users);
    }
    
    /**
     * 判断当前是否处于登录状态
     * @param request
     * @return
     */
    public boolean checkLogin(HttpServletRequest request){
        return getCurrentUser(request) != null;
    }
    
    /**
     * 得到当前用户
     * @param request
     * @return 如果登录了，就返回当前的用户，否则返回null
     */
    public Users getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            Object auth = session.getAttribute(TemplateConstants.AUTHEN_KEY);
            if(auth != null){
                return (Users) auth;
            }
            else
            {
                return null;
            }
        }else {
            return null;
        }
    }
}