package ua.btabank.ats.gui;

import ua.btabank.ats.watchdog.WatchDog;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


public class ObjectTableModel extends AbstractTableModel {

    private int columnCount = 16;
    public ArrayList<String[]> dataArrayList;

    public ObjectTableModel(){
        dataArrayList = new ArrayList<String[]>();
        for (int i = 0; i < dataArrayList.size(); i++) {
            dataArrayList.add(new String[getColumnCount()]);

        }
    }

    @Override
    public int getRowCount() { //Количество строк
        return dataArrayList.size();
    }



    @Override
    public String getColumnName(int columnIndex){
        switch (columnIndex) {
            case 0: return "Type";
            case 1: return "Name";
            case 2: return "Created";
            case 3: return "Sent";
            case 4: return "Name";
            case 5: return "Received";
            case 6: return "Processed";
            case 7: return "Name";
            case 8: return "Received";
            case 9: return "Processed";
            case 10: return "Name";
            case 11: return "Received";
            case 12: return "Processed";
            case 13: return "Name";
            case 14: return "Created";
            case 15: return "Sent";
          //  case 16: return "Time";
        }
        return"";
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) throws IndexOutOfBoundsException {
        String[] rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    public void addData(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        dataArrayList.add(rowTable);
    }

    public void addData(ArrayList<WatchDog.ArrayFiles> objects){


        dataArrayList.clear();
        for (WatchDog.ArrayFiles x : objects){
            String typeOfOperation;
            if (x.act){
                typeOfOperation = "Opened";
            }
            else {
                typeOfOperation = "Closed";
            }
            String[] row = { typeOfOperation, x.name0, String.valueOf(x.date0), String.valueOf(x.delDate0),
                                                    x.name1, String.valueOf(x.date1), String.valueOf(x.delDate1),
                                                    x.name2, String.valueOf(x.date2), String.valueOf(x.delDate2),
                                                    x.nameR0, String.valueOf(x.dateR0), String.valueOf(x.delDateR0),
                                                    x.nameR2, String.valueOf(x.dateR2), String.valueOf(x.delDateR2)};

            addData(row);
          }


    }

}

