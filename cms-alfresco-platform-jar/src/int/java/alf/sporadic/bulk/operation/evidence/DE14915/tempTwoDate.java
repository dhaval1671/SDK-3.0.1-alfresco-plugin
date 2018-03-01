package alf.sporadic.bulk.operation.evidence.DE14915;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class tempTwoDate {

    public static void main(String[] args) {

        String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
        String newstr = "2014-04-23T13:42:24.962-04:00";        
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATETIME_FORMAT);
        DateTime dt = formatter.parseDateTime(newstr);        
        Calendar c = Calendar.getInstance();
        c.setTime(dt.toDate());
        Date d = c.getTime();
        System.out.println(new DateTime(d).toString(DATETIME_FORMAT));        
        
    }

}
