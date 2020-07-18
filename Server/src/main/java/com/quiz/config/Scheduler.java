package com.quiz.config;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {
    private static final String directoryForScan =  "src" + File.separator + "main" + File.separator +"input"+File.separator+"questionsByUser";

    @Scheduled(fixedRate = 30 * DateUtils.MILLIS_PER_MINUTE)
    public void fixedRateSch() throws IOException, ParseException {
        System.out.println("Running scheduler");
        readAllFiles();
    }
    private void readAllFiles() throws IOException, ParseException {
        File directory = new File(directoryForScan);
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                BasicFileAttributes attr = Files.readAttributes(Paths.get(directoryForScan + File.separator + file.getName()), BasicFileAttributes.class);
                Date currentDateTime = convertCurrentDate();
                Date fileDateTime = convertDate(attr.creationTime());
                if(currentDateTime.compareTo(fileDateTime)>0){
                  long  diff = (currentDateTime.getTime() - fileDateTime.getTime())/(60 * 1000) % 60;
                    System.out.println("difference== "+diff);
                  if(diff > 40){
                      file.delete();
                  }
                }
            }
        }
    }

    private Date convertDate(FileTime fileDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       return sdf.parse( sdf.format(fileDate.toMillis()));
    }
    private Date convertCurrentDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
       return sdf.parse(sdf.format(new Date()));
    }
}