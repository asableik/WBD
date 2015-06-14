import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class FindPropertyDialog extends JDialog{
	
	JTable personT;
	JTable propertyT;
	
	JPanel container;
	
	JScrollPane personSP;	
	JScrollPane propertySP;
	
	DefaultTableModel personDTM;	
	DefaultTableModel propertyDTM;	
	
	SampleConnector sampleConnector;
	
	
	JButton findButton;
	
	public FindPropertyDialog(SampleConnector sampleConnector){
		setSize(new Dimension(1000,600));
	this.sampleConnector = sampleConnector;
	JPanel container = new JPanel();
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
	
	
	personT = new JTable();
	personDTM = new DefaultTableModel(){
		public boolean isCellEditable(int row, int column)  {
	       return false;
		}
	   };
	String[] presonColumnNames = {"ID",
			"First name",
			"Last name",
			"Birth date",
			"Phone number",
			"Email",
			"State",
			"Rent or buy",
			"Price min",
			"Price max",
			"Floor min",
			"Floor max",
			"Sqm min",
			"Sqm max"
			};
	personDTM.setColumnIdentifiers(presonColumnNames);
	personT.setModel(personDTM);
	
	
	propertyT = new JTable();
	propertyDTM = new DefaultTableModel(){
		public boolean isCellEditable(int row, int column)  {
	       return false;
		}
	   };
	   
	String[] propertyColumnNames = {"ID",
			"Type",
			"Price",
			"Adress",
			"Number of rooms",
			"Number of floors",
			"Square meters",
			"Date avaliable",
			"Date end"
			};
	propertyDTM.setColumnIdentifiers(propertyColumnNames);
	propertyT.setModel(propertyDTM);
	
	
	personSP = new JScrollPane(personT);
	propertySP = new JScrollPane(propertyT);
	
	
	findButton = new JButton("FIND");
	findButton.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			populatePropertyTable();
			
		}
		
		
	});
	
	container.add(personSP);
	container.add(propertySP);
	container.add(findButton);
	this.add(container);
	populateClientTable();

	}
		
		public void populatePropertyTable(){
			propertyDTM.setRowCount(0);
			ResultSet rs = sampleConnector.SelectOffers(""+(personT.getValueAt(personT.getSelectedRow(), 0)));
			try {
				while(rs.next()){
					propertyDTM.addRow(new Object[]{
							rs.getString("property_id"), 		//id
							rs.getString("property_type"),		//type
							rs.getString("price"),
							rs.getString("address_id"),
							rs.getString("number_of_rooms"),
							rs.getString("number_of_floors"),
							rs.getString("square_meters"),
							rs.getString("date_available"),
							rs.getString("date_end"),
							});
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		
		public void populateClientTable(){
			personDTM.setRowCount(0);
			ResultSet rs = sampleConnector.Select("Esajur.Client", new String[]{"*"});
			try {
				while(rs.next()){
					personDTM.addRow(new Object[]{
							rs.getString("person_id"), 		//id
							rs.getString("first_name"), 	//first name
							rs.getString("last_name"),		//last name
							rs.getString("birth_date"),		//birth date
							rs.getString("phone_number"),	//number
							rs.getString("email_address"),	//email
							rs.getString("state"), 			//state
							rs.getString("rent_or_buy"),	//rent or buy
							rs.getString("price_min"),		//price min
							rs.getString("price_max"),		//price max
							rs.getString("floor_min"),		//floor min
							rs.getString("floor_max"),		//floor max
							rs.getString("sqm_min"),		//sqm min
							rs.getString("sqm_max"),		//sqm max
							});
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		

}
