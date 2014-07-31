/*import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Single_source extends JApplet {
	private JTextField textField;
	private JTextField textField_1;
	String[] k1;
	String[] k2;

	*//**
	 * Create the applet.
	 *//*
	
	
	public Single_source() {
		getContentPane().setLayout(null);
		
		JLabel lblSingleSourceShortest = new JLabel("                              SINGLE SOURCE SHORTEST PATH");
		lblSingleSourceShortest.setBounds(37, 20, 309, 22);
		getContentPane().add(lblSingleSourceShortest);
		
		JLabel lblSource = new JLabel("Source :");
		lblSource.setBounds(55, 53, 98, 14);
		getContentPane().add(lblSource);
		
		JLabel lblDestination = new JLabel("Destination :");
		lblDestination.setBounds(185, 53, 107, 14);
		getContentPane().add(lblDestination);
		
		
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(k1));
		comboBox.setBounds(55, 112, 98, 20);
		getContentPane().add(comboBox);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(k2));
		comboBox_1.setBounds(185, 112, 107, 20);
		getContentPane().add(comboBox_1);
		
		final JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Dijkstra", "Floyd Warshall"}));
		comboBox_2.setBounds(302, 78, 98, 20);
		getContentPane().add(comboBox_2);
		
		JLabel lblAlgorithm = new JLabel("Algorithm :");
		lblAlgorithm.setBounds(300, 53, 61, 14);
		getContentPane().add(lblAlgorithm);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(55, 188, 327, 70);
		getContentPane().add(textArea);
		
		final JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(55, 148, 98, 29);
		getContentPane().add(textArea_1);
		
		JButton btnGetRoute = new JButton("Get Route");
		btnGetRoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				
			}
		});
		btnGetRoute.setBounds(182, 149, 89, 23);
		getContentPane().add(btnGetRoute);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			
				
			
			public void keyPressed(KeyEvent arg0) {
			}
		});
		textField.setBounds(55, 78, 96, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		textField_1.setBounds(185, 81, 107, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

	}
}
*/