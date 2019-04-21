package com.mayikt.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.pay.mayikt.base.BaseResponse;
import com.mayikt.pay.mayikt.constants.MemberLoginConstants;
import com.mayikt.pay.mayikt.constants.PageConstants;
import com.mayikt.member.controller.req.vo.LoginVO;
import com.mayikt.member.input.dto.UserLoginInpDTO;
import com.mayikt.pay.mayikt.web.base.BaseWebController;
import com.mayikt.pay.mayikt.web.bean.MeiteBeanUtils;
import com.mayikt.pay.mayikt.web.constants.WebConstants;
import com.mayikt.pay.mayikt.web.utils.CookieUtils;
import com.mayikt.pay.mayikt.web.utils.RandomValidateCodeUtil;
import com.mayikt.member.feign.MemberLoginServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Auther: liuwq
 * @Date: 2019/1/27 0027 17:34
 * @Version: v1.0
 * @Copyright: 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 * 私自分享视频和源码属于违法行为。
 */
@Controller
public class LoginController extends BaseWebController {

    @Autowired
    private MemberLoginServiceFeign memberLoginServiceFeign;

    /**
     * 跳转登录页面
     */
    @GetMapping("/login.html")
    public String loginPage() {
        return PageConstants.MB_LOGIN_FTL;
    }

    /**
     * 接收请求参数
     */
    @PostMapping("/login")
    public String postLogin(@ModelAttribute(value = "loginVO") @Validated LoginVO loginVO, BindingResult bindingResult,
                            Model model, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
        // 1.接收表单参数并做校验
        if (bindingResult.hasErrors()) {
            // 如果参数有校验错误，获取第一个错误显示到页面
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errorMsg);
            return PageConstants.MB_LOGIN_FTL;
        }
        // 验证码校验
        String graphicCode = loginVO.getGraphicCode();
        Boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        if (!checkVerify) {
            setErrorMsg(model, "验证码错误");
            return PageConstants.MB_LOGIN_FTL;
        }

        UserLoginInpDTO userLoginInpDTO = MeiteBeanUtils.voToDto(loginVO, UserLoginInpDTO.class);
        userLoginInpDTO.setLoginType(MemberLoginConstants.MEMBER_LOGIN_TYPE_PC);
        userLoginInpDTO.setDeviceInfor(getWebBrowserInfo(request));
        BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
        if (!isSuccess(login)) {
            setErrorMsg(model, login.getMsg());
            return PageConstants.MB_LOGIN_FTL;
        }

        // 保存登录信息，存入cookie
        JSONObject data = login.getData();
        String token = data.getString("token");
        CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIE_NAME, token);

        return PageConstants.MB_REDIRECT_INDEX_FTL;
    }

}
