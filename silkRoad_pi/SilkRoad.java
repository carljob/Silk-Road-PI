import java.util.*;

public class SilkRoad {
    private final int length;
    private final Map<Integer, Store> stores = new HashMap<>();
    private final Map<Integer, Robot> robots = new HashMap<>();
    private final Map<Integer, Store> initialStores = new HashMap<>();
    private final Map<Integer, Robot> initialRobots = new HashMap<>();
    private boolean visible = true;
    private boolean lastOk = true;
    private int profitToday = 0;
    private int arrivalCounter = 0;
    private ColorManager robotColors = new ColorManager();
    private ColorManager storeColors = new ColorManager();
    private static final int defaultLength = 1000;
    private static final int MATRIX_SIZE = 15; // matriz fija de 15x15
    private ProfitBar profitBar;

    // tablero de celdas
    private Cell[] cells;
    private int cellSize = 30;

    public SilkRoad(int length) {
        this.length = length;
        this.cells = new Cell[length];
        profitBar = new ProfitBar(1000); // máximo inicial

        List<Cell> recorrido = RouteBuilder.buildSpiralInGrid(MATRIX_SIZE, cellSize, 0, 0);

        for (int i = 0; i < length; i++) {
            cells[i] = recorrido.get(i);
            if (visible) cells[i].makeVisible();
        }
    }


    /** Coloca una tienda en una ubicación específica */
    public void placeStore(int location, int tenges) {
        if (location < 0 || location >= length) {
            lastOk = false;
            if (visible) System.out.println("Ubicación inválida para tienda: " + location);
            return;
        }
        if (stores.containsKey(location)) {
            lastOk = false;
            if (visible) System.out.println("Ya hay tienda en esa celda: " + location);
        } else {
            String color = storeColors.nextColor();
            Store nueva = new Store(location, tenges, color);
            stores.put(location, nueva);
            initialStores.put(location, nueva);

            if (cells[location] != null) {
                nueva.placeInCell(cells[location]);
                nueva.makeVisible();
            }

            actualizarMaxProfit(); 
            lastOk = true;
        }
    }

    /** Coloca un robot en una ubicación específica */
    public void placeRobot(int location) {
        if (location < 0 || location >= length) {
            lastOk = false;
            if (visible) System.out.println("Ubicación inválida para robot: " + location);
            return;
        }
        if (robots.containsKey(location)) {
            lastOk = false;
            if (visible) System.out.println("Ya hay robot en esa celda: " + location);
        } else {
            String color = robotColors.nextColor();
            Robot nuevo = new Robot(location, color);
            robots.put(location, nuevo);
            initialRobots.put(location, nuevo);

            if (cells[location] != null) {
                nuevo.placeInCell(cells[location]);
                nuevo.makeVisible();
            }

            lastOk = true;
        }
    }

    /** Elimina la tienda en la ubicación indicada */
    public void removeStore(int location) {
        if (location < 0 || location >= length) {
            lastOk = false;
            if (visible) System.out.println("Ubicación inválida para removeStore: " + location);
            return;
        }

        if (stores.containsKey(location)) {
            Store s = stores.remove(location);
            if (s != null) {
                s.makeInvisible();
                if (cells[location] != null) cells[location].clear();
            }
            actualizarMaxProfit(); 
            lastOk = true;
            return;
        }

        Store toRemove = null;
        Integer keyToRemove = null;
        for (Map.Entry<Integer, Store> e : stores.entrySet()) {
            if (e.getValue().getLocation() == location) {
                toRemove = e.getValue();
                keyToRemove = e.getKey();
                break;
            }
        }

        if (toRemove != null) {
            stores.remove(keyToRemove);
            toRemove.makeInvisible();
            if (cells[location] != null) cells[location].clear();
            actualizarMaxProfit(); 
            lastOk = true;
        } else {
            lastOk = false;
            if (visible) System.out.println("No existe tienda en la celda: " + location);
        }
    }

    /** Elimina el robot en la ubicación indicada */
    public void removeRobot(int location) {
        if (location < 0 || location >= length) {
            lastOk = false;
            if (visible) System.out.println("Ubicación inválida para removeRobot: " + location);
            return;
        }

        if (robots.containsKey(location)) {
            Robot r = robots.remove(location);
            if (r != null) {
                r.makeInvisible();
                if (cells[location] != null) cells[location].clear();
            }
            lastOk = true;
            return;
        }

        Robot toRemove = null;
        Integer keyToRemove = null;
        for (Map.Entry<Integer, Robot> e : robots.entrySet()) {
            if (e.getValue().getCurrentLocation() == location) {
                toRemove = e.getValue();
                keyToRemove = e.getKey();
                break;
            }
        }

        if (toRemove != null) {
            robots.remove(keyToRemove);
            toRemove.makeInvisible();
            if (cells[location] != null) cells[location].clear();
            lastOk = true;
        } else {
            lastOk = false;
            if (visible) System.out.println("No existe robot en la celda: " + location);
        }
    }

    /** Reabastecer todas las tiendas */
    public void resupplyStores() {
        if (stores.isEmpty()) {
            lastOk = false;
            if (visible) System.out.println("No hay tiendas para reabastecer.");
        } else {
            for (Store s : stores.values()) {
                s.resupply();
            }
            actualizarMaxProfit(); 
            lastOk = true;
        }
    }

    /** Retornar todos los robots a sus posiciones iniciales */
    public void returnRobots() {
        if (robots.isEmpty()) {
            lastOk = false;
            if (visible) System.out.println("No hay robots para retornar.");
        } else {
            for (Robot r : robots.values()) {
                r.resetPosition();
            }
            lastOk = true;
        }
    }

    /**
     * Mueve un robot a lo largo de la ruta.
     *
     * Este método realiza varias validaciones y acciones:
     * 
     *   Verifica que la ubicación inicial esté dentro de los límites.
     *   Busca el robot en la posición especificada o dentro del mapa de robots.
     *   Calcula la nueva posición y verifica que no salga de la ruta ni choque con otro robot.
     *   Mueve al robot, actualiza la celda de origen y destino y la visibilidad.
     *   Si hay una tienda en la nueva posición y no se ha vaciado hoy, calcula la ganancia
     *       restando la distancia recorrida al recurso actual de la tienda, actualiza las ganancias
     *       y vacía la tienda.
     *   Registra el orden de llegada del robot.
     *   Actualiza el estado de la operación en la variable.
     * 
     *
     * @param location la celda inicial donde se encuentra el robot
     * @param meters cantidad de celdas a mover (puede ser negativa para mover hacia atrás)
     */
    public void moveRobot(int location, int meters) {
     
        if (location < 0 || location >= length) {
            lastOk = false;
            if (visible) System.out.println("Ubicación inválida para mover: " + location);
            return;
        }
    
    
        Robot robot = robots.get(location);
        Integer sourceKey = null;
    
        if (robot == null) {
            for (Map.Entry<Integer, Robot> e : robots.entrySet()) {
                if (e.getValue().getCurrentLocation() == location) {
                    sourceKey = e.getKey();
                    robot = e.getValue();
                    break;
                }
            }
        } else {
            sourceKey = location;
        }
    
        if (robot == null) {
            lastOk = false;
            if (visible) System.out.println("Robot no encontrado en celda: " + location);
            return;
        }

        int oldPos = robot.getCurrentLocation();
        int newPos = oldPos + meters;
        if (newPos < 0 || newPos >= length) {
            lastOk = false;
            if (visible) System.out.println("El robot salió de la ruta");
            return;
        }

        boolean occupied = false;
        for (Map.Entry<Integer, Robot> e : robots.entrySet()) {
            Robot r = e.getValue();
            if (r == robot) continue;
            if (r.getCurrentLocation() == newPos) {
                occupied = true;
                break;
            }
        }
        if (occupied) {
            lastOk = false;
            if (visible) System.out.println("Movimiento inválido: otra pieza ocupa la celda " + newPos);
            return;
        }
    
        robot.makeInvisible();
        if (cells[oldPos] != null) cells[oldPos].clear();
        robot.move(meters);
        if (cells[newPos] != null) {
            robot.placeInCell(cells[newPos]);
            robot.makeVisible();
        }

        if (sourceKey != null && !sourceKey.equals(newPos)) {
            robots.remove(sourceKey);
        }
        robots.put(newPos, robot);
    
        Store store = stores.get(newPos);
        if (store != null && !store.isEmptiedToday()) {
            int distancia = Math.abs(newPos - oldPos);
            int ganancia = store.getCurrentTenges() - distancia;
            if (ganancia > 0) {
                profitToday += ganancia;
                profitBar.updateProfit(profitToday);
            }
            store.empty();
        }
    
        robot.setOrderOfArrival(arrivalCounter++);
        lastOk = true;
    }


    public long profit() {
        return profitToday;
    }

    public void reboot() {
        stores.clear();
        robots.clear();
        for (Cell c : cells) {
            if (c != null) c.clear();
        }

        for (Map.Entry<Integer, Store> e : initialStores.entrySet()) {
            placeStore(e.getKey(), e.getValue().getCurrentTenges());
        }
        for (Map.Entry<Integer, Robot> e : initialRobots.entrySet()) {
            placeRobot(e.getKey());
        }
        profitToday = 0;
        profitBar.updateProfit(0); 
        lastOk = true;
    }

    public List<String> stores() {
        List<Map.Entry<Integer, Store>> lista = new ArrayList<>(stores.entrySet());
        lista.sort(Comparator.comparingInt(Map.Entry::getKey));
        List<String> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Store> entry : lista) {
            resultado.add("[" + entry.getKey() + ", " + entry.getValue().getCurrentTenges() + "]");
        }
        return resultado;
    }

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

    public void makeVisible() {
        if (!visible) visible = true;
        for (Cell c : cells) {
            if (c != null) c.makeVisible();
        }
    }

    public void makeInvisible() {
        if (visible) visible = false;
        for (Cell c : cells) {
            if (c != null) c.makeInvisible();
        }
    }

    public void finish() {
        for (Cell c : cells) {
            if (c != null) c.makeInvisible();
        }
        stores.clear();
        robots.clear();
        profitToday = 0;
        profitBar.updateProfit(0); 
        visible = false;
        lastOk = true;
    }

    public boolean ok() {
        return lastOk;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCells(List<Cell> route) {
        this.cells = route.toArray(new Cell[0]);
        for (Cell c : cells) {
            if (c != null && visible) c.makeVisible();
        }
    }

    /** Calcula el máximo de ganancias posibles basado en las tiendas actuales */
    private void actualizarMaxProfit() {
        int max = 0;
        for (Store s : stores.values()) {
            max += s.getCurrentTenges();
        }
        profitBar.setMaxProfit(max);
    }
}
