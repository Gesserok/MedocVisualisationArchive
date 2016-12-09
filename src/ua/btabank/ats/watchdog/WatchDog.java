package ua.btabank.ats.watchdog;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class WatchDog {

    public static HashMap<Integer, String> outFiles = new HashMap<Integer, String>();

    public static HashMap<Integer, String> logMessages = new HashMap<Integer, String>();
    public static GregorianCalendar lastEvent = new GregorianCalendar();


    public static ArrayList<ArrayFiles> f0Files = new ArrayList<ArrayFiles>();

    public static class ArrayFiles {
        public  boolean act;
        public  String name0;
        public  Date date0;
        public  Date delDate0;
        public  String name1;
        public  Date date1;
        public  Date delDate1;
        public  String name2;
        public  Date date2;
        public  Date delDate2;
        public  String nameR0;
        public  Date dateR0;
        public  Date delDateR0;
        public  String nameR2;
        public  Date dateR2;
        public  Date delDateR2;

        public ArrayFiles(String name, Date date) {
            this.name0 = name;
            this.date0 = date;
        }
    }

    public static class NewFileNameDate {
        String fileName;
        Date dateOfFile;

        public NewFileNameDate(String name, Date date) {
            this.fileName = name;
            this.dateOfFile = date;
        }
    }

    public static boolean readFileLineByLine(String sFile) {

        try {
            File file = new File(sFile);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                //  System.out.println(line);
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line.contains("<OPER_TYPE>1</OPER_TYPE>")) {
                    reader.close();
                    return true;
                }
                if (line.contains("<OPER_TYPE>3</OPER_TYPE>")) {
                    reader.close();
                    return false;
                }
            }
            reader.close();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void sentFiles() {
        Path pathOut = Paths.get("I:\\INFO\\GNA\\OUT\\");

        WatchService watchService = null;

        java.util.Timer t = new java.util.Timer();


        int timeInterval = 1000;
        HeartBeatTask tt = new HeartBeatTask(timeInterval);

        t.schedule(tt, 0, timeInterval);


        try {

            watchService = pathOut.getFileSystem().newWatchService();
            pathOut.register(watchService,
                    StandardWatchEventKinds.OVERFLOW,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE);


        } catch (NoSuchFileException e) {
            String message = "Сетевой диск не доступен. Обратитесь к системному администратору";
            logMessages.put(logMessages.size() + 1, e.toString());
            logMessages.put(logMessages.size() + 1, message);
            e.printStackTrace();


        } catch (IOException e1) {
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
                            lastEvent = new GregorianCalendar();
                         //   System.out.println(lastEvent.getTime());
                            break;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                            break;
                        }

//                   case "ENTRY_MODIFY":

                    case "ENTRY_DELETE":
                        try {

                            delFile(String.valueOf(event.context()));
                            lastEvent = new GregorianCalendar();
                            //System.out.println(lastEvent.getTime());

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

    public static void logFile(Date date, String s1){
        File logPath = new File("I:\\INFO\\GNA\\ARCH\\");
        File folder = new File(String.valueOf(logPath).concat("\\").concat(logNameFile(date)));

        if (!(folder.exists()) || !(folder.isDirectory())) {

            new File("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date))).mkdirs();
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat(logNameFile(date)).concat(".txt"), s1);
            logMessages.put(logMessages.size()+1, s1);

        }
        else{

            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat(logNameFile(date)).concat(".txt"), s1);
            logMessages.put(logMessages.size()+1, s1);
        }


    }

    public static void logFile(Date date, String s1, String objDelete){
        File logPath = new File("I:\\INFO\\GNA\\ARCH\\");
        File folder = new File(String.valueOf(logPath).concat("\\").concat(logNameFile(date)));

        if (!(folder.exists()) || !(folder.isDirectory())) {

            new File("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date))).mkdirs();
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat(logNameFile(date)).concat(".txt"), s1);
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat("objDeleteLog.txt"), objDelete);
            logMessages.put(logMessages.size()+1, s1);
            logMessages.put(logMessages.size()+1, objDelete);
        }
        else{

            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat(logNameFile(date)).concat(".txt"), s1);
            write("I:\\INFO\\GNA\\ARCH\\".concat(logNameFile(date)).concat("\\").concat("objDeleteLog.txt"), objDelete);
            logMessages.put(logMessages.size()+1, s1);
            logMessages.put(logMessages.size()+1, objDelete);
        }


    }





    public static void arrayFilesDefinition(String s) throws ArrayIndexOutOfBoundsException {
        Date date = new Date();
        if (s.contains("@F0") || s.contains("@P0")) {

            NewFileNameDate file = new NewFileNameDate(s, date);
            f0Files.add(new ArrayFiles(file.fileName, file.dateOfFile));
            f0Files.get(f0Files.size() - 1).act = readFileLineByLine("I:\\INFO\\GNA\\OUT\\".concat(s));
            String s1 = "File " + f0Files.get(f0Files.size() - 1).name0 + " is created " + f0Files.get(f0Files.size() - 1).date0;
            logFile(date, s1);

            file = null;



        }

        if (s.contains("@R2")) {
            NewFileNameDate file = new NewFileNameDate(s, new Date());
            char f[] = new char[9];
            int j = 0;
            for (int i = 19; i < 28; i++) {
                f[j] = file.fileName.charAt(i);
                j++;
            }

            if (!f0Files.isEmpty()){
                for (ArrayFiles x : f0Files) {
                    if (x.name0.contains(String.copyValueOf(f))) {
                        x.nameR2 = file.fileName;
                        x.dateR2 = file.dateOfFile;
                        String s1 = "File " + x.nameR2 + " is created " + x.dateR2;
                        logFile(date, s1);
                    }

                }
            }
            else {
                String s1 = "File " + s + " is created " + date;
                logFile(date, s1);
            }
            file = null;

        }
    }


    public static void delFile(String s) throws NullPointerException {
        String message = "";
        String objWasDelete = "";



        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        if (s.contains("@F0") || s.contains("@P0")) {
            for (ArrayFiles x : f0Files) {
                if (s.equals(x.name0)) {
                    x.delDate0 = date;
                    String s1 = "File " + s
                            + " is sent!" + " " + date;
                     //  System.out.println(s1);

                    logFile(date, s1);
                }
            }
        }


        if (s.contains("@R2")) {

            if (!f0Files.isEmpty()) {
                for (ArrayFiles x : f0Files) {
                    if (s.equals(x.nameR2)) {
                        x.delDateR2 = date;
                        String objDelete = x.name0 + " " + format.format(x.date0) + " " + format.format(x.delDate0)
                                + " " + x.name1 + " " + format.format(x.date1) + " " + format.format(x.delDate1)
                                + " " + x.name2 + " " + format.format(x.date2) + " " + format.format(x.delDate2)
                                + " " + x.nameR0 + " " + format.format(x.dateR0) + " " + format.format(x.delDateR0)
                                + " " + x.nameR2 + " " + format.format(x.dateR2) + " " + format.format(x.delDateR2);
                        objWasDelete = objDelete;
                        String s1 = "File " + s
                                + " is sent!" + " " + date;
                       // logFile(date, s1);
                        logFile(date, s1, objWasDelete);
                    }
                }
            }
            else {
                String s1 = "File " + s
                        + " is sent!" + " " + date;
                logFile(date, s1);
            }

            objDelete(s);
            }
    }


    public static void objDelete(String s) {


        int count = 0;

        for (int i = 0; i < f0Files.size(); i++) {
            if ((s.equals(f0Files.get(i).nameR2) && f0Files.get(i).act) || (s.equals(f0Files.get(i).name2) && !f0Files.get(i).act)) {
                count = i;
                f0Files.remove(count);
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

           Calendar calendar = Calendar.getInstance();

            if (calendar.get(calendar.DAY_OF_MONTH) != lastEvent.get(lastEvent.DAY_OF_MONTH)) {
                logMessages.clear();
            }


            File outFolder = new File("I:\\INFO\\GNA\\OUT\\");
            outFolder.listFiles();


            outFiles.clear();
            for (File x : outFolder.listFiles()){
                if (x.getName().contains(".XML")) {
                    outFiles.put(outFiles.size()+1, String.valueOf(x.getName()));
                }
            }
            /*for (File x : outFolder.listFiles()){

                outFiles.put(outFiles.size()+1, String.valueOf(x.getName()));

            }*/

        }


    }



}





