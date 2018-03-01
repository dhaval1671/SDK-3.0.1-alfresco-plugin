package gov.uspto.trademark.cms.repo.webscripts;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.helpers.Formatter;

/**
 * The Class JodaDateFormatter.
 */
public class JodaDateFormatter implements Formatter<Date> {

    /** The Constant FORMATTER. */
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(TMConstants.DATETIME_FORMAT);

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.helpers.Formatter#format(java.lang.Object)
     */
    @Override
    public String format(Date date) {
        return new DateTime(date).toString(FORMATTER);
    }
}