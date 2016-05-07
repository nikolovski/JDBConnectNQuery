import java.sql.*;

/**
 * Created by Martino Nikolovski on 5/5/16.
 * This is a simple class that is used to connect to different
 */
public class ConnectNQuery {
    private String jdbcURL, username, password;
    private Connection connection;
    private static String jdbcDriver;
    private ResultSet resultSet;

    public ConnectNQuery(String jdbcURL, String username, String password) {
        System.out.println("Database ConnectNQuery provided by");
        System.out.println("          .--.  ,---.  _______ ,-..-. .-. .---.   \n" +
                "|\\    /| / /\\ \\ | .-.\\|__   __||(||  \\| |/ .-. )  \n" +
                "|(\\  / |/ /__\\ \\| `-'/  )| |   (_)|   | || | |(_) \n" +
                "(_)\\/  ||  __  ||   (  (_) |   | || |\\  || | | |  \n" +
                "| \\  / || |  |)|| |\\ \\   | |   | || | |)|\\ `-' /  \n" +
                "| |\\/| ||_|  (_)|_| \\)\\  `-'   `-'/(  (_) )---'   \n" +
                "'-'  '-'            (__)         (__)    (_)  ");
        this.password = password;
        this.username = username;
        this.jdbcURL = jdbcURL;
    }

    /**
     *This function sets the appropriate database driver to be used for different databases.
     * Before updating the switch statement with new driver, please download its library and import it
     * in the project
     * @param driver integer that chooses the driver for a specific database
     *               1 - Oracle Driver
     *               2 - MySQL Driver
     */
    public void setDriver(int driver){
        switch (driver) {
            case 1:
                jdbcDriver = "oracle.jdbc.driver.OracleDriver";
                System.out.println("Oracle database driver loaded.");
                break;
            case 2:
                jdbcDriver = "com.mysql.jdbc.Driver";
                System.out.println("MySQL database driver loaded.");
                break;
            default:
                System.err.println("Non-existing database driver!");
        }
    }

    /**
     * This function connects to the database by using the appropriate driver and connection parameters
     */
    public void connect(){
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcURL,username,password);
            System.out.println("DB URL "+jdbcURL+" is valid. Connection successful!");

        } catch (ClassNotFoundException e) {
            System.err.println("The jdbc driver "+jdbcDriver+" is not found!");
            System.out.println("Please download the required driver.");
        } catch (SQLException e) {
            System.err.println("Could not connect to the DB with the provided parameters!");
            e.printStackTrace();
        }
    }

    /**
     * This function closes the current database connection
     */
    public void disconnect(){
        try {
            connection.close();
            System.out.println("Disconnected");
        } catch (SQLException e) {
            System.err.println("Error occurred while attempting to end the DB connection!");
            e.printStackTrace();
        }
    }

    public void query(String queryStatement){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
            resultSet = preparedStatement.executeQuery();
            System.out.println("SQL statement: "+queryStatement);
            System.out.println("======================TABLE COLUMNS======================");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                System.out.println(i+"\t"+resultSetMetaData.getColumnName(i)+"\t"
                        +resultSetMetaData.getColumnTypeName(i)+"\t");

            }
            System.out.println("=========================================================");
        } catch (SQLException e) {
            System.err.println("Query error! Please check the SQL statement!");
            e.printStackTrace();
        }
    }

    /**
     * This function prints the preferred columns of each record. Needs to be customized by needs
     * Note: May throw SQL Exception if the string index is out of bounds meaning the column number does not exist in
     * current table. You might want to inspect the schema first before displaying the SQL query result.
     */
    public void displayResult(){
        try {
            System.out.println("Result of the query "+resultSet.getStatement());
            while(resultSet.next()){
                //customizing the display of the query
                for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i)+" ");
                }
                System.out.println();
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function calls all other functions needed to establish a connection and execute query.
     * @param jdbcURL String that represents the database URL.
     *                Ex. for Oracle jdbc:oracle:<drivertype>:@<database>
     *                    for MySQL jdbc:mysql://<database_link>
     * @param username String that represents a username in the database
     * @param password String that represents the corresponding password
     * @param driver   Integer 1 - Oracle DB    2 - MySQL DB    Note: can be extended to support other DBs
     * @param query    String that represents the SQL statement
     */
    public static void connectNQuery(String jdbcURL, String username, String password, int driver, String query){
        ConnectNQuery connectNQuery = new ConnectNQuery(jdbcURL,username,password);
        connectNQuery.setDriver(driver);
        connectNQuery.connect();
        connectNQuery.query(query);
        connectNQuery.displayResult();
        connectNQuery.disconnect();
    }

    public static void main(String[] args) {
        try{
            connectNQuery(args[0],args[1],args[2],Integer.parseInt(args[3]),args[4]);
        }
        catch (ArrayIndexOutOfBoundsException ob){
            System.err.println("Invalid input parameters!");
            System.err.println("$java ConnectNQuery <jdbcURL> <username> <password> <driver> <SQL_query>");
        }
        catch (NumberFormatException nfe){
            System.err.println("Number format exception!");
            System.err.println("$java ConnectNQuery <String jdbcURL> <String username> <String password> <int driver> <String SQL_query>");
        }
    }
}
