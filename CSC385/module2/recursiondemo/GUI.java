import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class GUI extends JFrame {
	
	RecursionTest rt;
	JLabel stringLabel;
	JLabel titleLabel;
	JLabel inputLabel;
	JTextField inputField;
	JButton reverseButton;
	JButton reverseLoopButton;
	JLabel resultLabel;
	JLabel fibSectionLabel;
	JLabel fibLabel;
	JTextField fibInput;
	JScrollPane fibTextPane;
	JTextArea fibTextArea;
	JButton fibRecursiveButton;
	JButton fibLinearButton;
	JButton dynFibRecursiveButton;
	JLabel otherLabel;
	JButton fractalButton;
	JButton rulerButton;
	JButton recursionStackButton;
	Container cp;
	Insets insets;
	
	public GUI() {
		
		cp = getContentPane();
		insets = cp.getInsets();
		
		setSize(640, 480);
		setTitle("Recursion Demo");
		
		cp.setLayout(null);
		
		
		titleLabel = new JLabel("Recursion Demo");
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
		
		stringLabel = new JLabel("Reverse String Example:");
		stringLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		inputLabel = new JLabel("Enter string:");
		inputLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		
		inputField = new JTextField();
		reverseButton = new JButton("Reverse");
		reverseLoopButton = new JButton("Reverse Loop");
		
		resultLabel = new JLabel("Results:");
		resultLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		
		fibSectionLabel = new JLabel("Fibonacci Example:");
		fibSectionLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		fibLabel = new JLabel("Compute which Fibonacci number?");
		fibLabel.setFont(new Font("Verdana", Font.BOLD, 12));
		
		fibInput = new JTextField();
		
		fibTextArea = new JTextArea();
		
		fibTextPane = new JScrollPane(fibTextArea);
		
		fibRecursiveButton = new JButton("Recursive Algorithm");
		fibLinearButton = new JButton("Linear Algorithm");
		dynFibRecursiveButton = new JButton("Dyn. Recursive Algorithm");
		
		otherLabel = new JLabel("Other Examples:");
		otherLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		fractalButton = new JButton("Fractal");
		rulerButton = new JButton("Ruler");
		recursionStackButton = new JButton("Stack Demo");
		
		titleLabel.setBounds(insets.left + 200, insets.top + 5, 300, 24);
		stringLabel.setBounds(insets.left + 10, insets.top + 50, 300, 25);
		inputLabel.setBounds(insets.left + 10, insets.top + 100, 90, 25);
		inputField.setBounds(insets.left + 100, insets.top + 95, 200, 25);
		reverseButton.setBounds(insets.left + 320, insets.top + 82, 90, 40);
		reverseLoopButton.setBounds(insets.left + 420, insets.top + 82, 120, 40);
		resultLabel.setBounds(insets.left + 10, insets.top + 140, 400, 25);
		fibSectionLabel.setBounds(insets.left + 10, insets.top + 190, 300, 25);
		fibLabel.setBounds(insets.left + 10, insets.top + 220, 250, 25);
		fibInput.setBounds(insets.left + 250, insets.top + 220, 100, 25);
		fibTextPane.setBounds(insets.left + 10, insets.top + 270, 350, 100);
		fibRecursiveButton.setBounds(insets.left + 400, insets.top + 220, 200, 40);
		fibLinearButton.setBounds(insets.left + 400, insets.top + 270, 200, 40);
		dynFibRecursiveButton.setBounds(insets.left + 400, insets.top + 320, 200, 40);
		otherLabel.setBounds(insets.left + 10, insets.top + 410, 170, 25);
		fractalButton.setBounds(insets.left + 190, insets.top + 395, 100, 40);
		rulerButton.setBounds(insets.left + 300, insets.top + 395, 100, 40);
		recursionStackButton.setBounds(insets.left + 410, insets.top + 395, 125, 40);
		
		
		reverseButton.addActionListener(new ButtonHandler());
		reverseLoopButton.addActionListener(new ButtonHandler());
		fibRecursiveButton.addActionListener(new ButtonHandler());
		fibLinearButton.addActionListener(new ButtonHandler());
		dynFibRecursiveButton.addActionListener(new ButtonHandler());
		fractalButton.addActionListener(new ButtonHandler());
		rulerButton.addActionListener(new ButtonHandler());
		recursionStackButton.addActionListener(new ButtonHandler());
		
		
		// allows the program to exit when the window is closed
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		
		
		cp.add(titleLabel);
		cp.add(stringLabel);
		cp.add(inputLabel);
		cp.add(inputField);
		cp.add(reverseButton);
		cp.add(reverseLoopButton);
		cp.add(resultLabel);
		cp.add(fibSectionLabel);
		cp.add(fibLabel);
		cp.add(fibInput);
		cp.add(fibTextPane);
		cp.add(fibRecursiveButton);
		cp.add(fibLinearButton);
		cp.add(dynFibRecursiveButton);
		cp.add(otherLabel);
		cp.add(fractalButton);
		cp.add(rulerButton);
		cp.add(recursionStackButton);
		
		
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


	public void registerModel(RecursionTest r) {
		rt = r;
	}


	private void showReverseString() {
		resultLabel.setText("Result:      " + rt.reverseString(inputField.getText()));
	}


	private void showReverseStringLoop() {
		resultLabel.setText("Result:      " + rt.reverseStringLoop(inputField.getText()));
	}
	

	private void showFibRecursive() {
		try {
			fibTextArea.append(rt.recursiveFibResults(Integer.parseInt(fibInput.getText())) + "\n\n");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid input!", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void showFibLinear() {
		try {
			fibTextArea.append(rt.linearFibResults(Integer.parseInt(fibInput.getText())) + "\n\n");
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Invalid input!", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	private void showDynFibRecursive() {
		try {
			fibTextArea.append(rt.dynamicRecursiveFibResults(Integer.parseInt(fibInput.getText())) + "\n\n");
		}
		catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Invalid input!", "", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	private void showFractal() {
		FractalStar f = new FractalStar(512);
	}


	private void showRuler() {
		Ruler r = new Ruler(511);
	}
	
	private void showStackDemo() {
		RecursionStackDemo rsd = new RecursionStackDemo();
	}


	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			
			if (b.getText().equals("Reverse"))
				showReverseString();
			else if (b.getText().equals("Reverse Loop"))
				showReverseStringLoop();
			else if (b.getText().equals("Recursive Algorithm"))
				showFibRecursive();
			else if (b.getText().equals("Linear Algorithm"))
				showFibLinear();
			else if (b.getText().equals("Dyn. Recursive Algorithm"))
				showDynFibRecursive();
			else if (b.getText().equals("Fractal"))
				showFractal();
			else if (b.getText().equals("Ruler"))
				showRuler();
			else if (b.getText().equals("Stack Demo"))
				showStackDemo();
		}
	}
}
