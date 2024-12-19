public class Main {

    public static void main(String[] args) {

        CanvasWindow window = new CanvasWindow();
        InputPublisher publisher = new InputPublisher();

        publisher.subscribe(window);
        window.canvas.addMouseMotionListener(publisher);
        window.addWindowListener(publisher);

        window.loop();

    }
}