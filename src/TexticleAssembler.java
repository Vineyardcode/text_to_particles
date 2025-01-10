import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class TexticleAssembler {

    public ArrayList<ArrayList<Particle>> texticleArrayListCollection = new ArrayList<>();

    public BufferedImage createTextImage(String text, int fontSize) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTemp = tempImage.createGraphics();
        g2dTemp.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        FontMetrics fontMetrics = g2dTemp.getFontMetrics();

        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        g2dTemp.dispose();

        BufferedImage textImage = new BufferedImage(textWidth, textHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = textImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));

        g2d.drawString(text, 0, fontMetrics.getAscent());

        g2d.dispose();

        return textImage;
    }

    public int[][] getTextImagePixelCoords(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixelData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[][] pixelCoords = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int pixel = pixelData[row * width + col];
                pixelCoords[row][col] = pixel;
            }
        }

        return pixelCoords;
    }

    public ArrayList<Particle> getParticleArrayList(int[][] textImagePixelCoords){
        int size = 1;
        int life = 1;
        ArrayList<Particle> particleArrayList = new ArrayList<>();

        for (int row = 0; row < textImagePixelCoords.length; row++) {
            for (int col = 0; col < textImagePixelCoords[row].length; col++) {
                if ((textImagePixelCoords[row][col] & 0xFF000000) != 0) {
                    particleArrayList.add(new Particle(col, row));
                }
            }
        }

        return particleArrayList;
    }

    public ArrayList<ArrayList<Particle>> getTexticleArrayListCollectionOfAsciiCharacters() {
        String ascii_characters = "▌!#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`{|}~";

        for (char asciiChar : ascii_characters.toCharArray()) {
            texticleArrayListCollection.add(
                    getParticleArrayList(
                            getTextImagePixelCoords(
                                    createTextImage(String.valueOf(asciiChar), 100)
                            )
                    )
            );
        }

        return texticleArrayListCollection;
    }

}
