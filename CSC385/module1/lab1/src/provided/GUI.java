package provided;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI extends JFrame {
	
	MaxContigSumFinder mcsf;
	JLabel titleLabel;
	JLabel inputLabel;
	JTextField inputField;
	JButton cubicButton;
	JButton quadraticButton;
	JButton linearButton;
	JLabel resultLabel;
	JScrollPane resultTextPane;
	JTextArea resultTextArea;
	Container cp;
	Insets insets;
	
	public GUI() {
		
		cp = getContentPane();
		insets = cp.getInsets();
		
		setSize(400, 300);
		setTitle("Maximum Contiguous Subsequence Sum Demo");
		
		cp.setLayout(null);
		
		
		titleLabel = new JLabel("MCS Sum Demo");
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		
		inputLabel = new JLabel("Enter a value for N:");
		inputLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		
		inputField = new JTextField();
		
		cubicButton = new JButton("Cubic");
		quadraticButton = new JButton("Quadratic");
		linearButton = new JButton("Linear");
		
		resultLabel = new JLabel("Results:");
		resultLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		
		resultTextArea = new JTextArea();
		
		resultTextPane = new JScrollPane(resultTextArea);
		
		titleLabel.setBounds(insets.left + 115, insets.top + 5, 300, 20);
		inputLabel.setBounds(insets.left + 10, insets.top + 50, 150, 20);
		inputField.setBounds(insets.left + 145, insets.top + 45, 50, 25);
		cubicButton.setBounds(insets.left + 10, insets.top + 80, 100, 25);
		quadraticButton.setBounds(insets.left + 120, insets.top + 80, 100, 25);
		linearButton.setBounds(insets.left + 230, insets.top + 80, 100, 25);
		resultLabel.setBounds(insets.left + 10, insets.top + 130, 100, 25);
		resultTextPane.setBounds(insets.left + 10, insets.top + 155, 350, 100);
		
		cubicButton.addActionListener(new ButtonHandler());
		quadraticButton.addActionListener(new ButtonHandler());
		linearButton.addActionListener(new ButtonHandler());
		
		// allows the program to exit when the window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
				
		cp.add(titleLabel);
		cp.add(inputLabel);
		cp.add(inputField);
		cp.add(cubicButton);
		cp.add(quadraticButton);
		cp.add(linearButton);
		cp.add(resultLabel);
		cp.add(resultTextPane);
		
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
			//pack();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "D'oh!!!", "Button 1", JOptionPane.ERROR_MESSAGE);
		}
		
		cp.validate();
		repaint();
		setVisible(true);
	}


	public void registerModel(MaxContigSumFinder model) {
		mcsf = model;
	}
	
	
	private void showResults(String algorithm)
	{
		try
		{
			int size = Integer.parseInt(inputField.getText());
			if (size < 1)
				throw new Exception();
			else
			{
				mcsf.init(size);
				resultTextArea.append("Running " + algorithm + " algorithm, N = " + size + "...\n");
				
				if (algorithm.equals("cubic"))
					resultTextArea.append(mcsf.cubicAlgorithmResults());
				else if (algorithm.equals("quadratic"))
					resultTextArea.append(mcsf.quadraticAlgorithmResults());
				else
					resultTextArea.append(mcsf.linearAlgorithmResults());
				
				resultTextArea.append("\n\n");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Please enter an integer between 1 and " + Integer.MAX_VALUE, "Button 1", JOptionPane.ERROR_MESSAGE);
		}
	}


	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if (b.getText().equals("Cubic"))
				showResults("cubic");
			else if (b.getText().equals("Quadratic"))
				showResults("quadratic");
			else if (b.getText().equals("Linear"))
				showResults("linear");
		}
	}
}
