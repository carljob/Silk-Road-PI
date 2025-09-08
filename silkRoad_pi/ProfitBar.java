import javax.swing.*;

/**
 * ================ idea de IAGen =========================
 * Representa una barra de progreso gráfica que muestra la ganancia acumulada
 * en la simulación de SilkRoad.
 * 
 * 
 * La barra tiene un valor máximo determinado al crearla, y se puede actualizar
 * dinamicamente mediante el metodo  Se muestra en
 * un JFrame independiente.
 * 
 */
public class ProfitBar {

    private JFrame frame;
    private JProgressBar bar;
    private int maxProfit;

    /**
     * Constructor que crea una ventana con una barra de progreso inicializada
     * @param maxProfit Ganancia maxima que la barra puede mostrar
     */
    public ProfitBar(int maxProfit) {
        this.maxProfit = maxProfit;
        frame = new JFrame("Ganancias SilkRoad");
        frame.setSize(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bar = new JProgressBar(0, maxProfit);
        bar.setValue(0);
        bar.setStringPainted(true);

        frame.add(bar);
        frame.setVisible(true);
    }

    /**
     * Actualiza la barra de progreso con la ganancia actual
     * Si el valor de la ganancia excede el máximo, se limita a
     * @param profit Ganancia actual a mostrar en la barra
     */
    public void updateProfit(int profit) {
        if (profit > maxProfit) {
            profit = maxProfit;
        }
        bar.setValue(profit);
        bar.setString("Ganancia: " + profit);
    }
}
