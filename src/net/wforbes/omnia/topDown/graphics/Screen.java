package net.wforbes.omnia.topDown.graphics;

public class Screen {

    private int width;
    private int height;
    private SpriteSheet sheet;
    private int[] pixels;
    public int xOffset;
    public int yOffset;
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
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public int[] getPixels(){
        return this.pixels;
    }

    //TODO: add formula(s) to calculate correct values if tile is not 8x8, removing literals
    public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale){
        yPos -= yOffset;
        xPos -= xOffset;
        boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;//sets if tile should be mirrored in the Y
        boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;//sets if the tile should be mirrored in the X
        int scaleMap = scale - 1; //scale map - (future use.. to modify for larger resolutions)
        int xTile = tile % 32; //xtile - the x coordinate of the tile sheet
        int yTile = tile / 32; //ytile - the y coordinate of the tile sheet
        //tileoffset - offset on the sheet to reach further tiles, bitshift 3 because 8x8 pixels so 2^3 = 8
        int tileOffset = (xTile << 3) + (yTile << 3) * this.sheet.getWidth();
        for(int y = 0; y < 8; y++){//loop for every y pixel on the tile
            int ySheet = y;
            if(mirrorY) //check for mirror
                ySheet = 7 - y;
            int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3)/ 2);//get pixel
            for(int x = 0; x < 8; x++){ //loop for every x pixel on the tile
                int xSheet = x;
                if(mirrorX) //check for mirror
                    xSheet = 7 - x;
                int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3)/ 2);//get pixel
                int pixelPos = xSheet + ySheet * this.sheet.getWidth() + tileOffset;//get color from the sheet
                int col = (color >> (this.sheet.pixels[pixelPos] * 8) & 255);
                if(col < 255){ //check that the color isnt transparent (255)
                    //use the scale to apply this color to each pixel evenly
                    //loop between 0 and yScale
                    for(int yScale = 0; yScale < scale; yScale++){
                        if(yPixel + yScale < 0 || yPixel + yScale >= height)//if rendering to scale would go off screen, ignore
                            continue;
                        for(int xScale = 0; xScale < scale; xScale++) {//loop between 0 and xScale
                            if(xPixel + xScale < 0 || xPixel + xScale >= width) //if rendering to scale would go off screen, ignore
                                continue;
                            this.pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;//apply color to the appropriate screen pixel
                        }
                    }
                }
            }
        }
    }
}
