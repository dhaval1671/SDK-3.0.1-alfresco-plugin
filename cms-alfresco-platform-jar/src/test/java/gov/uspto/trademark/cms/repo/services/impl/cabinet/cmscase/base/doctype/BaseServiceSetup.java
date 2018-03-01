package gov.uspto.trademark.cms.repo.services.impl.cabinet.cmscase.base.doctype;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.alfresco.repo.model.Repository;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.CopyService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import gov.uspto.trademark.cms.repo.helpers.CaseNodeCreator;
import gov.uspto.trademark.cms.repo.nodelocator.CaseNodeLocator;
import gov.uspto.trademark.cms.repo.nodelocator.CmsNodeLocator;
import gov.uspto.trademark.cms.repo.services.CaseService;
import gov.uspto.trademark.cms.repo.services.CmsNodeCreator;
import gov.uspto.trademark.cms.repo.services.VersionService;

public class BaseServiceSetup {

    @Mock
    private ServiceRegistry serviceRegistry = mock(ServiceRegistry.class);
    @Mock
    private ContentReader reader;
    @Mock
    protected ContentService contentService = mock(ContentService.class);
    @Mock
    protected NodeService nodeService = mock(NodeService.class);
    @Mock
    private BehaviourFilter behaviourFilter = mock(BehaviourFilter.class);
    @Mock
    protected TransactionService transactionService = mock(TransactionService.class);
    @Mock
    protected CopyService copyService = mock(CopyService.class);
    @Mock
    protected CmsNodeLocator cmsNodeLocator = mock(CaseNodeLocator.class);
    @Mock
    protected CmsNodeCreator cmsNodeCreator = mock(CaseNodeCreator.class);
    @Mock
    private CaseService caseService = mock(CaseService.class);
    @Mock
    private Repository repositoryHelper = mock(Repository.class);
    @Mock
    private VersionService versionService = mock(VersionService.class);
    @Mock
    protected FileFolderService fileFolderService = mock(FileFolderService.class);    
  
    
    protected static final NodeRef companyHomeNode = new NodeRef("workspace://companyHome/");

    @Before
    public void setUp() throws Exception {
        mockServiceRegistry();
        mockNodeService();
        Repository repoHelper = mock(Repository.class);
        when(repoHelper.getCompanyHome()).thenReturn(companyHomeNode);
    }

    private void mockServiceRegistry() {
        when(serviceRegistry.getNodeService()).thenReturn(nodeService);
        when(serviceRegistry.getContentService()).thenReturn(contentService);
        when(serviceRegistry.getCopyService()).thenReturn(copyService);
        when(serviceRegistry.getFileFolderService()).thenReturn(fileFolderService); 
    }

    private void mockNodeService() {
        when(nodeService.exists(companyHomeNode)).thenReturn(true);
        when(nodeService.getType((NodeRef) any()))
                .thenReturn(QName.createQName("http://www.uspto.gov/cms/model/content/1.0", "caseFolder"));
    }

    protected MultimediaService getMultimediaServiceInstance()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<MultimediaService> constructor = MultimediaService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        MultimediaService ms = spy(constructor.newInstance());
        ms.serviceRegistry = this.serviceRegistry;
        ms.transactionService = this.transactionService;
        ms.cmsNodeCreator = this.cmsNodeCreator;
        ms.cmsNodeLocator = this.cmsNodeLocator;
        setServices(MultimediaService.class, ms, "behaviourFilter", this.behaviourFilter, false);
        return ms;
    }

    protected EvidenceServiceBase getEvidenceServiceBaseInstance() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, SecurityException {
        EvidenceServiceBase es = Mockito.spy(new EvidenceServiceBase());
        es.cmsNodeLocator = this.cmsNodeLocator;
        setServices(EvidenceServiceBase.class, es, "serviceRegistry", this.serviceRegistry, true);
        setServices(EvidenceServiceBase.class, es, "transactionService", this.transactionService, true);
        setServices(EvidenceServiceBase.class, es, "caseService", this.caseService, true);
        setServices(EvidenceServiceBase.class, es, "repositoryHelper", this.repositoryHelper, true);
        setServices(EvidenceServiceBase.class, es, "versionService", this.versionService, true);
        return es;
    }

    private void setServices(Class<?> baseServiceClass, Object es, String name, Object serviceRegistry2, boolean setOnParent) throws IllegalAccessException {
        Field fieldOne = null;
        try {
            if(setOnParent){
                fieldOne = baseServiceClass.getSuperclass().getDeclaredField(name);
            }else{
                fieldOne = baseServiceClass.getDeclaredField(name);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        fieldOne.setAccessible(true);
        fieldOne.set(es, serviceRegistry2);
    }    
    
}
