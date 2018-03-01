// extract file attributes
var title = args.title;
var description = args.description;

// extract file
var file = null;
for each (field in formdata.fields)
{
  if (field.name == "file" && field.isFile)
  {
    file = field;
  }
}

// ensure file has been uploaded
if (file.filename == "")
{
  status.code = 400;
  status.message = "Uploaded file cannot be located";
  status.redirect = true;
}
else
{
  // create document in company home from uploaded file
  // upload = companyhome.createFile(file.filename) ;
  upload = companyhome.childByNamePath('Document_Library/Evidence Bank/WebCapture/99020').createFile(file.filename);
  upload.properties.content.guessMimetype(file.filename);
  upload.properties.content.write(file.content);
  upload.properties.title = title;
  upload.properties.description = description;
  upload.save();
  // setup model for response template
  model.upload = upload;
}