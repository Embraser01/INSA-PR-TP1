package client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Small class used for custom rendering of the messages in the message list
 *
 * @author Tristan Bourvon
 * @author Marc-Antoine FERNANDES
 * @version 1.0.0
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer<Object> {

    /**
     * Constructor which initializes the look&feel of messages
     */
    public MyListCellRenderer() {
        setFont(new Font("Serif", Font.PLAIN, 14));
    }

    /**
     * Used to render a message
     *
     * @param list Message list
     * @param value Value to be inserted in the list
     * @param index unused
     * @param isSelected unused
     * @param cellHasFocus unused
     * @return
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }
}
