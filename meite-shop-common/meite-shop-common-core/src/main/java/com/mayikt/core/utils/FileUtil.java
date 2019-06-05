package com.mayikt.core.utils;

import com.mayikt.constants.Constants;
import com.mayikt.core.exception.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.*;

/** 文件工具类 */
public class FileUtil {

	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	private static File   file;

	private FileUtil() {
	}

	/** <br>
	 * 方法名称: 获取不带后缀的文件名<br>
	 * 适用场景: <br>
	 * 业务逻辑说明: <br>
	 *
	 * @param fileName
	 * @return
	 * @author linyujia
	 * @time 2017年11月13日 下午3:24:13 */
	public static String getFileNameWithoutSuffix(String fileName) {
		File file = new File(fileName);

		if (file.isDirectory()) {
			log.error("传入地址为文件夹");
			throw new ResourceException(Constants.RESPCODE_ERROR, "传入地址为文件夹");
		}

		// if (file.isFile()) {
		// fileName = file.getName();
		// }

		int suffixIndex = fileName.lastIndexOf('.');
		if (0 >= suffixIndex) {
			return fileName;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/** <br>
	 * 方法名称: 获取文件名后缀<br>
	 * 适用场景: <br>
	 * 业务逻辑说明: <br>
	 *
	 * @param fileName
	 * @return
	 * @author linyujia
	 * @time 2017年11月13日 下午3:24:22 */
	public static String getFileSuffix(String fileName) {
		// 文件名后缀长度
		int suffixIndex = fileName.lastIndexOf('.');
		if (0 >= suffixIndex) {
			return "";
		}
		// 文件名后缀
		String suffix = fileName.substring(suffixIndex, fileName.length());
		return suffix;
	}

	/** 创建文件
	 * 
	 * @param in
	 * @param filePath */
	public static void createFile(InputStream in, String filePath) {
		if (null == in) {
			log.info("创建文件失败: 入参inputstream为空");
			throw new RuntimeException("创建文件失败: 入参inputstream为空");
		}
		if (null == filePath) {
			log.info("创建文件失败: 入参filePath为空");
			throw new RuntimeException("创建文件失败: 入参filePath为空");
		}
		int potPos = filePath.lastIndexOf('/') + 1;
		String folderPath = filePath.substring(0, potPos);
		createFolder(folderPath);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			byte[] by = new byte[1024];
			int c;
			while ((c = in.read(by)) != -1) {
				outputStream.write(by, 0, c);
			}
			outputStream.close();
			in.close();
		}
		catch (IOException e) {
			log.error("创建文件失败：" + e);
		}
	}

	/** Java从文件路径中获取文件名
	 * 
	 * @param filePath
	 * @return */
	public static String getPathFileName(String filePath) {
		if ((filePath != null) && (filePath.length() > 0)) {
			filePath = filePath.replaceAll("\\\\", "/");
			int dot = filePath.lastIndexOf("/");
			if ((dot > -1) && (dot < (filePath.length()))) {
				return filePath.substring(dot + 1, filePath.length());
			}
		}
		return filePath;
	}

	/** 文件允许上传格式
	 * 
	 * @param ex
	 * @return */
	public static boolean isAllowUp(String logoFileName) {
		logoFileName = logoFileName.toLowerCase();
		String allowTYpe = "gif,jpeg,jpg,bmp,swf,png,rar,doc,docx,xls,xlsx,pdf,zip,ico,txt";
		if (!logoFileName.trim().equals("") && logoFileName.length() > 0) {
			String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());
			// return allowTYpe.toString().indexOf(ex) >= 0;
			// lzf edit 20110717
			// 解决只认小写问题
			// 同时加入jpeg扩展名/png扩展名
			return allowTYpe.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
		}
		else {
			return false;
		}
	}

	/** 图片允许上传格式
	 * 
	 * @param logoFileName
	 *        图片名
	 * @return */
	public static boolean isAllowUpForPicture(String logoFileName) {
		logoFileName = logoFileName.toLowerCase();
		String allowTYpe = ".ico,.jpg,.png,";
		if (!logoFileName.trim().equals("") && logoFileName.length() > 0) {
			String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());
			// return allowTYpe.toString().indexOf(ex) >= 0;
			// 解决只认小写问题
			// 同时加入jpeg扩展名/png扩展名
			return allowTYpe.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
		}
		else {
			return false;
		}
	}

	/** Excel允许导入格式
	 * 
	 * @param logoFileName
	 *        Excel文件名
	 * @return */
	public static boolean isAllowUpForExcel(String logoFileName) {
		logoFileName = logoFileName.toLowerCase();
		String allowTYpe = "xlsx";
		if (!logoFileName.trim().equals("") && logoFileName.length() > 0) {
			String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());
			// return allowTYpe.toString().indexOf(ex) >= 0;
			// lzf edit 20110717
			// 解决只认小写问题
			// 同时加入jpeg扩展名/png扩展名
			return allowTYpe.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
		}
		else {
			return false;
		}
	}

	/** 自定义接受允许上传文件的类型
	 * 
	 * @param logoFileName
	 *        文件名
	 * @param allowType
	 *        文件类型
	 * @return */
	public static boolean isAllowUp(String logoFileName, String allowType) {
		logoFileName = logoFileName.toLowerCase();
		if (!logoFileName.trim().equals("") && logoFileName.length() > 0) {
			String ex = logoFileName.substring(logoFileName.lastIndexOf(".") + 1, logoFileName.length());
			return allowType.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
		}
		else {
			return false;
		}
	}

	/** 把内容写入文件
	 * 
	 * @param filePath
	 * @param fileContent */
	public static void write(String filePath, String fileContent) {

		try {
			FileOutputStream fo = new FileOutputStream(filePath);
			OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");

			out.write(fileContent);

			out.close();
		}
		catch (IOException ex) {
			log.error("把内容写入文件" + filePath + "失败:" + ex);
		}
	}

	/** 读取文件内容 默认是UTF-8编码
	 * 
	 * @param filePath
	 * @return */
	public static String read(String filePath, String code) {
		if (code == null || code.equals("")) {
			code = "UTF-8";
		}
		String fileContent = "";
		File file = new File(filePath);
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), code);
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent = fileContent + line + "\n";
			}
			read.close();
			read = null;
			reader.close();
			read = null;
		}
		catch (Exception ex) {
			log.error("读取文件" + file.getName() + "内容失败:" + ex);
			fileContent = "";
		}
		return fileContent;
	}

	public static boolean exist(String filepath) {
		File file = new File(filepath);
		return file.exists();
	}

	/** 创建文件夹
	 * 
	 * @param filePath */
	public static File createFolder(String destDirName) {
		// 添加分隔符
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}

		try {
			file = new File(destDirName);

			// 判断目录是否存在
			if (file.exists()) {
				log.info("目标目录已存在！");
				return file;
			}

			// 创建目标目录
			if (file.mkdirs()) {
				log.info("创建目录成功");
			}
			else {
				log.error("创建目录【" + destDirName + "】失败");
				throw new ResourceException(Constants.RESPCODE_ERROR, "创建目录【" + destDirName + "】失败");
			}
		}
		catch (Exception e) {
			log.error("创建目录【" + destDirName + "】失败:" + e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, "创建目录【" + destDirName + "】失败:" + e.getMessage());
		}

		return file;

	}

	/** 重命名文件、文件夹
	 * 
	 * @param from
	 * @param to */
	public static void renameFile(String from, String to) {
		try {
			File file = new File(from);
			if (file.exists()) {
				file.renameTo(new File(to));
			}
		}
		catch (Exception ex) {
			log.error("重命名文件" + from + "失败：" + ex);
		}
	}

	/** 得到文件的扩展名
	 * 
	 * @param fileName
	 * @return */
	public static String getFileExt(String fileName) {

		int potPos = fileName.lastIndexOf('.') + 1;
		String type = fileName.substring(potPos, fileName.length());
		return type;
	}

	/** 通过File对象创建文件
	 * 
	 * @param file
	 * @param filePath */
	public static void createFile(File file, String filePath) {
		int potPos = filePath.lastIndexOf('/') + 1;
		String folderPath = filePath.substring(0, potPos);
		createFolder(folderPath);
		FileOutputStream outputStream = null;
		FileInputStream fileInputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			fileInputStream = new FileInputStream(file);
			byte[] by = new byte[1024];
			int c;
			while ((c = fileInputStream.read(by)) != -1) {
				outputStream.write(by, 0, c);
			}
		}
		catch (IOException e) {
			log.error("写入文件失败：" + e);
		}
		try {
			outputStream.close();
		}
		catch (IOException e) {
			log.error("关闭outputStream失败：" + e);
		}
		try {
			fileInputStream.close();
		}
		catch (IOException e) {
			log.error("关闭inputStream失败：" + e);
		}
	}

	/** <br>
	 * 适用场景: 读取文件内容<br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param fileInfoBo
	 *        只需要文件地址fileAddress
	 * @return
	 * @autho linyujia
	 * @time 2017年7月17日 下午3:11:05 */
	public static String readFile(String filePath, String charset) {
		String content = "";
		try {
			String encoding = charset;
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				if ("UTF-8".equals(encoding)) {
					content = loadUTF8File(filePath);
				}
				else {
					content = loadOthersFile(filePath, encoding);
				}

			}
			else {
				log.error("找不到指定文件");
				throw new ResourceException(Constants.RESPCODE_ERROR, "找不到指定文件");
			}
		}
		catch (Exception e) {
			log.error("读取文件内容失败：" + e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, "读取文件内容失败：" + e.getMessage());
		}
		return content;
	}

	private static String loadOthersFile(String file, String encoding) {
		InputStreamReader read = null;
		String content = "";
		try {
			read = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				content += line + "\n";
			}
			read.close();
		}
		catch (Exception e) {
			log.error("读取文件内容出错:" + e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, "读取文件内容出错:" + e.getMessage());
		}
		finally {
			try {
				if (null != read) {
					read.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content;
	}

	private static String loadUTF8File(String file) {
		BufferedReader reader = null;
		CharArrayWriter writer = null;
		UnicodeReader r = null;
		char[] buffer = new char[16 * 1024];
		int read;
		try {
			r = new UnicodeReader(new FileInputStream(file));
			reader = new BufferedReader(r);
			writer = new CharArrayWriter();
			while ((read = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, read);
			}
			writer.flush();
			return writer.toString();
		}
		catch (IOException ex) {
			throw new ResourceException(Constants.RESPCODE_ERROR, "读取UTF-8文件错误：" + ex.getMessage());
		}
		finally {
			try {
				if (null != writer) {
					writer.close();
				}

				if (null != reader) {
					reader.close();
				}

				if (null != r) {
					r.close();
				}

			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String loadBigFile(String file, String encoding) {
		BufferedInputStream fis = null;
		BufferedReader bufferedReader = null;

		InputStreamReader read = null;
		char[] buffer = new char[16 * 1024];
		CharArrayWriter writer = null;
		String content = "";
		try {
			fis = new BufferedInputStream(new FileInputStream(file), 10 * 1024 * 1024); // 用10M的缓冲读取
			read = new InputStreamReader(fis, encoding);
			bufferedReader = new BufferedReader(read);

			writer = new CharArrayWriter();
			int line;
			while ((line = bufferedReader.read(buffer)) != -1) {
				writer.write(buffer, 0, line);
			}
			writer.flush();
			content = writer.toString();
			// String line = null;
			// while ((line = bufferedReader.readLine()) != null) {
			// content += line;
			// }
		}
		catch (Exception e) {
			log.error("读取文件内容出错:" + e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, "读取文件内容出错:" + e.getMessage());
		}
		finally {
			try {
				if (null != read) {
					read.close();
				}

				if (null != bufferedReader) {
					bufferedReader.close();
				}

				if (null != fis) {
					fis.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		return content;
	}

	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);

		}
		return stream;
	}

	public static String readStreamToString(InputStream stream) {
		String fileContent = "";

		try {
			InputStreamReader read = new InputStreamReader(stream, "utf-8");
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent = fileContent + line + "\n";
			}
			read.close();
			read = null;
			reader.close();
			read = null;
		}
		catch (Exception ex) {
			log.error("将流转换为字符串失败：" + ex);
			fileContent = "";
		}
		return fileContent;
	}

	public static String readFileToString(String fileName, String encoding) {
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *        String 原路径
	 * @param newPath
	 *        String 目标路径
	 * @param isChar
	 *        boolean 是否为字节流方式
	 * @return boolean */
	public static void copyFolder(String oldPath, String newPath, boolean isChar) {
		try {
			File newFile = new File(newPath);
			if (!newFile.exists()) {
				newFile.mkdirs(); // 如果文件夹不存在 则建立新文件夹
			}
			File oldFile = new File(oldPath);
			oldPath = oldFile.getAbsolutePath();
			newPath = newFile.getAbsolutePath();
			String[] file = oldFile.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				}
				else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					if (isChar) {
						copyFileByChar(temp.getAbsolutePath(), newPath);
					}
					else {
						copyFileByByte(temp.getAbsolutePath(), newPath);
					}
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i], isChar);
				}
			}
		}
		catch (Exception e) {
			log.error("复制整个文件夹" + oldPath + "内容操作出错：" + e);
		}
	}

	/** 移动文件到指定目录
	 * 
	 * @param oldPath
	 *        String 如：c:/fqf.txt
	 * @param newPath
	 *        String 如：d:/fqf.txt */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath, false);
		DeleteFolder(oldPath);
	}

	/** 复制文件内容(字节流)
	 * 
	 * @param oldPath
	 *        String 原路径
	 * @param newPath
	 *        String 目标路径
	 * @return boolean
	 * @throws Exception */
	public static File copyFile(String oldPathName, String newFilePath, String newFileName) {
		File newPath = new File(newFilePath);
		if (!newPath.exists()) {
			newPath.mkdirs(); // 如果文件夹不存在 则建立新文件夹
		}
		File newFile = new File(newPath, newFileName);
		File oldFile = new File(oldPathName);
		try {
			FileInputStream input = new FileInputStream(oldFile);
			FileOutputStream output = new FileOutputStream(newFile);
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		}
		catch (Exception e) {
			log.error("复制文件内容操作出错:" + e.getMessage());
			throw new ResourceException("复制文件内容操作出错:" + e.getMessage());
		}
		return newFile;
	}

	/** 复制文件内容(字节流)
	 * 
	 * @param oldPath
	 *        String 原路径
	 * @param newPath
	 *        String 目标路径
	 * @return boolean
	 * @throws Exception */
	public static void copyFileByByte(String oldPath, String newPath) throws IOException {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File oldFile = new File(oldPath);
			FileInputStream input = new FileInputStream(oldFile);
			FileOutputStream output = new FileOutputStream(newPath + "/" + (oldFile.getName()).toString());
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		}
		catch (Exception e) {
			log.error("复制文件内容操作出错:" + e);
			throw new IOException();
		}
	}

	/** <br>
	 * 适用场景: 复制文件到另一目录<br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param destDirName
	 * @return
	 * @autho linyujia
	 * @time 2017年7月12日 下午6:24:04 */
	public static void copyFile(String sourcePath, String targetPath) {
		File sourceFile = new File(sourcePath);
		File targetFile = new File(targetPath);
		if (!sourceFile.exists()) {
			throw new ResourceException(Constants.RESPCODE_ERROR, "源文件不存在");
		}
		if (!sourceFile.isFile()) {
			throw new ResourceException(Constants.RESPCODE_ERROR, "源路径不是一个文件");
		}

		try {
			FileCopyUtils.copy(sourceFile, targetFile);
		}
		catch (IOException e) {
			throw new ResourceException(Constants.RESPCODE_ERROR, "复制文件失败:" + e.getMessage());
		}
	}

	/** <br>
	 * 适用场景: 创建临时文件<br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param reqBo
	 * @return
	 * @autho linyujia
	 * @time 2017年7月13日 下午6:06:18 */
	public File createTempFile(String filePath, String fileName, byte[] mdf) {
		try {
			file = new File(filePath, fileName);
			FileCopyUtils.copy(mdf, new FileOutputStream(file));
		}
		catch (IOException e) {
			log.error("创建临时文件失败", e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, "创建临时文件失败：" + e.getMessage());
		}
		return file;
	}

	/** 复制文件内容(字符流)
	 * 
	 * @param oldPath
	 *        String 原路径
	 * @param newPath
	 *        String 目标路径
	 * @return boolean */
	public static void copyFileByChar(String oldPath, String newPath) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File oldFile = new File(oldPath);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(oldFile), "UTF-8"));
			newPath = newPath + "/" + (oldFile.getName()).toString();
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newPath), "UTF-8"));
			String line = null;
			while (null != (line = reader.readLine())) {
				writer.write(line + "\n");
			}
			writer.flush();
			writer.close();
			reader.close();
		}
		catch (Exception e) {
			log.error("复制文件内容操作出错:" + e);
		}
		finally {
			if (null != writer) {
				try {
					writer.close();
				}
				catch (IOException e) {
					log.error("关闭writer失败：" + e);
				}
			}

			if (null != reader) {
				try {
					reader.close();
				}
				catch (IOException e) {
					log.error("关闭reader失败：" + e);
				}
			}

		}
	}

	/** 替换文件内容
	 * 
	 * @param filepath
	 * @param oldStr
	 * @param newStr */
	public static void changeFileContent(File file, String oldStr, String newStr, Boolean onlyFirst) {
		try {
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String filename = file.getName();
			// tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。
			File tmpfile = new File(file.getParentFile().getAbsolutePath() + "\\" + filename + ".tmp");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpfile), "utf-8"));
			boolean flag = false;
			String str = null;
			while (true) {
				str = reader.readLine();
				if (str == null)
					break;
				if (str.contains(oldStr)) {
					if (onlyFirst) {
						if (!flag) {
							writer.write(newStr + "\n");
							flag = true;
						}
						else {
							writer.write(str + "\n");
						}
					}
					else {
						writer.write(newStr + "\n");
						flag = true;
					}
				}
				else {
					writer.write(str + "\n");
				}
			}
			is.close();
			writer.flush();
			writer.close();
			if (flag) {
				file.delete();
				tmpfile.renameTo(new File(file.getAbsolutePath()));
			}
			else
				tmpfile.delete();
		}
		catch (Exception e) {
			log.error("替换文件内容失败：" + e);
		}
	}

	/** <br>
	 * 适用场景: 根据路径删除指定的目录或文件<br>
	 * 调用方式: <br>
	 * 业务逻辑说明<br>
	 *
	 * @param deletePath
	 * @return
	 * @autho linyujia
	 * @time 2017年7月12日 下午3:00:00 */
	public static void DeleteFolder(String deletePath) {
		try {
			file = new File(deletePath);
			if (!file.exists()) {// 判断目录或文件是否存在
				log.error("目录或文件不存在");
			}
			else {

				if (file.isFile()) {// 判断是否为文件
					deleteFile(deletePath);// 为文件时调用删除文件方法

				}
				else {
					deleteDirectory(deletePath);// 为目录时调用删除目录方法
				}
			}
		}
		catch (Exception e) {
			log.error("删除失败:" + e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, "删除失败:" + e.getMessage());
		}

	}

	// 删除文件
	private static void deleteFile(String filePath) {
		log.info("删除文件");
		file = new File(filePath);
		try {
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
				log.info("删除文件成功");
			}
			else {
				log.error("文件不存在或路径错误");
				throw new ResourceException(Constants.RESPCODE_ERROR, "文件不存在或路径错误");
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, e.getMessage());
		}
	}

	// 删除目录（文件夹）以及目录下的文件
	private static void deleteDirectory(String dirPath) {
		// 添加文件分隔符
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}

		try {
			file = new File(dirPath);
			// 如果dir对应的文件不存在，或者不是一个目录，则退出
			if (!file.exists() || !file.isDirectory()) {
				log.error("目录不存在或不是目录");
				throw new ResourceException(Constants.RESPCODE_ERROR, "目录不存在或不是目录");
			}

			File[] files = file.listFiles();// 获得传入路径下的所有文件
			if (null != files && 0 < files.length) {
				// 循环遍历删除文件夹下的所有文件(包括子目录)
				for (int i = 0; i < files.length; i++) {
					// 删除子文件
					if (files[i].isFile()) {
						deleteFile(files[i].getAbsolutePath());
						log.info(files[i].getAbsolutePath() + " 删除成功");
					}
					else {// 删除子目录
						deleteDirectory(files[i].getAbsolutePath());
					}
				}
			}

			// 删除当前文件夹
			if (file.delete()) {
				log.info("删除当前文件夹成功");
			}
			else {
				throw new ResourceException(Constants.RESPCODE_ERROR, "删除当前文件夹失败");
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new ResourceException(Constants.RESPCODE_ERROR, e.getMessage());
		}

	}

}