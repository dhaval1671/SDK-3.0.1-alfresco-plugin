package gov.uspto.trademark.cms.repo.services.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.alfresco.repo.content.filestore.FileContentStore;
import org.alfresco.service.ServiceRegistry;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.webscripts.healthstatus.Detail;
import gov.uspto.trademark.cms.repo.webscripts.healthstatus.Status;

/**
 * Created by Sanjay Tank {linkedin.com/in/sanjaytaunk} on 08/18/2016.
 */
@Component("alfrescoHealthStatusService")
@DependsOn("defaultDataSource")
public class AlfrescoHealthStatusService {

    private static final String NO_ERROR_EXCEPTION_TO_REPORT = "";
    private static final String ALFRESCO_DB = "ALFRESCO_DB";
    private static final String ALFRESCO_MOUNT = "ALFRESCO_MOUNT";
    private static final String ALFRESCO_HEALTH_CHECK_TEMP_TXT = "alfrescoHealthCheckTemp.txt";
    private static final Logger log = LoggerFactory.getLogger(AlfrescoHealthStatusService.class);

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;

    @Autowired
    @Qualifier(value = "fileContentStore")
    protected FileContentStore fileContentStore;

    @Autowired
    @Qualifier(value = "defaultDataSource")
    protected BasicDataSource basicDataSource;

    /**
     * Instantiates a new web script helper.
     */
    private AlfrescoHealthStatusService() {

    }

    private void closeTempFiles(File file, FileWriter fw, BufferedWriter bw) {
        try {
            if (fw != null) {
                fw.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (IOException ignored) {
            if (file != null) {
                log.trace("Unable to close the file: " + file.getAbsolutePath(), ignored);
            }
        }
    }

    private File createFileAsNecessary() {
        String path = fileContentStore.getRootLocation();
        if (log.isDebugEnabled()) {
            log.debug("Alfresco Content Store Mount point: " + path);
            log.debug("Alfresco Content Store Mount point writable? :" + fileContentStore.isWriteSupported());
        }

        File file = new File(path + File.pathSeparator + ALFRESCO_HEALTH_CHECK_TEMP_TXT);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    log.error("Unable to create the file: " + file.getAbsolutePath());
                }
            } catch (IOException ioex) {
                log.error("Unable to create temp file: " + file.getAbsolutePath(), ioex);
                file = null;
            }
        }
        return file;
    }

    public Detail checkStatusOfAlfrescoMount() {
        Detail statusOfAlfMountPoint = new Detail();
        statusOfAlfMountPoint.setName(ALFRESCO_MOUNT);
        try {
            String content = "mount point health check test message";
            File file = createFileAsNecessary();
            if (file == null) {
                statusOfAlfMountPoint.setStatus(Status.RED.getStatus());
                statusOfAlfMountPoint.setDetails("Unable to create the temp file. Check logs.");
                return statusOfAlfMountPoint;
            }

            DateFormat df = new SimpleDateFormat(TMConstants.GMT_DATE_FORMAT_STRING);
            Date today = Calendar.getInstance().getTime();
            String reportDate = df.format(today);
            String dateToPrintToFile = reportDate;

            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);
                bw.write(dateToPrintToFile + " :: " + content);
                bw.flush();
            } finally {
                closeTempFiles(file, fw, bw);
                boolean deleted = file.delete();
                if (!deleted) {
                    log.warn("Unable to delete the file: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
            statusOfAlfMountPoint.setStatus(Status.RED.getStatus());
            statusOfAlfMountPoint.setDetails(e.getMessage());
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(e.getMessage(), e);
            }
            if (StringUtils.isBlank(statusOfAlfMountPoint.getStatus())) {
                statusOfAlfMountPoint.setStatus(Status.RED.getStatus());
                if (e.getCause() != null) {
                    statusOfAlfMountPoint.setDetails(e.getCause().getMessage());
                } else {
                    statusOfAlfMountPoint.setDetails(e.getMessage());
                }
            }
        }
        if (StringUtils.isBlank(statusOfAlfMountPoint.getStatus())) {
            statusOfAlfMountPoint.setStatus(Status.GREEN.getStatus());
            statusOfAlfMountPoint.setDetails(NO_ERROR_EXCEPTION_TO_REPORT);
        }
        return statusOfAlfMountPoint;
    }

    public Detail checkStatusOfAlfrescoDB() {
        Detail statusOfAlfDB = new Detail();
        statusOfAlfDB.setName(ALFRESCO_DB);
        Connection con = null;
        PreparedStatement preparedStmt = null;
        try {
            con = basicDataSource.getConnection();
            DatabaseMetaData meta = con.getMetaData();
            if (log.isDebugEnabled()) {
                log.debug("DB Product Name: " + meta.getDatabaseProductName() + "## DB Product Version: "
                        + meta.getDatabaseProductVersion());
            }
            String query = "SELECT 1 FROM ALF_NODE";
            preparedStmt = con.prepareStatement(query);
            preparedStmt.executeQuery();
        } catch (Exception e) {
            log.debug(e.getLocalizedMessage(), e);
            statusOfAlfDB.setStatus(Status.RED.getStatus());
            if (e.getCause() != null) {
                statusOfAlfDB.setDetails(e.getCause().getMessage());
            } else {
                statusOfAlfDB.setDetails(e.getMessage());
            }
        } finally {
            try {
                if (preparedStmt != null) {
                    preparedStmt.close();
                }
                if (con != null)
                    con.close();
            } catch (Exception e) {
                log.debug(e.getLocalizedMessage(), e);
                if (StringUtils.isBlank(statusOfAlfDB.getStatus())) {
                    statusOfAlfDB.setStatus(Status.RED.getStatus());
                    if (e.getCause() != null) {
                        statusOfAlfDB.setDetails(e.getCause().getMessage());
                    } else {
                        statusOfAlfDB.setDetails(e.getMessage());
                    }
                }
            }
        }
        if (StringUtils.isBlank(statusOfAlfDB.getStatus())) {
            statusOfAlfDB.setStatus(Status.GREEN.getStatus());
            statusOfAlfDB.setDetails(NO_ERROR_EXCEPTION_TO_REPORT);
        }
        return statusOfAlfDB;
    }

}