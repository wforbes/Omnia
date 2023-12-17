package net.wforbes.omnia.overworld.world.area.object.flora.shrub;

import net.wforbes.omnia.db.Database;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.world.area.object.AreaObjectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class ShrubType extends AreaObjectType {
    private String spriteFile;

    public enum SIZES { SMALL, MEDIUM, LARGE };

    public enum TYPES { BLUEBERRY }
    public String type;

    public ShrubType(TYPES type, Database db) {
        if (type == TYPES.BLUEBERRY) {
            //this.spriteFile = "Bush_blue_flowers3.png";
            this.type = "blueberry";
        }
        this.spriteFile = this.getSpriteFileFromDB(db);
    }

    public String getSpriteFileFromDB(Database db) {
        //System.out.println(this.type);
        String sql = "SELECT sprite_filename from flora_type WHERE genera='"+this.type+"';";
        try (
                Statement statement = db.connection.createStatement();
                ResultSet results = statement.executeQuery(sql)
        ) {
            results.next();
            //System.out.println(results.getString("sprite_filename"));
            return results.getString("sprite_filename");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return "";
    }

    public String getSpriteFile() { return spriteFile; }

    //TODO: can be moved to some kind of FloraType parent class
    //  to share use with TreeType and other similar classes
    public Loot getRandomLootInstance(Database db) {
        ArrayList<Item> lootItems = new ArrayList<>();
        String sql = "SELECT i.*, l.drop_rate from item i " +
                "INNER JOIN flora_type_harvest_loot l on l.item_id = i.id " +
                "INNER JOIN flora_type t on t.flora_id = l.flora_type_id " +
                "WHERE t.genera='"+this.type+"'";
        try {
            Statement statement = db.connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            Random r = new Random();
            while(results.next()) {
                float drop_rate = results.getFloat("drop_rate");
                if (drop_rate > r.nextFloat()) {
                    lootItems.add(new Item(
                            results.getInt("id"),
                            results.getString("name"),
                            results.getString("description"),
                            results.getString("icon_location")
                    ));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return (!lootItems.isEmpty()) ? new Loot(lootItems) : new Loot();
    }
}
