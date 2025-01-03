import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextFX {
    public void basicDisplacement(MouseEvent e, CopyOnWriteArrayList<Particle> particleArrayList, double groupX, double groupY) {
        for (Particle p : particleArrayList) {
            double dx = e.getX() - p.x - groupX;
            double dy = e.getY() - p.y - groupY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < 15) {
                p.x += dx * 3;
                p.y += dy * 3;
            }
        }
    }

    public void moveGroup() {

    }

}
