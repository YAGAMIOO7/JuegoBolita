/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package juego;

/**
 *
 * @author Julio C
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Juego extends JComponent{

    /**
     * @param args the command line arguments
     */
    
    //Declarando las constantes para dar al circulo
    private final static int ANCHO = 640;
    private final static int ALTO = 480;
    private final static int DIAMETRO = 20;
    private float x, y;
    private float vx, vy;
    private boolean arriba, abajo, izquierda, derecha;
    
    
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        JFrame jf = new JFrame("Demo2");
        
        jf.addWindowListener(new WindowAdapter(){
            //estamos sobreescribiendo el metodo de window closing
                public void WindowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        //para no redimensionar la ventana
        jf.setResizable(false);
    
        Juego demo2 = new Juego();
    
        jf.getContentPane().add(demo2);
    
        jf.pack();
        jf.setVisible(true);
        demo2.cicloPrincipalJuego();
            }
    
        public Juego()
        {
            setPreferredSize(new Dimension(ANCHO, ALTO));
            
            x=0;
            y=0;
            //keyAdapter es una clase abstracta q recibe eventos del teclado
            addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e){
                    actualiza(e.getKeyCode(), true);
                }
                //keyRelease
                @Override
                public void keyReleased(KeyEvent e){
                    
                    actualiza(e.getKeyCode(), false);
                }
                private void actualiza(int keyCode, boolean pressed){
                    switch(keyCode)
                    {
                        case KeyEvent.VK_W:
                            arriba = pressed;
                            break;
                        case KeyEvent.VK_S:
                            abajo = pressed;
                            break;
                        case KeyEvent.VK_A:
                            izquierda = pressed;
                            break;
                        case KeyEvent.VK_D:
                            derecha = pressed;
                            break;
                    }
                    }
                });
            setFocusable(true);
            }

        private float clamp(float valor, float min, float max)
        {
            if(valor > max)
                return max;
            if (valor < min)
                return min;
            return valor;
        }
        private void fisica(float dt)
        {
            vx = 0;
            vy = 0;
            
            if(arriba)
                vy = -300;
            if(abajo)
                vy = 300;
            if(izquierda)
                vx = -300;
            if(derecha)
                vx = 300;
            
            x = clamp(x + vx *dt, 0, ANCHO - DIAMETRO);
            y = clamp(y + vy *dt, 0, ALTO - DIAMETRO);
        }
        
    @Override
        public void paint(Graphics g)
        {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, ANCHO, ALTO);
            g.setColor(Color.BLUE);
            g.fillOval(Math.round(x), Math.round(y), DIAMETRO, DIAMETRO);
        }
        
        private void dibuja() throws Exception{
            SwingUtilities.invokeAndWait(new Runnable(){
                public void run(){
                    //Pinta la region especificada junto con todos los elementos que la componen inmediatamente
                    paintImmediately(0, 0, ANCHO, ALTO);
                }             
            });   
        }
        public void cicloPrincipalJuego() throws Exception{
            //Para determinar hasta cuando se va a detener
            long tiempoViejo = System.nanoTime();
            
            while(true){
                long tiempoNuevo = System.nanoTime();
                float dt = (tiempoNuevo - tiempoViejo)/ 1000000000f;
                tiempoViejo = tiempoNuevo;
                fisica(dt);
                dibuja();
                
                
            }
        }
}    

