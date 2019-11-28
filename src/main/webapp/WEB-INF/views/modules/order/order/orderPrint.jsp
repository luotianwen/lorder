<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>打印</title>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function(){
                form1.submit()
        });
    </script>
</head>
<body>
<h1>打印</h1>
<div id="head"></div>
<form id="form1" action="http://www.kdniao.com/External/PrintOrder.aspx" method="post" target="_self">
    <div style="">
        <div><input type="hidden" id="RequestData" name="RequestData" value="${printData.requestData}"/></div>
        <div><input type="hidden" id="EBusinessID" name="EBusinessID" value="${printData.ebusinessID}"/></div>
        <div><input type="hidden" id="DataSign" name="DataSign" value="${printData.dataSign}"/></div>
        <div><input type="hidden" id="IsPreview" name="IsPreview" value="${printData.isPreview}"/></div>
        <%--<button type="submit"></button>--%>
    </div>
</form>
</body>
</html>