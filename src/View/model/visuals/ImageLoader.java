package View.model.visuals;
import Model.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    public static final int numberOfPlayerImages = 14;

    public static final Map<Color, BufferedImage> tokenImages = new HashMap<>(5);
    public static final Map<String, BufferedImage> playerImages = new HashMap<>(2);
    public static final Map<String, BufferedImage> auxiliaryImages = new HashMap<>(5);

    public static void loadImages() {
        try {
            for (Model.Color color : Model.Color.values()) {
                tokenImages.put(color,
                        ImageIO.read(ImageLoader.class.getResource("/" + color + ".png")));
            }

            for (int i = 0; i < numberOfPlayerImages; i++) {
                playerImages.put("PLAYER_" + i, ImageIO.read(ImageLoader.class.getResource("/" + "PLAYER_" + i + ".png")));
            }

            auxiliaryImages.put("redFlag", ImageIO.read(ImageLoader.class.getResource("/RED_FLAG.png")));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


}
