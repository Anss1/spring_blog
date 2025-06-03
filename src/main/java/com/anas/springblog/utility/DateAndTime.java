package com.anas.springblog.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateAndTime {

    public static String now(){
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        return ldt.format(dtf);
    }
}
