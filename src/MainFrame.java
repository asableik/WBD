import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	JComboBox<String> operationBox;
	SampleConnector sampleConnector;
	
	public MainFrame(SampleConnector sc){
	this.sampleConnector = sc;
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setLayout(new MigLayout());
	JMenuBar menuBar  = new JMenuBar();
	JMenu addMenu = new JMenu("Add...");
	JMenuItem addClientMI = new JMenuItem("Client");
	addClientMI.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new AddClientDialog(sampleConnector).setVisible(true);
			
		}
		
	});
	
	addMenu.add(addClientMI);
	menuBar.add(addMenu);
	this.add(menuBar,"pushx,growx,wrap");
	this.pack();
	
	}

}
