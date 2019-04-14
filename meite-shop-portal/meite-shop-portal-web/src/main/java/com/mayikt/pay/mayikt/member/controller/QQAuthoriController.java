package com.mayikt.pay.mayikt.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.constants.Constants;
import com.mayikt.pay.mayikt.constants.MemberLoginConstants;
import com.mayikt.pay.mayikt.member.feign.QQAuthoriServiceFeign;
import com.mayikt.pay.mayikt.web.base.BaseWebController;
import com.mayikt.pay.mayikt.web.bean.MeiteBeanUtils;
import com.mayikt.pay.mayikt.web.constants.WebConstants;
import com.mayikt.pay.mayikt.web.utils.CookieUtils;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.constants.PageConstants;
import com.mayikt.pay.mayikt.member.controller.req.vo.LoginVO;
import com.mayikt.pay.mayikt.member.feign.MemberLoginServiceFeign;
import com.mayikt.pay.mayikt.member.input.dto.UserLoginInpDTO;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: QQ授权	http://shop.mayikt.com/qqAuth
 * @author: liuwq
 * @date: 2019年  下午8:31:58
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 *            私自分享视频和源码属于违法行为。
 */
@Controller
public class QQAuthoriController extends BaseWebController {
	
	private static final String MB_QQ_QQLOGIN = "member/qqlogin";
	
	@Autowired
	private QQAuthoriServiceFeign qqAuthoriServiceFeign;
	
	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;

	/**
	 * 生成授权链接
	 */
	@RequestMapping("/qqAuth")
	public String qqAuth(HttpServletResponse response, HttpServletRequest request) {
		try {
//			response.sendRedirect(new Oauth().getAuthorizeURL(request));
			String authorizeURL = new Oauth().getAuthorizeURL(request);
			return "redirect:" + authorizeURL;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR_500_FTL;
		}
	}
	
	/**
	 * 登录回调方法
	 */
	@RequestMapping("/qqLoginBack")
	public String qqLoginBack(HttpSession session, HttpServletRequest request, HttpServletResponse response, String code) {
		try {
			// 1. 使用授权码code获取accessToken
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			if (accessTokenObj == null) {
				return ERROR_500_FTL;
			}
			
			String accessToken = accessTokenObj.getAccessToken();
			if (StringUtils.isBlank(accessToken)) {
				return ERROR_500_FTL;
			}
			
			// 2. 使用accessToken获取用户openId
			OpenID openIDObj =  new OpenID(accessToken);
            String qqOpenId = openIDObj.getUserOpenID();
            if (StringUtils.isBlank(qqOpenId)) {
				return ERROR_500_FTL;
			}
            
			// 使用openId查询数据库是否已经关联账号信息
            BaseResponse<JSONObject> findByOpenId = qqAuthoriServiceFeign.findByOpenId(qqOpenId);
            if (!isSuccess(findByOpenId)) {
            	return ERROR_500_FTL;
            }
            
            // 如果调用接口返回203，则跳转到关联账号页面
            if (Constants.HTTP_RES_CODE_NOTUSER_203.equals(findByOpenId.getCode())) {
            	// 3. 使用openId获取用户信息
                UserInfo qzoneUserInfo = new UserInfo(accessToken, qqOpenId);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean == null) {
                	return ERROR_500_FTL;
                }
                
                // 用户的QQ头像
                String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
                request.setAttribute("avatarURL100", avatarURL100);
                
                // 把openId存在session
                session.setAttribute(WebConstants.LOGIN_QQ_OPENID, qqOpenId);
            	return MB_QQ_QQLOGIN;
            }
            
            // 如果能查询到用户信息，自动登录
            JSONObject data = findByOpenId.getData();
            String token = data.getString("token");
            CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIE_NAME, token);
            
			return PageConstants.MB_REDIRECT_INDEX_FTL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/qqJointLogin")
	public String qqJointLogin(@ModelAttribute("loginVo") LoginVO loginVO, Model model, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {

		// 1.获取用户openid
		String qqOpenId = (String) httpSession.getAttribute(WebConstants.LOGIN_QQ_OPENID);
		if (StringUtils.isEmpty(qqOpenId)) {
			return ERROR_500_FTL;
		}

		// 2.将vo转换dto调用会员登陆接口
		UserLoginInpDTO userLoginInpDTO = MeiteBeanUtils.voToDto(loginVO, UserLoginInpDTO.class);
		userLoginInpDTO.setQqOpenId(qqOpenId);
		userLoginInpDTO.setLoginType(MemberLoginConstants.MEMBER_LOGIN_TYPE_PC);
		String info = getWebBrowserInfo(request);
		userLoginInpDTO.setDeviceInfor(info);
		BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
		if (!isSuccess(login)) {
			setErrorMsg(model, login.getMsg());
			return MB_QQ_QQLOGIN;
		}
		
		// 3.登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
		JSONObject data = login.getData();
		String token = data.getString("token");
		CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIE_NAME, token);
		return PageConstants.MB_REDIRECT_INDEX_FTL;
	}
	
}
