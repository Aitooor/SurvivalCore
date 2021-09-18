package online.nasgar.survivalcore.data;

import lombok.Getter;
import online.nasgar.survivalcore.utils.CC;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mansitoh
 * Project: SurvivalCore Date: 18/18/2021 @ 18:25
 * Twitter: @Mansitoh_PY Github: https://github.com/Mansitoh
 * Discord: https://discord.link/Skilled
 * CEO: Skilled | Development
 */
@Getter
public class MySQLSystem {


    public String databaseHost = "localhost";
    public String databasePort = "3306";
    public String database = "survival";
    public String databaseUser = "root";
    public String DatabasePassword = "idk no se la contra xD";

    public Connection conection;


    public void connect() throws SQLException {
        conection = DriverManager.getConnection("jdbc:mysql://" + getDatabaseHost() + ":" + getDatabasePort() + "/" + getDatabase() + "?useSSL=false", getDatabaseUser(), getDatabasePassword());
        createPlayerDataTable();
    }


    private void createPlayerDataTable() {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getConection().prepareStatement("CREATE TABLE IF NOT EXISTS player_data" +
                    " (uuid TEXT(255),inventory TEXT(255), armor TEXT(255), enderchest TEXT(255),experience TEXT(255), health TEXT(255),food TEXT(255),gamemode TEXT(255),location TEXT(255))");
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void insertInDatabase(String table, HashMap<String, String> insert) {
        String keys = "";
        String values = "";
        for (String key : insert.keySet()) {
            String value = insert.get(key);
            if (keys == "") {
                keys = "`" + key + "`";
            } else {
                keys = keys + ",`" + key + "`";
            }
            if (values == "") {
                values = "'" + value + "'";
            } else {
                values = values + ",'" + value + "'";
            }
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConection().prepareStatement("INSERT INTO" +
                    " `" + table.toString().toLowerCase() + "`(" + keys + ") VALUES (" + values + ")");
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateInTable(String table, HashMap<String, String> where, HashMap<String, String> set) {
        String wherestring = "";
        for (String key : where.keySet()) {
            String value = where.get(key);
            if (wherestring == "") {
                wherestring = key + "='" + value + "'";
            } else {
                wherestring = wherestring + " AND " + key + "='" + value + "'";
            }
        }
        for (String key : set.keySet()) {
            String value = set.get(key);
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = getConection().prepareStatement("UPDATE " + table.toLowerCase() + " SET " + key + "='" + value + "' WHERE " + wherestring);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void add(String table, HashMap<String, String> map) {
        HashMap<String, String> uuidmap = new HashMap<>();
        uuidmap.put("UUID", map.get("UUID"));
        if (get(table, 1, "*", uuidmap).size() == 0) {
            insertInDatabase(table, map);
        } else {
            updateInTable(table, uuidmap, map);
        }
    }

    public List<Object> get(String table, int setgetstring, String select, HashMap<String, String> where) {
        String wherestring = "";
        if (where != null) {
            for (String key : where.keySet()) {
                String value = where.get(key);
                if (wherestring == "") {
                    wherestring = "WHERE " + key + "='" + value + "'";
                } else {
                    wherestring = wherestring + " AND " + key + "='" + value + "'";
                }
            }
        }
        List<Object> collection = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = getConection().prepareStatement("SELECT " + select + " FROM " + table.toLowerCase() + " " + wherestring);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                if (set.getString(setgetstring) != null) {
                    collection.add(set.getString(setgetstring).toString());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return collection;
    }

    public void remove(String table, HashMap<String, String> where) {
        String wherestring = "";
        if (where != null) {
            for (String key : where.keySet()) {
                String value = where.get(key);
                if (wherestring == "") {
                    wherestring = "WHERE " + key + "='" + value + "'";
                } else {
                    wherestring = wherestring + " AND " + key + "='" + value + "'";
                }
            }
        }
        PreparedStatement statement = null;
        try {
            statement = getConection().prepareStatement("DELETE FROM " + table.toLowerCase() + " " + wherestring);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addPlayerToPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        HashMap<String, String> map = new HashMap<>();
        map.put("inventory", CC.serializeItemStackArray(player.getInventory().getContents()));
        map.put("armor", CC.serializeItemStackArray(player.getInventory().getArmorContents()));
        map.put("enderchest", CC.serializeItemStackArray(player.getEnderChest().getContents()));
        map.put("experience", String.valueOf(player.getExp()));
        map.put("health", String.valueOf(player.getHealth()));
        map.put("food", String.valueOf(player.getFoodLevel()));
        map.put("gamemode", String.valueOf(player.getGameMode()));
        map.put("location", CC.serializeLocation(player.getLocation()));
        add("player_name", map);
    }
}
