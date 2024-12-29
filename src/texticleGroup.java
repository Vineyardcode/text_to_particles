import java.awt.*;
import java.util.ArrayList;

public class texticleGroup {

    public double groupX;
    public double groupY;
    public double rotation;
    public double scale;
    public ArrayList<Particle> texticleArrayList;
    public TexticleAssembler texticleAssembler = new TexticleAssembler();

    public texticleGroup(String text, Font font, Color color, double groupX, double groupY) {
        texticleAssembler.assembleTexticleArrayList(text, font, color);
        this.texticleArrayList = texticleAssembler.particleArrayList;
        this.groupX = groupX;
        this.groupY = groupY;
        this.rotation = 0;
        this.scale = 1;
    }

    public void renderGroup(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        for (Particle p : texticleArrayList) {
            g2d.translate(groupX, groupY);
            p.renderParticle(g2d);
            g2d.translate(-groupX, -groupY);
        }

        g2d.dispose();
    }

    public void updateGroup(double testSpeed) {
        for (Particle particle : texticleArrayList) {
            particle.update();
        }

        if (groupX <= 0) {
            groupX += testSpeed;
        }
        if (groupX >= 250){
            groupX -= testSpeed;
        }

    }
}
