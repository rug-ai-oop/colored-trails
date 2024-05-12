package View.model;
import Model.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    protected static final Map<Color, BufferedImage> tokenImages = new HashMap<>(5);
    protected static final Map<String, BufferedImage> playerImages = new HashMap<>(2);
    protected static final Map<String, BufferedImage> auxiliaryImages = new HashMap<>(5);

    public static void loadImages() {
        try {
            for (Model.Color color : Model.Color.values()) {
                tokenImages.put(color,
                        ImageIO.read(ImageLoader.class.getResource("/" + color + ".png")));
            }
            playerImages.put("Lukasz", ImageIO.read(ImageLoader.class.getResource("/PLAYER_LUKASZ.png")));
            playerImages.put("Csenge", ImageIO.read(ImageLoader.class.getResource("/PLAYER_CSENGE.png")));
            playerImages.put("Matei", ImageIO.read(ImageLoader.class.getResource("/PLAYER_Matei.png")));

            auxiliaryImages.put("redFlag", ImageIO.read(ImageLoader.class.getResource("/RED_FLAG.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static enum ImageIconNames {
        BLUE,
        CSENGE,
        Green_gamer,
        HARMEN,
        LUKASZ,
        Matei,
        Orange_catgirl,
        Orange_catgirl2,
        Orange_coolgirl,
        Orange_creepy2,
        Orange_girl,
        pink_creepy,
        Pink_elegant,
        Yellow_elegant2;
    }

}
