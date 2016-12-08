package ua.btabank.ats.watchdog;

/**
 * Created by Anton on 13.07.2016.
 */
public class WatchDogThread extends Thread{


        public WatchDogThread(){
            super();
        }

        public void run(){
                WatchDog.sentFiles();
        }
}


