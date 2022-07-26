package com.example.news_app.component;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateFormatter {
    public Date stringToDate(String date) throws ParseException {
        Date date1=new SimpleDateFormat("dd.MM.yyyy").parse(date);
        return date1;
    }

}
