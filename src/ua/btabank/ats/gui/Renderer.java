package ua.btabank.ats.gui;

import ua.btabank.ats.watchdog.WatchDog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Color;
/**
 * Created by Anton on 21.07.2016.
 */
public class Renderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value.equals("Opened")){
                cell.setBackground(Color.GREEN);
            }
            if (value.equals("Closed")){
                cell.setBackground(Color.WHITE);
            }

        return cell;
    }
}

