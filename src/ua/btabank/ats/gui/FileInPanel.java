package ua.btabank.ats.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Anton on 15.07.2016.
 */
public class FileInPanel extends JPanel implements Runnable {

    public JScrollPane scrollPageTable;


    private HashMap<Integer, String> objects;
    private FileInTableModel btm = new FileInTableModel();
    private JTable tableOfObjects = new JTable(btm);


    public FileInPanel(HashMap<Integer, String> objects) {


        this.objects = objects;
        setLayout(new GridBagLayout());
        new Thread(this).start();


    }

    public void init() {

         tableOfObjects.getColumnModel().getColumn(1).setCellRenderer( new Renderer());

        scrollPageTable = new JScrollPane(tableOfObjects);
        scrollPageTable.setPreferredSize(new Dimension(300, 100));

        add(scrollPageTable, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0));
        tableOfObjects.getColumnModel().getColumn(0).setMinWidth(50);
        tableOfObjects.getColumnModel().getColumn(0).setMaxWidth(50);
        tableOfObjects.getTableHeader().setReorderingAllowed(false);

    }

    @Override
    public void run() {
        while (true) {
                btm.addData(objects);
                btm.fireTableDataChanged();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }




