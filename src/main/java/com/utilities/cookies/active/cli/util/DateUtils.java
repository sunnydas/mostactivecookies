package com.utilities.cookies.active.cli.util;

import com.utilities.cookies.exception.AppExceptionCode;
import com.utilities.cookies.exception.AppProcessingException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat(DATE_PATTERN);

    public static boolean isOlder(String currentDay,
                            String inputDay) throws AppProcessingException {
        boolean isOlder = false;
        if(currentDay != null
                && inputDay != null) {
            try {
                Date curDate = DATE_FORMAT.parse(currentDay);
                Date inputDate = DATE_FORMAT.parse(inputDay);
                isOlder = curDate.before(inputDate);
            } catch (ParseException e) {
                throw new AppProcessingException(e, AppExceptionCode.
                        DATE_PARSING_ERR_FOUND);
            }
        }
        return isOlder;
    }

    public static boolean isValid(String date){
        boolean valid = true;
        try{
            Date dateObj = DATE_FORMAT.parse(date);
        }catch (ParseException e){
            valid = false;
        }
        return valid;
    }

}
