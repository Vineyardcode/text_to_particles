public class Main {

    public static void main(String[] args) {
        CanvasWindow window = new CanvasWindow();
        InputPublisher publisher = new InputPublisher();

        for (TexticleCell tc : window.texticleCellGrid) {
            publisher.subscribe(tc);
        }

        window.canvas.addMouseMotionListener(publisher);
        window.addWindowListener(publisher);

        window.loop();
    }
}