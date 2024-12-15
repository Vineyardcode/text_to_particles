import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Input implements MouseMotionListener {

    int mouseX;
    int mouseY;

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

    }

}
