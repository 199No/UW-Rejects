package Enemies;

class Vector {
    double x;
    double y;
    double magnitude; // length

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector normalize(double speed) {
        double magnitude = Math.sqrt(x * x + y * y);
        if (magnitude == 0) {
            // Return a zero vector if the magnitude is zero
            return new Vector(0, 0);
        }
        // Normalize and scale the vector
        double normalizedX = (x / magnitude) * speed;
        double normalizedY = (y / magnitude) * speed;
        return new Vector(normalizedX, normalizedY);
    }
    
    public double getxPos(){
        return this.x;
    }

    public double getyPos(){
        return this.y;
    }

    public double getMagnitude(){
        return this.magnitude;
    }

}
