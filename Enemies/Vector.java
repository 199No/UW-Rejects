package Enemies;


class Vector {
    double x, y;

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

}
