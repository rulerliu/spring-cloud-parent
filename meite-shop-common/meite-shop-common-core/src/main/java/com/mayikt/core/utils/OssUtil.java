package com.mayikt.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;

public class OssUtil {
	
	private static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
	
	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录
	// https://ram.console.aliyun.com 创建。https://usercenter.console.aliyun.com/#/manage/ak
	private static final String ACCESS_KEY_ID = "LTAIR47E7L8UaYZ8";
	private static final String ACCESS_KEY_SECRET = "aYYXpDiMQeMwKb0X9RdI8qQrsNPdyg";
	
	private static final String BUCKET_NAME = "fsdfsgdfdsdfsfs";
	
	/**
	 * @param file 本地file 如：D:\\Google Download\\test.png
	 * @param uploadPath oss上传路径 如：myfile3
	 */
	public static void upload(File file, String uploadPath) {
		OSSClient ossClient = null;
		InputStream inputStream = null;
		try {
//			String accessKeyId = "LTAIPvJ3SQtkk9MT";
//			String accessKeySecret = "T3hjXWLfFnuhFr8KwIjSGLRRYXlWYR";

			// 创建OSSClient实例。
			ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(file.length());

			// 上传文件流。
			inputStream = new FileInputStream(file);
			ossClient.putObject(BUCKET_NAME, uploadPath + "/" + file.getName(), inputStream, meta);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (ossClient != null) {
					// 关闭OSSClient。
					ossClient.shutdown();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 
	 * @param filePath oss的存储路径 如：myfile3/test.png
	 * @return
	 */
	public static File download(String filePath) {
		OSSClient ossClient = null;
		File dirFile = null;
		try {
			// 创建OSSClient实例。
			ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

			// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
			String fileName = "";
			if(filePath.indexOf("/") > 0){
				fileName = filePath.substring(filePath.lastIndexOf("/"), filePath.length());
			}
			String dir = System.getProperty("java.io.tmpdir");
			String localFilePath = dir + fileName;
			System.out.println("fileName:" + fileName);
			dirFile = new File(dir);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			dirFile = new File(localFilePath);
			ossClient.getObject(new GetObjectRequest(BUCKET_NAME, filePath), dirFile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ossClient != null) {
				// 关闭OSSClient。
				ossClient.shutdown();
			}
		}
		return dirFile;
	}
	
	public static void main(String[] args) {
		String uploadPath = "myfile3";
		File file = new File("D:\\Google Download\\test.png");
		upload(file, uploadPath);
		
		String downloadPath = "myfile3/test.png";
		File download = download(downloadPath);
		System.out.println(download.getName());
		
		System.out.println(System.getProperty("java.io.tmpdir"));
	}

}
