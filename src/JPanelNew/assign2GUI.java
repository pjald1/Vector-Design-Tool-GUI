package JPanelNew;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class assign2GUI extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener{

    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnPlot;
    private JButton btnLine;
    private JButton btnRectangle;
    private JButton btnEllipse;
    private JButton btnPolygon;
    private JButton btnUndo;

    private JFileChooser vecfile;
    private String file;
    private File Chosenfile;
    private JTextField Paneltext;
    private JTextArea information;
    private Image drawnimg, undoCount;
    private final UndoStack<Image> undoStack = new UndoStack<>(90);
    Graphics2D g;
    Graphics2D gc;

    private JTextArea areDisplay;

    private int x1,y1,x2,y2,buttonpressed;

    public assign2GUI(){

        JFrame Paint = new JFrame("Paint");
        Paint.setSize(1000,500);
        Paint.setBackground(Color.WHITE);
        Paint.getContentPane().add(this);


        btnLoad = new JButton("Load");
        btnLoad.addActionListener(this);
        btnSave = new JButton("Save");
        btnSave.addActionListener(this);
        btnPlot = new JButton("Plot");
        btnPlot.addActionListener(this);
        btnLine = new JButton("Line");
        btnLine.addActionListener(this);
        btnRectangle = new JButton("Rectangle");
        btnRectangle.addActionListener(this);
        btnEllipse = new JButton("Ellipse");
        btnEllipse.addActionListener(this);
        btnPolygon = new JButton("Polygon");
        btnPolygon.addActionListener(this);
        btnUndo = new JButton("Undo");
        btnUndo.addActionListener(this);

        this.add(btnLoad);
        this.add(btnSave);
        this.add(btnPlot);
        this.add(btnLine);
        this.add(btnRectangle);
        this.add(btnEllipse);
        this.add(btnPolygon);
        this.add(btnUndo);

        areDisplay = CreateTextArea();

        addMouseListener(this);
        Paint.setVisible(true);
        Paint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JTextArea CreateTextArea() {
        JTextArea display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        display.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));

        return display;
    }


    public void checkImagecoordinates()
    {
        if (x1 > x2)
        {
            int z = 0;
            z = x1;
            x1 = x2;
            x2 = z;
        }
        if (y1 > y2)
        {
            int z = 0;
            z = y1;
            y1 = y2;
            y2 = z;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (drawnimg == null)
        {
            int w = this.getWidth();                        // width
            int h = this.getHeight();                       // height
            drawnimg = (this.createImage(w, h));
            gc = (Graphics2D) drawnimg.getGraphics();
            gc.setColor(Color.BLACK);
        }

        g2.drawImage(drawnimg, 0, 0, null);
        checkImagecoordinates();
    }


    public void drawImage(){
        Graphics2D g = (Graphics2D) getGraphics();
        int w = x2 - x1;
        if (w < 0)
            w = w * (-1);

        int h = y2 - y1;
        if (h < 0)
            h = h * (-1);

        if (buttonpressed == 1)
        {
            checkImagecoordinates();
            gc.drawLine(x1, y1, x1, y1);
            repaint();
        }

        else if (buttonpressed == 2){
            gc.drawLine(x1 ,y1, x2,y2);
            repaint();
        }

        else if (buttonpressed == 3){
            checkImagecoordinates();
            gc.drawRect(x1,y1,w,h);
            repaint();
        }

        else if (buttonpressed == 4){
            checkImagecoordinates();
            gc.drawOval(x1,y1,w,h);
            repaint();
        }

    }

    private void setImage(Image img){
        g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(Color.black);
        this.drawnimg = img;
        repaint();
    }

    public void undo(){
        if (undoStack.size() > 0){
            undoCount = undoStack.pop();
            setImage(undoCount);
        }
    }

    private BufferedImage copyImage(Image img){
        BufferedImage copyOfImage = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = copyOfImage.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        return copyOfImage;
    }

    private void savetoStack(Image img){
        undoStack.push(copyImage(img));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.removeMouseMotionListener(this);

        int returnValue;

        Object src = e.getActionCommand();

        if (src.equals("Plot")) {
            buttonpressed = 1;
        }

        if (src.equals("Line")) {
            buttonpressed = 2;
        }

        if (src.equals("Rectangle")) {
            buttonpressed = 3;
        }

        if (src.equals("Ellipse")) {
            buttonpressed = 4;
        }

        if (src.equals("Load")) {
            vecfile = new JFileChooser();
            returnValue = vecfile.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Chosenfile = vecfile.getSelectedFile();
                file = Chosenfile.getPath();

                Scanner sc = new Scanner(System.in);
                String a =sc.next();
                String b = sc.next();
                System.out.println("First Word:" + a);
                System.out.println("Second Words:" +b);
                sc.close();
            }
        }

        if (src.equals("Undo")){
            undo();
        }
    }



    public static void main (String[] args){
        new assign2GUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        savetoStack(drawnimg);
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        x2 = e.getX();
        y2 = e.getY();
        drawImage();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    if(e.getPreciseWheelRotation() < 0){
    }
    }
}
