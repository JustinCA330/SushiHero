/* 
 *
 * Rice.java
 * Justin Christopher Abarquez
 * Mariko Ariane Vecta Sampaga
 *
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Rice {

    public static final int width = 82;
    public static final int length = 99;
    public int x, y;
    SushiClient tc;
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] wallImags = null;

    static {

        wallImags = new Image[]{tk.getImage(Wall.class
            .getResource("Images/Rice.gif")),};

    }

    public Rice(int x, int y, SushiClient tc) {

        this.x = x;
        this.y = y;
        this.tc = tc;

    }

    public void draw(Graphics g) {

        g.drawImage(wallImags[0], x, y, null);

    }

    public Rectangle getRect() {

        return new Rectangle(x, y, width, length);

    }
}



