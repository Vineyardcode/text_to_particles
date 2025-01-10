import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

public class TexticleCell implements InputSubscriber {
    public double x, y;
    public int width, height;
    public int fontSize;

    private int texticleSize = 2;
    public int currentTextIndex = (int)(Math.random() * specialCharactersArray.length);
    private int stepSize = 2; // every n-th particle will be painted

    public List<Particle> particles = new ArrayList<>();
    private static final String[] specialCharactersArray = {
            "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "▌", "/",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "!", "9", ":", ";", "<", "=",
            ">", "?", "@", "[", "]", "^", "_", "`", "{", "|", "}", "~", "\\", "\""
    };

    public TexticleCell(int x, int y, int fontSize) {
        this.x = x;
        this.y = y;
        this.fontSize = fontSize;
        this.width = fontSize/2;
        this.height = fontSize;
        initializeParticles(specialCharactersArray[currentTextIndex]);
    }

    private void initializeParticles(String text) {
        particles.clear();
        BufferedImage tempImage = createTextImage(text);
        int[] pixelData = ((DataBufferInt) tempImage.getRaster().getDataBuffer()).getData();

        for (int y = 0; y < height; y+=stepSize) {
            for (int x = 0; x < width; x+=stepSize) {
                int pixelIndex = y * width + x;
                if (pixelIndex < pixelData.length && (pixelData[pixelIndex] & 0xFF000000) != 0) {
                    particles.add(new Particle(x, y));
                }
            }
        }
    }

    private BufferedImage createTextImage(String text) {
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2dTemp = tempImage.createGraphics();

        g2dTemp.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("Monospaced", Font.PLAIN, fontSize);
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
        int particleIndex = 0;
        List<Particle> updatedParticles = new ArrayList<>();

        for (int y = 0; y < newTextImage.getHeight(); y+=stepSize) {
            for (int x = 0; x < newTextImage.getWidth(); x+=stepSize) {
                int pixelIndex = y * newTextImage.getWidth() + x;
                if ((pixelData[pixelIndex] & 0xFF000000) != 0) {
                    if (particleIndex < particles.size()) {
                        Particle particle = particles.get(particleIndex);
                        particle.setNewTarget(x, y);
                        updatedParticles.add(particle);
                    } else {
                        updatedParticles.add(new Particle(x, y));
                    }
                    particleIndex++;
                }
            }
        }

        particles = updatedParticles;
    }

    public void paintParticles(Graphics2D g) {
        for (Particle particle : particles) {
            particle.update();
            g.setColor(Color.WHITE);
            g.fillRect((int) particle.x, (int) particle.y, texticleSize, texticleSize);
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (Particle particle : particles) {
            double distance = Math.hypot(mouseX - particle.x, mouseY - particle.y);
            if (distance < 25) {
                particle.displace(mouseX, mouseY);
            }
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

    }

    @Override
    public void onWindowClosing(WindowEvent e) {

    }
}
