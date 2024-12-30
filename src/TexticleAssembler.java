import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class TexticleAssembler {

    public ArrayList<Particle> particleArrayList = new ArrayList<>();

    public BufferedImage createTextImage(String text, Font font) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTemp = tempImage.createGraphics();
        g2dTemp.setFont(font);
        FontMetrics fontMetrics = g2dTemp.getFontMetrics();

        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();

        g2dTemp.dispose();

        BufferedImage textImage = new BufferedImage(textWidth, textHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = textImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(font);

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

    public void getParticleArrayList(int[][] textImagePixelCoords, Color particleColor){
        int size = 3;
        int life = 1000;

        for (int row = 0; row < textImagePixelCoords.length; row++) {
            for (int col = 0; col < textImagePixelCoords[row].length; col++) {
                if ((textImagePixelCoords[row][col] & 0xFF000000) != 0) {
                    particleArrayList.add(new Particle(col, row, size, life, particleColor));
                }
            }
        }
    }

    public void assembleTexticleArrayList(String text, Font font, Color color) {
        getParticleArrayList(getTextImagePixelCoords(createTextImage(text, font)), color);
//        in case debug is needed:
//        BufferedImage textImage = createTextImage(text, font, color);
//        int[][] pixelData = getTextImagePixelData(textImage);
//        getParticleArrayList(pixelData);
    }

}
