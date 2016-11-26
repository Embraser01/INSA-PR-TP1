package client.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by marca on 26/11/2016.
 */
public class MyListCellRenderer extends JLabel implements ListCellRenderer<Object> {

    public MyListCellRenderer() {
        setFont(new Font("Serif", Font.PLAIN, 14));
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }
}
