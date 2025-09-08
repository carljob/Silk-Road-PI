/**
 * Representa un robot en un espacio horizontal en forma circular
 * Cada robot puede moverse reiniciar su posicion inicial y gestionar su visibilidad
 * Ademas se mantiene informacion sobre su orden de llegada y una cantidad de tenges
 */

public class Robot {

    private int initialLocation;
    private int currentLocation;
    private int orderOfArrival;
    private Circle shape; 
    private String color;
    private boolean isVisible;
    private int tenges;
    private static final int SCALE = 10; 

    /**
     * Crea un nuevo robot en una ubicacion inicial y con un color
     * @param location Posicion inicial horizontal del robot
     * @param color Color del robot
     */
    
    public Robot(int location, String color) {
        this.initialLocation = location;
        this.currentLocation = location;
        this.color = color;
        this.shape = new Circle();
        this.isVisible = false;
        this.tenges = 0;
        this.orderOfArrival = 0;

        updatePosition();
    }

    /**
     * Actualiza la posicion grafica del robot basado en su posicion actual
     * Mueve el circulo asociado horizontalmente y fija la vertical en 10
     */
    
    private void updatePosition() {
        shape.moveHorizontal(currentLocation * SCALE);
        shape.moveVertical(10);
    }
    
    /**
     * Obtiene la posicion actual del robot
     */

    public int getCurrentLocation() {
        return currentLocation;
    }
    
    /**
     * Mueve el robot una cantidad determinada de metros
     * @param meters Cantidad de metros a mover
     */

    public void move(int meters) {
        currentLocation += meters;
        updatePosition();
    }
    
     /**
     * Reinicia el robot a su posicion inicial
     */

    public void resetPosition() {
        currentLocation = initialLocation;
        updatePosition();
    }
    
    /**
     * Establece el orden de llegada del robot
     * @param order Numero que representa el orden de llegada.
     */

    public void setOrderOfArrival(int order) {
        this.orderOfArrival = order;
    }
    
    /**
     * Obtiene el orden de llegada del robot
     */

    public int getOrderOfArrival() {
        return orderOfArrival;
    }
    
    /**
     * Obtiene la cantidad de tenges del robot
     */

    public int getTenges() {
        return tenges;
    }

     /**
     * Hace visible al robot 
     * Cambia su color y actualiza su estado de visibilidad
     */
    
    public void makeVisible() {
        if (!isVisible) {
            shape.changeColor(color);
            shape.makeVisible();
            isVisible = true;
        }
    }
    
    /**
     * Hace invisible al robot
     * Actualiza el estado de visibilidad
     */

    public void makeInvisible() {
        if (isVisible) {
            shape.makeInvisible();
            isVisible = false;
        }
    }
}
