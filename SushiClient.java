/* 
 *
 * Sushi Hero
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
import java.io.*;
import java.io.IOException;
import java.util.List;
import javax.imageio.*;
import sun.audio.*;
import javax.swing.*;

public class SushiClient extends Frame implements ActionListener {

    //private static final long serialVersionUID = 1L;
    public static final int Fram_width = 805;
    public static final int Fram_length = 602;
    public static boolean printable = true;

    Image screenImage = null;

    String currentIngredient = "";
    int score = 0;

    List<Sushi> tanks = new ArrayList<Sushi>();
    List<Wall> unbreakableWall = new ArrayList<Wall>();
    List<FatTable> fatTable = new ArrayList<FatTable>();
    List<Carpet> carpet = new ArrayList<Carpet>();
    List<DeliveringCarpet> deliveringCarpet = new ArrayList<DeliveringCarpet>();
    List<WoodenTable> woodenTable = new ArrayList<WoodenTable>();
    List<ChoppingBoard> choppingBoard = new ArrayList<ChoppingBoard>();
    List<Ricecooker> ricecooker = new ArrayList<Ricecooker>();
    List<Rice> rice = new ArrayList<Rice>();
    List<Seaweed> seaweed = new ArrayList<Seaweed>();
    List<Avocado> avocado = new ArrayList<Avocado>();
    List<Egg> egg = new ArrayList<Egg>();
    List<Tuna> tuna = new ArrayList<Tuna>();

    Sushi homeTank = new Sushi(145, 277, true, Direction.INITIAL, this, 1);
    Sushi homeTank2 = new Sushi(600, 277, true, Direction.INITIAL, this, 2);
    Boolean Player2 = false;

    boolean player1Rice = false;
    boolean player1Seaweed = false;
    boolean player1Avocado = false;
    boolean player1Egg = false;
    boolean player1Tuna = false;

    boolean player2Rice = false;
    boolean player2Seaweed = false;
    boolean player2Avocado = false;
    boolean player2Egg = false;
    boolean player2Tuna = false;

    boolean riceAttained = false;
    boolean seaweedAttained = false;
    boolean avocadoAttained = false;
    boolean eggAttained = false;
    boolean tunaAttained = false;

    boolean riceCooked = false;
    boolean seaweedChopped = false;
    boolean avocadoChopped = false;
    boolean eggChopped = false;
    boolean tunaChopped = false;

    boolean everythingOK = false;

    String[] orders = {"Avocado Roll", "Tuna-Avocado Roll", "Tamago (Egg) Sushi"};
    //int ordersIndex;

    //Money life = new Money();
    Boolean win = false, lose = false;

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

                choppingBoard.add(new ChoppingBoard(145 + 20 * i, 133, this));

                fatTable.add(new FatTable(10 + 20 * i, 530, this));
                fatTable.add(new FatTable(413 + 20 * i, 530, this));

                carpet.add(new Carpet(373 + 20 * i, 175, this));
                woodenTable.add(new WoodenTable(375 + 20 * i, 165, this));
                //deliveringCarpet.add(new DeliveringCarpet(10 + 20 * i, 175, this));
                woodenTable.add(new WoodenTable(1 + 20 * i, 165, this));
                deliveringCarpet.add(new DeliveringCarpet(10 + 20 * i, 175, this));

                ricecooker.add(new Ricecooker(275 + 20 * i, 475, this));

                rice.add(new Rice(700 + 20 * i, 485, this));

                seaweed.add(new Seaweed(540 + 20 * i, 495, this));
                seaweed.add(new Seaweed(520 + 20 * i, 505, this));
                seaweed.add(new Seaweed(560 + 20 * i, 505, this));

                avocado.add(new Avocado(635 + 20 * i, 118, this));
                avocado.add(new Avocado(625 + 20 * i, 130, this));
                avocado.add(new Avocado(650 + 20 * i, 130, this));
                avocado.add(new Avocado(640 + 20 * i, 140, this));
                //avocado.add(new Avocado(640 + 20 * i, 200, this));

                tuna.add(new Tuna(727 + 20 * i, 118, this));
                tuna.add(new Tuna(720 + 20 * i, 120, this));
                tuna.add(new Tuna(715 + 20 * i, 125, this));

                egg.add(new Egg(510 + 20 * i, 120, this));
                egg.add(new Egg(535 + 20 * i, 120, this));
                egg.add(new Egg(525 + 20 * i, 125, this));

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
        g.drawString("$" + score, 365, 88);

        //displays Current Order
        g.setColor(Color.BLACK);
        g.setFont(new Font("Eras Demi ITC", Font.BOLD, 18));
        g.drawString(orders[0], 625, 63);

        //displays Current Ingredient
        g.setFont(new Font("Eras Demi ITC", Font.ITALIC, 18));
        g.drawString(displayCurrentIngredient(currentIngredient), 635, 95);

        //displays status of ingredients
        g.setColor(Color.RED);
        g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 17));
        g.drawString(displayIngredientStatus(), 10, 127);
        
        //displays status of rice
        g.setColor(Color.BLACK);
        g.setFont(new Font("Eras Demi ITC", Font.PLAIN, 17));
        g.drawString(displayRiceStatus(), 277, 127);
        
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

        for (int i = 0; i < choppingBoard.size(); i++) {

            ChoppingBoard cb = choppingBoard.get(i);
            homeTank.collideWithChoppingBoard(cb);

            if (homeTank.collideWithChoppingBoard(cb)) {
                if (avocadoAttained && seaweedAttained) {

                    everythingOK = true;

                }
            }

            if (Player2) {

                homeTank2.collideWithChoppingBoard(cb);

            }

            cb.draw(g);

        }

        String ingredient = "";

        for (int i = 0; i < carpet.size(); i++) {

            Carpet ca = carpet.get(i);
            homeTank.collideWithCarpet(ca);

            if (Player2) {

                homeTank2.collideWithCarpet(ca);

            }

            if (homeTank2.collideWithCarpet(ca)) {

                if (player2Rice == true) {

                    /*player1Seaweed = false;
                    player1Avocado = false;
                    player1Egg = false;*/
                    player1Rice = true;
                    riceAttained = true;

                    //ingredient = "Rice";
                    //player2Seaweed = false;
                }

                if (player2Seaweed == true) {

                    /*player1Rice = false;
                    player1Avocado = false;
                    player1Egg = false;*/
                    player1Seaweed = true;
                    seaweedAttained = true;

                    //ingredient = "Seaweed";
                    //player2Seaweed = false;
                }

                if (player2Avocado == true) {

                    /*player1Rice = false;
                    player1Seaweed = false;
                    player1Egg = false;*/
                    player1Avocado = true;
                    avocadoAttained = true;

                    //ingredient = "Avocado";
                    //player2Avocado = false;
                }

                if (player2Egg == true) {

                    /*player1Rice = false;
                    player1Seaweed = false;
                    player1Avocado = false;*/
                    player1Egg = true;
                    eggAttained = true;

                    //ingredient = "Egg";
                }

                if (player2Tuna == true) {

                    player1Tuna = true;
                    tunaAttained = true;

                    //ingredient = "Tuna";
                }

            }

            this.currentIngredient = ingredient;
            //g.drawString(displayCurrentIngredient(ingredient), 635, 95);
            ca.draw(g);

        }

        g.drawString(ingredient, 635, 95);

        for (int i = 0; i < deliveringCarpet.size(); i++) {

            DeliveringCarpet dc = deliveringCarpet.get(i);
            homeTank.collideWithDeliveringCarpet(dc);
            
            if (homeTank.collideWithDeliveringCarpet(dc)) {
                if (everythingOK && riceCooked) {

                    score += 12;

                }

            }

            if (Player2) {

                homeTank2.collideWithDeliveringCarpet(dc);

            }

            dc.draw(g);

        }

        for (int i = 0; i < woodenTable.size(); i++) {

            WoodenTable wt = woodenTable.get(i);
            homeTank.collideWithWoodenTable(wt);

            if (Player2) {

                homeTank2.collideWithWoodenTable(wt);

            }

            wt.draw(g);

        }

        for (int i = 0; i < ricecooker.size(); i++) {

            Ricecooker rc = ricecooker.get(i);
            homeTank.collideWithRicecooker(rc);

            if (homeTank.collideWithRicecooker(rc)) {
                if (riceAttained) {

                    riceCooked = true;

                }

            }

            if (Player2) {

                homeTank2.collideWithRicecooker(rc);

            }

            rc.draw(g);

        }

        for (int i = 0; i < rice.size(); i++) {

            Rice r = rice.get(i);
            homeTank.collideWithRice(r);

            if (Player2) {

                homeTank2.collideWithRice(r);

            }

            if (homeTank2.collideWithRice(r)) {

                player1Seaweed = false;
                player1Avocado = false;
                player1Egg = false;
                player1Tuna = false;
                player2Seaweed = false;
                player2Avocado = false;
                player2Egg = false;
                player2Tuna = false;

                player2Rice = true;

            }

            r.draw(g);

        }

        for (int i = 0; i < seaweed.size(); i++) {

            Seaweed s = seaweed.get(i);
            homeTank.collideWithSeaweed(s);

            if (Player2) {

                homeTank2.collideWithSeaweed(s);

            }

            if (homeTank2.collideWithSeaweed(s)) {

                player1Rice = false;
                player1Avocado = false;
                player1Egg = false;
                player1Tuna = false;
                player2Rice = false;
                player2Avocado = false;
                player2Egg = false;
                player2Tuna = false;

                player2Seaweed = true;

            }

            s.draw(g);

        }

        for (int i = 0; i < avocado.size(); i++) {

            Avocado a = avocado.get(i);
            homeTank.collideWithAvocado(a);

            if (Player2) {

                homeTank2.collideWithAvocado(a);

            }

            if (homeTank2.collideWithAvocado(a)) {

                player1Rice = false;
                player1Seaweed = false;
                player1Egg = false;
                player1Tuna = false;
                player2Rice = false;
                player2Seaweed = false;
                player2Egg = false;
                player2Tuna = false;

                player2Avocado = true;

            }

            a.draw(g);

        }

        for (int i = 0; i < egg.size(); i++) {

            Egg e = egg.get(i);
            homeTank.collideWithEgg(e);

            if (Player2) {

                homeTank2.collideWithEgg(e);

            }

            if (homeTank2.collideWithEgg(e)) {

                player1Rice = false;
                player1Seaweed = false;
                player1Avocado = false;
                player1Tuna = false;
                player2Rice = false;
                player2Seaweed = false;
                player2Avocado = false;
                player2Tuna = false;
                player2Egg = true;

            }

            e.draw(g);

        }

        for (int i = 0; i < tuna.size(); i++) {

            Tuna t = tuna.get(i);
            homeTank.collideWithTuna(t);

            if (Player2) {

                homeTank2.collideWithTuna(t);

            }

            if (homeTank2.collideWithTuna(t)) {

                player1Rice = false;
                player1Seaweed = false;
                player1Avocado = false;
                player1Egg = false;
                player2Rice = false;
                player2Seaweed = false;
                player2Avocado = false;
                player2Egg = false;

                player2Tuna = true;

            }

            t.draw(g);

        }

        //player collisions with each other
        homeTank.collideWithTanks(homeTank2);
        if (Player2) {

            homeTank2.collideWithTanks(homeTank);

        }
    }

    public String displayCurrentIngredient(String i) {

        if (player1Rice == true) {

            i = "Rice";

        }

        if (player1Seaweed == true) {

            i = "Seaweed";

        }

        if (player1Avocado == true) {

            i = "Avocado";

        }

        if (player1Egg == true) {

            i = "Egg";

        }

        if (player1Tuna == true) {

            i = "Tuna";

        }

        return i;
    }

    public String displayIngredientStatus() {

        if (everythingOK) {

            return "INGREDIENTS OK";

        } else {

            return "Not all Ingredients are Chopped";

        }

    }

    public String displayRiceStatus() {

        if (riceCooked){

            return "RICE IS READY";

        } else {

            return "Rice Not Ready";

        }

    }

    //public void changeOrder()
    public void changeScore() {

        /*
        if player 1 collides with Cashier
            if 
        
         */
    }

    public void displayScore() {

        System.out.print(score);

    }

    public static void playMusic() {

        AudioPlayer BGP = AudioPlayer.player;
        AudioStream BGM;
        AudioData AD;
        ContinuousAudioDataStream loop = null;

        try {

            //get audio input
            BGM = new AudioStream(new FileInputStream("Music/BGM.wav"));
            AD = BGM.getData();
            loop = new ContinuousAudioDataStream(AD);

            //AudioPlayer.player.start(BGM);
            //AD = BGM.getData();
        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        BGP.start(loop);

    }

    public void actionPerformed(ActionEvent e) {

        playMusic();

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
