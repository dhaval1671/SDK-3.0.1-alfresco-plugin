/* --------------------------------------------------
 * Script to Update Folder Permissions
 * -------------------------------------------------*/

/* Retrieve and initialize arguments */
var folder = args.folder;
var GROUP_TM_ADMINISTRATORS = "GROUP_TM_Content_Admin";
var GROUP_TM_Librarian = "GROUP_TM_Librarian";
var GROUP_TM_Evidence_Editor = "GROUP_TM_Evidence_Editor";
var DOCUMENT_LIBRARY = "Document_Library";
var EVIDENCE_BANK = "Evidence Bank";
var LIBRARY_2A = "2A";
var GUEST_HOME = "Guest Home";
var FOLDER_ROOT = "companyhome";

var folders = '{ "folders" : [';
var rootFolders = companyhome.children;
var folderCount = 0;

/*
 * Explicitly set the permission on root folders
 */
if (folder != null) {
    for (var i = 0; i < rootFolders.length; i++) {
        var node = rootFolders[i];
        if (node.name == folder || folder == FOLDER_ROOT) {
            logger.log("[" + node.name + "][" + node.typeShort + "][" + node.nodeRef + "]");
            if (node.name == DOCUMENT_LIBRARY) {
                applyDocumentLibraryPermissions(node);
            } else if (node.name == GUEST_HOME) {
                // applyGuestHomePermissions(node);
                applyDefaultPermissions(node);
            } else {
                applyDefaultPermissions(node);
            }
        }
        if (i == (rootFolders.length - 1) && folderCount > 1) {
            folders = folders.substring(0, folders.length - 1);
        }
    }
} else {
    folders += '{ "folder" : "Argument not found" }';
    model.message = "folder argument not found";
}

/*
 * Method updates the default permission on nodes.
 * EVERYONE group is being removed from default permissions
 */
function applyDefaultPermissions(node) {
    logger.log("Applying Default Permissions");
    if ((node !== null)) {
        node.setInheritsPermissions(false);
        node.removePermission("Consumer", "GROUP_EVERYONE");
        node.removePermission("Read", "GROUP_EVERYONE");
        node.setPermission("Coordinator", "GROUP_ALFRESCO_ADMINISTRATORS");
        node.setPermission("Coordinator", GROUP_TM_ADMINISTRATORS);
        addNodePermission(node);
    }
}

/*
 * Method updates permissions for Document Library folder
 */
function applyDocumentLibraryPermissions(node) {
    logger.log("Applying Document Library Permissions");
    logger.log(node.name + "(" + node.typeShort + "): " + node.nodeRef);
    var evidenceBankFolder = node.childByNamePath(EVIDENCE_BANK);
    applyEvidenceBankPermissions(evidenceBankFolder);
    if ((node !== null)) {
        node.setInheritsPermissions(false);
        node.removePermission("Consumer", "GROUP_EVERYONE");
        node.removePermission("Read", "GROUP_EVERYONE");
        node.setPermission("Coordinator", "GROUP_ALFRESCO_ADMINISTRATORS");
        node.setPermission("Coordinator", GROUP_TM_ADMINISTRATORS);
        node.setPermission("Coordinator", GROUP_TM_Evidence_Editor);
        node.setPermission("Consumer", GROUP_TM_Librarian);
        addNodePermission(node);
    }
}

/*
 * Method updates permissions for Guest Home folder
 */
function applyGuestHomePermissions(node) {
    logger.log("Applying Evidence Bank Permissions");
    if ((node !== null)) {
        node.setPermission("Coordinator", "GROUP_ALFRESCO_ADMINISTRATORS");
        node.setPermission("Coordinator", GROUP_TM_ADMINISTRATORS);
        addNodePermission(node);
    }
}

function applyEvidenceBankPermissions(node) {
    logger.log("Applying Evidence Bank Permissions");
    logger.log(node.name + "(" + node.typeShort + "): " + node.nodeRef);
    var library2ANode = node.childByNamePath(LIBRARY_2A);
    apply2APermissions(library2ANode);
    if ((node !== null)) {
        node.setInheritsPermissions(false);
        node.removePermission("Consumer", "GROUP_EVERYONE");
        node.removePermission("Read", "GROUP_EVERYONE");
        node.setPermission("Coordinator", "GROUP_ALFRESCO_ADMINISTRATORS");
        node.setPermission("Coordinator", GROUP_TM_ADMINISTRATORS);
        node.setPermission("Coordinator", GROUP_TM_Evidence_Editor);
        node.setPermission("Consumer", GROUP_TM_Librarian);
        addNodePermission(node);
    }
}

function apply2APermissions(node) {
    logger.log("Applying 2A Permissions");
    if ((node !== null)) {
        node.setInheritsPermissions(false);
        node.removePermission("Consumer", "GROUP_EVERYONE");
        node.removePermission("Read", "GROUP_EVERYONE");
        node.setPermission("Coordinator", "GROUP_ALFRESCO_ADMINISTRATORS");
        node.setPermission("Coordinator", GROUP_TM_ADMINISTRATORS);
        node.setPermission("Coordinator", GROUP_TM_Evidence_Editor);
        node.setPermission("Coordinator", GROUP_TM_Librarian);
        addNodePermission(node);
    }
}
function addNodePermission(node) {
    if (folderCount == 1) {
        folders += ','
    }
    folders = folders + '{ "name":"' + node.name + '" , "permissions": [';
    var perms = node.fullPermissions;
    for (var count = 0; count < perms.length; count++) {
        folders = folders + '{"permission":"' + perms[count] + '"}';
        if (count < (perms.length - 1)) {
            folders = folders + ','
        }
    }
    if (folderCount > 0) {
        folders = folders + ']},'
    } else {
        folders = folders + ']}'
    }
    folderCount++;
}
folders = folders + ']}';
model.folderList = folders;
model.message = "Script Completed.";
