package src;

import java.awt.Color;
import java.awt.Graphics;


/**
 *
 * @author Chamtty
 */
public class Cobra extends Colisor {

    private int velocidadeX = 1, velocidadeY = 0;
    private float velocidadeMovimento;
    private int lastX, lastY;
    public static final int UP = -1;
    public static final int DOWN = 1;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    public boolean UP_BOOL = true;
    public boolean DOWN_BOOL = true;
    public boolean LEFT_BOOL = true;
    public boolean RIGHT_BOOL = true;
    private boolean morre = false;
    private int cor = 255;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocidadeX() {
        return velocidadeX;
    }

    public int getVelocidadeY() {
        return velocidadeY;
    }

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void setVelocidadeY(int velocidadeY) {
        this.velocidadeY = velocidadeY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }
    
    public String getLastPos(){
        return "LastX: "+getLastX()+" LastY: "+getLastY();
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }
    
    public Cobra(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean verificadorColisao(Colisor box2) {
        return super.verificadorColisao(this, box2);
    }

    public void verificaUltimaPos() {
        this.lastX = this.x;
        this.lastY = this.y;
    }

    public void update() {
        move();
    }

    private void move() {
        if (!morre) {
            this.x += ((velocidadeX * size));
            this.y += (velocidadeY * size);
        }
    }

    public void morrer() {
        this.morre = true;
    }

    public void setPos(int x, int y) {
        if (!morre) {
            this.x = x;
            this.y = y;
        }
    }
    
    public String getPos(){
        return "X: "+getX()+" Y: "+getY();
    }

    public void normalizaValores() {
        UP_BOOL = true;
        DOWN_BOOL = true;
        RIGHT_BOOL = true;
        LEFT_BOOL = true;
    }
    public void zeraValores() {
        UP_BOOL = true;
        DOWN_BOOL = true;
        RIGHT_BOOL = true;
        LEFT_BOOL = true;
        this.morre = false;
        this.velocidadeX = 1;
        this.velocidadeY = 0;
        this.lastX = 0;
        this.lastY = 0;
        this.x=0;
        this.y=0;
    }

    public void moveUp() {
        if (UP_BOOL && !morre) {
            velocidadeX = 0;
            velocidadeY = -1;
        }
    }

    public void moveDown() {
        if (DOWN_BOOL && !morre) {
            velocidadeX = 0;
            velocidadeY = 1;
        }
    }

    public void moveRight() {
        if (RIGHT_BOOL && !morre) {
            velocidadeY = 0;
            velocidadeX = 1;
        }
    }

    public void moveLeft() {
        if (LEFT_BOOL && !morre) {
            velocidadeY = 0;
            velocidadeX = -1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(cor,cor,cor));
        g.fillRect(x, y, size, size);
        g.setColor(Color.black);
    }
    public void draw(Graphics g, int label) {
        g.setColor(new Color(cor,cor,cor));
        g.fillRect(x, y, size, size);
        g.setColor(Color.black);
        if(label < 10){
            g.drawString(String.valueOf(label), x+(this.size/2), y+(this.size/3)*2);
        }else if(label >= 10){
            g.drawString(String.valueOf(label), x+(this.size/2)/2, y+(this.size/3)*2);
        }
    }

    @Override
    public String toString() {
        return "size: " + size + " velocidadeX: " + velocidadeX + " velocidadeY: " + velocidadeY
                + " x: " + x + " y: " + y + " UP: " + UP + " DOWN: " + DOWN + " LEFT: " + LEFT + " RIGHT: " + RIGHT
                + " UP_BOOL: " + UP_BOOL + " DOWN_BOOL: " + DOWN_BOOL + " LEFT_BOOL: " + LEFT_BOOL + " RIGHT_BOOL: " + RIGHT_BOOL;
    }

}
