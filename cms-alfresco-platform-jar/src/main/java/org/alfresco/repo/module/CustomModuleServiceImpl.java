/*
 @Custom  Dhaval Joshi
 * 
 */
package org.alfresco.repo.module;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.admin.registry.RegistryKey;
import org.alfresco.repo.admin.registry.RegistryService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.tenant.TenantAdminService;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.module.ModuleDetails;
import org.alfresco.service.cmr.module.ModuleService;
import org.alfresco.service.descriptor.DescriptorService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.google.gdata.model.atom.Author;

/**
 * This component controls the execution of
 * {@link org.alfresco.repo.module.ModuleComponent module startup components}.
 * <p/>
 * All required startup executions are performed in a single transaction, so this
 * component guarantees that the module initialization is consistent.  Module components are
 * executed in dependency order <i>only</i>.  The version numbering is not to be used
 * for ordering purposes.
 * <p/>
 * Afterwards, execution details are persisted in the
 * {@link org.alfresco.repo.admin.registry.RegistryService service registry} to be used when the
 * server starts up again.
 *
 * @author Roy Wetherall
 * @author Derek Hulley
 * @since 2.0
 */
public class CustomModuleServiceImpl implements ApplicationContextAware, ModuleService
{
    /** Error messages **/
    private static final String ERR_UNABLE_TO_OPEN_MODULE_PROPETIES = "module.err.unable_to_open_module_properties";

    /** The classpath search path for module properties */
    private static final String MODULE_CONFIG_SEARCH_ALL = "classpath*:alfresco/module/*/module.properties";
    
    private static Log logger = LogFactory.getLog(ModuleServiceImpl.class);

    private ServiceRegistry serviceRegistry;
    private ModuleComponentHelper moduleComponentHelper;
    /** A cache of module details by module ID */
    private Map<String, ModuleDetails> moduleDetailsById;
    private RegistryService registryService;
    
    private String degradeModuleAllow;
    private String moduleList;

    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    
    /** Default constructor */
    public CustomModuleServiceImpl()
    {    	
        moduleComponentHelper = new ModuleComponentHelper();        
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
        this.moduleComponentHelper.setServiceRegistry(this.serviceRegistry);
        
    }

    public void setDescriptorService(DescriptorService descriptorService)
    {
        this.moduleComponentHelper.setDescriptorService(descriptorService);
    }
    

    /**
     * @param registryService the service used to persist component execution details.
     */
        
    public void setRegistryService(RegistryService registryService)
    {
    	this.registryService = registryService;
    	this.setModuleService();  //Set module service for the Module Component Helper
    	this.moduleComponentHelper.setRegistryService(this.registryService);
    }
    
    public void setTenantAdminService(TenantAdminService tenantAdminService)
    {
        this.moduleComponentHelper.setTenantAdminService(tenantAdminService);
    }
    
    /**
     * @throws UnsupportedOperationException This feature was never active and cannot be used (ALF-19207)
     */
    public void setApplyToTenants(boolean applyToTenants)
    {
        throw new UnsupportedOperationException("Applying modules to individual tenants is unsupported. See ALF-19207: MT module startup does not work");
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        setResolver(applicationContext);
    } 
    
    private synchronized void setResolver(ApplicationContext applicationContext)
    {
    	resolver = applicationContext;
    }

    /**
     * @see ModuleComponentHelper#registerComponent(ModuleComponent)
     */
    public void registerComponent(ModuleComponent component)
    {

    	
        this.moduleComponentHelper.registerComponent(component);
    }
    
    
    /**
     * {@inheritDoc}
     * 
     * @see ModuleComponentHelper#startModules()
     */
    public void startModules()
    {
    	boolean moduleDegrade = Boolean.parseBoolean(getDegradeModuleAllow());
    	
    	if(moduleDegrade)
    		removeOldEntryForCMSModule(getCustomModuleList());
    	
        moduleComponentHelper.startModules();
    }
    
    
    private String[] getCustomModuleList()
    {
    	String[] moduleArr = {"cms-alfresco-repo-amp","cms-alfresco-platform-jar"};
    	String moduleListStr = getModuleList();
    	if(StringUtils.isNotEmpty(moduleListStr))
    	{
    		return moduleListStr.split(",");
    	}
    	
    	return moduleArr;
    }
    
    
    
    private void setModuleService()
    {
    	this.moduleComponentHelper.setModuleService(this);
    }
    
    /**
     * {@link Author} Dhaval Joshi
     * Before starting Module Remove registered entry (Stored as node in file system) for the cms-alfresco-repo-amp. Remove module version information from the registry using RegistryService utility     * 
     */
    public void removeOldEntryForCMSModule(final String[] moduleArr)
    {
    	final RegistryService localRegistryService = this.registryService;
    	 AuthenticationUtil.runAs(new RunAsWork<Object>()
    	            {
    	                public Object doWork() throws Exception
    	                {
    	                	
    	                	for (String moduleStr : moduleArr) 
    	                	{
    	                		System.out.println("Module Name to remove ::: "+moduleStr);
    	                		RegistryKey moduleKey = new RegistryKey("http://www.alfresco.org/system/modules/1.0","modules",moduleStr, null);
        	                    //Remove module from registry
        	                    localRegistryService.delete(moduleKey);
								
							} 
    	                    
    	                    return null;
    	                }
    	            }, AuthenticationUtil.getSystemUserName());
    }

    /**
     * {@inheritDoc}
     * 
     * @see ModuleComponentHelper#shutdownModules()
     */
    public void shutdownModules()
    {
        moduleComponentHelper.shutdownModules();
    }
    
    /**
     * {@inheritDoc}
     */
    public ModuleDetails getModule(String moduleId)
    {
        cacheModuleDetails();
        // Get the details of the specific module
        ModuleDetails details = moduleDetailsById.get(moduleId);
        // Done
        return details;
    }

    /**
     * {@inheritDoc}
     */
    public List<ModuleDetails> getAllModules()
    {
        cacheModuleDetails();
        Collection<ModuleDetails> moduleDetails = moduleDetailsById.values();
        // Make a copy to avoid modification of cached data by clients (and to satisfy API)
        List<ModuleDetails> result = new ArrayList<ModuleDetails>(moduleDetails);
        // Done
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public List<ModuleDetails> getMissingModules()
    {
        cacheModuleDetails();
        
        // Get the IDs of all modules from the registry
        Collection<String> moduleIds = moduleComponentHelper.getRegistryModuleIDs();
        
        List<ModuleDetails> result = new ArrayList<ModuleDetails>();
        
        //Check for missing modules
        for (String moduleId : moduleIds)
        {
            ModuleDetails moduleDetails = getModule(moduleId);
            if (moduleDetails == null)
            {
                // Get the specifics of the missing module and add them to the list.
                ModuleVersionNumber currentVersion = moduleComponentHelper.getVersion(moduleId);
                
                ModuleDetails newModuleDetails = new ModuleDetailsImpl(moduleId, currentVersion, "", "");
                
                result.add(newModuleDetails);
            }
        }
        return result;
    }

    /**
     * Ensure that the {@link #moduleDetailsById module details} are populated.
     * <p/>
     * We will have to avoid caching or add context listening if we support reloading of beans one day.
     */
    private synchronized void cacheModuleDetails()
    {
        if (moduleDetailsById != null)
        {
            // There is nothing to do
            return;
        }
        try
        {
            moduleDetailsById = new HashMap<String, ModuleDetails>(13);
            
            Resource[] resources = resolver.getResources(MODULE_CONFIG_SEARCH_ALL);
            
            // Read each resource
            for (Resource resource : resources)
            {
                try
                {
                    InputStream is = new BufferedInputStream(resource.getInputStream());
                    Properties properties = new Properties();
                    properties.load(is);
                    ModuleDetails details = new ModuleDetailsImpl(properties);
                    moduleDetailsById.put(details.getId(), details);
                }
                catch (Exception e)
                {
                    logger.error("Unable to use module information.",e);
                    throw AlfrescoRuntimeException.create(e, ERR_UNABLE_TO_OPEN_MODULE_PROPETIES, resource);
                }
            }
        }
        catch (IOException e)
        {
            throw new AlfrescoRuntimeException("Failed to retrieve module information", e);
        }
        // Done
        if (logger.isDebugEnabled())
        {
            logger.debug(
                    "Found " + moduleDetailsById.size() + " modules: \n" +
                    "   Modules: " + moduleDetailsById);
        }
    }

	public String getDegradeModuleAllow() {
		return degradeModuleAllow;
	}

	public void setDegradeModuleAllow(String degradeModuleAllow) {
		this.degradeModuleAllow = degradeModuleAllow;
	}

	public String getModuleList() {
		return moduleList;
	}

	public void setModuleList(String moduleList) {
		this.moduleList = moduleList;
	}
	
	
    
    
    
}
