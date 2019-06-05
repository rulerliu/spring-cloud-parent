package com.mayikt.core.utils;

import com.mayikt.core.exception.ResultObj;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 
     * @getRequestByPost(post请求)
     * @作者:dww
     * @创建时间:2017年12月14日 下午6:19:58
     * @param requestUrl 请求地址
     * @param inParam 请求参数
     * @param charsets 请求编码
     * @return
     * @throws Exception
     */
    public static String getRequestByPost(String requestUrl, String inParam, String... charsets) throws Exception {
        String result = null;
        String charset = "UTF-8";
        if (ArrayUtils.isNotEmpty(charsets)) {
            charset = charsets[0];
        }
        HttpURLConnection httpUrlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl.toString());
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            // 添加超时时间
            httpUrlConnection.setConnectTimeout(60000);
            httpUrlConnection.setReadTimeout(60000);
            // 2、设置连接参数
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setInstanceFollowRedirects(false);
            httpUrlConnection.setRequestProperty("Content-Type", "application/json;" + charset);
            outputStream = httpUrlConnection.getOutputStream();
            if (inParam != null) {
                outputStream.write(inParam.getBytes(charset));
            }
            outputStream.flush();
            outputStream.close();
            inputStream = httpUrlConnection.getInputStream();
            result = getStreamAsString(inputStream, charset);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            } catch (Exception e) {
                logger.error("post请求异常", e);
            }
        }
        return result;
    }

    /**
     * 
     * @getResultByFormGet(Get请求)
     * @作者:dww
     * @创建时间:2017年12月14日 下午6:19:58
     * @param requestUrl 请求地址
     * @param inParam 请求参数
     * @param charsets 请求编码
     * @return
     * @throws Exception
     */
    public static String getRequestByGet(String requestUrl, String inParam, String... charsets) throws Exception {
        String result = null;
        String charset = "UTF-8";
        if (ArrayUtils.isNotEmpty(charsets)) {
            charset = charsets[0];
        }
        HttpURLConnection httpUrlConnection = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl.toString());
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            // 添加超时时间
            httpUrlConnection.setConnectTimeout(60000);
            httpUrlConnection.setReadTimeout(60000);
            // 2、设置连接参数
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setInstanceFollowRedirects(false);
            httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;" + charset);
            outputStream = httpUrlConnection.getOutputStream();
            if (inParam != null) {
                outputStream.write(inParam.getBytes(charset));
            }
            outputStream.flush();
            outputStream.close();
            inputStream = httpUrlConnection.getInputStream();
            result = getStreamAsString(inputStream, charset);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            } catch (Exception e) {
                logger.error("get请求异常:", e);
            }
        }
        return result;
    }

    /**
     * 
     * @getStreamAsString(获取输入流里面的参数)
     * @作者:dww
     * @创建时间:2017年12月14日 下午6:13:31
     * @param stream
     *            输入流
     * @param charset
     *            编码
     * @return
     * @throws Exception
     */
    public static String getStreamAsString(InputStream stream, String charset) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();
            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    /**
     * 
     * @httpGetRequest(htpp get 请求)
     * @作者:dww
     * @创建时间:2017年10月25日 上午9:38:45
     * @param url请求地址
     * @return
     */
    public static String httpGetRequest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("text/html;UTF-8"));
        org.springframework.http.HttpEntity<String> strEntity = new org.springframework.http.HttpEntity<String>(null,
                headers);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class, strEntity);
        return result;
    }

    /**
     * 
     * @postObjectJSon(实体POST请求)
     * @作者:dww
     * @创建时间:2017年11月14日 下午5:43:00
     * @param t请求实体
     * @param url请求地址
     * @return
     */
    public static <T> ResultObj postObjectJSon(T t, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        org.springframework.http.HttpEntity<T> userEntity = new org.springframework.http.HttpEntity<T>(t, headers);
        ResponseEntity<ResultObj> responseEntity = restTemplate.exchange(url, HttpMethod.POST, userEntity,
                ResultObj.class);
        return responseEntity.getBody();
    }

    /**
     * 
     * @getSplitJoint(get请求参数拼接)
     * @作者:dww
     * @创建时间:2017年11月20日 上午11:07:56
     * @param reqMap请求参数
     * @return
     */
    public static String getSplitJoint(Map<String, String> reqMap) {
        String queryString = "";
        for (String key : reqMap.keySet()) {
            Object value = reqMap.get(key);
            queryString += key + "=" + value + "&";
        }
        return queryString = queryString.substring(0, queryString.length() - 1);
    }

    /**
     * 
     * @getRemoteHost(获取客户端IP地址)
     * @作者:dww
     * @创建时间:2017年11月20日 下午2:42:48
     * @param request用户请求
     * @return 客户端访问IP
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 
     * @clientType(判断请求用户是android还是ios)
     * @作者:dww
     * @创建时间:2017年12月15日 上午10:57:48
     * @param request
     * @return 客户端类型(1.安卓;2.IOS)
     */
    public static String clientType(HttpServletRequest request) {
        String agent = request.getHeader("user-agent");
        if (agent.contains("Android")) {
            return "1";
        } else if (agent.contains("iPhone") || agent.contains("iPod") || agent.contains("iPad")) { // 通过微信进入此界面时，由于苹果市场并没有提供在浏览器中打开的提示
            return "2";
        }
        return null;
    }

    /**
     * @author Chenjh
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL){
    	String strAllParam=null;
    	String[] arrSplit=null;
    	strURL=strURL.trim();
    	arrSplit=strURL.split("[?]");
    	if(strURL.length()>1){
    		if(arrSplit.length>1){
    			if(arrSplit[1]!=null){
                  strAllParam=arrSplit[1];
                 }
    		}
    	}
    	return strAllParam;   
    }
    /**
     * 解析出url参数值的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static List<String> URLRequest(String URL){
    	List<String> listRequest = new ArrayList<String>();
	    String[] arrSplit=null;
	    String strUrlParam=TruncateUrlPage(URL);
	    if(strUrlParam==null){
	    	return listRequest;
	    }
	    //每个键值为一组 www.2cto.com
	    arrSplit=strUrlParam.split("[&]");
	    for(String strSplit:arrSplit){
          String[] arrSplitEqual=null;         
          arrSplitEqual= strSplit.split("[=]");
          //解析出键值
          if(arrSplitEqual.length>1){
              //正确解析
        	  listRequest.add(arrSplitEqual[1]);
          }
	    }   
	    return listRequest;   
    }
    
    /**
     * 
     * @httpGetRequestText(http get 请求,返回文本格式，经过处理变成JSONArray字符串)
     * @作者:chenjh
     * @创建时间:2017年10月25日 上午9:38:45
     * @param url
     * @return
     */
    public static String httpGetRequestText(String url) {
    	try {
    		List<Map<String,Object>> lists= new ArrayList<Map<String,Object>>();
    		HttpHeaders headers = new HttpHeaders();
    		headers.setContentType(MediaType.valueOf("text/html;UTF-8"));
    		org.springframework.http.HttpEntity<String> strEntity = new org.springframework.http.HttpEntity<String>(null,
    				headers);
    		RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<byte[]> response = restTemplate.exchange(url,HttpMethod.GET,strEntity,byte[].class);
    		byte[] result = response.getBody();
    		InputStream inputStream  = new ByteArrayInputStream(result);
    		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
    		String lineTxt = null;
    		while((lineTxt = bufferedReader.readLine()) != null){
    			String[] keys= lineTxt.split("\t");
    			Map<String,Object> map = new HashMap<String, Object>();
    			for(String key:keys){
    				String[] values =key.split("\n");
    				if(!StringUtils.isEmpty(values[0])){
    					map.put(values[0],values[1]);
    				}
    			}
    			lists.add(map);
    		}
    		bufferedReader.close();
//    		return (JSONArray.fromObject(lists)!=null && JSONArray.fromObject(lists).size()>0)?JSONArray.fromObject(lists).toString():null;
    		return null;
		} catch (Exception e) {
			logger.error("游戏人生查询每日用户数据接口异常", e);
			return null;
		}
    }
    
    /**
     * 
     * @isMobile(判断是移动设备还是PC电脑)
     * @作者:dww
     * @创建时间:2018年1月17日 上午10:34:52
     * @param request
     * @return
     * @throws Exception
     */
    public static boolean isMobile(HttpServletRequest request) throws Exception {
        boolean isMobile = false;
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser" + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        // 移动设备正则匹配：手机端、平板
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            isMobile = true;
        } else {
            isMobile = false;
        }
        return isMobile;
    }
    
    /**
     * 
     * @sendPost(请求微信获取openID)
     * @作者:dww
     * @创建时间:2018年1月17日 上午10:34:52
     * @param request
     * @return
     * @throws Exception
     */
    public static String sendPost(String urlString) {
        String result = null;
        URL url;
        try {
            logger.info("请求微信url：" + urlString);
            url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Encoding","UTF-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("content-type", "text/html");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setReadTimeout(10 * 1000); //10s 超时时间
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            
            conn.connect();
            //读取返回数据
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            //rtnStr = new String(sb.toString().getBytes("ISO-8859-1"),"GBK");
            conn.disconnect();
            logger.info("请求微信接收报文：" + result);
        } catch (Exception e) {
            e.printStackTrace();
            result = "ERROR";
        }
        return result;
    }
    
    /**
     * 
     * @param url 请求地址
     * @param xml xml报文
     * @param charsets 字符编码
     * @return
     * @throws Exception
     */
    public static String getRequestXmlByPost(String url, String xml, String... charsets) throws Exception {
        String resultXml = null;
        String charset = "UTF-8";
        if (ArrayUtils.isNotEmpty(charsets)) {
            charset = charsets[0];
        }
        
        RequestEntity requestEntity = new StringRequestEntity(xml.toString(), "text/xml", charset);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		postMethod.setRequestHeader("ContentType",
				"text/xml;charset=utf-8");
		postMethod.setRequestHeader("SOAPACTION", "urn:SimpleInOutMessageReceiver");
		postMethod.setRequestEntity(requestEntity);
		int statusCode = httpClient.executeMethod(postMethod);
		resultXml = postMethod.getResponseBodyAsString();
		System.out.println("状态码 ==>" + statusCode);
		System.out.println("返回报文 ==>\n" + resultXml);
        return resultXml;
    }
}
