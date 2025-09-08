/**
 * Clase que gestiona colores 
 * Esta clase se utiliza para asignar colores 
 */
public class ColorManager {

    private String[] colors;
    private int index;

    /**
     * Inicializa la lista de colores con los valores
     * "red", "blue", "green", "yellow", "magenta", "black"
     */
    public ColorManager() {
        colors = new String[]{"red", "blue", "green", "yellow", "magenta", "black"};
        index = 0;
    }

    /**
     * Retorna el siguiente color de manera ciclica
     * Una vez alcanzado el final de la lista el indice se reinicia
     */
    public String nextColor() {
        String color = colors[index];
        index++;
        if (index >= colors.length) {
            index = 0;  // Reinicia el ciclo
        }
        return color;
    }
}
