package src;

/**
 *
 * @author Chamtty
 */
public class Colisor {
    
    protected int x, y;
    protected static int size = 20;

    public static void setSize(int size) {
        Colisor.size = size;
    }
    public static int getSize() {
        return Colisor.size;
    }
    
    protected boolean verificadorColisao(Colisor box1, Colisor box2){
        //SE OUVER COLISÃO RETORNA TRUE
        if(box1.x+size > box2.x && box1.x < box2.x+box2.size
           && box1.y+size > box2.y && box1.y < box2.y+box2.size){
            return true;
        }else{
            return false;
        }
    }
}
