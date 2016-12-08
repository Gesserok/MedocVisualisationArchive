package ua.btabank.ats.start;

import ua.btabank.ats.gui.GUI;
import ua.btabank.ats.watchdog.WatchDogInThread;
import ua.btabank.ats.watchdog.WatchDogThread;


/**
 * Created by Anton on 12.07.2016.
 */
public class Main {

    public static void main (String[] args){





        Thread t1 = new Thread(new WatchDogThread());
        t1.start();

        Thread t2 = new Thread(new WatchDogInThread());
        t2.start();

        Thread t3 = new Thread(new GUI());
        t3.start();








    }
}
