package JFrameNew;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;



public class Assign2GUI extends JFrame{

    private int xbegin = 0;
    private int ybegin = 0;
    private int xend = 0;
    private int yend = 0;

    BufferedImage lastImage;

    public Assign2GUI(){
        setTitle("Line Drawer!!!!!");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseMotionHandler);
        setVisible(true);
    }


    public MouseListener mouseHandler = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            xbegin = xend = e.getX();
            ybegin = yend = e.getY();
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            xend = e.getX();
            yend = e.getY();
            repaint();
        }

    };

    public MouseMotionListener mouseMotionHandler = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            xend = e.getX();
            yend = e.getY();
            repaint();
        }

    };

    public void paint(Graphics g){
        super.paint(g);
        g.drawLine(xbegin, ybegin, xend, yend);
    }

    public static void main(String[] args) {
        new Assign2GUI();
    }
}
