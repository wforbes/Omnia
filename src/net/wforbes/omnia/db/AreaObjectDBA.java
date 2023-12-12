package net.wforbes.omnia.db;

import net.wforbes.omnia.overworld.world.area.object.AreaObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AreaObjectDBA {

    private final Database db;

    public class AreaObjectStruct {
        public int id;
        public String name;
        public String type;
        public int size;
        public String sprite_path;
        //public GatherSkill gather_skill;
    }

    public AreaObjectDBA(Database db) {
        this.db = db;
    }
    //TODO: Is this even a good idea?
    public AreaObjectStruct getByNameAndType(String name, String type) {
        String sql = "SELECT * FROM area_object WHERE name='"+
                name+" AND type='"+type+"' ' LIMIT 1;";
        try {
            Statement statement = this.db.connection.createStatement();
            ResultSet results = statement.executeQuery(sql);
            results.next();
            AreaObjectStruct aos = new AreaObjectStruct();
            aos.id = results.getInt("id");
            aos.name = name; //results.getString("name");
            aos.type = type; //results.getString("type");
            aos.size = results.getInt("size");
            aos.sprite_path = results.getString("sprite_path");
            //aos.gather_skill = new GatherSkill(results.getInt("gather_skill_id"); //TODO

            return aos;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
