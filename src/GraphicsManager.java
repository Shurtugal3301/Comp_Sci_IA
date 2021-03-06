import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Graphics driver — manages the creation and tracking of JComponent objects and groups
 */
public class GraphicsManager {

    protected static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    protected static final Font ARIAL_9 = new Font("Arial", Font.PLAIN, 9);
    protected static final Font ARIAL_12 = new Font("Arial", Font.PLAIN, 12);
    protected static final Font ARIAL_15 = new Font("Arial", Font.PLAIN, 15);
    protected static final Font ARIAL_20 = new Font("Arial", Font.PLAIN, 20);
    protected static final Font ARIAL_30 = new Font("Arial", Font.PLAIN, 30);

    public static Map<String, JComponent> components = new TreeMap<>();

    private static Map<Integer, ArrayList<String>> groups = new TreeMap<>();

    private static JPanel currentPanel;

    private static boolean panelOn = false;
    private static boolean groupOn = false;
    private static int groupIdx = 0;

    /**
     * Creates a new JButton with a specified message, location, size, and action
     *
     * @param id             Id of the JButton created
     * @param msg            Text of the JButton
     * @param location       Location of the JButton
     * @param width          Width of the JButton
     * @param height         Height of the JButton
     * @param font           Font of the text of the JButton
     * @param actionListener Action for the button to perform when pressed
     * @return The JButton object created
     */
    protected static JButton newButton(String id, String msg, Point location, int width, int height, Font font, ActionListener actionListener) {

        if (components.get(id) != null)
            return null;

        JButton b = new JButton(msg);
        b.addActionListener(actionListener);
        newJComponent(b, id, location, width, height, font);

        return b;

    }

    /**
     * Creates a new JTextField with a specified message, location, size, and whether or not it can be edited
     *
     * @param id       Id of the JTextField created
     * @param editable Whether of not this text field can be edited
     * @param msg      Text of the JTextField
     * @param location Location of the JTextField
     * @param width    Width of the JTextField
     * @param height   Height of the JTextField
     * @param font     Font of the text of the TextField
     * @return The JTextField object created
     */
    protected static JTextField newTextField(String id, boolean editable, String msg, Point location, int width, int height, Font font) {

        if (components.get(id) != null)
            return null;

        JTextField t = new JTextField(msg);
        t.setEnabled(editable);
        newJComponent(t, id, location, width, height, font);

        return t;

    }

    /**
     * Creates a new JTextField with a specified message, location, and size
     *
     * @param id           Id of the JLabel created
     * @param msg          Text of the JLabel
     * @param location     Location of the JLabel
     * @param width        Width of the JLabel
     * @param height       Height of the JLabel
     * @param font         Font of the text of the JLabel
     * @param hozAlignment Horizontal alignment of the text in the JLabel
     * @return The JLabel object created
     */
    protected static JLabel newLabel(String id, String msg, Point location, int width, int height, Font font, int hozAlignment) {

        if (components.get(id) != null)
            return null;

        JLabel l = new JLabel(msg);
        l.setHorizontalAlignment(hozAlignment);
        newJComponent(l, id, location, width, height, font);

        return l;

    }

    /**
     * Adds the JComponent to the component reference map and sets up basic parameters
     *
     * @param component The component to be added
     * @param id        String identification variable of the component
     * @return The JComponent that was added
     */
    protected static JComponent newJComponent(JComponent component, String id) {

        if (groupOn) {

            groups.get(groupIdx).add(id);

        }

        components.put(id, component);

        Window.AddComponent(component);

        return component;

    }

    /**
     * Adds the JComponent to the component reference map and sets up basic parameters
     *
     * @param component The component to be added
     * @param id        String identification variable of the component
     * @param location  Location of the component
     * @param width     Width of the component
     * @param height    Height of the component
     * @param font      Font of the text within that component
     * @return The JComponent that was added
     */
    protected static JComponent newJComponent(JComponent component, String id, Point location, int width, int height, Font font) {

        if (groupOn) {

            groups.get(groupIdx).add(id);

        }

        component.setBounds(location.x - width / 2, location.y - height / 2, width, height);
        component.setFont(font);
        components.put(id, component);

        if (panelOn) {
            currentPanel.add(component);
            Window.DoRepaint();
        } else
            Window.AddComponent(component);

        return component;

    }

    /**
     * Sets whether a component is visible or not
     *
     * @param id   String id of the component
     * @param show Whether of not the component should be visible
     */
    protected static void showComponent(String id, boolean show) {

        components.get(id).setVisible(show);

    }

    /**
     * Removes a component from the component reference map
     *
     * @param id String id of the component to be removed
     */
    protected static void removeComponent(String id) {

        showComponent(id, false);
        components.remove(id);

    }

    /**
     * Starts the tracking for a group of components
     *
     * @return Group id of the current group being tracked
     */
    protected static int startGroup() {

        groupOn = true;

        groups.put(groupIdx, new ArrayList<>());

        return groupIdx;

    }

    /**
     * Stops the tracking of the current group
     */
    protected static void stopGroup() {

        groupOn = false;
        groupIdx++;

    }

    /**
     * Removed a component from a group
     *
     * @param id    Id of the component
     * @param group Group index of the component
     */
    protected static void removeFromGroup(String id, int group) {

        groups.get(group).remove(id);

    }

    /**
     * Sets the visibility for a group of components
     *
     * @param groupID Id of the group being changed
     * @param show    Whether of not the components should be visible
     */
    protected static void showGroup(int groupID, boolean show) {

        ArrayList<String> arr = groups.get(groupID);

        for (String s : arr)
            showComponent(s, show);

    }

    /**
     * Removes a group of components from the component reference map and group reference map
     *
     * @param groupID Id of the group being removed
     */
    protected static void removeGroup(int groupID) {

        ArrayList<String> arr = groups.get(groupID);

        for (String s : arr)
            removeComponent(s);

        groups.remove(groupID);

    }

    /**
     * Creates a JPanel and starts tracking added objects
     *
     * @param id       Id of the JPanel
     * @param location Location of the JPanel
     * @param width    Width of the JPanel
     * @param height   Height of the JPanel
     * @return The JPanel that was created and is currently being tracked
     */
    protected static JPanel startPanel(String id, Point location, int width, int height) {

        currentPanel = new JPanel();

        currentPanel.setLayout(null);
        newJComponent(currentPanel, id, location, width, height, ARIAL_15);
        panelOn = true;

        return currentPanel;

    }

    /**
     * Stops tracking the current JPanel
     */
    protected static void stopPanel() {

        panelOn = false;
        currentPanel = null;

    }

    /**
     * Creates a new full-screen JFrame
     *
     * @return The JFrame object created
     */
    public static JFrame newFrame() {

        JFrame window = new JFrame();

        window.setSize(SCREEN_SIZE.width, SCREEN_SIZE.height);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(new Color(250, 250, 250));
        window.setLayout(null);

        return window;

    }

}
