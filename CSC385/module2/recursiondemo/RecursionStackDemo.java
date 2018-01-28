import java.awt.*;
import java.awt.Color.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class RecursionStackDemo extends JFrame {
	
	Container cp;
	Insets insets;
	JTextField inputField;
	JTextArea statField;
	int frameWidth = 640;
	int frameHeight = 480;
	int currentTop = frameHeight;
	int cellLeft = 10;
	int cellHeight = 20;
	int cellWidth = 225;
	int spacer = 3;
	ArrayList textFields = new ArrayList();
	Font boldFont;
	Font boldItalicFont;
	int[] calculatedNumbers;
	boolean pauseOn = true;
	
	public RecursionStackDemo() {
		
		boldFont = new Font("Verdana", Font.BOLD, 12);
		boldItalicFont = new Font("Verdana", Font.BOLD|Font.ITALIC, 12);
		
		cp = getContentPane();
		insets = cp.getInsets();
		
		setSize(frameWidth, frameHeight);
		setTitle("Recursion Demo for Calculating the nth Fibonacci Number Using a Stack");
		
		cp.setLayout(null);
		
		JLabel inputLabel = new JLabel("Enter value for n (1 - 10):");
		inputLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		
		inputField = new JTextField();
		inputField.setFont(new Font("Verdana", Font.BOLD, 12));
		
		JButton startButton = new JButton("Start");
		
		JLabel statLabel = new JLabel("Statistics:   n     times calc.");
		statLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		
		statField = new JTextArea();
		statField.setFont(new Font("Verdana", Font.BOLD, 12));
		
		JScrollPane statPane = new JScrollPane(statField);
		
		JButton togglePauseButton = new JButton("Pause On");
		
		inputLabel.setBounds(insets.left + 300, insets.top + 5, 210, 40);
		inputField.setBounds(insets.left + 525, insets.top + 10, 50, 25);
		startButton.setBounds(insets.left + 525, insets.top + 50, 100, 40);
		statLabel.setBounds(insets.left + 340, insets.top + 120, 250, 40);
		statPane.setBounds(insets.left + 425, insets.top + 170, 200, 150);
		togglePauseButton.setBounds(insets.left + 525, insets.top + 400, 100, 40);

		startButton.addActionListener(new ButtonHandler());
		togglePauseButton.addActionListener(new ButtonHandler());
	
		// allows the program to exit when the window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		try {
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "D'oh!!!", "Button 1", JOptionPane.ERROR_MESSAGE);
		}

		cp.add(inputLabel);
		cp.add(inputField);
		cp.add(startButton);
		cp.add(statLabel);
		cp.add(statPane);
		cp.add(togglePauseButton);

		cp.validate();
		repaint();
		setVisible(true);
	}
	
	
	public void drawActivationRecord(String topCellContents, String bottomCellContents) {
		JTextField newCell;
		
		if (pauseOn)
			JOptionPane.showMessageDialog(null, "Click to continue", "", JOptionPane.PLAIN_MESSAGE);
			
		newCell = drawCell(cellLeft, currentTop - 2 * spacer, boldFont, bottomCellContents);		
		cp.add(newCell);
		textFields.add(newCell);
		repaint();
		currentTop -= cellHeight;
			
		newCell = drawCell(cellLeft, currentTop - spacer, boldItalicFont, topCellContents);
		newCell.setForeground(Color.RED);
		cp.add(newCell);
		textFields.add(newCell);
		repaint();
		currentTop -= cellHeight;
	}
	
	
	private void removeActivationRecord() {
		if (pauseOn)
			JOptionPane.showMessageDialog(null, "Click to continue", "", JOptionPane.PLAIN_MESSAGE);
			
		cp.remove((JTextField)textFields.get(textFields.size() - 1));
		textFields.remove(textFields.size() - 1);
		cp.remove((JTextField)textFields.get(textFields.size() - 1));
		textFields.remove(textFields.size() - 1);
		currentTop = currentTop + 2 * (cellHeight);
	}
	
	
	private JTextField drawCell(int left, int bottom, Font f, String contents) {
		int height = 50;
		JTextField cell = null;
		
		if (left > 0 && (bottom - height) > 0) {
			cell = new JTextField(contents);
			cell.setFont(f);
			cell.setBounds(insets.left + left, insets.top + bottom - height - spacer, cellWidth, cellHeight);		
		}
		
		return cell;
	}
	
	
	private void showRecursionStackDemo() {
		try {
			int n = Integer.parseInt(inputField.getText());
			
			if (n < 0 || n > 40)
				throw new Exception("Invalid input!!!  Enter a number between 0 and 10");
			
			statField.setText("");
			initializeCalculatedNumbers(n);	
			long result = fib(n);
			JOptionPane.showMessageDialog(null, "Computation done: result = " + result, "resultDialog", JOptionPane.PLAIN_MESSAGE);
			displayStatistics();
		}
		catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "You must enter a number between 0 and 10!", "", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	
	private void displayStatistics() {
		for (int i = 0; i < calculatedNumbers.length; i++)
			statField.append("" + i + ":     " + calculatedNumbers[i] + "\n");
	}
	
	
	private void initializeCalculatedNumbers(int n) {
		calculatedNumbers = new int[n + 1];
		
		for (int i = 0; i <= n; i++)
			calculatedNumbers[i] = 0;
	}
	
	
	private long fib(int n) {
		long fib_nMinusOne;
		long fib_nMinusTwo;
		
		calculatedNumbers[n]++;
		
		if (!pauseOn)
			if (n <= 1)
				return n;
			else {
				fib_nMinusOne = fib(n - 1);
				fib_nMinusTwo = fib(n - 2);
				return fib_nMinusOne + fib_nMinusTwo;
			}
		else
			if (n <= 1) {
				drawActivationRecord("n = " + n, "returning " + n);
				removeActivationRecord();
				return n;	
			}
			else {
				drawActivationRecord("n = " + n, "Result = fib(n - 1) + fib(n - 2)");
				fib_nMinusOne = fib(n - 1);
				removeActivationRecord();
				drawActivationRecord("n = " + n, "Result = " + fib_nMinusOne + " + fib(n - 2)");
				fib_nMinusTwo = fib(n - 2);
				removeActivationRecord();
				drawActivationRecord("n = " + n, "Result = " + (fib_nMinusOne + fib_nMinusTwo));
				removeActivationRecord();
				return fib_nMinusOne + fib_nMinusTwo;
			}
	}
	
	
	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if (b.getText().equals("Start"))
				showRecursionStackDemo();
			else if (b.getText().equals("Pause On")) {
				pauseOn = false;
				b.setText("Pause Off");
			}
			else if (b.getText().equals("Pause Off")) {
				pauseOn = true;
				b.setText("Pause On");
			}
		
		}
	}
}