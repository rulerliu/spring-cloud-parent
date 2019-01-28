package com.mayikt.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.mayikt.base.BaseResponse;
import com.mayikt.constants.PageConstants;
import com.mayikt.member.controller.req.vo.RegisterVO;
import com.mayikt.member.feign.MemberRegisterServiceFeign;
import com.mayikt.member.input.dto.UserInpDTO;
import com.mayikt.web.base.BaseWebController;
import com.mayikt.web.bean.MeiteBeanUtils;
import com.mayikt.web.utils.RandomValidateCodeUtil;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Auther: liuwq
 * @Date: 2019/1/27 0027 17:39
 * @Version: v1.0
 * @Copyright: 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 * 私自分享视频和源码属于违法行为。
 */
@Controller
public class RegisterController extends BaseWebController {

    @Autowired
    private MemberRegisterServiceFeign memberRegisterServiceFeign;

    /**
     * 跳转注册页面
     */
    @GetMapping("/register.html")
    public String registerPage() {
        return PageConstants.MB_REGISTER_FTL;
    }

    /**
     * 接收请求参数
     */
    @PostMapping("/register")
    public String register(@ModelAttribute(value = "registerVO") @Validated RegisterVO registerVO, BindingResult bindingResult,
                           Model model, HttpSession httpSession) {
        // 1.接收表单参数并做校验
        if (bindingResult.hasErrors()) {
            // 如果参数有校验错误，获取第一个错误显示到页面
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errorMsg);
            return PageConstants.MB_REGISTER_FTL;
        }

        // 验证码校验
        String graphicCode = registerVO.getGraphicCode();
        Boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        if (!checkVerify) {
            setErrorMsg(model, "验证码错误");
            return PageConstants.MB_REGISTER_FTL;
        }

        UserInpDTO userInpDTO = MeiteBeanUtils.voToDto(registerVO, UserInpDTO.class);
        String registCode = registerVO.getRegistCode();
        userInpDTO.setUserName("liuwq");
        userInpDTO.setEmail("729754701@qq.com");
        BaseResponse<JSONObject> register = memberRegisterServiceFeign.register(userInpDTO, registCode);
        if (!isSuccess(register)) {
            setErrorMsg(model, register.getMsg());
            return PageConstants.MB_REGISTER_FTL;
        }

        return PageConstants.MB_LOGIN_FTL;
    }

}
