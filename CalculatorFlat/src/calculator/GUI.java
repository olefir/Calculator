package calculator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 * @author Vladimir Olefir 2016
 *
 */
public class GUI extends JPanel {

	private static final long serialVersionUID = 1L;

	private JFrame formCalculator;
	private JTextField textField;

	public GUI() {
		initialize();
	}

	/**
	 * Initialize & run Calculator
	 *
	 */

	void initialize() {

		formCalculator = new JFrame();
		formCalculator.getContentPane().setForeground(Color.BLACK);
		formCalculator.getContentPane().setBackground(new Color(210, 180, 140));
		formCalculator.setTitle("Calculator Flat v." + serialVersionUID);
		formCalculator.setSize(281, 304);
		formCalculator.setResizable(false);
		formCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		formCalculator.getContentPane().setLayout(new FlowLayout());

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBackground(Color.WHITE);
		textField.setFont(new Font("Lucida Sans", Font.PLAIN, 36));
		textField.setEditable(false);
		textField.setColumns(8);
		textField.setText("0");
		formCalculator.getContentPane().add(textField);

		Action buttonAction = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			double lastNumber, newNumber = 0;

			boolean plus, multiply, divide, minus, comma, reset, nulll = false;
			String newChar;

			@Override
			public void actionPerformed(ActionEvent e) {
				// textField.replaceSelection(e.getActionCommand());
				// String s = e.getActionCommand();

				JButton source = (JButton) e.getSource();
				String sourceEvent = source.getText();

				/**
				 * Check event & make action
				 *
				 */

				if (sourceEvent == "1" || sourceEvent == "2" || sourceEvent == "3" || sourceEvent == "4"
						|| sourceEvent == "5" || sourceEvent == "6" || sourceEvent == "7" || sourceEvent == "8"
						|| sourceEvent == "9" || sourceEvent == "0") {
					newChar = sourceEvent;
					textFormat();
				}

				if (sourceEvent == "C") {
					textField.setText("0");
					lastNumber = newNumber = 0;
					comma = false;
					reset = false;
					minus = false;
					plus = false;
					multiply = false;
					divide = false;
				}

				if (sourceEvent == "," && comma == false) {
					newChar = ".";
					textFormat();
					comma = true;
				}

				if (sourceEvent == "-") {
					longCalculations();
					reset = true;
					minus = true;
					plus = false;
					multiply = false;
					divide = false;
					textField.setText(calculationsFormat());

				}

				if (sourceEvent == "+") {
					longCalculations();
					reset = true;
					plus = true;
					minus = false;
					multiply = false;
					divide = false;
					textField.setText(calculationsFormat());

				}
				if (sourceEvent == "*") {
					longCalculations();
					reset = true;
					multiply = true;
					plus = false;
					minus = false;
					divide = false;
					textField.setText(calculationsFormat());
				}
				if (sourceEvent == "/") {
					longCalculations();
					reset = true;
					divide = true;
					plus = false;
					multiply = false;
					minus = false;
					textField.setText(calculationsFormat());

				}
				if (sourceEvent == "=") {
					newNumber = Double.valueOf(textField.getText());
					lastNumber = result();
					textField.setText(calculationsFormat());
					lastNumber = newNumber = 0;
					reset = true;
				}
				
//				if(sourceEvent=="<"){
//					newNumber = Double.valueOf(textField.getText());
//					newChar=String.valueOf((int)newNumber/10);
//					textFormat();
//				}
			}

			/**
			 * make calculation
			 *
			 */

			public double result() {

				if (lastNumber != 0) {
					if (minus == true) {
						lastNumber = lastNumber - newNumber;
						minus = false;
					} else if (plus == true) {
						lastNumber = lastNumber + newNumber;
						plus = false;
					} else if (multiply == true) {
						lastNumber = lastNumber * newNumber;
						multiply = false;
					} else if (divide == true && newNumber != 0) {
						lastNumber = lastNumber / newNumber;
						divide = false;
					} else if (divide == true && newNumber == 0) {
						lastNumber = newNumber = 0;
						nulll = true;
					}
				} else
					lastNumber = newNumber;
				return lastNumber;
			}

			public void textFormat() {

				if (reset == true) {
					textField.setText("");
					reset = false;
					comma = false;
				} else if (Double.valueOf(textField.getText()) == 0 && comma == false) {
					textField.setText("");
				}
				if (textField.getText().length() < 12)
					textField.setText(textField.getText() + newChar);

			}

			public String calculationsFormat() {

				/**
				 * you can not divide by zero
				 */

				if (nulll == true) {
					nulll = false;
					reset = true;
					lastNumber = newNumber = 0;

					/**
					 * delete zero after comma
					 */

				} else if (lastNumber == (long) lastNumber)
					return String.format("%d", (long) lastNumber);
				else
					return String.format("%s", lastNumber);

				return "divide by zero!";

			}

			void longCalculations() {
				newNumber = Double.valueOf(textField.getText());
				if (minus == true || plus == true || multiply == true || divide == true) {
					lastNumber = result();
				} else {
					lastNumber = newNumber;
					newNumber = 0;
				}
			}
		};

		/**
		 * set buttons names and input map
		 */

		final String[] buttonsNames = { "7", "8", "9", "/", "C", "4", "5", "6", "*", "<", "1", "2", "3", "-", "+", "0",
				",", "=" };
		final char[] buttonsActionCode = { '7', '8', '9', 111, 27, '4', '5', '6', 106, 8, '1', '2', '3', 109, 107, '0',
				110, 10 };

		for (int num = 0; num < 18; ++num) {

			JButton button = new JButton(buttonsNames[num]);
			button.setBackground(new Color(222, 184, 135));
			button.setFont(new Font("Lucida Sans", Font.PLAIN, 20));

			int width;
			if (buttonsNames[num].equals("0") || buttonsNames[num].equals("=")) {
				width = 105;
			} else
				width = 50;

			button.setPreferredSize(new Dimension(width, 50));
			button.addActionListener(buttonAction);

			InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(KeyStroke.getKeyStroke(buttonsActionCode[num], 0), buttonsNames[num]);
			inputMap.put(KeyStroke.getKeyStroke("NUMPAD" + buttonsActionCode[num]), buttonsNames[num]);
			button.getActionMap().put(buttonsNames[num], buttonAction);

			formCalculator.getContentPane().add(button);

		}

		formCalculator.setVisible(true);
	}

}