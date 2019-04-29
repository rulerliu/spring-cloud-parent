package com.mayikt.pay.mayikt.weixin.mp.handler;

import com.mayikt.base.BaseResponse;
import com.mayikt.constants.Constants;
import com.mayikt.core.utils.RedisUtil;
import com.mayikt.core.utils.RegexUtils;
import com.mayikt.member.output.dto.UserOutDTO;
import com.mayikt.pay.mayikt.weixin.feign.MemberServiceFeign;
import com.mayikt.pay.mayikt.weixin.mp.builder.TextBuilder;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {
	
	/**
	 * 发送验证码消息
	 */
	@Value("${mayikt.weixin.registration.code.message}")
	private String registrationCodeMessage;
	/**
	 * 默认回复消息
	 */
	@Value("${mayikt.weixin.default.registration.code.message}")
	private String defaultRegistrationCodeMessage;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private MemberServiceFeign memberServiceFeign;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        
        // 1.获取微信客户端发送的消息
        String phoneNum = wxMessage.getContent();
        // 2.判断内容格式
        if (RegexUtils.checkMobile(phoneNum)) {
        	// 检查手机号码是否已注册
			BaseResponse<UserOutDTO> userOutDTO = memberServiceFeign.existMobile(phoneNum);
			if (Constants.HTTP_RES_CODE_200.equals(userOutDTO.getCode())) {
        		return new TextBuilder().build("该手机号码：" + phoneNum + "已经注册了", wxMessage, weixinService);
        	}
        	
        	if (!Constants.HTTP_RES_CODE_EXISTMOBILE_203.equals(userOutDTO.getCode())) {
        		return new TextBuilder().build(userOutDTO.getMsg(), wxMessage, weixinService);
        	}
        	
        	// 3.如果是手机号码格式，生成4位随机数注册码
        	int registCode = getRegistCode();
        	// 4.把注册码存放到redis，有效期30分钟
        	String replyContent = String.format(registrationCodeMessage, registCode);
        	redisUtil.setString(Constants.WEIXINCODE_KEY + phoneNum, registCode + "", Constants.WEIXINCODE_TIMEOUT);
        	return new TextBuilder().build(replyContent, wxMessage, weixinService);
        }

        //TODO 组装回复消息
//        String content = "收到信息内容：" + JsonUtils.toJson(wxMessage) + "完毕！！！";
        
        // 否则情况下返回默认消息，或者调用第三方机器人接口
        return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);

    }
    
    // 获取注册码
 	private int getRegistCode() {
 		int registCode = (int) (Math.random() * 9000 + 1000);
 		return registCode;
 	}

 	public static void main(String[] args) {
		String test = "haklsdjajlfkjslkaf%sksldjjdlf";
		String format = String.format(test, 1234567);
		// haklsdjajlfkjslkaf1234567ksldjjdlf
		System.out.println(format);
	}
 	
}
