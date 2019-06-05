package com.mayikt.core.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

@Component
public class ZipUtil {

	public static void doZip(ZipOutputStream zipOutputStream, String imageName, BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
        String entryName = imageName;
        ZipEntry entry = new ZipEntry(entryName);
        zipOutputStream.putNextEntry(entry);
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) > 0) {
            zipOutputStream.write(buffer, 0, len);
            zipOutputStream.flush();
        }
        zipOutputStream.closeEntry();
        inputStream.close();
    }
	
}
