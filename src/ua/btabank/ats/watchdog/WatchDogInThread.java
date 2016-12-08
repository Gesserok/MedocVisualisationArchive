package ua.btabank.ats.watchdog;

/**
 * Created by Anton on 13.07.2016.
 */
public class WatchDogInThread extends Thread{


    public WatchDogInThread(){
        super();
    }

    public void run(){
        WatchDogIn.sentFiles();
    }
}


