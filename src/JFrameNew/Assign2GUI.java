package JFrameNew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;


public class Assign2GUI extends JFrame implements ActionListener {

    private int xbegin = 0;
    private int ybegin = 0;
    private int xend = 0;
    private int yend = 0;
    public int buttonpressed;

    BufferedImage lastImage;

    public Assign2GUI(){
        setTitle("LuiPaoJer Paint");
        setSize(1000, 1000);
        setLocationRelativeTo(null);

        JButton btnLine = new JButton("Line");
        JPanel panel = new JPanel();

        panel.add(btnLine);
        this.getContentPane().add(panel);

        btnLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        setDefaultLookAndFeelDecorated(true);
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseMotionHandler);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

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

    @Override
    public void actionPerformed(ActionEvent e) {

        this.removeMouseMotionListener(null);

        int returnValue;

        Object src = e.getActionCommand();

        if(src.equals("Line")){
            buttonpressed = 1;
        }
    }

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

//        if (buttonpressed == 1) {
            g.drawLine(xbegin, ybegin, xend, yend);

//        }
    }

    public static void main(String[] args) {
        new Assign2GUI();
    }


}
