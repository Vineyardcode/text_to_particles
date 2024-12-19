import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public interface InputSubscriber {
    void onMouseMoved(MouseEvent e);
    void onWindowClosing(WindowEvent e);
}
