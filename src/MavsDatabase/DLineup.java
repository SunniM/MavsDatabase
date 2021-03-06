package MavsDatabase;

import java.sql.*;
import java.util.LinkedList;
import javax.swing.*;

public class DLineup extends Command {
    
    public DLineup(Connection connection) {
        super(connection);
        //TODO Auto-generated constructor stub
    }

    public ResultSet makeQuery() {
        try {
            Statement myStatement = conn.createStatement();
            myStatement.executeQuery("SELECT Number, Name, steals, blocks, defensive_rdb FROM players, defensive_stats WHERE number=PLAYER_NUM AND blocks IN( SELECT blocks FROM players, defensive_stats where number=player_num and defensive_rdb in ( select defensive_rdb from players,defensive_stats where number=player_num order by defensive_rdb desc) order by steals desc) order by blocks desc limit 5;");
            ResultSet myResultSet = myStatement.getResultSet();
            return myResultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void createAndShowGUI() {

        JFrame frame = new JFrame("Defensive Statistics");
        String[] column = {"NUMBER","NAME", "STEALS", "BLOCKS", "DEFENSIVE_RDB" };
        JTable jt = new JTable(getData(column, makeQuery()), column);
        jt.setBounds(30, 40, 1000, 3000);
        JScrollPane sp = new JScrollPane(jt);
        frame.add(sp);
        frame.setSize(1000, 400);
        frame.setVisible(true);

        // JTable table = new JTable(resultSet.getMetaData();
    }

    public String[][] getData(String[] column, ResultSet resultSet) {

        LinkedList<LinkedList<String>> list = new LinkedList<>();
        LinkedList<String> row;
        try {
            while (resultSet.next()) {
                row = new LinkedList<>();
                for (String string : column) {
                    // resultSet.getArray(string);
                    row.add(resultSet.getString(string));
                }
                list.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String string : column) {
            string = string.toUpperCase();
        }
        return list.stream().map(l -> l.stream().toArray(String[]::new)).toArray(String[][]::new);
    }

    @Override
    public void Execute() {
        createAndShowGUI();
    }

    @Override
    public void Execute(String playerNum) {
        createAndShowGUI();
    }
}

