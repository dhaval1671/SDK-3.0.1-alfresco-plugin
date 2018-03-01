[#ftl]
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>${msg("bfsit.page.title")}</title>
    <link rel="stylesheet" href="${url.context}/css/main.css" TYPE="text/css">

    <!-- YUI 3.x -->
    <link rel="stylesheet" type="text/css" href="${url.context}/css/yui-3.3.0-dependencies.css">

    <script type="text/javascript" src="${url.context}/scripts/yui-3.3.0-dependencies.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/alfresco.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/yui-common-min.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/yui-common.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/ajax_helper.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/common.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/yahoo/connection/connection.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/yahoo/dom/dom.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/yahoo/event/event.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/yahoo/utilities/utilities.js"></script>
    <script type="text/javascript" src="${url.context}/scripts/ajax/yahoo/yahoo/yahoo.js"></script>
    <script type="text/javascript" src="${url.context}/yui/yahoo.js"></script>
    <script type="text/javascript" src="${url.context}/yui/connection.js"></script>
    <script type="text/javascript" src="${url.context}/yui/dom.js"></script>
    <script type="text/javascript" src="${url.context}/yui/event.js"></script>
    <script type="text/javascript" src="${url.context}/yui/calendar.js"></script>

</head>
<body class="yui-skin-sam">

<table>
    <tr>
        <td><img src="${url.context}/images/logo/AlfrescoLogo32.png" alt="Alfresco"/></td>
        <td>
            <nobr>Repository Permission Update</nobr>
        </td>
    </tr>
    <tr>
        <td>
        <td>Alfresco ${server.edition} v${server.version}
</table>
<p>

<table align="center" width="80%" border="0" cellpadding="2" cellspacing="2"
       style="background-color: #FFFFFF;">
    <form method="post" action="${url.service}" enctype="multipart/form-data" accept-charset="utf-8">
        <tr valign="top">
            <td style="border-width : 0px;">
                <br/>
                <div style=" text-align: left; text-indent: 0px; padding: 10px; margin: 0px;">
                    <table width="100%" border="0" cellpadding="2" cellspacing="2"
                           style="background-color: #e8e8e8;padding: 10px">
                        <tr valign="top">
                            <td style="border-width : 0px; width:15%">Response:
                            </td>
                            <td>${folderList}</td>
                        </tr>
                        <br/>
                        <br/>
                    </table>
                </div>
            </td>
        </tr>
        <tr valign="top">
            <td style="border-width : 0px;">&nbsp;
            </td>
        </tr>
    </form>
</table>
</p>
</body>
</html>

