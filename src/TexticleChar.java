import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TexticleChar {

    public double x;
    public double y;
    public int width;
    public int height;
    private double progress;
    public TexticleAssembler assembler = new TexticleAssembler();
    public ArrayList<ArrayList<Particle>> texticleCollectionOfAsciiChars;
    public int currentCharIndex = 0;
    public CopyOnWriteArrayList<Particle> defaultParticleArrayList = new CopyOnWriteArrayList<>();

    public TexticleChar(int x, int y) {
        this.x = x;
        this.y = y;
        this.progress = 0.0;
        for (int i = 0; i < 4420; i++) {
            defaultParticleArrayList.add(new Particle(0, 0, 5, 1, Color.cyan));
        }
        this.texticleCollectionOfAsciiChars = assembler.getTexticleArrayListCollectionOfAsciiCharacters();

    }

    public void renderCell(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) g.create();

        for (Particle p : defaultParticleArrayList) {
            g2d.translate(x, y);
            p.renderParticle(g2d);
            g2d.translate(-x, -y);
        }

        g2d.dispose();
    }

    public void update() {
        for (Particle p : defaultParticleArrayList) {
            p.update();
        }
    }

    public void changeCharacter() {
        if (progress < 1.0) {
            progress += 0.02;
            if (progress > 1.0) {
                progress = 1.0;
            }

            ArrayList<Particle> nextParticleArrayList = texticleCollectionOfAsciiChars.get(currentCharIndex);

            int sizeDifference = defaultParticleArrayList.size() - nextParticleArrayList.size();

            CopyOnWriteArrayList<Particle> updatedParticleList = new CopyOnWriteArrayList<>();

            if (sizeDifference > 0) {
                for (int i = 0; i < nextParticleArrayList.size(); i++) {
                    updatedParticleList.add(defaultParticleArrayList.get(i));
                }
            } else if (sizeDifference < 0) {
                updatedParticleList.addAll(defaultParticleArrayList);
                for (int i = 0; i < Math.abs(sizeDifference); i++) {
                    updatedParticleList.add(new Particle(0, 0, 1, 1, Color.cyan)); // Add new default particles
                }
            } else {
                updatedParticleList.addAll(defaultParticleArrayList);
            }

            for (int i = 0; i < updatedParticleList.size(); i++) {
                Particle currentParticle = updatedParticleList.get(i);
                Particle nextParticle = nextParticleArrayList.get(i);

                double easedProgress = easeInOutBack(progress);

                currentParticle.x = currentParticle.originX + (nextParticle.originX - currentParticle.originX) * easedProgress;
                currentParticle.y = currentParticle.originY + (nextParticle.originY - currentParticle.originY) * easedProgress;
                currentParticle.originX = currentParticle.x;
                currentParticle.originY = currentParticle.y;
            }

            currentCharIndex++;
        }
    }


    private double easeInOutBack(double x) {
        final double c1 = 1.70158;
        final double c2 = c1 * 1.525;

        return x < 0.5
                ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
                : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
    }
}
