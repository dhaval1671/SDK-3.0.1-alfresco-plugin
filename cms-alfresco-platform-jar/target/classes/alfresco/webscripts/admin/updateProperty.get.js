/*
A webscript to update a property with the given value.
 */

(function() {
	
	// Read input from query string
	logger.log("Entered into the script");
	var serialNumber = url.templateArgs.serialNumber;
	var fileName = url.templateArgs.fileName;
	var propertyName = url.templateArgs.propertyName;
	var propertyValue = url.templateArgs.propertyValue;
	var serialNumberFirst3 = serialNumber.substring(0,3);
	var serialNumberSecond3 = serialNumber.substring(3,6);
		
	// Get document from the repository
	var document = companyhome.childByNamePath("cabinet/case/" + serialNumberFirst3 + "/" + serialNumberSecond3 + "/" + serialNumber + "/" + fileName);
	logger.log("cabinet/case/" + serialNumberFirst3 + "/" + serialNumberSecond3 + "/" + serialNumber + "/" + fileName);
	logger.log(fileName);

	if(document != null) {
		// Update properties
		document.properties[propertyName] = propertyValue;
		document.save();
	}
})();