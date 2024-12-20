import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class TextFXManager {

    public ArrayList<Particle> particleArrayList = new ArrayList<>(10);

    public BufferedImage drawText(String text, int textX, int textY, int width, int height, Font font, Color color) {

        BufferedImage textImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = textImage.createGraphics();

        g2d.setFont(font);
        g2d.setColor(color);
        g2d.drawString(text, textX, textY);
        g2d.dispose();

        return textImage;
    }

    public int[][] getTextPixelData(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixelData = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int pixel = pixelData[row * width + col];
                result[row][col] = pixel;
            }
        }

        return result;
    }

    public void assembleTextFromParticles(int[][] textPixelData){

        int size = 3;
        int life = 1000;
        for (int row = 0; row < textPixelData.length; row++) {
            for (int col = 0; col < textPixelData[row].length; col++) {
                if ((textPixelData[row][col] & 0xFF000000) != 0) {
                    particleArrayList.add(new Particle(col, row, size, life, Color.black));
                }
            }
        }
    }

}
