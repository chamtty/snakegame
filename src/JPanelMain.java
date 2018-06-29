package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Chamtty
 */
public class JPanelMain extends JPanel implements Runnable {

    private JFrame frame;
    private Thread animacao;
    private Colisor colisor;
    private ArrayList<Cobra> cobra;
    private Comida comida;
    private int cobrasLenght = 5;
    private int grid;
    private int maxVelDificult = 130;
    private int minVelDificult = 90;
    private int velDificult;
    private boolean morreu = false;
    private static boolean gameOver = false;
    private int cor = 255;

    public JPanelMain(JFrame main) {
        frame = main;
        this.setSize(frame.getSize());

        setBackground(Color.black);

        inicia();
        if (animacao == null) {
            animacao = new Thread(this);
            animacao.start();
        }

        keyMap();
    }

    @Override
    public void run() {
        comida.gerarComida(0, this.getWidth(), 0, this.getHeight());
        while (true) {

            try {
                //DÁ O UPDATE NA PRIMEIRA COBRA
                cobra.get(0).update();

                //VERIFICA SE A CABEÇA DA COBRA COLIDIU
                //COM OUTRA PARTE DO CORPO
                for (int i = 0; i < cobra.size(); i++) {
                    try {
                        if (cobra.get(0).verificadorColisao(cobra.get(i + 1))) {
                            morreu = true;
                            cobra.get(0).morrer();
                            gameOver = true;
                        }
                    } catch (Exception e) {}

                }
                
                //VERIFICA A ULTIMA POSIÇÃO DAS COBRAS
                for (int i = 0; i < cobra.size(); i++) {
                    cobra.get(i).verificaUltimaPos();
                }
                
                //MOVIMENTA O CORPO DA COBRA
                //PARA A ULTIMA POSIÇÃO DETECTADA
                for (int i = 0; i < cobra.size(); i++) {
                    if (!morreu) {
                        int lastX = cobra.get(i).getLastX();
                        int lastY = cobra.get(i).getLastY();
                        int index = i;
                        cobra.get((index + 1)).setPos(lastX, lastY);
                    }
                }
            } catch (Exception e) {}

            //CHECA A COLISAO COM A COMIDA
            if (cobra.get(0).verificadorColisao(comida)) {
                int lastX = cobra.get(cobra.size() - 1).getX();
                int lastY = cobra.get(cobra.size() - 1).getY();

                cobra.add(new Cobra(lastX, lastY));
                comida.gerarComida(0, this.getWidth(), 0, this.getHeight(), this.cobra);
                this.setBackground(Color.white);
                if (cobra.size() % 2 == 0) {
                    velDificult -= 10;
                    if (velDificult < minVelDificult) {
                        velDificult = minVelDificult;
                    }
                }
            } else {
                this.setBackground(Color.black);
            }
            //-----------------------

            //ULTIMA VERIFICACAO
            verificaCobraSaiuTela();
//            verificaGrid();
            repaint();
            //-----------------------

            //ADICIONA UM TEMPO PARA O PROXIMO QUADRO
            try {
                Thread.sleep(velDificult);
            } catch (InterruptedException ex) {
            }
            //-----------------------

        }
    }

    @Override
    public void paintComponent(Graphics g
    ) {
        super.paintComponent(g);

        for (int i = 1; i < cobra.size(); i++) {

            cobra.get(0).setCor(255);
            cobra.get(0).draw(g, cobra.size());

            if (cobra.get(i).getCor() == 255) {
                cor -= 3;
                if (cor < 50) {
                    cor = 50;
                }
                cobra.get(i).setCor(cor);
            }
            cobra.get(i).draw(g);
        }
        comida.draw(g);

        g.dispose();
    }

    private void verificaGrid(){
        if(colisor != null){
            grid = (this.getHeight() * this.getHeight()) / 10000;
            colisor.setSize(grid);
        }
    }
    
    private void verificaCobraSaiuTela() {
        cobra.get(0).normalizaValores();
        if (cobra.get(0).getX() + cobra.get(0).getSize() >= this.getWidth() - cobra.get(0).getSize()) { //PAREDE DA DIREITA
            cobra.get(0).RIGHT_BOOL = false;
            cobra.get(0).setVelocidadeX(0);

        } else if (cobra.get(0).getX() <= 0) { //PAREDE DA ESQUERDA
            cobra.get(0).LEFT_BOOL = false;
            cobra.get(0).setVelocidadeX(0);

        }
        if (cobra.get(0).getY() + cobra.get(0).getSize() >= this.getHeight() - cobra.get(0).getSize()) { //CHÃO
            cobra.get(0).DOWN_BOOL = false;
            cobra.get(0).setVelocidadeY(0);

        } else if (cobra.get(0).getY() <= 0) { // TETO
            cobra.get(0).UP_BOOL = false;
            cobra.get(0).setVelocidadeY(0);

        }
    }

    private void reinicia() {

        if (gameOver == true) {
            inicia();
            for (int i = 0; i < cobra.size(); i++) {
                cobra.get(i).zeraValores();
            }
            comida.gerarComida(0, this.getWidth(), 0, this.getHeight(), this.cobra);
            morreu = false;
            gameOver = false;
        } else {
            gameOver = true;
            reinicia();
        }
    }

    private void inicia() {
        velDificult = maxVelDificult;

        if (colisor == null) {
            colisor = new Colisor();
        }

        verificaGrid();
        
        if (cobra == null) {
            cobra = new ArrayList();
        }

        cobra.removeAll(cobra);
        for (int i = cobrasLenght; i > 0; i--) {
            cobra.add(new Cobra(0, 0));
        }

        cor = 255;

        if (comida == null) {
            comida = new Comida();
        }
    }

    private void keyMap() {
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UP");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DOWN");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LEFT");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RIGHT");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "RESTART");

        ActionMap actionMap = this.getActionMap();
        actionMap.put("UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cobra.get(0).moveUp();
            }
        });
        actionMap.put("DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cobra.get(0).moveDown();
            }
        });
        actionMap.put("LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cobra.get(0).moveLeft();
            }
        });
        actionMap.put("RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                cobra.get(0).moveRight();
            }
        });
        actionMap.put("RESTART", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                reinicia();
            }
        });
    }

}
