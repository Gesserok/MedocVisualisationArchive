package ua.btabank.ats.gui;

/**
 * Created by Anton on 13.07.2016.
 */
public class GUI  extends Thread {


        public GUI(){
            super();
        }

        public void run(){
            new MedocForm("Medoc.exe");
        }





}
