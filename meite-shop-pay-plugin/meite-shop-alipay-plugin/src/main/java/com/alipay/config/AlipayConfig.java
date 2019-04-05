package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/5 0005 23:28
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class AlipayConfig {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092000555908";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDMBP5T4uicBJxnjNZSklsXBavkxK835uDhdbd6jcOm2j3pvL6RzTB88q3lGYXg0i1w/dRSdZ6o0PqSDIlbk9vzXMSPoDwjZtgQjHrcCLvttaHiUUi3xPzTDsmmTzjbkFKLf+ZctPZOw0chaGEyeOfIoykRkBAi1SUI0s2icv/DaLEoHe0VcF0FK1zvzBg5k1K3OUJfvAPCzWl8rZNkiWzH/EkQtOQG9aSGestpJxsnLd5Wx4xJEaXYHAZNKD84lxXclE6MZMxGJlqGkwSIgSltZv+6xBjBwQ4dIROmcOJMgkteJFc6VmhpXOqm7w0u3A7VdAekBBfh7Gz14a/76TsnAgMBAAECggEAK0/QEkz0ptKPxafvg/4QGnYYxOtAa3S3PwX3mqvn4k8on6tzJY7TAWOfqShe9JiKDxfD8HGJ6jrbRxAkRO3B3wtNq1QQEE/wBKteFcJA6zkJEfvBnUnThpb4FjdxUaN08qqHZB0CwTvRqPT41Xc3da+1s0XLNynav+HIx/jz9S6uY+a9Ccvbk5w09zR1hayBV2rU5+chFakTgS/53EEo8Wv3QII4s48FJX2UaIPYnAewJsi1t+snNz1bBBSzJPSW1SSye1ipbYrNLfKrEYMYJ8kNUe4sKYV+fhZGgvf5SI5IcvT2SLnkYiJFsaoT5Eoooccz4EBuCdIDoX/8sjIgeQKBgQDx+6+HsCbrYqVmqjYTh27xLFQsAdAQvKbtGnGLwa/KuLX+WYXXuW3Qu1gM4wQnzk1/thx0ahOJp2PXIYo+Ei3zlUC4cBMqBu4LLJ24MhO1zQ3/fkAW8a5qdKyv1sC1wx6sbpU7idmaGu/EynHm4/afK/HTKw6plwTpRMfzokyjfQKBgQDX1lp4TRMHLpHyv/T8boowMe1qqZUCX6B5p9n5nG2aSL1YoruMCkPc6RiW2gk+T7B4kCEVZNZFgnXdobbJBI851erOEHwpFC2Rx7xZhvIMcOiTGyD+opzSzoKe2kJgTgBxxxJCY/VcbYwU4RMeQ2zFIa6z4nDMQdBScZvk5oEScwKBgBEEk06mm8YYFOvIcq8i6jF53Z2OrxROQCsSngOnJnbKULpHj3shZlC0rDeTy5oFDzw5EX06bI9Uv1fE2xUMcEDeMUQHVcGx5RHSHHF0zCAQALkEJvu2GX8RH4fqQXlG9KRJzqL2SeBlnpEnb6U+SPzeuU4RjCFvJoBFPw6qTlt9AoGAF0OrjLwCCQwj/x5hsbPYgUlgZdbRkOftzC8jd5w81BxzDFK+FGIm3nDhjaECzgyY4OXoKv4remKNgc4kfZZYOcus+3kbaPIb11LIIn5BKMYiy3hpHgk5Dh/kRGsSLH873QewGCfeEpBcQQPho6lw1+7BffIyZlHRupE0VH/j+bECgYBD+rcGQjZQA+RL8TD+MWjJNFMwuDkK4/SL4tWl+84ajjMjfnMMrqsOcwVPpnY3qDo2mzXc8qmiabVmllwW9Rx9g0bS+v3cemzIukrbo43RIrXD4eivPOFmGWxtT4DGQn+TFLVfBasKQ4zw9kWj26sUcAQFSxGTwVcIakxldXcqTQ==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4NrY7pyZYozo2WVc5DQrGISPBOw5ISER73w3k4/m4AsB49+63byWrct/C2SRVBgsM6JidYkNYEvhtZG/4xqh7+cU5wGrz+8HcIVhNW3IrSn6N32DjcOuJ4XkU0+9eFegLPfsTqwUJJqtxge2kkDicV72UXlNRwqwCKkIxLVjZnMmOyyxcCzSzkvhrDSLYWRdkwFppSq1VUwdAuZ09dqoD++L6oS43CEw6/2edzWEziF6dGNixTJ2oaZu0l3x0gBZLOsAIYS4KaVG932UF+j4cOiDMcC7eB2WJRT9BJo7ALckArnByKJnPY97e69OVmLqN38fJBl+6RkZNf+LmWb9KwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://4rhi5d.natappfree.cc/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://4rhi5d.natappfree.cc/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
