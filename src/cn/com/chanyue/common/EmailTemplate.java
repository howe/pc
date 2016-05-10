package cn.com.chanyue.common;

/**
 * 邮件模板
 * 
 * @author howe
 * 
 */
public class EmailTemplate {

	/**
	 * 邮件头部名称
	 */
	public static final String HEADNAME = "发卡发手游服务平台 - fakafa.com";

	/**
	 * 验证邮箱-邮件内容 <br />
	 * 替换验证信息@VALID
	 */
	public static String VALIDMAIL = "亲爱的用户,<br /><br />感谢您使用发卡发手游服务平台 - fakafa.com。您在发卡发平台以此电子邮件地址完成了注册，请点击以下链接以确认您的邮箱↓<br /><a href=\"http://www.fakafa.com/validEmail.html?key=@VALID\" target=\"_blank\">http://www.fakafa.com/validEmail.html?key=@VALID</a><br />(若无法接打开，请复制↑链接用浏览器打开)<br /><br /><br /><br />谢谢！<br />fakafa 团队<br /><a href=\"http://www.fakafa.com/\" target=\"_blank\">http://www.fakafa.com/</a><br />";
	/**
	 * 验证邮箱-发件人帐号
	 */
	public static final String VALIDMAIL_FROM = "register@fakafa.com";
	/**
	 * 验证邮箱-主题
	 */
	public static final String VALIDMAIL_SUBJECT = "邮件验证";
	/**
	 * 验证邮箱-邮件头部值
	 */
	public static final String VALIDMAIL_HEADVALUE = "fakafa用户邮件验证";
	/**
	 * 验证邮箱-发件人密码
	 */
	public static final String VALIDMAIL_PWD = "fakafa.com@REGISTER#chanyue";

	/**
	 * 重置密码-邮件内容 <br />
	 * 替换密码信息@PASSWORD
	 */
	public static String RESETPASSWORD = "亲爱的用户,<br /><br />感谢您使用发卡发手游服务平台 - fakafa.com，您在发卡发平台提交了重置密码服务。 <br />您的密码已重置为：<strong><span style=\"color:#E53333;\">@PASSWORD</span></strong>，为了您的帐号安全，请尽快登录<a href=\"http://www.fakafa.com/user/modifyPswd.jhtm\" target=\"_blank\">用户中心</a>修改密码。 <br /><br /><br />谢谢！<br />fakafa 团队<br /><a href=\"http://www.fakafa.com/\" target=\"_blank\">http://www.fakafa.com/</a><br />";
	/**
	 * 重置密码-发件人帐号
	 */
	public static final String RESETPASSWORD_FROM = "password@fakafa.com";
	/**
	 * 重置密码-主题
	 */
	public static final String RESETPASSWORD_SUBJECT = "重置密码";
	/**
	 * 重置密码-邮件头部值
	 */
	public static final String RESETPASSWORD_HEADVALUE = "fakafa用户重置密码";
	/**
	 * 重置密码-发件人密码
	 */
	public static final String RESETPASSWORD_PWD = "fakafa.com@PASSWORD#chanyue";

	/**
	 * 礼卡领取-邮件内容 <br />
	 * 替换游戏名称@GAMENAME <br />
	 * 替换礼卡名称@CARDNAME <br />
	 * 替换礼卡ID@CARDID <br />
	 * 替换礼卡卡号@CARDNUMBER <br />
	 * 替换礼卡密码@CARDPASSWORD
	 */
	public static String LINGKA = "亲爱的用户,<br /><br />感谢您使用发卡发手游服务平台，您在发卡发平台购取了【@GAMENAME@CARDNAME】，已保存至您的<a href=\"http://www.fakafa.com/user/cardBox.jhtm\" target=\"_blank\">发卡箱</a>。<br /><br />相关信息如下↓<br />卡号：<strong><span style=\"color:#E53333;\">@CARDNUMBER</span></strong>，密码：<strong><span style=\"color:#337FE5;\">@CARDPASSWORD</span></strong>，使用说明参见<a href=\"http://www.fakafa.com/card-@CARDID.jhtm\" target=\"_blank\">卡详细页面</a>。<br /><br />说明：<span style=\"color:#666666;\">为防止卡被恶意滥领，已成功领取的卡将会在3小时后被释放到淘卡池，请领卡后尽快使用</span>。<br /><br /><br />谢谢！<br />fakafa 团队<br /><a href=\"http://www.fakafa.com/\" target=\"_blank\">http://www.fakafa.com/</a><br />	";
	/**
	 * 礼卡领取-发件人帐号
	 */
	public static final String LINGKA_FROM = "ka@fakafa.com";
	/**
	 * 礼卡领取-主题
	 */
	public static final String LINGKA_SUBJECT = "礼卡发放";
	/**
	 * 礼卡领取-邮件头部值
	 */
	public static final String LINGKA_HEADVALUE = "fakafa礼卡发放";
	/**
	 * 礼卡领取-发件人密码
	 */
	public static final String LINGKA_PWD = "fakafa.com@KA#chanyue";

	/**
	 * 快捷登录-邮件内容 <br />
	 * 替代快捷登录名称@APPID <br />
	 * 替代邮箱地址@EMAIL <br />
	 * 替代随机密码@PASSWORD <br />
	 * 替换验证信息@VALID
	 */
	public static final String FAST = "亲爱的用户,<br /><br />感谢您使用发卡发手游服务平台，您在发卡发平台选择用<strong><span style=\"color:#337FE5;\">@APPID</span></strong>登录。 <br />您可以使用<strong><span style=\"color:#337FE5;\">@EMAIL</span></strong>登录发卡发平台<br />初始随机密码为：<strong><span style=\"color:#337FE5;\">@PASSWORD</span></strong>，为了您的帐号安全，请尽快登录<a href=\"http://www.fakafa.com/user/modifyPswd.jhtm\" target=\"_blank\">用户中心</a>修改密码。 <br /><br />请点击以下链接以确认您的邮箱↓<br /><a href=\"http://www.fakafa.com/validEmail.html?key=@VALID\" target=\"_blank\">http://www.fakafa.com/validEmail.html?key=@VALID</a><br />(若无法接打开，请复制↑链接用浏览器打开)<br /><br /><br />谢谢！<br />fakafa 团队<br /><a href=\"http://www.fakafa.com/\" target=\"_blank\">http://www.fakafa.com/</a><br />";
	/**
	 * 快捷登录-发件人帐号
	 */
	public static final String FAST_FROM = "fast@fakafa.com";
	/**
	 * 快捷登录-主题
	 */
	public static final String FAST_SUBJECT = "快捷登录";
	/**
	 * 快捷登录-邮件头部值
	 */
	public static final String FAST_HEADVALUE = "fakafa快捷登录";
	/**
	 * 快捷登录-发件人密码
	 */
	public static final String FAST_PWD = "fakafa.com@FAST#chanyue";
}
