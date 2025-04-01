import java.util.Objects;

public class Vector2 {
    public double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2 get() {
        return new Vector2(this.x, this.y);
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public void normalize() {
        double length = length();
        if (length != 0) {
            this.x /= length;
            this.y /= length;
        }
    }

    public Vector2 normalized() {
        double length = length();
        if (length != 0) {
            return new Vector2(this.x / length, this.y / length);
        }
        return new Vector2(0, 0); // Return zero vector if length is zero
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 getNormal() {
        return new Vector2(this.y, -this.x);
    }

    public static double dot(Vector2 v1, Vector2 v2) {
        return (v1.x * v2.x) + (v1.y * v2.y);
    }

    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

    public void log() {
        System.out.println("Vector2: (" + this.x + ", " + this.y + ")");
    }

    public static Vector2 add(Vector2 vecA, Vector2 vecB) {
        return new Vector2(vecA.x + vecB.x, vecA.y + vecB.y);
    }

    public static Vector2 sub(Vector2 vecA, Vector2 vecB) {
        return new Vector2(vecA.x - vecB.x, vecA.y - vecB.y);
    }

    public static Vector2 scale(Vector2 vec, double scalar) {
        return new Vector2(vec.x * scalar, vec.y * scalar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Double.compare(vector2.x, x) == 0 && Double.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
