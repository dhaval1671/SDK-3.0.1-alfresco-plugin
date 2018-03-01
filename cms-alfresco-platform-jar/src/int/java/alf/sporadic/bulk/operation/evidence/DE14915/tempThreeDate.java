package alf.sporadic.bulk.operation.evidence.DE14915;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class tempThreeDate {

    public static void main(String[] args) {

        String startDateString = "06/TwentySeven/2007";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
        Date startDate;
        try {
            startDate = df.parse(startDateString);
            String newDateString = df.format(startDate);
            System.out.println(newDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }    
        
    }

}
