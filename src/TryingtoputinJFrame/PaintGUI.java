package TryingtoputinJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class PaintGUI extends JFrame implements ActionListener, Runnable, MouseListener, MouseMotionListener {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private JPanel pnlDraw;
    private JPanel pnlTwo;
    private JPanel pnlThree;
    private JPanel pnlBtn;
    private JPanel pnlFive;

    private JButton btnLoad;
    private JButton btnSave;
    private JButton btnPlot;
    private JButton btnLine;
    private JButton btnRectangle;
    private JButton btnEllipse;
    private JButton btnPolygon;
    private JButton btnUndo;

    private int x1, x2, y1, y2;
    private static final int stroke = 0;
    private Color c;
    private int buttonpressed;

    public PaintGUI(String title) {

        super(title);
    }


    public void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pnlDraw = createPanel(Color.WHITE);
        pnlTwo = createPanel(Color.LIGHT_GRAY);
        pnlBtn = createPanel(Color.LIGHT_GRAY);

        btnLoad = createButton("Load VEC file");
        btnLoad.addActionListener(this);
        btnSave = createButton("Save VEC file");
        btnSave.addActionListener(this);
        btnPlot = createButton("Plot");
        btnPlot.addActionListener(this);
        btnLine = createButton("Line");
        btnLine.addActionListener(this);
        btnRectangle = createButton("Rectangle");
        btnRectangle.addActionListener(this);
        btnEllipse = createButton("Ellipse");
        btnEllipse.addActionListener(this);
        btnPolygon = createButton("Polygon");
        btnPolygon.addActionListener(this);
        btnUndo = createButton("Undo");
        btnUndo.addActionListener(this);

        getContentPane().add(pnlDraw, BorderLayout.CENTER);
        getContentPane().add(pnlTwo, BorderLayout.EAST);
        getContentPane().add(pnlBtn, BorderLayout.SOUTH);
        repaint();
        this.setVisible(true);
        addMouseListener(this);
        layoutButtonPanel();
    }

    private JPanel createPanel(Color c) {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(c);
        return newPanel;
    }

    private JButton createButton(String str) {
        JButton newButton = new JButton();
        newButton.setText(str);
        newButton.addActionListener(this);
        return newButton;
    }

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlBtn.setLayout(layout);

        //adding components to grid
        GridBagConstraints constraints = new GridBagConstraints();

        addToPanel(pnlBtn, btnLoad, constraints, 0, 0, 2, 1);
        addToPanel(pnlBtn, btnSave, constraints, 2, 0, 2, 1);
        addToPanel(pnlBtn, btnPlot, constraints, 4, 0, 2, 1);
        addToPanel(pnlBtn, btnLine, constraints, 6, 0, 2, 1);
        addToPanel(pnlBtn, btnRectangle, constraints, 8, 0, 2, 1);
        addToPanel(pnlBtn, btnEllipse, constraints, 10, 0, 2, 1);
        addToPanel(pnlBtn, btnPolygon, constraints, 12, 0, 2, 1);
        addToPanel(pnlBtn, btnUndo, constraints, 14, 0, 2, 1);
    }

    private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }

    public void check()
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

    BufferedImage grid;
    Graphics2D gc;

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (grid == null)
        {
            int w = this.getWidth();                        // width
            int h = this.getHeight();                       // height
            grid = (BufferedImage) (this.createImage(w, h));
            gc = grid.createGraphics();
            gc.setColor(Color.BLACK);
        }
        g2.drawImage(grid, null, 0, 0);
        check();
    }

    public void draw(){
        Graphics2D g = (Graphics2D) getGraphics();
        int w = x2 - x1;
        if (w < 0)
            w = w * (-1);

        int h = y2 - y1;
        if (h < 0)
            h = h * (-1);

        if (buttonpressed == 1)
        {
            check();
            gc.drawLine(x1, y1, x1, y1);
            pnlDraw.repaint();
        }

        else if (buttonpressed == 2){
            check();
            gc.drawLine(x1 ,y1, x2,y2);
            pnlDraw.repaint();
        }

        else if (buttonpressed == 3){
            //check();
           // gc.drawRect(x1,y1,w,h);
            gc.drawRect(10,10,50,60);
            repaint();
        }

        else if (buttonpressed == 4){
            check();
            gc.drawOval(x1,y1,w,h);
            repaint();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.removeMouseMotionListener(this);

        Object src = e.getActionCommand();

        if (src.equals("Plot")){
            buttonpressed = 1;
        }

        if (src.equals("Line")){
            buttonpressed = 2;
        }

        if (src.equals("Rectangle")){
            buttonpressed = 3;
        }

        if (src.equals("Ellipse")){
            buttonpressed = 4;
        }
    }

    @Override
    public void run(){
        createGUI();
    }

    public static void main(String[] args){

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new PaintGUI("Paint"));


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        draw();
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
}

