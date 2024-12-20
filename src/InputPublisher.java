import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class InputPublisher implements MouseMotionListener, WindowListener {

    CopyOnWriteArrayList<InputSubscriber> subscribers = new CopyOnWriteArrayList<>();

    public void subscribe(InputSubscriber sub) {
        subscribers.add(sub);
    }

    @SuppressWarnings("unused")
    public void unsubscribe(InputSubscriber sub){
        subscribers.remove(sub);
    }

    public void notifyMouseMoved(MouseEvent e) {
        for (InputSubscriber sub : subscribers){
            sub.onMouseMoved(e);
        }
    }

    public void notifyMouseDragged(MouseEvent e) {
        for (InputSubscriber sub : subscribers){
            sub.onMouseDragged(e);
        }
    }

    public void notifyWindowClosing(WindowEvent e) {
        for (InputSubscriber sub : subscribers){
            sub.onWindowClosing(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        notifyMouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        notifyMouseMoved(e);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        notifyWindowClosing(e);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
