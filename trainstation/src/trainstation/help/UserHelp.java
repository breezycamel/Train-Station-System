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

public class UserHelp {
	public int registerUser(User user) throws ClassNotFoundException {
        String INSERT_USERS_SQL = "INSERT INTO users" +
            "  (userName, firstName, lastName, password, SSN, email, userRole) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";

        int result = 0;

        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            
        	preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getFirstName());    
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getSSN());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setString(7, user.getUserRole());

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        return result;
    }
	
	/*
	 * Verifies user logins.
	 * Parameter:
	 * 	String userName, String password
	 * Return:
	 * 	null - if username and password combination does not exist in the database
	 * 	User object - if username and password combination exist in the database
	 */
	public User login(String userName, String password) throws ClassNotFoundException {
		
        String SELECT_USER_SQL = "SELECT userName, firstName, lastName, password, ssn, email, userRole from users" +
            "  WHERE userName = ? and password = ?;";
        
        ResultSet result = null;
        User user = null;
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            PreparedStatement query = connection.prepareStatement(SELECT_USER_SQL)) {
        	query.setString(1, userName);
        	query.setString(2, password);
            System.out.println(query);
            result = query.executeQuery();
            
            if(!result.next()) {
            	return null;
            }
            user = new User(
            		result.getString(1),	//userName
            		result.getString(2), 	//firstName
            		result.getString(3), 	//lastName
            		result.getString(4), 	//password
            		result.getString(5), 	//ssn
            		result.getString(6),	//email
            		result.getString(7)		//userRole
            );
        } catch (SQLException e) {
            printSQLException(e);
        }
        
        return user;
    }
	
	public void updateUser(User user) throws ClassNotFoundException {
		String DELETE_USERS_SQL = "UPDATE users SET firstName=?, lastName=?, password=?, SSN=?, email=?, userRole=? WHERE userName = ? ;";
	 	Class.forName("com.mysql.jdbc.Driver");

	        try (Connection connection = DriverManager
	            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
	            preparedStatement.setString(1, user.getFirstName());    
	            preparedStatement.setString(2, user.getLastName());
	            preparedStatement.setString(3, user.getPassword());
	            preparedStatement.setString(4, user.getSSN());
	            preparedStatement.setString(5, user.getEmail());
	            preparedStatement.setString(6, user.getUserRole());
	            preparedStatement.setString(7, user.getUsername());
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            // process sql exception
	            printSQLException(e);
	        }
	}
	
	public ArrayList<User> getAllUsers () throws ClassNotFoundException {
		 String SELECT_USERS_SQL = "SELECT * FROM users";
		 ArrayList<User> users = new ArrayList<User>();

		       // User[] result = new User[100];
		 		ResultSet result = null;
		        Class.forName("com.mysql.jdbc.Driver");

		        try (Connection connection = DriverManager
		            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

		            // Step 2:Create a statement using connection object
		            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_SQL)) {
		            System.out.println(preparedStatement);
		            // Step 3: Execute the query or update query
		            result = preparedStatement.executeQuery();
		            if (!result.next()) {
		            	return null;
		            }
		            while (result.next()) {
		            	String userName = result.getString("userName");
		            	String firstName = result.getString("firstName");
		            	String lastName = result.getString("lastName");
		            	String password = result.getString("password");
		            	String SSN = result.getString("SSN");
		            	String email = result.getString("email");
		            	String userRole = result.getString("userRole");
		            	
		            	users.add(new User(userName, firstName, lastName, password, SSN, email, userRole));
		            }

		        } catch (SQLException e) {
		            // process sql exception
		            printSQLException(e);
		        }
		        return users;
		
	}
	
	public ArrayList<User> getAllRep() throws ClassNotFoundException {
		 String SELECT_USERS_SQL = "SELECT * FROM users WHERE userRole = 'rep';";
		 ArrayList<User> users = new ArrayList<User>();

		 		ResultSet result = null;
		        Class.forName("com.mysql.jdbc.Driver");

		        try (Connection connection = DriverManager
		            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

		            // Step 2:Create a statement using connection object
		            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS_SQL)) {
		            System.out.println(preparedStatement);
		            // Step 3: Execute the query or update query
		            result = preparedStatement.executeQuery();
		            while (result.next()) {
		            	String userName = result.getString("userName");
		            	String firstName = result.getString("firstName");
		            	String lastName = result.getString("lastName");
		            	String password = result.getString("password");
		            	String SSN = result.getString("SSN");
		            	String email = result.getString("email");
		            	String userRole = result.getString("userRole");
		            	
		            	users.add(new User(userName, firstName, lastName, password, SSN, email, userRole));
		            }

		        } catch (SQLException e) {
		            // process sql exception
		            printSQLException(e);
		        }
		        return users;
	}
	
	public void deleteUser(String userName) throws ClassNotFoundException {
		 String DELETE_USERS_SQL = "DELETE FROM users WHERE userName = ?;";

	 		ResultSet result = null;
	        Class.forName("com.mysql.jdbc.Driver");

	        try (Connection connection = DriverManager
	            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
	        	preparedStatement.setString(1, userName);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            // process sql exception
	            printSQLException(e);
	        }
	}
	
	public int getFare(String trainID) throws ClassNotFoundException{
		String GET_FARE = "SELECT fare from trainSchedule WHERE trainID = ?";
		int fare = 0;
		ResultSet result = null;
        Class.forName("com.mysql.jdbc.Driver");

		 try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            // Step 2:Create a statement using connection object
            PreparedStatement query = connection.prepareStatement(GET_FARE)) {
//		            System.out.println(query);
            // Step 3: Execute the query or update query
            
            query.setString(1, trainID);
            System.out.println(query);
            result = query.executeQuery();
            System.out.println(result);
		    
            while(result.next()) {
            	fare = result.getInt("fare");
            }
            
	        } catch (SQLException e) {
	            // process sql exception
	            printSQLException(e);
	        }
		 return fare;
	}
	
	public ArrayList<TrainSchedule>getTrainSchedule (String trainID, String origin, String destination)throws ClassNotFoundException{
		String SELECT_SCHEDULE_SQL = "SELECT * FROM stop" + " WHERE trainId =?" + 
	            " ORDER BY arrivalTime;";
		ArrayList<TrainSchedule> schedule = new ArrayList<TrainSchedule>();
		
		ResultSet result = null;
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            // Step 2:Create a statement using connection object
            PreparedStatement query = connection.prepareStatement(SELECT_SCHEDULE_SQL)) {
//            System.out.println(query);
            // Step 3: Execute the query or update query
            System.out.println(trainID + " hihi " + origin + " hihi " + destination);
            
            query.setString(1, trainID);
            System.out.println(query);
            result = query.executeQuery();
            System.out.println(result);
//            if (!result.next()) {
//            	return null;
//            }
            boolean startToAdd = false;
            int count = 1;
            while (result.next()) {
            	if (!startToAdd) {
            		String stationID = result.getString("stationId");
            		if (stationID.equals(origin)) startToAdd = true;	
            	}
            	if (startToAdd) {
            	String train_ID = result.getString("trainId");
            	String stationID = result.getString("stationId");
            	int fare = getFare(train_ID)*count;
            	String arrivalTime = result.getTime("arrivalTime").toString();
            	String departTime = result.getTime("departTime").toString();
//            	String arrivalTime = result.getString("arrivalTime")
//            	String departTime = result.getString("departTime")
            	
            	schedule.add(new TrainSchedule(train_ID, stationID, arrivalTime, departTime, fare));
            	count++;
            	if(stationID.equals(destination)) break;
            	}
            }

        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        int indexOfOrigin = -1;
        int indexOfdes = -2;
        for (int i =0; i< schedule.size(); i++) {
        	TrainSchedule t = schedule.get(i);
        	if (t.getstationId().equals(origin)) indexOfOrigin = i;
        	if (t.getstationId().equals(destination)) indexOfdes = i;
        	//if(indexOfOrigin >= 0 && indexOfdes > 0) break;
        }
        if (indexOfdes > indexOfOrigin  && indexOfOrigin >=0)
        	return schedule;
        return null;
		
	}
	
	public ArrayList<TrainRoute>getTrainRoute (String origin, String destination)throws ClassNotFoundException{
		String SELECT_SCHEDULE_SQL = "select ori.trainId, ori.arrivalTime as departTime , des.arrivalTime as arrivalTime,  ori.stationId as origin, des.stationId as destination from \r\n" + 
				"(select * from stop\r\n" + 
				"where stationId= ?) ori\r\n" + 
				", \r\n" + 
				"(select * from stop\r\n" + 
				"where stationId= ?) des\r\n" + 
				"where ori.trainId = des.trainId\r\n" + 
				"and ori.arrivalTime < des.arrivalTime\r\n" + 
				";";
		ArrayList<TrainRoute> trainRoute = new ArrayList<TrainRoute>();
		
		ResultSet result = null;
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager
            .getConnection("jdbc:mysql://database-1.cjsw9rqqllkz.us-east-2.rds.amazonaws.com:3306/trainstation", "admin", "group28tlp");

            // Step 2:Create a statement using connection object
            PreparedStatement query = connection.prepareStatement(SELECT_SCHEDULE_SQL)) {
//            System.out.println(query);
            // Step 3: Execute the query or update query
            System.out.println(" hihi " + origin + " hihi " + destination);
            
            query.setString(1, origin);
            query.setString(2, destination);
            
            System.out.println(query);
            result = query.executeQuery();
            System.out.println(result);
            
            while(result.next()) {
            	String originShow = result.getString("origin");
            	String destinationShow = result.getString("destination");
            	String trainId = result.getString("trainId");
            	String departTime = result.getString("departTime");
            	String arrivalTime = result.getString("arrivalTime");
            	ArrayList<TrainSchedule>temp = getTrainSchedule(trainId, originShow, destinationShow);
            	int totalFare = temp.get(temp.size() - 1).getFare();
            	trainRoute.add(new TrainRoute(trainId, arrivalTime, departTime, originShow, destinationShow, totalFare));
            }
        } catch (SQLException e) {
            // process sql exception
            printSQLException(e);
        }
        
		return trainRoute;
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
