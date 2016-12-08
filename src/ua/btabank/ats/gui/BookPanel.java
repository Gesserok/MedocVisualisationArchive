package ua.btabank.ats.gui;

import ua.btabank.ats.watchdog.WatchDog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class BookPanel extends JPanel implements Runnable {

    public JScrollPane scrollPageTable;


    private ArrayList<WatchDog.ArrayFiles> objects;
    private ObjectTableModel btm = new ObjectTableModel();
    private JTable tableOfObjects = new JTable(btm);


    public BookPanel(ArrayList<WatchDog.ArrayFiles> objects) {


        this.objects = objects;
        setLayout(new GridBagLayout());
        new Thread(this).start();


    }

    public void init() {

         tableOfObjects.getColumnModel().getColumn(1).setCellRenderer( new Renderer());

        scrollPageTable = new JScrollPane(tableOfObjects);
        scrollPageTable.setPreferredSize(new Dimension(2100, 300));

        add(scrollPageTable, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0));

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




