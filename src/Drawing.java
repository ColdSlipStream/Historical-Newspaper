import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.xml.internal.ws.util.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Brandon on 11/15/14.
 */
public class Drawing extends JFrame {

    private final int rectangleWidth = 340;
    private final int rectangleHeight = 200;
    private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
    private Font headerFont = new Font("Arial", 0, 15);
    private String testText[] = {"line 1", "line 2", "line 3", "line 4", "line 5", "line 6"};

    private final String[] TITLES = {"Inventions", "Founded", "Births", "Deaths", "Wars", "Presidents"};

    private static ArrayList<String> wikiData = new ArrayList<String>(); //Inventions, founded, births, and deaths

    private static int titleYear = 0;

    public Drawing() {
        initComponents();
        addWindowListener(new FrameListener());
    }

    public static void main(String args[]) {
        titleYear = Integer.parseInt(JOptionPane.showInputDialog(null, "What year were you born in?", "Enter in year", JOptionPane.QUESTION_MESSAGE));
        Drawing gui = new Drawing();
        gui.setVisible(true);

        DeriveData test = new DeriveData(titleYear);
        wikiData = test.getOverallData();
    }

    private static JPanel jPanel1;

    boolean ranDraw = false;
    public void initComponents() {
        setResizable(true);
        setMinimumSize(new Dimension(700, 700));
        this.setBackground(Color.decode("#ffefaf"));
        setTitle("The " +titleYear +" Newspaper");
        jPanel1 = new JPanel() {
            protected void paintComponent(Graphics graphic) {
                Graphics2D g = (Graphics2D) graphic;

                rectangles.clear();
                drawPaper(g);
                //TODO: Add sorting for data
                for (int i = 0; i < rectangles.size(); i++) {
                    switch (i) {
                        case 0:
                            drawText(Sorting.getInventions(wikiData), rectangles.get(i), g);
                            break;

                        case 1:
                            drawText(Sorting.getFounded(wikiData), rectangles.get(i), g);
                            break;

                        case 2:
                            drawText(Sorting.getBirths(wikiData), rectangles.get(i), g);
                            break;

                        case 3:
                            drawText(Sorting.getDeaths(wikiData), rectangles.get(i), g);
                            break;

                        case 4:
                            DeriveData holder = new DeriveData(titleYear);
                            drawText(holder.getCleanedWarData(), rectangles.get(i), g);
                            break;

                        case 5:
                            DeriveData hold = new DeriveData(titleYear);
                            drawText(hold.getPresidentData(), rectangles.get(i), g);
                            break;
                    }
                }

                g.setFont(new Font("Jokerman", 0, 20));
                g.drawString("The " + titleYear +" Newspaper", (int)this.getSize().getWidth()/2-100, 20);
            }
        };

        jPanel1.setBackground(Color.decode("#ffefaf"));

        add(jPanel1);
        pack();
    }

    public void drawPaper(Graphics2D g) {

        int numberOfTopics = 6;
        int x = 0;
        int y = 40;
        //Width will be 100
        //Length will be 100
        boolean increaseY = false;
        for(int i = 0; i < numberOfTopics; i++) {
            drawBox(TITLES[i],x, y, g);
            if (increaseY) {
                x=0;
                y+=rectangleHeight;
            } else {
                x+=rectangleWidth;
            }
            increaseY = !increaseY;
        }
    }

    public void drawText(ArrayList<String> text, Rectangle rectangle, Graphics2D g) {
        int numberOfBullets = 7;
        int stringX = rectangle.x +5;
        int stringY = rectangle.y + headerFont.getSize() + (rectangle.height-headerFont.getSize())/numberOfBullets;
        g.setFont(new Font("Arial", 0, 10));
        g.setColor(Color.BLACK);
        for (int i = 0; i < numberOfBullets; i++) {
            //TODO: fix this bug, it'll go into the next box LOL
            if (i+1 < text.size()) {
                text.set(i,Sorting.cleanText(text.get(i)));
                if (stringTooLong(text.get(i), rectangle, g)) {
                    String[] holder = text.get(i).split(" ");
                    String finalProduct = "";
                    for (String s: holder) {
                        if (g.getFontMetrics().getStringBounds(finalProduct + " " + s, g).getWidth() > rectangle.getWidth()-35 && !finalProduct.contains("\n")) {
                            finalProduct+= "\n " +s;
                        } else {
                            if (g.getFontMetrics().getStringBounds(finalProduct + " " + s, g).getWidth() > (rectangle.getWidth()-35)*2 && (finalProduct.length() - finalProduct.replaceAll("\n", "").length()) < 2) {
                                finalProduct+= "\n " +s;
                            } else if(g.getFontMetrics().getStringBounds(finalProduct + " " + s, g).getWidth() > (rectangle.getWidth()-35)*3 && (finalProduct.length() - finalProduct.replaceAll("\n", "").length()) < 3) {
                                finalProduct+= "\n " +s;
                            } else {
                                finalProduct += " " +s;
                            }
                        }
                    }
                    if (finalProduct.contains("\n")) {
                        for (String line : finalProduct.split("\n"))
                            if (rectangle.contains(stringX, stringY + g.getFontMetrics().getHeight())) {
                                g.drawString(line, stringX, stringY += g.getFontMetrics().getHeight());
                            }
                    } else {
                        if (rectangle.contains(stringX, stringY)) {
                            g.drawString(finalProduct, stringX, stringY);
                        }
                    }
                } else {
                    if (rectangle.contains(stringX, stringY)) {
                        g.drawString(text.get(i), stringX, stringY);
                    }
                }
                stringY+= (rectangle.height-headerFont.getSize())/numberOfBullets;
            }
        }

    }

    private boolean stringTooLong(String text, Rectangle rectangle, Graphics2D g) {
        return g.getFontMetrics().getStringBounds(text, g).getWidth() > rectangle.getWidth();
    }

    public void drawBox(String title, int x, int y, Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", 0, 15));
        Rectangle rectangle = new Rectangle(x, y, rectangleWidth, rectangleHeight);
        g.drawString(title, (int)rectangle.getCenterX()-10, y+g.getFontMetrics().getHeight());

        //remove
        g.setColor(this.getBackground());
        g.drawRect(rectangle.x, rectangle.y, rectangleWidth, rectangleHeight);

        rectangles.add(rectangle);
    }

    private class FrameListener extends WindowAdapter {
        public void windowIconified(WindowEvent e) {
        }

        public void windowDeiconified(WindowEvent e) {
        }

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
}
