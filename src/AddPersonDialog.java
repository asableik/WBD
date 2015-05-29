import java.text.ParseException;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class AddPersonDialog extends JDialog{
	
	JLabel firstNameL, lastNameL, phoneNumberL, birthDateL, emailAdressL;
	JTextField firstNameTF, lastNameTF ,emailAdressTF;
	JTextField phoneNumberTF;
	JScrollPane tableSP;
	JTable personT;
	DefaultTableModel dtm;	
	//JFormattedTextField phoneNumberTF;
	SqlDateModel model;
	Properties p;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	SampleConnector sampleConnector;
	
	
	
	
	public AddPersonDialog(SampleConnector sampleConnector){
		this.sampleConnector = sampleConnector;
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//this.setResizable(false);
		this.setLayout(new MigLayout());
	
		firstNameL = new JLabel("First Name: ");
		firstNameTF = new JTextField(20);
		
		lastNameL = new JLabel("Last Name: ");
		lastNameTF = new JTextField(20);
		
		birthDateL = new JLabel("Birth date: ");
		
		model = new SqlDateModel();
		p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		model.setDate(1980, 4, 20);
		model.setSelected(true);
		datePanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		
		MaskFormatter mf1 = null;
		try {
			mf1 = new MaskFormatter("###-###-###");
			mf1.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		phoneNumberL = new JLabel("Phone Number: ");
		phoneNumberTF = new JFormattedTextField(20);
		
		emailAdressL = new JLabel("Email Adress: ");
		emailAdressTF = new JTextField(20);
		
		this.add(firstNameL);
		this.add(firstNameTF,"growx,push,wrap");
		this.add(lastNameL);
		this.add(lastNameTF,"growx,push,wrap");
		this.add(birthDateL);
		this.add(datePicker,"growx,push,wrap");
		this.add(phoneNumberL);
		this.add(phoneNumberTF,"growx,push,wrap");
		this.add(emailAdressL);
		this.add(emailAdressTF,"growx,push,wrap");
	}//constructor
	

	
}
