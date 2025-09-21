import java.awt.*;
import java.awt.geom.*;

/**
 * A circle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0.  (15 July 2000) 
 */

public class Circle{

    public static final double PI=3.1416;
    
    private int diameter;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private double area; 
    private int disco = 0;
    

    public Circle(){
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }

    public Circle(double area){
        
        this.area = area;
        double radio = Math.sqrt(area / PI );
        diameter = (int)(2 * radio);
        xPosition = 20;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }

    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, 
                diameter, diameter));
            canvas.wait(10);
        }
    }

    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
    /**
     * Move the circle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the circle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the circle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the circle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the circle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the circle horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }

    /**
     * Slowly move the circle vertically
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        }else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }

    /**
     * Change the size.
     * @param newDiameter the new size (in pixels). Size must be >=0.
     */
    public void changeSize(int newDiameter){
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    /**
     * area del circulo dado el diametro
     */
    
    public double area(){
        double radio = diameter / 2.0;
        double area= PI * radio * radio;
        return area;
    }

    public void bigger(int percentage){
        if(percentage < 0|| percentage > 100){
            System.out.println("error en el pocentaje");
            return;
        }
        double area = area();
        double incremento = area *(percentage / 100.0);
        double newArea = area + incremento;
        double newRadio = Math.sqrt(newArea/PI);
        diameter = (int)(2 * newRadio);
        
        erase();
        draw();

    }

        /**
         * Con ayuda de IAGEN formula para calcular la reduccion del circulo
         */
        
    public void shrink(int times, int areaF) {
        for (int i = 0; i < times; i++) {
            if (area() <= areaF) {
                break;
            }
            double newArea = area() / 2.0; 
            double newRadius = Math.sqrt(newArea / Math.PI);
            diameter = (int)(2 * newRadius);
            erase();
            draw();
        }
    }
    
    public void setPosition(int newX, int newY) {
        int dx = newX - xPosition;
        int dy = newY - yPosition;
        moveHorizontal(dx);
        moveVertical(dy);
    }
    
    public void setAbsolutePosition(int newX, int newY) {
        erase();
        xPosition = newX;
        yPosition = newY;
        draw();
    }


    public void discoTime(int time){
        String[] colors = {"red", "green", "blue", "yellow", "magenta", "black"};
        for(int i = 0; i < time; i++){
            changeColor(colors[disco]);
            disco++;
            if(disco >= colors.length){
                disco = 0;
            }
            draw();
        }
    }
}
