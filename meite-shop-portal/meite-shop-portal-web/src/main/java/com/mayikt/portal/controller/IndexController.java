package com.mayikt.portal.controller;

import com.mayikt.base.BaseResponse;
import com.mayikt.constants.MemberLoginConstants;
import com.mayikt.constants.PageConstants;
import com.mayikt.member.feign.MemberServiceFeign;
import com.mayikt.member.output.dto.UserOutDTO;
import com.mayikt.web.base.BaseWebController;
import com.mayikt.web.constants.WebConstants;
import com.mayikt.web.utils.CookieUtils;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Auther: liuwq
 * @Date: 2019/1/27 0027 17:29
 * @Version: v1.0
 * @Copyright: 该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下，
 * 私自分享视频和源码属于违法行为。
 */
@Controller
public class IndexController extends BaseWebController {
    
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    /**
     * 跳转index页面
     */
    @GetMapping("/")
    public String indexPage(HttpServletRequest request, Model model) {
        // 从cookie中获取登录token
        String token = CookieUtils.getCookieValue(request, WebConstants.LOGIN_TOKEN_COOKIE_NAME);
        if (!StringUtils.isBlank(token)) {
            // 2.调用会员服务接口,查询会员用户信息
            BaseResponse<UserOutDTO> userInfo = memberServiceFeign.getUserInfo(token);
            if (isSuccess(userInfo)) {
                UserOutDTO data = userInfo.getData();
                if (data != null) {
                    String mobile = data.getMobile();
                    // 对手机号码实现脱敏
                    String desensMobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                    model.addAttribute("desensMobile", desensMobile);
                }

            }
        }
        return PageConstants.MB_INDEX_FTL;
    }

}
