import java.awt.*;

public class Particle {

    public double x;
    public double y;
    public double originX;
    public double originY;
    private int size;
    private int life;
    private Color color;

    public Particle(int x, int y, int size, int life, Color c){
        this.x = x;
        this.y = y;
        this.originX = x;
        this.originY = y;
        this.size = size;
        this.life = life;
        this.color = c;
    }

    public void renderParticle(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(color);
        g2d.fillRect((int) x-(size/2), (int) y-(size/2), size, size);

        g2d.dispose();
    }

    public void update() {
        double easingFactor = 0.1;
        double dx = originX - x;
        double dy = originY - y;

        if (Math.abs(dx) > 0.1) {
            x += dx * easingFactor;
        }
        if (Math.abs(dy) > 0.1) {
            y += dy * easingFactor;
        }
    }

}
