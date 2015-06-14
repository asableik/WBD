import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;


public class AddClientDialog extends AddPersonDialog {
	JLabel priceMinL, priceMaxL, stateL, floorMinL, floorMaxL, sqmMinL,sqmMaxL,rentOrBuyL;
	
	JComboBox<String> stateCB;
	JTextField priceMinTF, priceMaxTF, floorMinTF, floorMaxTF,sqmMinTF, sqmMaxTF;
	JButton addB;
	
	JComboBox<String> updatePicker;
	JTextField updateTextField;
	
	
	JComboBox<String> rentOrBuyCB;
	
	int i = 3;
	private JButton editB;
	private JButton deleteB;
	private AbstractButton editCancelB;
	private JButton editOkB;
	@SuppressWarnings({ "unchecked", "serial" })
	public AddClientDialog(final SampleConnector sampleConnector){
		super(sampleConnector);
		
		NumberFormat longFormat = NumberFormat.getIntegerInstance();
		NumberFormatter numberFormatter = new NumberFormatter(longFormat);
		numberFormatter.setMinimum(0l);
		numberFormatter.setValueClass(Long.class); //optional, ensures you will always get a long value
		numberFormatter.setAllowsInvalid(true); //this is the key!!
		
		this.setTitle("Add Client");
		priceMinL = new JLabel("Minimum price:");
		priceMinTF = new JTextField();
		
		priceMaxL = new JLabel("Maximum price:");
		priceMaxTF = new JTextField();
		
		
		String []rentOrBuyString  = {"Rent","Buy"};
		rentOrBuyL = new JLabel("Rent or buy:");
		rentOrBuyCB = new JComboBox<String>(rentOrBuyString);
		rentOrBuyCB.setSelectedIndex(0);
		
		String []states = {"New","Aftermarket"};
		stateL = new JLabel("State:");
		stateCB = new JComboBox<String>(states);
		stateCB.setSelectedIndex(0);
		
		
		
		floorMinL = new JLabel("Minimum floor:");
		floorMinTF = new JTextField();
		
		floorMaxL = new JLabel("Maximum floor:");
		floorMaxTF = new JTextField();
		
		sqmMinL = new JLabel("Minimum SQM: ");
		sqmMinTF = new JTextField();
		
		sqmMaxL = new JLabel("Maximum SQM: ");
		sqmMaxTF = new JTextField();
		
		personT = new JTable();
		dtm = new DefaultTableModel(){
			public boolean isCellEditable(int row, int column)  {
		       return false;
			}
		   };
		String[] columnNames = {"ID","First name","Last name","Birth date","Phone number","Email",};
		dtm.setColumnIdentifiers(columnNames);
		personT.setModel(dtm);
		ActionMap am = personT.getActionMap();
		
		am.put("selectPreviousColumnCell", new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		        manager.focusPreviousComponent();
			}
			
		});    
		am.put("selectNextColumnCell",  new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent e) {
				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		        manager.focusNextComponent();
				
			}
			
		}); 
		
		
		
		populateTable();

		tableSP = new JScrollPane(personT);

		addB = new JButton("Add client");
		addB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int id = 0;
				ResultSet count = sampleConnector.SelectMax("Esajur.Client","person_id");
				try {
					count.next();
					id = count.getInt(1)+1;
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String table = "ESajur.Client";
				String []values = {
						""+id,
						priceMinTF.getText(),
						priceMaxTF.getText(),
						""+stateCB.getSelectedIndex(),
						floorMinTF.getText(),
						floorMaxTF.getText(),	
						sqmMinTF.getText(),
						sqmMaxTF.getText(),
						firstNameTF.getText(),
						lastNameTF.getText(),
						datePicker.getModel().getValue().toString(),
						phoneNumberTF.getText(),
						emailAdressTF.getText(),
						""+rentOrBuyCB.getSelectedIndex()
			};
				
				sampleConnector.Insert(table, values);
				populateTable();
			}
			
		});
		
		
		editB = new JButton("Edit client");
		editB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(personT.getSelectedRow());
				editCancelB.setEnabled(true);
				editOkB.setEnabled(true);
				addB.setEnabled(false);
				editB.setEnabled(false);
				deleteB.setEnabled(false);
				personT.setRowSelectionAllowed(false);
			}
			
		});
		
		editCancelB = new JButton("Cancel");
		editCancelB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				editCancelB.setEnabled(false);
				editOkB.setEnabled(false);
				addB.setEnabled(true);
				editB.setEnabled(true);
				deleteB.setEnabled(true);
				personT.setRowSelectionAllowed(true);
				
			}
			
		});
		editCancelB.setEnabled(false);
		
		editOkB = new JButton("OK");
		editOkB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sampleConnector.Update("Esajur.Client", updatePicker.getSelectedItem().toString(), updateTextField.getText(), "person_id", ""+personT.getValueAt(personT.getSelectedRow(), 0));
				
				editCancelB.setEnabled(false);
				editOkB.setEnabled(false);
				addB.setEnabled(true);
				editB.setEnabled(true);
				deleteB.setEnabled(true);
				personT.setRowSelectionAllowed(true);
				
				populateTable();
			}
			
		});
		editOkB.setEnabled(false);
		
		
		String[] updateValues = {"first_name",
								 "last_name",
								 "birth_date",
								 "phone_number",
								 "email_address",
								 "price_min",
								 "price_max",
								 "state",
								 "floor_min",
								 "floor_max",
								 "sqm_min",
								 "sqm_max"
				
		};
		updatePicker = new JComboBox<String>(updateValues);
		updatePicker.setSelectedIndex(0);
		
		updateTextField = new JTextField();
		
		
		
		
		deleteB = new JButton("Delete client");
		deleteB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sampleConnector.Delete("Esajur.Client", "person_id", ""+personT.getValueAt(personT.getSelectedRow(),0));
				populateTable();
			}
			
		});
		this.add(priceMinL);
		this.add(priceMinTF,"growx,push,wrap");
		
		this.add(priceMaxL);
		this.add(priceMaxTF,"growx,push,wrap");
		
		this.add(stateL);
		this.add(stateCB,"growx,push,wrap");
		
		this.add(rentOrBuyL);
		this.add(rentOrBuyCB,"growx,push,wrap");
		
		this.add(floorMinL);
		this.add(floorMinTF,"growx,push,wrap");
		
		this.add(floorMaxL);
		this.add(floorMaxTF,"growx,push,wrap");
		
		this.add(sqmMinL);
		this.add(sqmMinTF,"growx,push,wrap");
		
		this.add(sqmMaxL);
		this.add(sqmMaxTF,"growx,push,wrap");
		
		this.add(addB,"wrap");
		this.add(editB);
		this.add(editCancelB,"split 2,al 100%");
		this.add(editOkB,"wrap");
		this.add(updatePicker,"growx,push");
		this.add(updateTextField,"growx,push,wrap");
		
		
		
		this.add(deleteB,"wrap");
		
		this.add(tableSP,"width 600:700:,pushx, growx,east");
		this.pack();
		
	}
	public void populateTable(){
		dtm.setRowCount(0);
		ResultSet rs = sampleConnector.Select("Esajur.Client", new String[]{"*"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{
						rs.getString(1), //id
						rs.getString(9), //first name
						rs.getString(10),//last name
						rs.getString(11),//birth date
						rs.getString(12),//number
						rs.getString(13)//email
						});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
