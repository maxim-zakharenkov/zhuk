package engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author Max
 */
public class Resources {

    public static final BufferedImage cherryImage;
    public static final BufferedImage redBugImage;
    public static final BufferedImage greenBugImage;
    public static final byte[] munch;
    
    static {
        cherryImage = loadImage("cherry.png");
        redBugImage = loadImage("red_bug.png");
        greenBugImage = loadImage("green_bug.png");
        munch = readSound("munch.wav");
    }

    public static byte[] readSound(String name) {
        try {
            InputStream is = Resources.class.getResourceAsStream("/" + name);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int count  = is.read(buf);
            while(count != -1) {
                os.write(buf, 0, count);
                count  = is.read(buf);
            }
            return  os.toByteArray();
        } catch(Exception ex) {
            ex.printStackTrace();
            return new byte[0];
        }
    }

    public static BufferedImage loadImage(String name) {
        try {
            return ImageIO.read(Resources.class.getResourceAsStream("/" + name));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
