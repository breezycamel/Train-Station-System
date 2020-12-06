package trainstation.help;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import trainstation.model.User;
import trainstation.model.TrainSchedule;
import trainstation.model.TrainRoute;
import trainstation.model.Reservation;

public class ReservationHelp {
	
	public int reservation(Reservation reservation) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO reservation" +
            "  (trainId, origin, destination, tripType, travelDate, originTime, destinationTime, fare, userName, ticketType) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            
        	preparedStatement.setString(1, reservation.gettrainId());
            preparedStatement.setString(2, reservation.getOrigin());    
            preparedStatement.setString(3, reservation.getDestination());
            preparedStatement.setString(4, reservation.getTripType());
            preparedStatement.setString(5, reservation.getTravelDate());
            preparedStatement.setString(6, reservation.getOriginTime());
            preparedStatement.setString(7, reservation.getDestinationTime());
            preparedStatement.setInt(8, reservation.getFare());
            preparedStatement.setString(9, reservation.getUsername());
            preparedStatement.setString(10, reservation.getTicketType());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            System.out.print(result);
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	
	public ArrayList<Reservation>getReservationHistory (String userName)throws ClassNotFoundException{
		String SELECT_SCHEDULE_SQL = "SELECT * FROM reservation" + " WHERE userName =?" + 
	            " ORDER BY reservationId";
		ArrayList<Reservation> rHistory = new ArrayList<Reservation>();
		
		ResultSet result = null;
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            // Step 2:Create a statement using connection object
            PreparedStatement query = connection.prepareStatement(SELECT_SCHEDULE_SQL)) {
//            System.out.println(query);
            // Step 3: Execute the query or update query
            System.out.println(userName + " hihi ");
            
            query.setString(1, userName);
            System.out.println(query);
            result = query.executeQuery();
            System.out.println(result);
//            if (!result.next()) {
//            	return null;
//            }
            while (result.next()) {
            	
            	String train_ID = result.getString("trainId");
            	String origin = result.getString("origin");
            	String destination = result.getString("destination");
            	String tripType = result.getString("tripType");
            	String travelDate = result.getString("travelDate");
            	int fare = result.getInt("fare");
            	String arrivalTime = result.getString("destinationTime");
            	String departTime = result.getString("originTime");
            	String ticketType = result.getString("ticketType");
            	
            	rHistory.add(new Reservation(userName, train_ID, origin, destination, tripType, travelDate, departTime, arrivalTime, fare, ticketType));
            	
//            	String arrivalTime = result.getString("arrivalTime")
//            	String departTime = result.getString("departTime")
            	
//            	schedule.add(new TrainSchedule(train_ID, stationID, arrivalTime, departTime, fare));
//            	count++;
//            	if(stationID.equals(destination)) break;
  
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
//        int indexOfOrigin = -1;
//        int indexOfdes = -2;
//        for (int i =0; i< schedule.size(); i++) {
//        	TrainSchedule t = schedule.get(i);
//        	if (t.getstationId().equals(origin)) indexOfOrigin = i;
//        	if (t.getstationId().equals(destination)) indexOfdes = i;
//        	//if(indexOfOrigin >= 0 && indexOfdes > 0) break;
//        }
//        if (indexOfdes > indexOfOrigin  && indexOfOrigin >=0)
//        	return schedule;
//        return null;
        
        return rHistory;
	}
	
	 private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
	 
}