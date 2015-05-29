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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;


public class AddClientDialog extends AddPersonDialog {
	JLabel priceMinL, priceMaxL, stateL, floorMinL, floorMaxL, sqmMinL,sqmMaxL;
	JComboBox<String> stateCB;
	JFormattedTextField priceMinTF, priceMaxTF, floorMinTF, floorMaxTF,sqmMinTF, sqmMaxTF;
	JButton addB;
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
		priceMinTF = new JFormattedTextField(numberFormatter);
		
		priceMaxL = new JLabel("Maximum price:");
		priceMaxTF = new JFormattedTextField(numberFormatter);
		
		String []states = {"New","Aftermarket"};
		stateL = new JLabel("State:");
		stateCB = new JComboBox<String>(states);
		stateCB.setSelectedIndex(0);
		
		floorMinL = new JLabel("Minimum floor:");
		floorMinTF = new JFormattedTextField(numberFormatter);
		
		floorMaxL = new JLabel("Maximum floor:");
		floorMaxTF = new JFormattedTextField(numberFormatter);
		
		sqmMinL = new JLabel("Minimum SQM: ");
		sqmMinTF = new JFormattedTextField(numberFormatter);
		
		sqmMaxL = new JLabel("Maximum SQM: ");
		sqmMaxTF = new JFormattedTextField(numberFormatter);
		
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
				String table = "ESajur.Client";
				String []values = {
						""+i++,
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
				editCancelB.setEnabled(false);
				editOkB.setEnabled(false);
				addB.setEnabled(true);
				editB.setEnabled(true);
				deleteB.setEnabled(true);
				personT.setRowSelectionAllowed(true);
			}
			
		});
		editOkB.setEnabled(false);
		
		deleteB = new JButton("Delete client");
		deleteB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.add(priceMinL);
		this.add(priceMinTF,"growx,push,wrap");
		
		this.add(priceMaxL);
		this.add(priceMaxTF,"growx,push,wrap");
		
		this.add(stateL);
		this.add(stateCB,"growx,push,wrap");
		
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
