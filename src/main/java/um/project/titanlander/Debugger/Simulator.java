package um.project.titanlander.Debugger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Simulator {

    public static void main(String[] args) {
        new Simulator();
    }

    //---
    private LandingModule landingModule = new LandingModule(new Vector2(0, 1000), new Vector2(0, -4), LandingModule.ControllerMode.OPEN);
    private UI ui = new UI();

    public Simulator() {
        update();
    }

    public void update() {
        while (true) {
            landingModule.updateController();
            landingModule.updateVelocity();
            landingModule.updatePosition();
            System.out.println(landingModule);
            ui.repaint();
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class UI extends JPanel {

        private JFrame frame = new JFrame();

        public UI() {
            this.frame.add(this);
            this.frame.setSize(1280, 720);
            this.frame.setVisible(true);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            this.frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        System.out.println("thrust");
                        landingModule.upThruster.burn(1);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                        System.out.println("thrust");
                        landingModule.rightThruster.burn(1);
                    }
                    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        System.out.println("thrust");
                        landingModule.leftThruster.burn(1);
                    }
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

            g.setColor(Color.GREEN);
            g.drawRect(0, 0, 1280, 720);

            {
                g.setColor(Color.BLUE);
                g.drawLine(0, 1280, frame.getWidth(), 1280);
            }

            {
                Vector2 screen = toScreenCoordinates(landingModule.getPosition());
                g.setColor(Color.RED);
                g.drawRect((int) (screen.getX()-(landingModule.getWidth()/2D)), (int) (screen.getY()-(landingModule.getHeight()/2D)), (int) landingModule.getWidth(), (int) landingModule.getHeight());
            }

        }

        public Vector2 toScreenCoordinates(Vector2 vec) {
            return new Vector2(vec.getX() + 640.0, 720 - vec.getY());
        }

    }

}