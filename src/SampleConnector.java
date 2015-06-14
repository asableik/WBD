import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SampleConnector {
	Connection dbConnection = null;
	public SampleConnector(){
	String url = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	String login ="esajur";
	String password = "esajur";
	try {
		dbConnection = DriverManager.getConnection(url,login,password);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	
	
	public ResultSet SelectMax(String table, String item){
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = dbConnection.createStatement();
			System.out.println("SELECT max("+item+") FROM "+ table);
			myRs= myStmt.executeQuery("SELECT max("+item+") FROM "+ table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myRs;
		
	}
	
	
	public ResultSet SelectOffers(String personID){
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = dbConnection.createStatement();
			String statement = "SELECT * FROM Esajur.Client c, Esajur.Property p WHERE c.person_id = '"+personID+"' AND "
																					 + "(p.price BETWEEN c.price_min AND c.price_max) AND"
																					 + "(p.square_meters BETWEEN c.sqm_min AND c.sqm_max) AND"
																					 + "(p.rent_or_buy = c.rent_or_buy) AND "
																					 + "(p.property_type = c.state) AND"
																					 + "(p.floor BETWEEN c.floor_min AND c.floor_max)";
			System.out.println(statement);
			myRs= myStmt.executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return myRs;
	}
	public ResultSet Select(String table, String[] itemsToSelect){
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myStmt = dbConnection.createStatement();
			String statement ="SELECT "+toSqlStatement(itemsToSelect)+" FROM "+ table;
			System.out.println(statement);
			myRs= myStmt.executeQuery(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return myRs;
	}
	
	
	
	
	public String toSqlStatement(String []values){
		if(values[0].equals("*")) return "*";
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		for(String s:values){
			builder.append("'"+s+"',");
		}
		builder.append(")");
		builder.replace(builder.length()-2, builder.length(), ")");
		
		return builder.toString();
		
	}
	
	
	
	
	public void Insert(String table, String []values){
		Statement myStmt;
		try {
			myStmt = dbConnection.createStatement();
			System.out.println("INSERT INTO "+table+" VALUES "+toSqlStatement(values));
			myStmt.executeUpdate("INSERT INTO "+table+" VALUES "+toSqlStatement(values));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void Delete(String table,String valueName, String value){
		Statement myStmt;
		try {
		myStmt = dbConnection.createStatement();
		String statement = "DELETE FROM "+table+" WHERE "+valueName+" = "+"'"+value+"'";
		System.out.println(statement);
		myStmt.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void Update(String table,String column, String newValue, String someColumn, String someValue){
		Statement myStmt;
		try {
		myStmt = dbConnection.createStatement();
		String statement = "UPDATE "+table+" SET "+column+" = "+"'"+newValue+"'"+" WHERE "+someColumn+" = '"+someValue+"'";
		System.out.println(statement);
		myStmt.executeUpdate(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args){
		final SampleConnector sampleConnector = new SampleConnector();
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				
				new MainFrame(sampleConnector).setVisible(true);
			}
			
		});



	}
}
