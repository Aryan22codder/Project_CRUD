package Automobilems;

import com.mysql.cj.protocol.Resultset;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class buyer {
    private Connection connection;

    private Scanner scanner;

    public buyer(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addcar() {
        System.out.println("Enter Name:");
        String name = scanner.next();
        System.out.println("Enter Car Model: ");
        String model = scanner.next();
        System.out.println("Enter Car Colour: ");
        String colour = scanner.next();

        try {
            String query = "INSERT INTO buyer(name,model,colour) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, colour);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Car Added Successfully");
            } else {
                System.out.println("Failed to add Car");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void viewcar() {
        String query = "select * from buyer";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            System.out.println("Car Buyer");
            System.out.println("+--------+-------------------------+----------------+-----------+");
            System.out.println("|Buyer ID|Name                     |Model           |Colour     |");
            System.out.println("+--------+-------------------------+----------------+-----------+");
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String model = resultset.getString("model");
                String colour = resultset.getString("colour");
                System.out.printf("|%-8s|%-25s|%-16s|%-11s|\n", id, name, model, colour);
                System.out.println("+--------+-------------------------+----------------+-----------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean getcarbyid(int id) {
        String query = "SELECT * FROM buyer WHERE id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

}