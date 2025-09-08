/**
 * Representa una tienda en una linea horizontal con una cantidad de recursos llamada "tenges".
 * Cada tienda tiene una posicion fija puede reabastecerse vaciarse y ser mostrada graficamente
 */

public class Store {
    private int location;
    private int initialTenges;
    private int currentTenges;
    private Rectangle shape;
    private String color;
    private boolean isVisible;
    private static final int SCALE = 10; 

    /**
     * Crea una nueva tienda en una ubicacion especifica con cierta cantidad de tenges y un color
     * @param location Ubicacion horizontal de la tienda
     * @param tenges Cantidad inicial de tenges
     * @param color Color de la tienda
     */
    
    public Store(int location, int tenges, String color) {
        this.location = location;
        this.initialTenges = tenges;
        this.currentTenges = tenges;
        this.color = color;
        this.shape = new Rectangle();
        this.isVisible = false;

        updatePosition();
    }
    
    /**
     * Actualiza la posicion grafica de la tienda segun su ubicacion.
     * Mueve el rectangulo horizontalmente y fija la posicion vertical en 100.
     */

    private void updatePosition() {
        shape.moveHorizontal(location * SCALE);
        shape.moveVertical(100); 
    }

    /**
     * Obtiene la ubicacion horizontal de la tienda
     */
    
    public int getLocation() {
        return location;
    }
    
    /**
     * Obtiene la cantidad actual de tenges disponibles en la tienda
     */

    public int getCurrentTenges() {
        return currentTenges;
    }
    
    /**
     * Reabastece la tienda a su cantidad inicial de tenges
     */

    public void resupply() {
        currentTenges = initialTenges;
    }
    
    /**
     * Vacia la tienda dejando 0 tenges disponibles
     */

    public void empty() {
        currentTenges = 0;
    }

    /**
     * Indica si la tienda se ha quedado sin tenges hoy
     */
    
    public boolean isEmptiedToday() {
        return currentTenges == 0;
    }
    
     /**
     * Hace visible la tienda en la interfaz grafica
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
     * Hace invisible la tienda en la interfaz grafica y actualiza su estado de visibilidad.
     */

    public void makeInvisible() {
        if (isVisible) {
            shape.makeInvisible();
            isVisible = false;
        }
    }
}
