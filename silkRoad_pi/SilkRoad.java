import java.util.*;

public class SilkRoad {
    private final int length;
    private final Map<Integer, Store> stores = new HashMap<>();
    private final Map<Integer, Robot> robots = new HashMap<>();
    private boolean visible = true;
    private boolean lastOk = true;
    private int profitToday = 0;
    private int arrivalCounter = 0;
    private ColorManager robotColors = new ColorManager();
    private ColorManager storeColors = new ColorManager();
    private static final int defaultLength = 1000;

    /**
     * Crea una nueva Ruta de la Seda de longitud especificada
     * Si la longitud es menor o igual a 0 se asigna un valor por defecto de 100
     * @param length Longitud de la ruta
     */
    
    public SilkRoad(int length) {
        if (length <= 0) {
            this.length = defaultLength; 
            lastOk = false;     
        } else {
            this.length = length;
            lastOk = true;
        }
        profitToday = 0;
        visible = true;
    }

    /**
     * Coloca una tienda en una ubicacion especifica con cierta cantidad de tenges
     * @param location Ubicacion de la tienda
     * @param tenges Cantidad inicial de tenges
     */

    public void placeStore(int location, int tenges) {
        if (stores.containsKey(location)) {
            lastOk = false;
        } else {
            String color = storeColors.nextColor();
            Store nueva = new Store(location, tenges, color);
            stores.put(location, nueva);
            lastOk = true;
        }
    }

    /**
     * Coloca un robot en una ubicacion especifica
     * @param location Ubicacion inicial del robot
     */

    public void placeRobot(int location) {
        if (robots.containsKey(location)) {
            lastOk = false;
        } else {
            String color = robotColors.nextColor();
            Robot nuevo = new Robot(location, color);
            robots.put(location, nuevo);
            lastOk = true;
        }
    }
    
    /** 
    * Elimina la tienda en la ubicacion indicada
    */
   
    public void removeStore(int location) {
        if (stores.containsKey(location)) {
            stores.remove(location);   
            lastOk = true;
        } else {
            lastOk = false;
        }
    }
    
    /** 
    * Elimina el robot cuyo location inicial es igual al indicado
    */
   
    public void removeRobot(int location) {
        if (robots.containsKey(location)) {
            robots.remove(location);   
            lastOk = true;
        } else {
            lastOk = false;
        }
    }

    /** 
    * Reabastecer todas las tiendas con sus valores iniciales 
    */
     
    public void resupplyStores() {
        if (stores.isEmpty()) {
            lastOk = false; 
        } else {
            for (Store s : stores.values()) {
                s.resupply();
            }
            lastOk = true;
        }
    }
    
    /** 
    * Retornar todos los robots a sus posiciones iniciales 
    */
    public void returnRobots() {
        if (robots.isEmpty()) {
            lastOk = false; // no habia robots
        } else {
            for (Robot r : robots.values()) {
                r.resetPosition();
            }
            lastOk = true;
        }
    }
    
    /**
     * Mueve un robot una cantidad determinada de metros
     * Calcula las ganancias si el robot llega a una tienda con tenges disponibles
     * @param location Ubicacion inicial del robot
     * @param meters Distancia a mover
     */

    public void moveRobot(int location, int meters) {
        Robot robot = robots.get(location);
    
        if (robot == null) {
            lastOk = false;
            return;
        }
    
        int currentPos = robot.getCurrentLocation();
        Robot firstArrived = robot;
    
        for (Robot r : robots.values()) {
            if (r.getCurrentLocation() == currentPos) {
                if (r.getOrderOfArrival() < firstArrived.getOrderOfArrival()) {
                    firstArrived = r;
                }
            }
        }
    
        if (firstArrived != robot) {
            lastOk = false;
            return;
        }
    
        int oldPosition = robot.getCurrentLocation();
        robot.move(meters);
        int newPosition = robot.getCurrentLocation();
    
        Store store = stores.get(newPosition);
        if (store != null && !store.isEmptiedToday()) {
            int distancia = Math.abs(newPosition - oldPosition);
            int ganancia = store.getCurrentTenges() - distancia;
            if (ganancia > 0) {
                profitToday += ganancia;
            }
            store.empty();
        }
    
        robot.setOrderOfArrival(arrivalCounter++);
    
        lastOk = true;
    }

    /** Devuelve la ganancia acumulada en el dia
    *
    */
    public long profit() {
        return profitToday;
    }
    
    /** Reinicia la ruta de seda a su estado inicial. 
    *
    */
    public void reboot() {
        for (Store s : stores.values()) {
            s.resupply();
        }
    
        for (Robot r : robots.values()) {
            r.resetPosition();
        }
    
        profitToday = 0;
    
        lastOk = true;
    }
    
    /** Devuelve las tiendas ordenadas por localización [location, tenges]. 
        Idea de ordenamiento saca con IAGen 
        ya que no teniamos conocimiento de como hacerlo al usar HashMaps
       */
    
    public List<String> stores() {
        List<Map.Entry<Integer, Store>> lista = new ArrayList<>(stores.entrySet());
        lista.sort(Comparator.comparingInt(Map.Entry::getKey));
    
        List<String> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Store> entry : lista) {
            resultado.add("[" + entry.getKey() + ", " + entry.getValue().getCurrentTenges() + "]");
        }
        return resultado;
    }
    
    /** Devuelve los robots ordenados por localización [location, tenges]. 
        Idea de ordenamiento saca con IAGen 
        ya que no teniamos conocimiento de como hacerlo al usar HashMaps
        */
    public List<String> robots() {
        List<Map.Entry<Integer, Robot>> lista = new ArrayList<>(robots.entrySet());
        lista.sort(Comparator.comparingInt(Map.Entry::getKey));
    
        List<String> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Robot> entry : lista) {
            int loc = entry.getKey();
            int tenges = entry.getValue().getTenges();
            resultado.add("[" + loc + ", " + tenges + "]");
        }
        return resultado;
    }
    
    /**
     * Hace visible graficamente la ruta mostrando todas las tiendas y robots
     */

    public void makeVisible() {
        if (!visible) {
            visible = true;
        }
        Canvas canvas = Canvas.getCanvas();
    
        for (Store s : stores.values()) {
            s.makeVisible();
        }
    
        for (Robot r : robots.values()) {
            r.makeVisible();
        }
    }
    
    /**
     * Oculta graficamente la ruta todas las tiendas y robots
     */
    
    public void makeInvisible() {
        if (visible) {
            visible = false;
        }
        
        for (Store s : stores.values()) {
            s.makeInvisible();
        }
        
        for (Robot r : robots.values()) {
            r.makeInvisible();
        }
    }
    
    /**
    * Finaliza la simulacion oculta todo y marca que termino
    */
   
    public void finish() {
        for (Store s : stores.values()) {
            s.makeInvisible();
        }
        
        for (Robot r : robots.values()) {
            r.makeInvisible();
        }
    
        stores.clear();
        robots.clear();
        profitToday = 0;
        visible = false;
    
        lastOk = true;
    }
    
    /**
     * Indica si la ultima operacion fue exitosa
     */

    public boolean ok() { 
        return lastOk;
    }
}
