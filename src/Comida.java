package src;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Chamtty
 */
public class Comida extends Colisor {

    private Random ran = new Random();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Comida() {

    }

    public void gerarComida(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        //System.out.println("minWidth: "+minWidth+" maxWidth: "+maxWidth+" minHeight: "+minHeight+" maxHeight: "+maxHeight);
        this.x = ((ran.nextInt(((maxWidth - this.getSize()) - minWidth)) + minWidth) / this.getSize()) * this.getSize();
        this.y = ((ran.nextInt(((maxHeight - this.getSize()) - minHeight)) + minHeight) / this.getSize()) * this.getSize();
    }

    public void gerarComida(int minWidth, int maxWidth, int minHeight, int maxHeight, ArrayList colisor) {

        this.x = ((ran.nextInt(((maxWidth - this.getSize()) - minWidth)) + minWidth) / this.getSize()) * this.getSize();
        this.y = ((ran.nextInt(((maxHeight - this.getSize()) - minHeight)) + minHeight) / this.getSize()) * this.getSize();
        for (int i = 0; i < colisor.size(); i++) {
            if (verificadorColisao(this, (Colisor) colisor.get(i))) {
                gerarComida(minWidth, maxWidth, minHeight, maxHeight, colisor);
            }
        }

    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, size, size);
    }

}
