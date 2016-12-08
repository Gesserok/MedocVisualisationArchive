package ua.btabank.ats.gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

public class FileInTableModel extends AbstractTableModel {

    private int columnCount = 2;
    public ArrayList<String[]> dataArrayList;

    public FileInTableModel(){
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
            case 0: return "№";
            case 1: return "IN";

        }
        return"";
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataArrayList.get(rowIndex);
        return rows[columnIndex];
    }

    public void addData(String[] row){
        String[] rowTable = new String[getColumnCount()];
        rowTable = row;
        dataArrayList.add(rowTable);
    }

    public void addData(HashMap<Integer, String> objects){

        dataArrayList.clear();
        for (HashMap.Entry<Integer, String> pair : objects.entrySet()){

            String[] row = {String.valueOf(pair.getKey()), pair.getValue()};

            addData(row);
          }


    }

}

