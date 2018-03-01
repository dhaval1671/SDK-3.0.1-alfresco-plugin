package gov.uspto.trademark.cms.repo.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.alfresco.service.namespace.QName;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import gov.uspto.trademark.cms.repo.TmngCmsException;
import gov.uspto.trademark.cms.repo.constants.TMConstants;
import gov.uspto.trademark.cms.repo.model.AbstractBaseType;
import gov.uspto.trademark.cms.repo.model.aspects.EvidenceAspect;
import gov.uspto.trademark.cms.repo.model.aspects.MultimediaRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.PcmRelatedAspect;
import gov.uspto.trademark.cms.repo.model.aspects.mixin.EvidenceAspectMixIn;
import gov.uspto.trademark.cms.repo.model.aspects.mixin.MultimediaAspectMixIn;
import gov.uspto.trademark.cms.repo.model.aspects.mixin.PcmRelatedAspectMixIn;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Evidence;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Legacy;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.MarkDoc;
import gov.uspto.trademark.cms.repo.model.cabinet.cmscase.Specimen;
import gov.uspto.trademark.cms.repo.model.drive.efile.Efile;
import gov.uspto.trademark.cms.repo.model.mixin.BaseTypeMixIn;
import gov.uspto.trademark.cms.repo.model.mixin.EfileMixIn;
import gov.uspto.trademark.cms.repo.model.mixin.MarkDocMixIn;
import gov.uspto.trademark.cms.repo.model.mixin.MultimediaMixIn;
import gov.uspto.trademark.cms.repo.model.mixin.jsontodto.AbstractBaseTypeMixInJsonToDto;

/**
 * The Class JacksonHelper.
 */
public class JacksonHelper {

    /** The Constant LOG. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonHelper.class);

    /**
     * Instantiates a new jackson helper.
     */
    private JacksonHelper() {
    }

    /**
     * Generate client json fr dto.
     *
     * @param object
     *            the object
     * @return the byte[]
     */
    public static byte[] generateClientJsonFrDTO(Object object) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectMapper mapper = outGoingMapperDtoToClientJson();
        try {
            mapper.writeValue(out, object);
        } catch (IOException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return out.toByteArray();
    }

    /**
     * Generate dto fr alfresco repo props.
     *
     * @param <T>
     *            the generic type
     * @param repositoryProperties
     *            the repository properties
     * @param target
     *            the target
     * @return the t
     */
    public static <T> T generateDTOFrAlfrescoRepoProps(Map<String, Serializable> repositoryProperties, Class<T> target) {
        T t = null;
        try {
            ObjectMapper mapper = outGoingMapperAlfRepoToDto();
            t = mapper.readValue(mapper.writeValueAsBytes(repositoryProperties), target);
        } catch (UnsupportedEncodingException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        } catch (JsonMappingException e) {
            if (StringUtils.isNotBlank(e.getMessage())) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        e.getMessage() + " 'object instance' generation from 'Alfresco repo properties Map' failed ", e);
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        target.getName() + "'object instance' generation from 'Alfresco repo properties Map' failed ", e);
            }
        } catch (IOException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return t;
    }

    /**
     * Incoming mapper client json to dto.
     *
     * @return the object mapper
     */
    public static ObjectMapper incomingMapperClientJsonToDto() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getDeserializationConfig().addMixInAnnotations(AbstractBaseType.class, AbstractBaseTypeMixInJsonToDto.class);
        return mapper;
    }

    /**
     * Incoming mapper dto to alf repo.
     *
     * @return the object mapper
     */
    public static ObjectMapper incomingMapperDtoToAlfRepo() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
        mapper.getSerializationConfig().addMixInAnnotations(EvidenceAspect.class, EvidenceAspectMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(MarkDoc.class, MarkDocMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Evidence.class, MultimediaMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Legacy.class, MultimediaMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Specimen.class, MultimediaMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(MultimediaRelatedAspect.class, MultimediaAspectMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(AbstractBaseType.class, BaseTypeMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Efile.class, EfileMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(PcmRelatedAspect.class, PcmRelatedAspectMixIn.class);
        return mapper;
    }

    /**
     * Out going mapper alf repo to dto.
     *
     * @return the object mapper
     */
    public static ObjectMapper outGoingMapperAlfRepoToDto() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(TMConstants.DATETIME_FORMAT));
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.getDeserializationConfig().addMixInAnnotations(EvidenceAspect.class, EvidenceAspectMixIn.class);
        mapper.getDeserializationConfig().addMixInAnnotations(MarkDoc.class, MarkDocMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Evidence.class, MultimediaMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Legacy.class, MultimediaMixIn.class);
        mapper.getSerializationConfig().addMixInAnnotations(Specimen.class, MultimediaMixIn.class);
        mapper.getDeserializationConfig().addMixInAnnotations(MultimediaRelatedAspect.class, MultimediaAspectMixIn.class);
        mapper.getDeserializationConfig().addMixInAnnotations(AbstractBaseType.class, BaseTypeMixIn.class);
        mapper.getDeserializationConfig().addMixInAnnotations(Efile.class, EfileMixIn.class);
        mapper.getDeserializationConfig().addMixInAnnotations(PcmRelatedAspect.class, PcmRelatedAspectMixIn.class);
        return mapper;
    }

    /**
     * Out going mapper dto to client json.
     *
     * @return the object mapper
     */
    public static ObjectMapper outGoingMapperDtoToClientJson() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().addMixInAnnotations(AbstractBaseType.class, BaseTypeMixIn.ClientBaseTypeMixIn.class);
        mapper.setDateFormat(new SimpleDateFormat(TMConstants.DATETIME_FORMAT));
        mapper.setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL);
        return mapper;
    }

    /**
     * Generate dto fr incoming client json.
     *
     * @param <T>
     *            the generic type
     * @param incomingClientJsonproperties
     *            the incoming client jsonproperties
     * @param target
     *            the target
     * @return the t
     */
    public static <T> T generateDTOFrIncomingClientJson(Map<String, Serializable> incomingClientJsonproperties, Class<T> target) {
        T t = null;
        ObjectMapper mapper = incomingMapperClientJsonToDto();
        try {
            t = mapper.readValue(mapper.writeValueAsBytes(incomingClientJsonproperties), target);
        } catch (JsonParseException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        } catch (JsonMappingException e) {
            if (StringUtils.isNotBlank(e.getMessage())) {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } else {
                throw new TmngCmsException.CMSWebScriptException(HttpStatus.BAD_REQUEST,
                        "Incoming client json does not conform to " + target.getName(), e);
            }
        } catch (JsonGenerationException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        } catch (IOException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return t;
    }

    /**
     * Un marshall.
     *
     * @param <T>
     *            the generic type
     * @param jsonString
     *            the json string
     * @param targetClass
     *            the target class
     * @return the t
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static <T> T unMarshall(String jsonString, Class<T> targetClass) throws IOException {
        ObjectMapper mapper = incomingMapperClientJsonToDto();
        return mapper.readValue(jsonString, targetClass);
    }

    /**
     * Un marshall.
     *
     * @param <T>
     *            the generic type
     * @param inputStream
     *            the json string
     * @param targetClass
     *            the target class
     * @return the t
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static <T> T unMarshall(InputStream inputStream, Class<T> targetClass) throws IOException {
        ObjectMapper mapper = incomingMapperClientJsonToDto();
        return mapper.readValue(inputStream, targetClass);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * gov.uspto.trademark.cms.repo.services.BaseService#generateOutGoingDTO
     * (org.alfresco.service.cmr.repository.NodeRef, java.lang.Class)
     */
    public static <T extends AbstractBaseType> T generateOutGoingDTO(Map<QName, Serializable> repositoryProperties,
            Class<T> target) {
        Map<String, Serializable> convertedMap = WebScriptHelper.stringifyMap(repositoryProperties);
        T t = null;
        t = JacksonHelper.generateDTOFrAlfrescoRepoProps(convertedMap, target);
        return t;
    }
}