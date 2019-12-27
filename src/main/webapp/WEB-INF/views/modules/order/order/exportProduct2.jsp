<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<html>

<head>
    <title>订单管理管理</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/printThis/printThis.js" type="text/javascript"></script>
    <script type="text/javascript">
        function dy(){
        $('#contentTable').printThis({
            importCSS: true,
            header: "<h1>备货单</h1>"
        });
        }
    </script>
</head>
<body>

<input id="btnExport" class="btn btn-primary" type="button" onclick="dy()" value="打印"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>


        <th>物料编码</th>
        <th>物料名称</th>
        <th>数量</th>
        <th>库存</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${items}" var="od">
        <tr>

            <td>
                    ${od.productNo}
            </td>
            <td>
                    ${od.name}
            </td>
            <td>
                    ${od.amount}
            </td>
            <td>
                    ${od.stock}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>