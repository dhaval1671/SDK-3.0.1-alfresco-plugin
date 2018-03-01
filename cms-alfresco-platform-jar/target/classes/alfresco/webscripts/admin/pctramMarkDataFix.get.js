/*
The code below will fix production Mark metadata,
which had been migrated before September 14, 2015. 
The data had been migrated with default access level = internal 
The code will search for those migrated files and will change 
the access level to public. 
 */

(function() {
	
	// Read input from query string
	logger.log("Entered into the script");
	var serialNumber = url.templateArgs.serialNumber;
	var fileName = url.templateArgs.fileName;
	var serialNumberFirst3 = serialNumber.substring(0,3);
	var serialNumberSecond3 = serialNumber.substring(3,6);
		
	// Get document from the repository
	var document = companyhome.childByNamePath("cabinet/case/" + serialNumberFirst3 + "/" + serialNumberSecond3 + "/" + serialNumber + "/" + fileName);
	logger.log("cabinet/case/" + serialNumberFirst3 + "/" + serialNumberSecond3 + "/" + serialNumber + "/" + fileName);
	logger.log(fileName);

	if(document != null) {
		// Update properties
		document.properties["tm:accessLevel"] = "public";
		document.save();
	}
})();