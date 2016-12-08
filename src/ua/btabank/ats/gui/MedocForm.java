package ua.btabank.ats.gui;

import ua.btabank.ats.watchdog.WatchDog;
import ua.btabank.ats.watchdog.WatchDogIn;

import javax.swing.*;
import java.awt.*;



/**
 * Created by Anton on 13.07.2016.
 */
public class MedocForm extends JFrame{
    public MedocForm(String s) {
        JFrame mainFrame = new JFrame(s);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();


        mainFrame.setSize(new Dimension(dim.getSize()));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());


        JPanel btaPanel = new JPanel();
        btaPanel.setLayout(new BorderLayout());
        PicturePanel picturePanel = new PicturePanel();
        picturePanel.init();


        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        BookPanel bookPanel = new BookPanel(WatchDog.f0Files);
        bookPanel.init();



        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        LogPanel logMessagePanel = new LogPanel(WatchDog.logMessages);
        logMessagePanel.init();


        JPanel filePanel = new JPanel();
        filePanel.setLayout(new BorderLayout());
        FilePanel fileMessagePanel = new FilePanel(WatchDog.outFiles);
        fileMessagePanel.init();


        JPanel fileInPanel = new JPanel();
        fileInPanel.setLayout(new BorderLayout());
        FileInPanel fileInMessagePanel = new FileInPanel(WatchDogIn.inFiles);
        fileInMessagePanel.init();


        filePanel.add(fileMessagePanel, BorderLayout.CENTER);
        fileInPanel.add(fileInMessagePanel, BorderLayout.CENTER);
        logPanel.add(logMessagePanel, BorderLayout.CENTER);
        tablePanel.add(bookPanel, BorderLayout.CENTER);




        mainFrame.add(tablePanel, BorderLayout.NORTH);
        mainFrame.add(filePanel, BorderLayout.WEST);
        mainFrame.add(fileInPanel, BorderLayout.EAST);
        mainFrame.add(logPanel, BorderLayout.SOUTH);
        mainFrame.add(picturePanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }


}
