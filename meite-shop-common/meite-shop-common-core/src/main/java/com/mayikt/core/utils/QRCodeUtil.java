package com.mayikt.core.utils;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {

	/**
     * 
     * 方法功能描述: 生成可带logo图片和logo文字的二维码到输出字节流对象
     * @param url
     * @param QRCODE_WIDTH
     * @param QRCODE_HEIGHT
     * @param content
     * @param logoWidth
     * @param logoHeight
     * @param fontSizde
     * @param logo
     * @param outputStream
     * @throws Exception 
     * @代码检查人： <br>@检查时间
     */
	public static BufferedImage createImageWithLogoTextToStream(String url, int QRCODE_WIDTH, int QRCODE_HEIGHT, String content,
			int logoWidth, int logoHeight, int fontSizde, File logo, String format, OutputStream outputStream)
			throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT,
				hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
			}
		}
		// 插入logo
		LogoConfig logoConfig = new LogoConfig();
		BufferedImage logoImage = createLogoWithTextImage(content, logoWidth, logoHeight, fontSizde, logo);
		addLogoQRCode(image, logoImage, logoConfig);
		if (!ImageIO.write(image, format, outputStream)) {
			throw new IOException("Could not write an image of format " + format);
		}
		
		return image;
	}
    
    /**
     * 生成logo 图片
     * @param storeId
     * @param storeName
     * @param width
     * @param height
     * @return
     */
	public static BufferedImage createLogoWithTextImage(String content, Integer width, Integer height, int fontSize,
			File logo) {
//        Font font = new Font("微软雅黑", Font.BOLD, fontSize );
		Font font = new Font("黑体", Font.BOLD, fontSize);
//        Font font = new Font("DejaVu Sans", Font.BOLD, fontSize );
		FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		if (logo != null) {
			BufferedImage logoImge = null;
			try {
				logoImge = ImageIO.read(logo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			g2.drawImage(logoImge, 0, 0, width, height, null);
		} else {
			g2.setBackground(Color.WHITE);
			g2.clearRect(0, 0, width, height);
		}
//        g2.setPaint(Color.BLACK);
		g2.setPaint(new Color(46, 46, 46));
		g2.setFont(font);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// 求出文字的长度与宽的倍数
		int lines = fm.stringWidth(content) / width;
		if (fm.stringWidth(content) % width > 0) {
			lines++;
		}
		String lessText = content;
		// 求出行间距
		int lineHeiht = (height - lines * fm.getHeight()) / (lines + 1);
		for (int i = 0; i < lines; i++) {
			String lineContent = getlineText(fm, lessText, width, false);
			lessText = lessText.substring(lessText.indexOf(lineContent) + lineContent.length(), lessText.length());
			int y = (lineHeiht + fm.getHeight()) * (i + 1);
			g2.drawString(lineContent, (width - fm.stringWidth(lineContent)) / 2, y);
		}

		return bi;
	}
    
    private static void addLogoQRCode(BufferedImage bim, BufferedImage logo, LogoConfig logoConfig) throws Exception {
        try {
            // 对象流传输
            BufferedImage image = bim;
            Graphics2D g = image.createGraphics();
            // 设置logo的大小,本人设置为二维码图片的20%,因为过大会盖掉二维码
            int widthLogo = logo.getWidth(null) > image.getWidth() * 2 / 10 ? (image.getWidth() * 2 / 10) : logo.getWidth(null);
            int heightLogo = logo.getHeight(null) > image.getHeight() * 2 / 10 ? (image.getHeight() * 2 / 10) : logo.getWidth(null);
            // 计算图片放置位置
            // logo放在中心
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2;
            // 开始绘制图片
            g.drawImage(logo, x, y, widthLogo, heightLogo, null);
            g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
            g.setStroke(new BasicStroke(logoConfig.getBorder()));
            g.setColor(Color.white);
            g.drawRect(x, y, widthLogo, heightLogo);
            g.dispose();
            logo.flush();
            image.flush();
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * 获取每行可以最多放置的字符串
     * @param fm 字体信息
     * @param content z字符串内容
     * @param lineWidth 行宽
     * @param isLastLine 是否是最后一行
     * @return
     */
    private static String getlineText(FontMetrics fm, String content, int lineWidth, boolean isLastLine) {
        if (fm.stringWidth(content) <= lineWidth) {
            return content;
        }
        for (int index = 0; index <= content.length(); index++) {
            String sub = isLastLine ? content.substring(0, index) + "..." : content.substring(0, index);
            if (fm.stringWidth(sub) > lineWidth) {
                return isLastLine ? content.substring(0, index - 1) + "..." : content.substring(0, index - 1);
            }
        }
        return "";
    }
    
    
    //##########################生成简单的二维码，不带logo#########################
    
    // 二维码颜色
    private static final int BLACK = 0xFF000000;
    // 二维码颜色
    private static final int WHITE = 0xFFFFFFFF;
    
    /**
     * 获取二维码图片缓存对象
     * @param text
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    public static BufferedImage generateQRCodeImage(String text, int width, int height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 指定编码格式
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);// 指定纠错等级
        hints.put(EncodeHintType.MARGIN, 1); // 白边大小，取值范围0~4
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        return toBufferedImage(bitMatrix);
    }
    
    /**
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }
	
}
