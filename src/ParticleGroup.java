import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class ParticleGroup {

    public double globalX;
    public double globalY;
    public double rotation;
    public double scale;
    public ArrayList<Particle> particleArrayList;

    public ParticleGroup(ArrayList<Particle> particles, double globalX, double globalY) {
        this.particleArrayList = particles;
        this.globalX = globalX;
        this.globalY = globalY;
        this.rotation = 0;
        this.scale = 1;
    }

    public void renderGroup(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        AffineTransform originalTransform = g2d.getTransform();

        g2d.translate(globalX, globalY);
        g2d.scale(scale, scale);
        g2d.rotate(Math.toRadians(rotation));

        for (Particle p : particleArrayList) {
            g2d.translate(p.x, p.y);
            p.renderParticle(g2d);
            g2d.translate(-p.x, -p.y);
        }

        g2d.setTransform(originalTransform);

        g2d.dispose();
    }

    public void updateGroup() {
        for (Particle particle : particleArrayList) {
            particle.update();
        }

        globalX++;
        if (globalX >= 250) {
            globalX *= -1;
        }

    }
}
