package alf.unit.services;


/**
 * Unit testing for EfileService.
 *
 * @author stank{linkedin.com/in/sanjaytaunk}
 */
public class _02_EfileServiceTest extends BaseTest {

    /**
     * Inits the setup.
     */
    /**  
     * @Title: initSetup  
     * @Description:     
     * @return void   
     * @throws  
     */ 
 /*   @BeforeClass
    public static void initSetup() {

        LOG.debug("### Executing "+ Thread.currentThread().getStackTrace()[1].getMethodName() +" ####");
        deleteCreatedFile();

        NodeRef trgtNodeRef = null;
        try{
            trgtNodeRef = efileService.getEfileFolderNodeRef(TRADEMARK_ID);
        }catch(CMSException.SerialNumberNotFoundException cesnnfe){
            //eat the exception, proceed further.
        }
        if(trgtNodeRef == null){
            createEfileFolder(TRADEMARK_ID);    
        }        

        NodeRef noticeNoderef = efileService.getEfileDocumentNodeRef(TRADEMARK_ID, EFILE_FILE_NAME, null, TradeMarkModel.EFILE_QNAME);
        if(noticeNoderef == null){
            createEfileInsideEfileFolder();
        }

    }

    *//**
     * Delete files.
     *//*
    *//**  
     * @Title: deleteFiles  
     * @Description:     
     * @return void   
     * @throws  
     *//* 
    @AfterClass
    public static void deleteFiles() {

       }    

    *//**
     * Delete created file.
     *//*
    *//**  
     * @Title: deleteCreatedFile  
     * @Description:     
     * @return void   
     * @throws  
     *//* 
    private static void deleteCreatedFile() {
        try {

            Set<QName> qnameSet = new HashSet<QName>();
            qnameSet.add(TradeMarkModel.EFILE_QNAME);
            NodeRef caseFolderNodeRef = efileService.getEfileFolderNodeRef(TRADEMARK_ID);
            List<ChildAssociationRef> cars = serviceRegistry.getNodeService().getChildAssocs(caseFolderNodeRef, qnameSet);
            for (ChildAssociationRef car : cars) {
                NodeRef efileNR = car.getChildRef();
                String noticeFileName = (String) nodeService.getProperty(efileNR, ContentModel.PROP_NAME);
                LOG.debug("Deleting " + noticeFileName + " from " + TRADEMARK_ID);
                nodeService.deleteNode(efileNR);
            }

        } catch (CMSException.SerialNumberNotFoundException e) {
            // Nothing to delete then.
        }
    }

    *//**
     * Creates the efile inside efile folder.
     *//*
    *//**  
     * @Title: createEfileInsideEfileFolder  
     * @Description:     
     * @return void   
     * @throws  
     *//* 
    private static void createEfileInsideEfileFolder() {
        FileInputStream fis = getEfileInputStream();
        Efile notice = getEfile(); 
        Map<QName, Serializable> repoMap = WebScriptHelper.generateAlfRepoPropsFrDTO(notice);
        NodeRef p = null;
        try {
            p = efileService.createEfile(TRADEMARK_ID, EFILE_FILE_NAME, fis, repoMap);
        } catch (Exception e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            }             
            LOG.error(e.getMessage(), e);
        }
        assertNotNull(p);
        LOG.debug("Creating " + EFILE_FILE_NAME + " in " + TRADEMARK_ID);
    }

    *//**
     * Gets the efile.
     *
     * @return the efile
     *//*
    *//**  
     * @Title: getEfile  
     * @Description:   
     * @return  
     * @return Efile   
     * @throws  
     *//* 
    private static Efile getEfile() {
        String jsonString = "{     \"customProperties\": {         \"eFileProperty\": \"Property Value\"     } }";
        Map<String, Serializable> properties = processJson(jsonString);        
        Efile efile = JacksonHelper.generateDTOFrIncomingClientJson(properties, Efile.class);
        return efile;
    }

    *//**
     * Gets the efile input stream.
     *
     * @return the efile input stream
     *//*
    *//**  
     * @Title: getEfileInputStream  
     * @Description:   
     * @return  
     * @return FileInputStream   
     * @throws  
     *//* 
    private static FileInputStream getEfileInputStream() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("src//test//resources//efile//1_Efile1.0.pdf");
        } catch (java.io.FileNotFoundException e) {
            if(LOG.isInfoEnabled()){
                LOG.info(e.getMessage(), e);
            } 
        }
        return fis;
    }

    *//**
     * Creates the efile folder.
     *
     * @param caseNumber the case number
     *//*
    *//**  
     * @Title: createEfileFolder  
     * @Description:   
     * @param caseNumber  
     * @return void   
     * @throws  
     *//* 
    private static void createEfileFolder(final String caseNumber) {
        RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
        RetryingTransactionCallback<NodeRef> callback = new RetryingTransactionCallback<NodeRef>() {
            public NodeRef execute() throws Throwable {
                String serialNumber = caseNumber;
                NodeRef nr = efileService.getEfileFolderNodeRef(serialNumber, true);
                return nr;
            }
        };
        NodeRef nr1 = txnHelper.doInTransaction(callback, false, true);
        LOG.debug("Creating Efile folder " + caseNumber + ": " + nr1);
        assertNotNull(nr1);
    }
*/
}
