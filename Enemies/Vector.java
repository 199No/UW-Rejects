package Enemies;


// Class to represent a Vector (for direction and movement)
class Vector {
    double x, y;

    // Constructor to initialize vector components
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Normalize the vector (make it a unit vector)
    public Vector normalize() {
        double magnitude = Math.sqrt(x * x + y * y);  // Calculate the magnitude (length)
        return new Vector(x / magnitude, y / magnitude);  // Return normalized vector
    }
    public double getxPos(){
        return this.x;
    }
    public double getyPos(){
        return this.y;
    }

    // Optional: String representation for easy printing
    @Override
    public String toString() {
        return String.format("Vector(%.2f, %.2f)", x, y);
    }
}
