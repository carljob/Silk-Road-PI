/**
 * Clase de prueba para la simulación SilkRoad.
 * 
 * Este programa realiza un ciclo completo de operaciones basicas en la Ruta de la Seda
 * Crear la ruta con una longitud especifica
 * Colocar tiendas y robots en posiciones determinadas
 * Mover un robot y calcular la ganancia acumulada
 * Reabastecer las tiendas y devolver los robots a sus posiciones iniciales
 * Verificar el estado de las operaciones usando
 * Este test permite observar el comportamiento de la simulacion y validar la
 * interaccion entre robots tiendas y la acumulacion de ganancias
 * 
 */
public class TestCycle {

    /**
     * Metodo principal que ejecuta un ciclo de prueba completo en SilkRoad
     */
    public static void main(String[] args) {
        // Crear la ruta de la seda con longitud 1000
        SilkRoad road = new SilkRoad(1000);

        // Hacer visible la ruta inicialmente
        road.makeVisible();

        // === Colocando tiendas ===
        System.out.println("== Colocando tiendas ==");
        road.placeStore(10, 200);  // Tienda en posición 10 con 200 tenges
        road.placeStore(50, 300);  // Tienda en posición 50 con 300 tenges
        road.makeVisible();         // Actualizar visualización
        System.out.println("Tiendas: " + road.stores());
        System.out.println("ok = " + road.ok());  // Verificar éxito de la operación

        // === Colocando robots ===
        System.out.println("\n== Colocando robots ==");
        road.placeRobot(5);   // Robot en posición 5
        road.placeRobot(20);  // Robot en posición 20
        road.makeVisible();
        System.out.println("Robots: " + road.robots());
        System.out.println("ok = " + road.ok());

        // === Moviendo un robot ===
        System.out.println("\n== Moviendo robot en 5 posiciones +5 ==");
        road.moveRobot(5, 5);   // Mueve el robot de la posición 5 a la 10
        road.makeVisible();
        System.out.println("Ganancia acumulada: " + road.profit());
        System.out.println("ok = " + road.ok());

        // === Reabasteciendo tiendas ===
        System.out.println("\n== Reabasteciendo ==");
        road.resupplyStores();
        road.makeVisible();
        System.out.println("Tiendas: " + road.stores());
        System.out.println("ok = " + road.ok());

        // === Devolviendo robots a posición inicial ===
        System.out.println("\n== Devolviendo robots ==");
        road.returnRobots();
        road.makeVisible();
        System.out.println("Robots: " + road.robots());
        System.out.println("ok = " + road.ok());
    }
}
