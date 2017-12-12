/* 
 *
 * SushiClient.java
 * Justin Christopher Abarquez
 * Mariko Ariane Vecta Sampaga
 *
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.List;
import javax.imageio.*;
import javax.swing.JOptionPane;

public class SushiClient extends Frame implements ActionListener {

    //private static final long serialVersionUID = 1L;
    public static final int Fram_width = 805;
    public static final int Fram_length = 602;
    public static boolean printable = true;

    Image screenImage = null;

    Sushi homeTank = new Sushi(145, 277, true, Direction.INITIAL, this, 1);
    Sushi homeTank2 = new Sushi(600, 277, true, Direction.INITIAL, this, 2);
    Boolean Player2 = false;

    boolean player1Seaweed = false;
    boolean player1Avocado = false;

    boolean player2Seaweed = false;
    boolean player2Avocado = false;

    Money life = new Money();
    Boolean win = false, lose = false;

    List<Sushi> tanks = new ArrayList<Sushi>();
    List<Wall> unbreakableWall = new ArrayList<Wall>();
    List<FatTable> fatTable = new ArrayList<FatTable>();
    List<WoodenTable> woodenTable = new ArrayList<WoodenTable>();
    List<Ricecooker> ricecooker = new ArrayList<Ricecooker>();
    List<Avocado> avocado = new ArrayList<Avocado>();
    List<Seaweed> seaweed = new ArrayList<Seaweed>();

    BufferedImage bg;

    public SushiClient() {

        try {

            bg = ImageIO.read(CompletedOrder.class.getResource("Images/Background.jpg"));

        } catch (IOException ex) {

        }

        setSize(Fram_width, Fram_length);
        setResizable(false);
        setVisible(true);

        this.setLocation(600, 200);
        this.setTitle("SUSHI HERO");

        //display resources
        for (int i = 0; i < 20; i++) {
            if (i < 1) {

                unbreakableWall.add(new Wall(10 + 20 * i, 130, this));
                unbreakableWall.add(new Wall(413 + 20 * i, 130, this));

                fatTable.add(new FatTable(10 + 20 * i, 530, this));
                fatTable.add(new FatTable(413 + 20 * i, 530, this));

                woodenTable.add(new WoodenTable(375 + 20 * i, 165, this));
                woodenTable.add(new WoodenTable(1 + 20 * i, 165, this));

                ricecooker.add(new Ricecooker(275 + 20 * i, 475, this));

                avocado.add(new Avocado(635 + 20 * i, 125, this));
                avocado.add(new Avocado(625 + 20 * i, 135, this));
                avocado.add(new Avocado(650 + 20 * i, 135, this));
                avocado.add(new Avocado(640 + 20 * i, 145, this));
                avocado.add(new Avocado(640 + 20 * i, 200, this));

                seaweed.add(new Seaweed(440 + 20 * i, 495, this));
                seaweed.add(new Seaweed(420 + 20 * i, 505, this));
                seaweed.add(new Seaweed(460 + 20 * i, 505, this));

            }
        }

        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {

                System.exit(0);

            }

        });

        this.addKeyListener(new KeyMonitor());
        new Thread(new PaintThread()).start();

    }

    public static void main(String[] args) {

        SushiClient Player2add = new SushiClient();
        Player2add.Player2 = true;

    }

    public void update(Graphics g) {

        screenImage = this.createImage(Fram_width, Fram_length);
        Graphics gps = screenImage.getGraphics();
        Color c = gps.getColor();
        displayCurrentIngredient();

        gps.setColor(Color.MAGENTA);
        gps.fillRect(0, 0, Fram_width, Fram_length);
        gps.setColor(c);

        framPaint(gps);
        g.drawImage(screenImage, 0, 0, null);

    }

    public void framPaint(Graphics g) {

        g.drawImage(bg, 0, 0, Fram_width, Fram_length, null);

        Color c = g.getColor();
        g.setColor(Color.RED);
        Font f1 = g.getFont();
        g.setFont(f1);

        //displays Score
        g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 35));
        g.drawString("$" + homeTank.getLife(), 365, 88);

        //displays Current Order
        g.setColor(Color.BLACK);
        g.setFont(new Font("Eras Demi ITC", Font.BOLD, 18));
        g.drawString("Avocado Sushi"/*homeTank2.getLife()*/, 625, 63);

        //displays Current Ingredient
        g.setFont(new Font("Eras Demi ITC", Font.ITALIC, 18));
        g.drawString(displayCurrentIngredient(), 635, 95);

        //display player avatars
        homeTank.draw(g);
        if (Player2) {

            homeTank2.draw(g);

        }

        //calls each resource collision method for both players and draws them
        for (int i = 0; i < unbreakableWall.size(); i++) {

            Wall w = unbreakableWall.get(i);
            homeTank.collideWithWall(w);

            if (Player2) {

                homeTank2.collideWithWall(w);

            }

            w.draw(g);

        }

        for (int i = 0; i < fatTable.size(); i++) {

            FatTable ft = fatTable.get(i);
            homeTank.collideWithFatTable(ft);

            if (Player2) {

                homeTank2.collideWithFatTable(ft);

            }

            ft.draw(g);

        }

        for (int i = 0; i < woodenTable.size(); i++) {

            WoodenTable wt = woodenTable.get(i);
            homeTank.collideWithWoodenTable(wt);

            if (Player2) {

                homeTank2.collideWithWoodenTable(wt);

            }

            if (homeTank2.collideWithWoodenTable(wt)) {

                player2Seaweed = false;
                player2Avocado = false;
                
                player1Seaweed = true;
                player1Avocado = true;

            }

            wt.draw(g);

        }

        for (int i = 0; i < ricecooker.size(); i++) {

            Ricecooker r = ricecooker.get(i);
            homeTank.collideWithRicecooker(r);

            if (Player2) {

                homeTank2.collideWithRicecooker(r);

            }

            r.draw(g);

        }

        for (int i = 0; i < avocado.size(); i++) {

            Avocado a = avocado.get(i);
            homeTank.collideWithAvocado(a);

            if (Player2) {

                homeTank2.collideWithAvocado(a);

            }

            if (homeTank2.collideWithAvocado(a)) {

                player2Avocado = true;

            }

            a.draw(g);

        }

        for (int i = 0; i < seaweed.size(); i++) {

            Seaweed s = seaweed.get(i);
            homeTank.collideWithSeaweed(s);

            if (Player2) {

                homeTank2.collideWithSeaweed(s);

            }

            if (homeTank2.collideWithSeaweed(s)) {

                player2Seaweed = true;

            }

            s.draw(g);

        }

        //player collisions with each other
        homeTank.collideWithTanks(homeTank2);
        if (Player2) {

            homeTank2.collideWithTanks(homeTank);

        }
    }

    public String displayCurrentIngredient() {

        String i = "none";

        while (player1Avocado == true) {

            i = "Avocado";

        } 
        
        return i;

    }

    public void actionPerformed(ActionEvent e) {

    }

    private class PaintThread implements Runnable {

        public void run() {

            while (printable) {

                repaint();

                try {

                    Thread.sleep(50);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {

            homeTank.keyReleased(e);
            homeTank2.keyReleased(e);

        }

        public void keyPressed(KeyEvent e) {

            homeTank.keyPressed(e);
            homeTank2.keyPressed(e);

        }
    }
}
