package gov.uspto.trademark.cms.repo.action;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DeleteFileAction.
 */
public class DeleteFileAction extends ActionExecuterAbstractBase {

    /** The Constant LOG. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteFileAction.class);

    /** The Constant PARAM_ASPECT_NAME. */
    public static final String PARAM_ASPECT_NAME = "aspect-name";

    /** the node service. */
    private NodeService nodeService;

    /**
     * Set the node service.
     *
     * @param nodeService
     *            the node service
     */
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.alfresco.repo.action.executer.ActionExecuterAbstractBase#executeImpl
     * (org.alfresco.service.cmr.action.Action,
     * org.alfresco.service.cmr.repository.NodeRef)
     */
    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Delete file action fired");
            LOGGER.debug("NodeRef " + actionedUponNodeRef);
        }
        nodeService.deleteNode(actionedUponNodeRef);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.alfresco.repo.action.ParameterizedItemAbstractBase#
     * addParameterDefinitions(java.util.List)
     */
    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        paramList.add(new ParameterDefinitionImpl(PARAM_ASPECT_NAME, DataTypeDefinition.QNAME, true,
                getParamDisplayLabel(PARAM_ASPECT_NAME)));

    }

}
