package com.mayikt.test;

import java.io.*;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/5/7 0007 下午 5:23
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
public class FileTest {

    public static void main(String[] args) {
        String filePath = "e:/test3.txt";
        String charset = "utf-8";
//        test1();

//        String s = loadBigFile("e:/test.txt", "utf-8");
//        System.out.println(s);

        String read = read(filePath, charset);
        System.out.println(read);

        write(filePath, "aaaaaaaaa\nbbbbbbbb");

        System.out.println(">>>>>>>>");
        read = read(filePath, charset);
        System.out.println(read);
    }

    public static void test1() {
        try (FileInputStream fis = new FileInputStream("e:/test.txt");
             FileOutputStream fos = new FileOutputStream("e:/test2.txt")) {
            int len;
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    /** 把内容写入文件，原来的内容被覆盖
     *
     * @param filePath
     * @param fileContent */
    public static void write(String filePath, String fileContent) {
        FileOutputStream fo = null;
        OutputStreamWriter out = null;
        try {
            File file = new File("filePath");
            if (!file.exists()) {
                file.createNewFile();
            }
            fo = new FileOutputStream(filePath);
            out = new OutputStreamWriter(fo, "UTF-8");

            out.write(fileContent);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fo != null) {
                    fo.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        InputStreamReader read = null;
        BufferedReader reader = null;
                StringBuffer fileContent = new StringBuffer();
        File file = new File(filePath);
        try {
            read = new InputStreamReader(new FileInputStream(file), code);
            reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line + "\n");
            }

        }
        catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (read != null) {
                    read.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileContent.toString();
    }

}
