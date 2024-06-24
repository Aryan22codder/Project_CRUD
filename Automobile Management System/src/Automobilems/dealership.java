package Automobilems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class dealership {
    private Connection connection;


    public dealership(Connection connection, Scanner scanner) {
        this.connection = connection;
    }

    public void viewdealership() {
        String query = "select * from dealership";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            System.out.println("Dealership: ");
            System.out.println("+--------------+-------------------------+-------------+");
            System.out.println("|Dealership ID |Name                     |Location     |");
            System.out.println("+--------------+-------------------------+-------------+");
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String location = resultset.getString("location");
                System.out.printf("|%-14s|%-25s|%-13s|\n", id, name, location);
                System.out.println("+--------------+-------------------------+-------------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean getdealershipbyid(int id) {
        String query = "SELECT * FROM dealership WHERE id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultset = preparedStatement.executeQuery();
            if(resultset.next()) {
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
