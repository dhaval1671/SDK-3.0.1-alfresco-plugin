package gov.uspto.trademark.cms.repo.action;

import java.util.Calendar;

import org.alfresco.repo.action.scheduled.CronScheduledQueryBasedTemplateActionDefinition;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bgummadi on 2/12/2015.
 */
public class DeleteFileActionDefinition extends CronScheduledQueryBasedTemplateActionDefinition {

    /** The Constant PLUS_ONE. */
    private static final int PLUS_ONE = +1;

    /** The Constant MINUS_TWO. */
    private static final int MINUS_TWO = -2;

    /** The Constant LOG. */
    private static final Logger lOGGER = LoggerFactory.getLogger(DeleteFileActionDefinition.class);

    /** The Constant SOLR_DATE_FORMAT. */
    public static final String SOLR_DATE_FORMAT = "yyyyMMdd";

    /** The custom query template. */
    private String customQueryTemplate = "+PATH:\"/app:company_home/cm:drive/cm:eFile//*\" +@cm\\:created:[#1* TO #2*]";

    /**
     * Get the template from which to build the query.
     * 
     * @return - the template for the query.
     */
    @Override
    public String getQueryTemplate() {
        Calendar now = Calendar.getInstance();

        // Get date object older than 2 years
        now.add(Calendar.YEAR, DeleteFileActionDefinition.MINUS_TWO);
        String years2Ago = DateFormatUtils.format(now.getTime(), String.valueOf(SOLR_DATE_FORMAT));

        // Get date object older than a year
        now.add(Calendar.YEAR, DeleteFileActionDefinition.PLUS_ONE);
        String years1Ago = DateFormatUtils.formatUTC(now.getTime(), String.valueOf(SOLR_DATE_FORMAT));

        customQueryTemplate = StringUtils.replace(customQueryTemplate, "#1", years2Ago);
        customQueryTemplate = StringUtils.replace(customQueryTemplate, "#2", years1Ago);

        lOGGER.debug(customQueryTemplate);

        return customQueryTemplate;
    }
}
