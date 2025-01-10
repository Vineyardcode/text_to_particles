public class Particle {

    public double x, y;
    public double originalX, originalY;
    public double targetX, targetY;
    double velocityX, velocityY;
    private int life;

    public Particle(int x, int y){
        this.x = this.originalX = x;
        this.y = this.originalY = y;
        this.targetX = x;
        this.targetY = y;
        this.life = 1;
        this.velocityX = 0;
        this.velocityY = 0;
    }

    public void setNewTarget(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void displace(int mouseX, int mouseY) {
        double angle = Math.atan2(mouseY - this.y, mouseX - this.x);
        this.targetX = this.x + Math.cos(angle) * 50;
        this.targetY = this.y + Math.sin(angle) * 50;
    }

    public void update() {
        double acceleration = 1;
        double damping = 0.42;

        velocityX += (targetX - x) * acceleration;
        velocityY += (targetY - y) * acceleration;

        velocityX *= damping;
        velocityY *= damping;

        x += velocityX * easeInOutBack(damping);
        y += velocityY * easeInOutBack(damping);
    }

    private double easeInOutBack(double x) {
        final double c1 = 1.70158;
        final double c2 = c1 * 1.525;

        return x < 0.5
                ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
                : (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
    }

}
