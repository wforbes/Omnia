package net.wforbes.omnia.topDown.graphics;

public class ColorsTest {
    private SpriteSheet sheet;
    private int[] colors;

    public ColorsTest() {
        try {
            this.sheet = new SpriteSheet("/sprite_sheet.png");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void test() {
        /*
        Player sets Colors.get(-1, 100, 005, 543)
        Screen sets (color >> (sheet pixel & 255)
         */
        int playerColor = getColor(-1, 100, 005, 543);
        //System.out.println(playerColor);
        /*
        for(int i = 0; i < this.sheet.pixels.length; i++) {
            System.out.println(this.sheet.pixels[i]);
        }*/

        int screenColor = playerColor >> (3 * 8) & 255;
        System.out.println(screenColor);


    }

    public int getColor( int c1, int c2, int c3, int c4)
    {
        return (getColor(c4) << 24) + ( getColor(c3) << 16) + ( getColor(c2) << 8) +  getColor(c1);
    }
    private int getColor(int color)
    {
        if(color < 0) return 255;

        int r = color/100%10;
        int g = color/10%10;
        int b = color%10;
        return (r*36) + (g*6) + b;
    }

    private void initColors() {
        int i = 0;
        for(int r = 0; r < 6; r++){
            for(int g = 0; g < 6; g++){
                for(int b = 0; b < 6; b++){
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);
                    colors[i++] = rr << 16 | gg << 8 | bb;
                }
            }
        }
    }
}
