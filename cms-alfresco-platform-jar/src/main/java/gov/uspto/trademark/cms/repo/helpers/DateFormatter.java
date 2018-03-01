package gov.uspto.trademark.cms.repo.helpers;

import java.util.Date;

import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by bgummadi on 6/9/2014.
 */
public class DateFormatter {

    /**
     * Returns the ISO date format for a given date.
     *
     * @param date
     *            the date
     * @return the UTC string
     */
    public static String getUTCString(Date date) {
        return ISODateTimeFormat.dateTime().withZone(DateTimeZone.UTC).print(date.getTime());
    }
}
