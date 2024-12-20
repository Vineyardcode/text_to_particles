import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public interface InputSubscriber {
    void onMouseMoved(MouseEvent e);
    void onMouseDragged(MouseEvent e);
    void onWindowClosing(WindowEvent e);
}
