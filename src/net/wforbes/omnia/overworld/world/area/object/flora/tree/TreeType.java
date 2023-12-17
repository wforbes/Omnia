package net.wforbes.omnia.overworld.world.area.object.flora.tree;

import net.wforbes.omnia.db.Database;
import net.wforbes.omnia.overworld.gui.item.Item;
import net.wforbes.omnia.overworld.gui.loot.Loot;
import net.wforbes.omnia.overworld.world.area.object.AreaObjectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class TreeType extends AreaObjectType {

    public enum SIZES { SMALL, MEDIUM, LARGE };

    public enum TYPES { OAK };

    public TreeType(TreeType.TYPES type, Database db) {
        if (type == TreeType.TYPES.OAK) {
            //this.spriteFile = "fixed_Tree1.png";
            this.type = "oak";
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

    //TODO: can be moved to some kind of FloraType parent class
    //  to share use with ShrubType and other similar classes
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
                float nextFloat = r.nextFloat();
                if (drop_rate > nextFloat) {
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
