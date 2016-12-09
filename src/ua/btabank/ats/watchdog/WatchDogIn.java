package ua.btabank.ats.watchdog;

import java.io.IOException;
import java.nio.file.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.TimerTask;


public class WatchDogIn {

    public static HashMap<Integer, String> inFiles = new HashMap<Integer, String>();

    public static void sentFiles() {
        Path pathIn = Paths.get("I:\\INFO\\GNA\\IN\\");

        WatchService watchService = null;

        java.util.Timer t = new java.util.Timer();


        int timeInterval = 30000;
        HeartBeatTask tt = new HeartBeatTask(timeInterval);

        t.schedule(tt, 0, timeInterval);



        try {

            watchService = pathIn.getFileSystem().newWatchService();

           pathIn.register(watchService,
                    StandardWatchEventKinds.OVERFLOW,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);

        }  catch (NoSuchFileException e) {
            String message = "Сетевой диск не доступен. Обратитесь к системному администратору";
            WatchDog.logMessages.put(WatchDog.logMessages.size()+1, e.toString());
            WatchDog.logMessages.put(WatchDog.logMessages.size()+1, message);
            e.printStackTrace();

        }  catch (IOException e1){
            e1.printStackTrace();
        }

        // Бесконечный цикл
        for (; ; ) {
            WatchKey key = null;
            try {
                key = watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            t.cancel();
            tt.cancel();

            t = new java.util.Timer();

            tt = new HeartBeatTask(timeInterval);
            t.schedule(tt, 0, timeInterval);



            // Итерации для каждого события
            for (WatchEvent event : key.pollEvents()) {

                switch (event.kind().name()) {
                    case "OVERFLOW":
                        System.out.println("We lost some events");
                        break;
                    case "ENTRY_CREATE":

                        try {

                            arrayFilesDefinition(String.valueOf(event.context()));

                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            break;
                        }

//                   case "ENTRY_MODIFY":

                    case "ENTRY_DELETE":
                        try {
                            delFile(String.valueOf(event.context()));

                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            break;
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            break;
                        }
                }
            }
            // Сброс ключа важен для получения последующих уведомлений
            key.reset();
        }
    }


    public static void logFile(Date date, String s1, String objDelete){
        File logPath = new File("I:\\INFO\\GNA\\ARCH\\");
        File folder = new File(String.valueOf(logPath).concat("\\").concat(logNameFile(date)));

        if (!(folder.exists()) || !(folder.isDirectory())) {

            new File("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date))).mkdirs();
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat(logNameFile(date)).concat(".txt"), s1);
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat("objDeleteLog.txt"), objDelete);
            WatchDog.logMessages.put(WatchDog.logMessages.size()+1, s1);
        }
        else{

            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat(logNameFile(date)).concat(".txt"), s1);
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat("objDeleteLog.txt"), objDelete);
            WatchDog.logMessages.put(WatchDog.logMessages.size()+1, s1);
        }


    }





    public static void arrayFilesDefinition(String s) throws ArrayIndexOutOfBoundsException {
        Date date = new Date();

        if (s.contains("@F1") || s.contains("@P1")) {

            WatchDog.NewFileNameDate file = new WatchDog.NewFileNameDate(s, new Date());
            char f[] = new char[9];
            int j = 0;
            for (int i = 19; i < 28; i++) {
                f[j] = file.fileName.charAt(i);
                j++;
            }
            if (!WatchDog.f0Files.isEmpty()){
                for (WatchDog.ArrayFiles x : WatchDog.f0Files) {
                    if (x.name0.contains(String.copyValueOf(f))) {
                        x.name1 = file.fileName;
                        x.date1 = file.dateOfFile;
                        String s1 = "File " + x.name1 + " is received " + x.date1;
                        WatchDog.logFile(date, s1);
                    }
                }
            }
            else
            {
                String s1 = "File " + s + " is received " + date;
                WatchDog.logFile(date, s1);
            }

            file = null;

        }

        if (s.contains("@F2") || s.contains("@P2")) {

            WatchDog.NewFileNameDate file = new WatchDog.NewFileNameDate(s, new Date());
            char f[] = new char[9];
            int j = 0;
            for (int i = 19; i < 28; i++) {
                f[j] = file.fileName.charAt(i);
                j++;
            }
            if (!WatchDog.f0Files.isEmpty()) {
                for (WatchDog.ArrayFiles x : WatchDog.f0Files) {
                    if (x.name0.contains(String.copyValueOf(f))) {
                        x.name2 = file.fileName;
                        x.date2 = file.dateOfFile;
                        String s1 = "File " + x.name2 + " is received " + x.date2;
                        WatchDog.logFile(date, s1);
                    }
                }
            }
            else {
                String s1 = "File " + s + " is received " + date;
                WatchDog.logFile(date, s1);
            }
            file = null;
        }

        if (s.contains("@R0")) {
            WatchDog.NewFileNameDate file = new WatchDog.NewFileNameDate(s, new Date());
            char f[] = new char[9];
            int j = 0;
            for (int i = 19; i < 28; i++) {
                f[j] = file.fileName.charAt(i);
                j++;
            }
            if (!WatchDog.f0Files.isEmpty()) {
                for (WatchDog.ArrayFiles x : WatchDog.f0Files) {
                    if (x.name0.contains(String.copyValueOf(f))) {
                        x.nameR0 = file.fileName;
                        x.dateR0 = file.dateOfFile;
                        String s1 = "File " + x.nameR0 + " is received " + x.dateR0;
                        WatchDog.logFile(date, s1);
                    }
                }
            }
            else {
                String s1 = "File " + s + " is received " + date;
                WatchDog.logFile(date, s1);
            }
            file = null;
            }
        }


    public static void delFile(String s) throws NullPointerException {
        String objWasDelete = "";


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        if (s.contains("@F1") || s.contains("@P1")) {

            if (!WatchDog.f0Files.isEmpty()) {
                for (WatchDog.ArrayFiles x : WatchDog.f0Files) {
                    if (s.equals(x.name1)) {
                        x.delDate1 = date;
                        String s1 = "File " + s
                                + " is processed!" + " " + date;
                        WatchDog.logFile(date, s1);
                    }
                }
            } else {
                String s1 = "File " + s
                        + " is processed!" + " " + date;
                WatchDog.logFile(date, s1);
            }

        }
        if (s.contains("@F2") || s.contains("@P2")) {

            if (!WatchDog.f0Files.isEmpty()) {


                for (WatchDog.ArrayFiles x : WatchDog.f0Files) {
                    if (s.equals(x.name2)) {
                        x.delDate2 = date;


                        if (x.act) {
                            String s1 = "File " + s
                                    + " is processed!" + " " + date;
                            WatchDog.logFile(date, s1);
                        }


                        if (!x.act) {
                            String objDelete = x.name0 + " " + format.format(x.date0) + " " + format.format(x.delDate0)
                                    + " " + x.name1 + " " + format.format(x.date1) + " " + format.format(x.delDate1)
                                    + " " + x.name2 + " " + format.format(x.date2) + " " + format.format(x.delDate2);
                            objWasDelete = objDelete;

                            String s1 = "File " + s
                                    + " is processed!" + " " + date;

                            //WatchDog.logFile(date, s1);
                            logFile(date, s1, objWasDelete);
                        }
                    }
                }
            } else {
                String s1 = "File " + s
                        + " is processed!" + " " + date;
                WatchDog.logFile(date, s1);

            }

            objDelete(s);
        }
        if (s.contains("@R0")) {

            if (!WatchDog.f0Files.isEmpty()) {
                for (WatchDog.ArrayFiles x : WatchDog.f0Files) {
                    if (s.equals(x.nameR0)) {
                        x.delDateR0 = date;
                        String s1 = "File " + s
                                + " is processed!" + " " + date;
                        WatchDog.logFile(date, s1);


                    }
                }
            } else {
                String s1 = "File " + s
                        + " is processed!" + " " + date;
                WatchDog.logFile(date, s1);
            }
        }
    }


    public static void objDelete(String s) {


        int count = 0;

        for (int i = 0; i < WatchDog.f0Files.size(); i++) {
            if ((s.equals(WatchDog.f0Files.get(i).nameR2) && WatchDog.f0Files.get(i).act) || (s.equals(WatchDog.f0Files.get(i).name2) && !WatchDog.f0Files.get(i).act)) {
                count = i;
                WatchDog.f0Files.remove(count);
            }
        }


    }


    public static void write(String fileName, String text) {
        File myFile = new File(fileName);
        try {
            PrintWriter writer =
                    new PrintWriter(new BufferedWriter(new FileWriter(myFile, true)));
            writer.println(text);
            writer.flush();
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public static String logNameFile(Date date) {
        SimpleDateFormat nameTxtFile = new SimpleDateFormat("yyyyMMdd");
        String name = nameTxtFile.format(date);
        //System.out.println(name);
        return name;
    }


    public static class HeartBeatTask extends TimerTask {


        private int timerInterval;


        public HeartBeatTask(int timeInterval) {

            this.timerInterval = timeInterval;

        }


        public void run() {

            File inFolder = new File("I:\\INFO\\GNA\\IN\\");
            inFolder.listFiles();


            inFiles.clear();
            for (File x : inFolder.listFiles()){
                if (x.getName().contains(".XML")) {
                    inFiles.put(inFiles.size() + 1, String.valueOf(x.getName()));
                }
            }
        }
    }
}
