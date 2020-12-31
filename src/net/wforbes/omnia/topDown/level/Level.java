package net.wforbes.omnia.topDown.level;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import net.wforbes.omnia.topDown.entity.Entity;
import net.wforbes.omnia.topDown.graphics.Screen;
import net.wforbes.omnia.topDown.level.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private byte[] tiles;
    public int width;
    public int height;
    public List<Entity> entities = new ArrayList<>();
    private String imagePath;
    //private BufferedImage image; //TODO: reimplement
    private Image image;

    public Level(String imagePath){
        if(imagePath != null){
            this.imagePath = imagePath;
            this.loadLevelFromFile();
        }else{
            this.width = 64;
            this.height = 64;
            tiles = new byte[width * height];
            this.generateLevel();
        }
    }

    //TODO: Test this
    public void generateLevel()
    {
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(x * y % 10 < 8){//generates a specified distribution of grass and stones
                    tiles[x + y * width] = Tile.GRASS.getId();
                }else{
                    tiles[ x + y * width] = Tile.STONE.getId();
                }
            }
        }
    }

    public void loadLevelFromFile(){
        try {
            image = new Image(getClass().getResourceAsStream(this.imagePath));
            this.width = (int)image.getWidth();
            this.height = (int)image.getHeight();
            this.tiles = new byte[width * height];
            this.loadTiles();
        } catch(Exception e) {
            e.printStackTrace();
        }

        /* //TODO: reimplement
        try{
            this.image = ImageIO.read(Level.class.getResourceAsStream(this.imagePath));
            this.width = image.getWidth();
            this.height = image.getHeight();
            tiles = new byte[width * height];
            this.loadTiles();
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

    private void loadTiles(){
        PixelReader pixelReader = this.image.getPixelReader();
        int[] tileColors = new int[width * height];
        pixelReader.getPixels(0, 0, this.width, this.height, PixelFormat.getIntArgbInstance(), tileColors, 0, this.width);

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                tileCheck: for(Tile t : Tile.tiles) {
                    if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
                        this.tiles[x + y * width] = t.getId();
                        break tileCheck;
                    }
                }
            }
        }
        /* //TODO: reimplement
        //get the color information from the level file
        int[] tileColors = this.image.getRGB(0, 0, width, height, null, 0, width);
        //for each pixel in the level file
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                //use the color information to set the tile array
                //  with the appropriate type of tile
                tileCheck: for(Tile t : Tile.tiles){
                    if(t != null && t.getLevelColor() == tileColors[x + y * width]){
                        this.tiles[x + y * width] = t.getId();
                        break tileCheck;
                    }
                }
            }
        } */
    }

    public void tick(){
        for(Entity e : entities)
        {
            e.tick();
        }

        for(Tile t : Tile.tiles){
            if(t ==null){
                break;
            }
            t.tick();
        }
    }


    public void renderTiles(Screen screen, int xOffset, int yOffset)
    {
        //System.out.println("L renderTiles()");
        int f = 3;

        if(xOffset < 0) xOffset = 0;
        if(yOffset < 0) yOffset = 0;
        if(xOffset > ((width << f) - screen.getWidth())) xOffset = ((width << f) - screen.getWidth());
        if(yOffset > ((height << f) - screen.getHeight())) yOffset = ((height << f) - screen.getHeight());

        screen.setOffset(xOffset, yOffset);

        for(int y = (yOffset >> f); y < (yOffset + screen.getHeight() >> f) + 1; y++){
            for(int x = (xOffset >> f); x < (xOffset + screen.getWidth() >> f) + 1; x++){
                getTile(x, y).render(screen, this, x << f, y << f);
            }
        }
    }

    public Tile getTile(int x, int y)
    {
        if( 0 > x || x >= width || 0 > y || y >= height)
            return Tile.VOID;
        return Tile.tiles[tiles[ x + y * width]];
    }

    public void addEntity(Entity entity)
    {
        this.entities.add(entity);
    }

    public void renderEntities(Screen screen)
    {
        this.sortEntitiesByYPos();
        for(Entity e : entities)
        {
            e.render(screen);
        }
    }

    private void sortEntitiesByYPos() {
        entities.sort((e1, e2) -> {
            if (e1.y == e2.y) {
                return 0;
            } else if (e1.y > e2.y) {
                return 1;
            } else {
                return -1;
            }
        });
    }

    /** For future use **/
    private void saveLevelToFile(){
        /*
        try{
            ImageIO.write
                    (image, "png", new File(Level.class.getResource(this.imagePath).getFile()));
        }catch(IOException e){
            e.printStackTrace();
        }
         */
    }
    /** For future use **/
    public void alterTile(int x, int y, Tile newTile){
        this.tiles[x +y * width] = newTile.getId();
        //image.setRGB(x, y, newTile.getLevelColor());
    }
}
