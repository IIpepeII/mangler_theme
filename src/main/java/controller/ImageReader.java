package controller;

import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageReader {

    public static String base64image(String picLocation) {
        try {
            BufferedImage image = ImageIO.read(new File(picLocation));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] res = Base64.getEncoder().encode(baos.toByteArray());
            String encodedImage = "data:image/jpeg;base64," + new String(res);
            return encodedImage;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some problem has occurred.Please try again");
        }

        return null;
    }

}

