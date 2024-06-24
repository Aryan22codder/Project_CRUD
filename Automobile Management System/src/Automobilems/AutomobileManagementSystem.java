package Automobilems;

import javax.lang.model.element.NestingKind;
import java.sql.*;
import java.util.Scanner;

public class AutomobileManagementSystem {

    private static final String url = "jdbc:mysql://127.0.0.1:3306/automobile";

    private static final String username = "root";

    private static final String password = "root";

    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);

        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            buyer buyer = new buyer(connection, scanner);
            dealership dealership = new dealership(connection, scanner);
            while(true){
                System.out.println("AUTOMOBILE MANAGEMENT SYSTEM ");
                System.out.println("1.Add Car");
                System.out.println("2.View Car");
                System.out.println("3.View Dealership");
                System.out.println("4.Book Delivery Date");
                System.out.println("5.Exit");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        //Add Car
                        buyer.addcar();
                        System.out.println();
                        break;
                    case 2:
                        //View Car
                        buyer.viewcar();
                        System.out.println();
                        break;
                    case 3:
                        //view Dealership
                        dealership.viewdealership();
                        System.out.println();
                        break;
                    case 4:
                        //Book Delivery Date
                        bookdeliverydate(buyer, dealership, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        return;

                }


            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookdeliverydate(buyer buyer, dealership dealership, Connection connection, Scanner scanner){
        System.out.println("Enter Buyer_ID");
        int buyerID = scanner.nextInt();
        System.out.println("Enter Dealership_ID");
        int dealershipID = scanner.nextInt();
        System.out.println("Enter Delivery date (YYYY-MM-DD): ");
        String deliverydate = scanner.next();
        if(buyer.getcarbyid(buyerID) && dealership.getdealershipbyid(dealershipID)){
            if(checkDealershipAvailability(dealershipID, deliverydate, connection)){
                String deliveryQuery = "INSERT INTO delivery(buyer_id, dealership_id,  delivery VALUES(?, ?, ?)  )";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(deliverydate);
                    preparedStatement.setInt(1,buyerID);
                    preparedStatement.setInt(2,dealershipID);
                    preparedStatement.setString(3,deliverydate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.println("Delivery Date Booked Successfully ");
                    }else{
                        System.out.println("Failed to Book Delivery Date");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }else{
                System.out.println("Dealership is not available on this date!!!");
            }
        }else{
            System.out.println("Either Dealership or Buyer doesn't exist!!");
        }
    }

    public static boolean checkDealershipAvailability(int dealershipID , String deliverydate, Connection connection){
        String query = "SELECT COUNT(*) FROM delivery WHERE dealership_id = ? AND delivery_date = ?";
       try {
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1, dealershipID);
           preparedStatement.setString(2,deliverydate);
           ResultSet resultSet = preparedStatement.executeQuery();
           if(resultSet.next()){
               int count = resultSet.getInt(1);
               if(count==0){
                   return true;
               }else{
                   return false;
               }
           }
       }catch (SQLException e){
           e.printStackTrace();
       }
       return false;
    }
}
