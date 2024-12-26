import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TextFXManager {

    public void particleEffect(MouseEvent e, ArrayList<Particle> particleArrayList) {
        double threshold = 7;

        for (Particle p : particleArrayList) {
            double dx = e.getX() - p.x;
            double dy = e.getY() - p.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < threshold) {
                p.x += dx * 5;
                p.y += dy * 5;
            }
        }
    }
}
