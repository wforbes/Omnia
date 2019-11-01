package net.wforbes.graphics;

public class Colors
{
    //c1 = darkest
    public static int get( int c1, int c2, int c3, int c4 )
    {
        return ( get(c4) << 24) + ( get(c3) << 16) + ( get(c2) << 8) +  get(c1);
    }

    private static int get(int color)
    {
        if(color < 0) return 255;

        int r = color/100%10;
        int g = color/10%10;
        int b = color%10;
        return r*36 + g*6 + b;
    }
}
