public class Main {

    public static void main(String[] args) {
        CanvasWindow window = new CanvasWindow();
        InputPublisher publisher = new InputPublisher();

        window.canvas.addMouseMotionListener(publisher);
        window.addWindowListener(publisher);

        publisher.subscribe(window);

        window.loop();
    }
}