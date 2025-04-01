import java.awt.*;

public class Particle {

    public double x, y;
    public Vector2 originalPosition;
    public Vector2 position;
    public Vector2 prevPosition;
    private Vector2 targetPosition;
    private Vector2 velocity;
    public boolean isDisplaced = false;
    public Color color = Color.WHITE;
    private int life;
    public int size;
    private float hue, saturation, brightness;

    public Particle(int x, int y){
        this.originalPosition = new Vector2(x, y);
        this.x = x;
        this.y = y;
        this.life = 0;
        this.position = new Vector2(x, y);
        this.prevPosition = new Vector2(x, y);
        this.targetPosition = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.size = 2;

    }

    public void setNewTarget(Vector2 newTarget) {
        this.targetPosition = newTarget;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getPrevPosition() {
        return prevPosition;
    }

    public void setPosition(Vector2 newPosition) {
        this.prevPosition = this.position;
        this.position = newPosition;
    }

    public void setPrevPosition(Vector2 newPosition) {
        this.prevPosition = newPosition;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void displace() {
        this.life = 100;
        this.isDisplaced = true;
    }

    public void update() {
        double acceleration = 1;
        double damping = 0.37;

        if (this.isDisplaced){
            this.life--;

            this.x += (this.position.x - this.x) * acceleration;
            this.y += (this.position.y - this.y) * acceleration;

            this.velocity.x *= damping;
            this.velocity.y *= damping;

            this.x += this.velocity.x * damping;
            this.y += this.velocity.y * damping;

            if (this.life == 0){
                this.isDisplaced = false;
            }
        } else {
            this.velocity.x += (this.targetPosition.x - this.x) * acceleration;
            this.velocity.y += (this.targetPosition.y - this.y) * acceleration;

            this.velocity.x *= damping;
            this.velocity.y *= damping;

            this.x += this.velocity.x * damping;
            this.y += this.velocity.y * damping;

        }

    }
}