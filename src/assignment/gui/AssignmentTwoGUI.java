package assignment.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains all the methods which constructs the frame of the gui and sets up
 * its functionality
 */
public class AssignmentTwoGUI extends JPanel implements ActionListener, MouseListener, MouseMotionListener{

    private JButton btnColour, btnLoad, btnSave, btnPlot, btnLine;
    private JButton btnRectangle, btnEllipse, btnPolygon, btnUndo;

    private Image drawnImg, undoCount;
    private final UndoStack<Image> undoStack = new UndoStack<>(90);
    Graphics2D gc;

    private int x1,y1,x2,y2, buttonPressed;
    int WIDTH = 1000;
    int HEIGHT = 500;
    Color penColour = Color.BLACK;
    Color fillColour = Color.WHITE;

    /**
     * The frame of the GUI is constructed, the buttons are added with actionlisteners,
     * and mouselistener is added
     */
    public AssignmentTwoGUI(){

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

        addMouseListener(this);
        Paint.setVisible(true);
        Paint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Ensures that rectangles and ellipse are drawn correctly by making sure that the x and y coordinates
     * are not inverted
     */
    public void checkCoordinates()
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

    /** The paint component is one of the swing components
     * @param g the graphics which will be used to draw the images
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (drawnImg == null)
        {
            int w = this.getWidth();
            int h = this.getHeight();
            drawnImg = (this.createImage(w, h));
            gc = (Graphics2D) drawnImg.getGraphics();
            gc.setColor(Color.BLACK);
        }
        g2.drawImage(drawnImg, 0, 0, null);
        checkCoordinates();
    }


    /**
     * This method draws the different type of shapes, which is plot, line,
     * rectangle and ellipse based on the button that is pressed
     *
     */
    public void drawShape(){
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

        if (buttonPressed == 1)
        {
            checkCoordinates();
            gc.drawLine(x1, y1, x1, y1);
            repaint();
        }

        else if (buttonPressed == 2){
            gc.drawLine(x1 ,y1, x2,y2);
            repaint();
        }

        else if (buttonPressed == 3){
            checkCoordinates();
            gc.drawRect(x1,y1,w,h);
            repaint();
        }

        else if (buttonPressed == 4){
            checkCoordinates();
            gc.drawOval(x1,y1,w,h);
            repaint();
        }
//        else if (buttonPressed == 5){
//            gc.drawPolygon(new Polygon(xPoints, yPoints, 3));
//            repaint();
//        }
    }

    /** Sets the latest shape that has been drawn to an image
     * @param img the latest drawn shape is set to this variable
     */
    private void setImage(Image img){
        gc = (Graphics2D) img.getGraphics();
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gc.setPaint(Color.black);
        this.drawnImg = img;
        repaint();
    }

    /**
     * As long as the size of the stack is greater than 0, the latest drawn image is removed
     * when this function is called
     */
    public void undo(){
        if (undoStack.size() > 0){
            undoCount = undoStack.pop();
            setImage(undoCount);
        }
    }

    /** Takes a copy of the recently drawn shape
     * @param img the variable where the most recent drawn shape is copied into
     * @return the copy of the recently drawn image
     */
    private BufferedImage copyImage(Image img){
        BufferedImage copyofImage = new BufferedImage(getSize().width,
                getSize().height, BufferedImage.TYPE_INT_RGB);
        Graphics g = copyofImage.createGraphics();
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

        return copyofImage;
    }

    /**Adds the undone image to the stack
     * @param img the latest drawn image is stored to this variable
     */
    private void saveStack(Image img){
        undoStack.push(copyImage(img));
    }

    /** This function will choose and load the vec files and will filter out all
     * the files that are not in .vec format
     *
     */
    public void choosingFile() {
        JFileChooser chosenFile = new JFileChooser();
        FileNameExtensionFilter vecFilter = new FileNameExtensionFilter(
                ".vec", "vec");
        chosenFile.setFileFilter(vecFilter);

        int file = chosenFile.showOpenDialog(null);

        if (file == JFileChooser.APPROVE_OPTION) {
            File input = new File(String.valueOf(chosenFile.getSelectedFile()));
            try {
                Scanner scan = new Scanner(input);
                while (scan.hasNext()) {
                    String[] line = scan.nextLine().split(" ");

                    String shapeDraw = line[0];
                    loadShapes(shapeDraw, line);

                }
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** The rectangles from the files are scanned then drawn with the pen and
     * fill colour desired
     * @param line an array which contains the coordinates of the rectangles
     *             which are used to draw them
     * @param penColour the colour of the pen desired
     * @param fillColour the colour of the fill desired
     */
    public void drawRectangles(String[] line, Color penColour, Color fillColour){
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

        gc.setColor(fillColour);
        gc.fillRect(vx, vy, vw, vh);
        gc.setColor(penColour);
        gc.drawRect(vx, vy, vw, vh);
        repaint();
    }

    /** Reads the colours that are in hexadecimal format and converts them
     * to RGB colour model
     * @param colorStr the colour in hexadecimal format
     * @return the color that has been read
     */
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    /** Scans the contents of the files, draws the shapes and sets the pen and
     * fill colours based on the hexadecimal format that has been read
     * @param shapeDraw the string which specifies which shape to draw
     * @param line an array which contains all the coordinates required to draw each shape
     */
    public void loadShapes(String shapeDraw, String[] line){
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
            drawRectangles(line, penColour, fillColour);
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

            gc.setColor(fillColour);
            gc.fillOval(vx,vy,vw,vh);
            gc.setColor(penColour);
            gc.drawOval(vx,vy,vw,vh);
            repaint();
        }

        if(shapeDraw.equals("POLYGON")){
            ArrayList<String> xPoints = new ArrayList<>();
            ArrayList<String> yPoints = new ArrayList<>();

            for (int i =1 ; i < line.length; i = i+2)
            {
                xPoints.add(line[i]);
            }

            for (int i =2; i < line.length; i = i+ 2)
            {
                yPoints.add(line[i]);
            }

            double[]dxPoints = new double[xPoints.size()];
            for (int i = 0; i < xPoints.size(); i++){
                dxPoints[i] = Double.parseDouble(xPoints.get(i)) * WIDTH;
            }

            double[]dyPoints = new double[yPoints.size()];
            for (int i = 0; i < yPoints.size(); i++){
                dyPoints[i] = Double.parseDouble(yPoints.get(i)) * HEIGHT;
            }

            GeneralPath polygon =
                    new GeneralPath(GeneralPath.WIND_EVEN_ODD, dxPoints.length);

            polygon.moveTo (dxPoints[0], dyPoints[0]);
            for (int i = 1; i < dxPoints.length; i++){
                polygon.lineTo(dxPoints[i],dyPoints[i]);
            }

            polygon.closePath();
            gc.setColor(fillColour);
            gc.fill(polygon);
            gc.setColor(penColour);
            gc.draw(polygon);
            repaint();
        }

        if (shapeDraw.equals("PEN")){
            String penColour = (line[1]);
            Color c = hex2Rgb(penColour);
            gc.setColor(c);
            this.penColour = c;
        }

        if (shapeDraw.equals("FILL")){
            String penColour = (line[1]);
            if (line[1].equals("OFF") != true) {
                Color c = hex2Rgb(penColour);
                gc.setColor(c);
                fillColour = c;
            }
        }
    }

    /** The Actionlistener implemented method
     * @param e an ActionEvent which detects which button is pressed
     *          and performs their respective functionality
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        super.removeMouseMotionListener(this);

        Object src = e.getActionCommand();

        if (src.equals("Plot")) {
            buttonPressed = 1;
        }

        if (src.equals("Line")) {
            buttonPressed = 2;
        }

        if (src.equals("Rectangle")) {
            buttonPressed = 3;
        }

        if (src.equals("Ellipse")) {
            buttonPressed = 4;
        }
        if (src.equals("Polygon")){
            buttonPressed = 5;
        }

        if (e.getActionCommand().equals("Colours"))
        {
            Color bgColour = JColorChooser.showDialog(this, "Choose Colour", getBackground());
            if (bgColour != null)
                gc.setColor(bgColour);
        }

        if (src.equals("Load")) {
            choosingFile();
        }

        if (src.equals("Undo")){
            undo();
        }

    }

    /** Main method where the gui is called
     * @param args contains the supplied command-line arguments as an array of string
     */
    public static void main (String[] args){
        new AssignmentTwoGUI();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /** Gets the required values when the mouse is pressed
     * @param e is used to get the x1 and y1 coordinates for the shapes
     */
    @Override
    public void mousePressed(MouseEvent e) {
        saveStack(drawnImg);
        x1 = e.getX();
        y1 = e.getY();
    }

    /** Gets the required values when the mouse is released and draws the shapes
     * @param e is used to get the x2 and y2 coordinates for the shapes
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        drawShape();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /** Implemented method of mousemotionlistener and is used to help get
     * the coordinates for the shapes when the mouse is dragged
     * @param e gets the x2 and y2 values
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
