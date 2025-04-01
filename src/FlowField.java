import java.awt.*;

public class FlowField {
    private int cols, rows;
    private float scaleX, scaleY;
    private float[][] angles;

    public FlowField(int width, int height, float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.cols = (int) (width / scaleX) + 1;
        this.rows = (int) (height / scaleY) + 1;
        this.angles = new float[cols][rows];

        initializeAngles();
    }

    private void initializeAngles() {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                angles[i][j] = 0;
            }
        }
    }

    public float getAngleAtPosition(double x, double y) {
        int col = (int) (x / scaleX);
        int row = (int) (y / scaleY);
        if (col >= 0 && col < cols && row >= 0 && row < rows) {
            return angles[col][row];
        }
        return 0;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.CYAN);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                float angle = angles[i][j];
                float x1 = i * scaleX;
                float y1 = j * scaleY;
                float x2 = x1 + (float) Math.cos(angle) * scaleX * 0.5f;
                float y2 = y1 + (float) Math.sin(angle) * scaleY * 0.5f;

                g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            }
        }
    }

    public void updateAngles(){

    }
}
