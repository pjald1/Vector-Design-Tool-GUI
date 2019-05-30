package JPanelNew;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.io.*;
import java.util.Scanner;

public class assign2GUI extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener{

    final JPanel NorthPanel = new JPanel();


    private JButton btnColour;
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
    int WIDTH = 1000;
    int HEIGHT = 500;

    public assign2GUI(){

        JFrame Paint = new JFrame("Paint");
        Paint.setSize(WIDTH,HEIGHT);
        Paint.setBackground(Color.WHITE);
        Paint.getContentPane().add(this);

        btnColour = new JButton("Colours");
        btnColour.addActionListener(this);
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
        this.add(btnColour);
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

        //int[] xPoint = {x1, x2};
        //int[] yPoint = {y1, y2};

//        if (mousePressed();) {
//            xPoint = new int[] { x1 * width, (x1 + 1) * width, (int) ((0.5 + x1) * width) };
//            yPoint = new int[] { (y1 + 1) * height, (y1 + 1) * height, y1 * height };
//        } else if (heading.equals(DOWN)) {
//            xPoint = new int[] { x1 * width, (x1 + 1) * width, (int) ((0.5 + x1) * width) };
//            yPoint = new int[] { y1 * height, y1 * height, (y1 + 1) * height };
//        } else if (heading.equals(LEFT)) {
//            xPoint = new int[] { (x1 + 1) * width, (x1 + 1) * width, x1 * width };
//            yPoint = new int[] { y1 * height, (y1 + 1) * height, (int) ((0.5 + y1) * height) };
//        } else if (heading.equals(RIGHT)) {
//            xPoint = new int[] { x1 * width, x1 * width, (x1 + 1) * width };
//            yPoint = new int[] { y1 * height, (y1 + 1) * height, (int) ((0.5 + y1) * height) };
//        }


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
//        else if (buttonpressed == 5){
//            gc.drawPolygon(new Polygon(xPoint, yPoint, 5));
//            repaint();
//        }

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


    public void vecFileChoose() {

        JFileChooser chooseFile = new JFileChooser();
        FileNameExtensionFilter vecFilter = new FileNameExtensionFilter(
                ".vec", "vec");
        chooseFile.setFileFilter(vecFilter);

        int file = chooseFile.showOpenDialog(null);

        if (file == JFileChooser.APPROVE_OPTION) {
            File input = new File(String.valueOf(chooseFile.getSelectedFile()));
            try {
                Scanner scan = new Scanner(input);
                while (scan.hasNext()) {
                    String[] line = scan.nextLine().split(" ");

                    String shapeDraw = line[0];
                    drawTheShapes(shapeDraw, line);

                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void drawRectangles(String[] line){
        double dvx = Double.parseDouble((line[1])) * (WIDTH);
        int vx = (int)dvx;
        double dvy = Double.parseDouble((line[2])) * HEIGHT;
        int vy = (int)dvy;
        double dvx1 = Double.parseDouble((line[3])) * WIDTH;
        double dvy1 = Double.parseDouble((line[4])) * HEIGHT;

        double w = dvx1 - dvx;
        int vw = (int)w;
        if (vw < 0)
            vw = vw * (-1);

        double h = dvy1 - dvy;
        int vh = (int)h;
        if (vh < 0)
            vh = vh * (-1);

        gc.drawRect(vx, vy, vw, vh);

        repaint();
    }

    public void drawTheShapes(String shapeDraw, String[] line){

        if(shapeDraw.equals("PLOT")){
            double dvx = Double.parseDouble(line[1]) * WIDTH;
            int vx = (int)dvx;
            double dvy = Double.parseDouble(line[2])*HEIGHT;
            int vy = (int)dvy;

            gc.drawLine(vx,vy,vx,vy);
            repaint();
        }

        if(shapeDraw.equals("LINE")){
            double dvx = Double.parseDouble(line[1]) * WIDTH;
            int vx = (int)dvx;
            double dvy = Double.parseDouble(line[2])*HEIGHT;
            int vy = (int)dvy;
            double dvx1 = Double.parseDouble(line[3])*WIDTH;
            int vx1 = (int)dvx1;
            double dvy1 = Double.parseDouble(line[4])*HEIGHT;
            int vy1 = (int)dvy1;
            gc.drawLine(vx,vy,vx1,vy1);
            repaint();
        }


        if(shapeDraw.equals("RECTANGLE")){
//            if (shapeDraw.equals("FILL")){
//                double dvx = Double.parseDouble((line[1])) * (WIDTH);
//                int vx = (int)dvx;
//                double dvy = Double.parseDouble((line[2])) * HEIGHT;
//                int vy = (int)dvy;
//                double dvx1 = Double.parseDouble((line[3])) * WIDTH;
//                double dvy1 = Double.parseDouble((line[4])) * HEIGHT;
//
//                double w = dvx1 - dvx;
//                int vw = (int)w;
//                if (vw < 0)
//                    vw = vw * (-1);
//
//                double h = dvy1 - dvy;
//                int vh = (int)h;
//                if (vh < 0)
//                    vh = vh * (-1);
//
//            String fillColour = (line[1]);
//            if (fillColour.equals("#000000")){
//                gc.setColor(Color.BLACK);
//                gc.drawRect(vx,vy,vw,vh);
//                gc.fillRect(vx,vy,vw,vh);
//                repaint();
//            }
//
//            }
                drawRectangles(line);
        }

        if(shapeDraw.equals("ELLIPSE")){
            double dvx = Double.parseDouble(line[1])*WIDTH;
            int vx = (int)dvx;
            double dvy = Double.parseDouble(line[2])*HEIGHT;
            int vy = (int)dvy;
            double dvx1 = Double.parseDouble(line[3])*WIDTH;
            double dvy1 = Double.parseDouble(line[4])*HEIGHT;

            double w = dvx1 - dvx;
            int vw = (int)w;
            if (vw < 0)
                vw = vw * (-1);

            double h = dvy1 - dvy;
            int vh = (int)h;
            if (vh < 0)
                vh = vh * (-1);

            gc.drawOval(vx,vy,vw,vh);
            repaint();
        }
//
//        if(shapeDraw.equals("POLYGON")){
//
////            double dvx = Double.parseDouble(line[1])*WIDTH;
////            int vx = (int)dvx;
////            double dvy = Double.parseDouble(line[2])*HEIGHT;
////            int vy = (int)dvy;
//
//            //int lineLength = line.length;
//
//            for (int i = ; i < line.length; i = line[])
//            {
//                System.out.print("y: " + i);
//            }
//
////            String one = line[1];
////            String two = line[2];
////            String three = line[3];
////            String four = line[4];
////            String five = line[5];
////            String six = line[6];
////            String seven = line[7];
//
////            System.out.print(shapeDraw + " " + one + " " +two + " " + three + " " + four+ " " + five +" "+ six +"\n " );
//
////            System.out.print(" " + lineLength + "\n");
//
//
//        }

        if (shapeDraw.equals("PEN")){
            String penColour = (line[1]);
            if (penColour.equals("#000000")){
                gc.setColor(Color.BLACK);
            }
            if (penColour.equals("#FFFFFF")){
                gc.setColor(Color.WHITE);
            }
            if (penColour.equals("#0000FF")){
                gc.setColor(Color.BLUE);
            }
            if (penColour.equals("#808080")){
                gc.setColor(Color.GRAY);
            }
            if (penColour.equals("#FF0000")){
                gc.setColor(Color.RED);
            }
            if (penColour.equals("#008000")){
                gc.setColor(Color.GREEN);
            }
            if (penColour.equals("#FFFF00")){
                gc.setColor(Color.YELLOW);
            }
        }

//        if (shapeDraw.equals("FILL")){
//            String fillColour = (line[1]);
//            if (fillColour.equals("#000000")){
//
//            }
//        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.removeMouseMotionListener(this);

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
        if (src.equals("Polygon")){
            buttonpressed = 5;
        }

        if (e.getActionCommand().equals("Colours"))
        {
            Color bgColour = JColorChooser.showDialog(this, "Choose Colour", getBackground());
            if (bgColour != null)
                gc.setColor(bgColour);
        }

        if (src.equals("Load")) {
            vecFileChoose();
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
        x2 = e.getX();
        y2 = e.getY();
        repaint();

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
