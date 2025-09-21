/**
 * Representa un robot en un espacio horizontal basado en celdas.
 * Cada robot puede moverse, reiniciar su posición inicial y gestionar su visibilidad.
 * Además mantiene información sobre su orden de llegada y su cantidad de tenges.
 */
public class Robot {

    private int initialLocation;
    private int currentLocation;
    private int orderOfArrival;
    private Circle shape;          
    private Circle eyeLeft;        
    private Circle eyeRight;       
    private Rectangle mouth;       
    private String color;
    private boolean isVisible;
    private int tenges;

    public Robot(int location, String color) {
        this.initialLocation = location;
        this.currentLocation = location;
        this.color = color;
        this.shape = null;
        this.eyeLeft = null;
        this.eyeRight = null;
        this.mouth = null;
        this.isVisible = false;
        this.tenges = 0;
        this.orderOfArrival = 0;
    }

    /**
     * Coloca el robot dentro de una celda, centrado, con cara (ojos y boca).
     */
    public void placeInCell(Cell cell) {
        int cx = cell.getX();
        int cy = cell.getY();
        int cellSize = cell.getSize();

        int bodySize = (int)(cellSize * 0.7);
        int offset = (cellSize - bodySize) / 2;

        shape = new Circle();
        shape.changeSize(bodySize);
        shape.changeColor(color);
        shape.setAbsolutePosition(cx + offset, cy + offset);

        int eyeSize = Math.max(4, bodySize / 5);

        eyeLeft = new Circle();
        eyeLeft.changeSize(eyeSize);
        eyeLeft.changeColor("white");
        eyeLeft.setAbsolutePosition(cx + offset + bodySize / 4,
                                    cy + offset + bodySize / 4);

        eyeRight = new Circle();
        eyeRight.changeSize(eyeSize);
        eyeRight.changeColor("white");
        eyeRight.setAbsolutePosition(cx + offset + (bodySize / 2),
                                     cy + offset + bodySize / 4);

        mouth = new Rectangle();
        mouth.changeSize(Math.max(3, bodySize / 10), bodySize / 2);
        mouth.changeColor("black");
        mouth.setAbsolutePosition(cx + offset + (bodySize / 4),
                                  cy + offset + (2 * bodySize / 3));

        if (isVisible) {
            shape.makeVisible();
            eyeLeft.makeVisible();
            eyeRight.makeVisible();
            mouth.makeVisible();
        }
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int loc) {
        this.currentLocation = loc;
    }

    public void move(int meters) {
        currentLocation += meters; 
    }

    public void resetPosition() {
        currentLocation = initialLocation;
    }

    public void setOrderOfArrival(int order) {
        this.orderOfArrival = order;
    }

    public int getOrderOfArrival() {
        return orderOfArrival;
    }

    public int getTenges() {
        return tenges;
    }

    public String getColor() {
        return color;
    }

    public void makeVisible() {
        if (!isVisible) {
            if (shape != null) shape.makeVisible();
            if (eyeLeft != null) eyeLeft.makeVisible();
            if (eyeRight != null) eyeRight.makeVisible();
            if (mouth != null) mouth.makeVisible();
            isVisible = true;
        }
    }

    public void makeInvisible() {
        if (isVisible) {
            if (shape != null) shape.makeInvisible();
            if (eyeLeft != null) eyeLeft.makeInvisible();
            if (eyeRight != null) eyeRight.makeInvisible();
            if (mouth != null) mouth.makeInvisible();
            isVisible = false;
        }
    }
}
