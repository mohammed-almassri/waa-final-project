package miu.waa.group5.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    public static int[] getImageSize(InputStream inputStream) throws IOException {
        BufferedImage image = ImageIO.read(inputStream);
        if (image == null) {
            throw new IOException("Invalid image file");
        }
        int width = image.getWidth();
        int height = image.getHeight();
        return new int[]{width, height};
    }
}