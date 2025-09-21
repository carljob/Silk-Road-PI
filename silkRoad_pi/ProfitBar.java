import javax.swing.*;

public class ProfitBar {
    private JFrame frame;
    private JProgressBar bar;
    private int maxProfit;

    public ProfitBar(int maxProfit) {
        this.maxProfit = maxProfit;

        frame = new JFrame("Ganancias SilkRoad");
        frame.setSize(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bar = new JProgressBar(0, maxProfit);
        bar.setValue(0);
        bar.setStringPainted(false); 

        frame.add(bar);
        frame.setVisible(true);
    }

    /** Actualiza el valor actual de la barra */
    public void updateProfit(int profit) {
        if (profit > maxProfit) profit = maxProfit;
        bar.setValue(profit);
    }

    /** Si cambia el máximo de ganancias posibles */
    public void setMaxProfit(int maxProfit) {
        this.maxProfit = maxProfit;
        bar.setMaximum(maxProfit);
    }
}
