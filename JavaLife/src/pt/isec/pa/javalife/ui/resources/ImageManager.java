package pt.isec.pa.javalife.ui.resources;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;

public class ImageManager {
    private ImageManager() { }
    private static final HashMap<String, Image> images = new HashMap<>();

    /**
     * Funçao que terá o diretorio de onde se encontra a imagem , indo a buscar e dando retorno da mesma
     * @param filename nome da imagem
     * @return imagem que foi buscar
     */
    public static Image getImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try (InputStream is = ImageManager.class.getResourceAsStream("images/"+filename)) {
                image = new Image(is);
                images.put(filename,image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

}
