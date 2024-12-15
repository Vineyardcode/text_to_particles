public class Main {

    public static void main(String[] args) {

        CanvasWindow window = new CanvasWindow(500,500);
        window.manageMouseInput();
        window.loop();

    }
}