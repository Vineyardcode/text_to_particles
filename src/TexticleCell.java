import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

public class TexticleCell {
    public double x, y;
    public int width, height;
    public int fontSize;

    public int currentTextIndex = (int)(Math.random() * specialCharactersArray.length);
    private int stepSize = 2; // every n-th particle will be painted

    public List<Particle> cellParticles = new ArrayList<>();
    private static final String[] specialCharactersArray = {
            "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "!", "9", ":", ";", "<", "=",
            ">", "?", "@", "[", "]", "^", "_", "`", "{", "|", "}", "~", "\\", "\""
    };
    // caret like thing for later use : "▌"

    public TexticleCell(int x, int y, int fontSize) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.width = fontSize/2;
        this.height = fontSize;
        initializeParticles(specialCharactersArray[currentTextIndex]);

        Timer textChangeTimer = new Timer((int)(Math.random() * 1500), e -> {updateTargetText();});
        textChangeTimer.start();
    }

    private void initializeParticles(String text) {
        cellParticles.clear();
        BufferedImage tempImage = createTextImage(text);
        int[] pixelData = ((DataBufferInt) tempImage.getRaster().getDataBuffer()).getData();

        for (int y = 0; y < height; y+=stepSize) {
            for (int x = 0; x < width; x+=stepSize) {
                int pixelIndex = y * width + x;
                if (pixelIndex < pixelData.length && (pixelData[pixelIndex] & 0xFF000000) != 0) {
                    cellParticles.add(new Particle(x, y));
                }
            }
        }
    }

    private BufferedImage createTextImage(String text) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTemp = tempImage.createGraphics();

        g2dTemp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Monospaced", Font.LAYOUT_NO_LIMIT_CONTEXT, fontSize);
        g2dTemp.setFont(font);
        FontMetrics metrics = g2dTemp.getFontMetrics(font);

        width = metrics.stringWidth(text);
        height = metrics.getHeight();

        g2dTemp.dispose();

        BufferedImage textImage = new BufferedImage(width + (int) x, height + (int) y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dText = textImage.createGraphics();

        g2dText.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2dText.setFont(font);

        int textX = (int) x;
        int textY = (int) y + metrics.getAscent() - metrics.getDescent();


        g2dText.setColor(Color.WHITE);
        g2dText.drawString(text, textX, textY);
        g2dText.dispose();

        return textImage;
    }

    public void updateTargetText() {
        currentTextIndex = (currentTextIndex + 1) % specialCharactersArray.length;
        BufferedImage newTextImage = createTextImage(specialCharactersArray[currentTextIndex]);
        updateParticleTargets(newTextImage);
    }

    private void updateParticleTargets(BufferedImage newTextImage) {
        int[] pixelData = ((DataBufferInt) newTextImage.getRaster().getDataBuffer()).getData();
        List<Particle> updatedParticles = new ArrayList<>();
        int particleIndex = 0;

        for (int y = 0; y < newTextImage.getHeight(); y += stepSize) {
            for (int x = 0; x < newTextImage.getWidth(); x += stepSize) {
                int pixelIndex = y * newTextImage.getWidth() + x;
                if ((pixelData[pixelIndex] & 0xFF000000) != 0) {
                    Particle particle;
                    if (particleIndex < cellParticles.size()) {
                        particle = cellParticles.get(particleIndex);
                        particle.setNewTarget(new Vector2(x, y));
                    } else {
                        particle = new Particle(x, y);
                    }
                    updatedParticles.add(particle);
                    particleIndex++;
                }
            }
        }

        if (!hasDisplacedParticles()) {
            cellParticles = updatedParticles;
        }
    }

    public boolean hasDisplacedParticles() {
        for (Particle particle : cellParticles) {
            if (particle.isDisplaced) {
                return true;
            }
        }
        return false;
    }

    public void drawComponents(Graphics2D g) {
        for (Particle particle : cellParticles) {
            particle.update();
            g.setColor(particle.color);
            g.drawRect((int) particle.x, (int) particle.y, particle.size, particle.size);
        }
    }
}
