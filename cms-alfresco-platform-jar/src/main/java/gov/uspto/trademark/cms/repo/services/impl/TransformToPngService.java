package gov.uspto.trademark.cms.repo.services.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.content.filestore.FileContentWriter;
import org.alfresco.repo.content.transform.magick.ImageTransformationOptions;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.util.TempFileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Sanjay Tank {linkedin.com/in/sanjaytaunk} on 5/10/2016.
 */
@Component("transformToPngService")
public class TransformToPngService {

    /** The service registry. */
    @Autowired
    @Qualifier(value = "ServiceRegistry")
    protected ServiceRegistry serviceRegistry;

    /**
     * Instantiates a new web script helper.
     */
    private TransformToPngService() {

    }

    public ContentReader transformImageToPNG(String fileName, ContentReader contentReader) {
        Set<String> staticListOfMimetypeToBeTransformed = new HashSet<String>();
        staticListOfMimetypeToBeTransformed.add(MimetypeMap.MIMETYPE_IMAGE_TIFF);
        staticListOfMimetypeToBeTransformed.add(MimetypeMap.MIMETYPE_IMAGE_JPEG);
        return transformImageToPNG(fileName, contentReader, staticListOfMimetypeToBeTransformed);
    }

    public ContentReader transformImageToPNG(String fileName, ContentReader contentReader,
            Set<String> staticListOfMimetypeToBeTransformed) {
        String incomingMimetype = contentReader.getMimetype();
        ContentReader lclContentReader;
        boolean transformNeeded = checkIfTransformationNeeded(incomingMimetype, staticListOfMimetypeToBeTransformed);
        if (transformNeeded) {
            String fileExtension = serviceRegistry.getMimetypeService().getExtension(MimetypeMap.MIMETYPE_IMAGE_PNG);
            File targetFile = TempFileProvider.createTempFile(getClass().getSimpleName() + fileName, "." + fileExtension);
            ContentWriter targetWriter = new FileContentWriter(targetFile);
            targetWriter.setMimetype(MimetypeMap.MIMETYPE_IMAGE_PNG);
            serviceRegistry.getContentService().getImageTransformer().transform(contentReader.getReader(), targetWriter,
                    new ImageTransformationOptions());
            lclContentReader = targetWriter.getReader();
        } else {
            lclContentReader = contentReader;
        }
        return lclContentReader;
    }

    private static boolean checkIfTransformationNeeded(String incomingMimetype, Set<String> staticListOfMimetypeToBeTransformed) {
        boolean returnVal = false;
        for (String s : staticListOfMimetypeToBeTransformed) {
            if (s.equalsIgnoreCase(incomingMimetype)) {
                returnVal = true;
                break;
            }
        }
        return returnVal;
    }

}