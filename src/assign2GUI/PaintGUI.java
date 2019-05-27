package assign2GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


public class PaintGUI extends JFrame implements Runnable, ActionListener, MouseListener, MouseMotionListener {


    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private JPanel pnlOne;
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

    private JFileChooser VECFile;
    private String file;
    private File Chosenfile;
    private JTextField Paneltext;
    private JTextArea information;

    private int x1, x2, y1, y2;
    private static final int stroke = 0;
    private Color c;
    private int buttonpressed;

    private JTextArea areDisplay;

    public PaintGUI(String title) {

        super(title);
    }


    public void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pnlOne = createPanel(Color.WHITE);
        pnlTwo = createPanel(Color.LIGHT_GRAY);
        //pnlThree = createPanel(Color.LIGHT_GRAY);
        pnlBtn = createPanel(Color.LIGHT_GRAY);
        //pnlFive = createPanel(Color.LIGHT_GRAY);

        btnLoad = createButton("Load VEC file");
        btnSave = createButton("Save VEC file");
        btnPlot = createButton("Plot");
        btnLine = createButton("Line");
        btnLine.addActionListener(this);
        btnRectangle = createButton("Rectangle");
        btnEllipse = createButton("Ellipse");
        btnPolygon = createButton("Polygon");
        btnUndo = createButton("Undo");

        //areDisplay = CreateTextArea();
        //pnlOne.setLayout(new BorderLayout());
        //pnlOne.add(areDisplay, BorderLayout.CENTER);

        getContentPane().add(pnlOne, BorderLayout.CENTER);
        getContentPane().add(pnlTwo, BorderLayout.EAST);
        //getContentPane().add(pnlThree, BorderLayout.NORTH);
        getContentPane().add(pnlBtn, BorderLayout.SOUTH);
        //getContentPane().add(pnlFive, BorderLayout.WEST);
        repaint();
        this.setVisible(true);

        layoutButtonPanel();
        addMouseListener(this);
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

    private JTextArea CreateTextArea() {
        JTextArea display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        display.setFont(new Font("Arial", Font.CENTER_BASELINE, 10));

        return display;
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

    public void paintrect() {
        Graphics2D g = (Graphics2D) getGraphics();
        g.drawRect(60, 60, 150, 40);
        g.setColor(Color.BLUE);
        g.fillRect(60, 60, 150, 40);
    }


    BufferedImage grid;
    Graphics2D gc;


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (grid == null) {
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage) (this.createImage(w, h));
            gc = grid.createGraphics();
            gc.setColor(Color.BLUE);
        }

        g2.drawImage(grid, null, 0, 0);
        check();
    }

    public void draw(){
    if (buttonpressed == 2){
        if(stroke == 0)
            gc.setStroke(new BasicStroke(3));
        if (stroke == 1)
            gc.setStroke(new BasicStroke(6));
        gc.drawLine(x1,y1,x2,y2);
        repaint();
    }

    else if (buttonpressed == 1){
        paintrect();
    }
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



    @Override
    public void actionPerformed(ActionEvent e) {

        super.removeMouseListener(this);

        Object src = e.getSource();
        int returnValue;

        if (src == btnLoad) {
            VECFile = new JFileChooser();
            returnValue = VECFile.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Chosenfile = VECFile.getSelectedFile();
                file = Chosenfile.getPath();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));

                    String line;

                    while ((line = br.readLine()) != null)

                        areDisplay.setText(line);

                        line = br.readLine();
                        if (line == "RECTANGLE")
                        {
                            paintrect();
                        }




                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }


        }

        else if (src == btnRectangle) {
            //JButton btn = ((JButton)src);
            //areDisplay.setText(btn.getText().trim());
            //paintrect();
            buttonpressed = 1;
        }

        else if (src == btnLine){
            buttonpressed = 2;
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
    public void mouseDragged(MouseEvent re) {
        c = gc.getColor();
        gc.setColor(Color.WHITE);
        gc.drawRect(re.getX(), re.getY(), 50, 50);
        gc.setColor(c);
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
