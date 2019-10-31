package net.wforbes.graphics;

public class Screen {

    private int width;
    private int height;
    private SpriteSheet sheet;
    private int[] pixels;
    private int xOffset;
    private int yOffset;
    private static final byte BIT_MIRROR_X = 0x01;
    private static final byte BIT_MIRROR_Y = 0x02;

    public Screen(int width, int height, SpriteSheet sheet)
    {
        this.width = width;
        this.height = height;
        this.sheet = sheet;
        this.pixels = new int[width * height];
    }

    public void setOffset(int xOffset, int yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    //TODO: add formula(s) to calculate correct values if tile is not 8x8, removing literals
    public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale){
        yPos -= yOffset;
        xPos -= xOffset;
        //sets if tile should be mirrored
        boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
        boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
        //scale map - (future use.. to modify for larger resolutions)
        int scaleMap = scale - 1;
        //xtile - the x coordinate of the tile sheet
        int xTile = tile % 32;
        //ytile - the y coordinate of the tile sheet
        int yTile = tile / 32;
        //tileoffset - offset on the sheet to reach further tiles, bitshift 3 because 8x8 pixels so 2^3 = 8
        int tileOffset = (xTile << 3) + (yTile << 3) * this.sheet.getWidth();

        //loop for every y pixel on the tile
        for(int y = 0; y < 8; y++){
            int ySheet = y;
            //check for mirror
            if(mirrorY)
                ySheet = 7 - y;
            //get pixel
            int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3)/ 2);

            //loop for every x pixel on the tile
            for(int x = 0; x < 8; x++){
                int xSheet = x;
                //check for mirror
                if(mirrorX)
                    xSheet = 7 - y;
                //get pixel
                int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3)/ 2);

                //get color from the sheet
                int pixelPos = xSheet + ySheet * this.sheet.getWidth() + tileOffset;
                int col = (color >> (this.sheet.pixels[pixelPos] * 8) & 255);
                //check that the color isnt transparent (255)
                if(col < 255){
                    //use the scale to apply this color to each pixel evenly
                    //loop between 0 and yScale
                    for(int yScale = 0; yScale < scale; yScale++){
                        //if rendering to scale would go off screen, ignore
                        if(yPixel + yScale < 0 || yPixel + yScale >= height)
                            continue;
                        //loop between 0 and xScale
                        for(int xScale = 0; xScale < scale; xScale++) {
                            //if rendering to scale would go off screen, ignore
                            if(xPixel + xScale < 0 || xPixel + xScale >= width)
                                continue;
                            //apply color to the appropriate screen pixel
                            this.pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
                        }
                    }
                }
            }
        }
    }
}
