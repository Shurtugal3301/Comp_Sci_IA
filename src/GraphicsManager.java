import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GraphicsManager {

	static Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

	static JButton b1, b2, b3, b4, b5, b6, b7;
	static JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14, l15, l16, l17, l18, l19, l20, l21, l22;
	static JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18;
	static JTable jt;
	static JScrollPane jtp;
	@SuppressWarnings("rawtypes")
	static JComboBox c1, c2;
	static int people;

	/**
	 * Creates a new JButton with a specified message, location, size, and action
	 * 
	 * @param msg
	 *            Text of the JButton
	 * @param location
	 *            Location of the JButton
	 * @param width
	 *            Width of the JButton
	 * @param height
	 *            Height of the JButton
	 * @param actionListener
	 *            Action for the button to perform when pressed
	 * @return The JButton object created
	 */
	static JButton newButton(String msg, Point location, int width, int height, ActionListener actionListener) {

		JButton b = new JButton(msg);
		b.setBounds(location.x - width / 2, location.y - height / 2, width, height);
		b.addActionListener(actionListener);

		return b;

	}

	/**
	 * Creates a new JTextField with a specified message, location, size, and
	 * whether or not it can be edited
	 * 
	 * @param editable
	 *            Whether of not this text field can be edited
	 * @param msg
	 *            Text of the JTextField
	 * @param location
	 *            Location of the JTextField
	 * @param width
	 *            Width of the JTextField
	 * @param height
	 *            Height of the JTextField
	 * @return The JTextField object created
	 */
	static JTextField newTextField(boolean editable, String msg, Point location, int width, int height) {

		JTextField t = new JTextField(msg);
		t.setEnabled(editable);
		t.setBounds(location.x - width / 2, location.y - height / 2, width, height);

		return t;

	}

	/**
	 * Creates a new JTextField with a specified message, location, and size
	 * 
	 * @param msg
	 *            Text of the JLabel
	 * @param location
	 *            Location of the JLabel
	 * @param width
	 *            Width of the JLabel
	 * @param height
	 *            Height of the JLabel
	 * @return The JLabel object created
	 */
	static JLabel newLabel(String msg, Point location, int width, int height) {

		JLabel l = new JLabel(msg);
		l.setBounds(location.x - width / 2, location.y - height / 2, width, height);

		return l;

	}

	/**
	 * Creates a new full-screen JFrame
	 * 
	 * @return The JFrame object created
	 */
	public static JFrame newFrame() {

		JFrame window = new JFrame();

		window.setSize(screen.width, screen.height);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setUndecorated(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(new Color(250, 250, 250));
		window.setLayout(null);

		return window;

	}

}
