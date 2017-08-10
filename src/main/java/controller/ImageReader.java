package controller;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class ImageReader {

    public static String base64image(String picLocation) {
        try {
            BufferedImage image = ImageIO.read(new File("src/main/resources/public" +picLocation));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] res = baos.toByteArray();
            String encodedImage = "data:image/jpeg;base64," + Base64.encode(baos.toByteArray());
            return encodedImage;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Some problem has occurred.Please try again");
        }

        return null;
    }

}

