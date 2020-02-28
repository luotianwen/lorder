<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>订单管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

        $(document).ready(function () {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        var oldAction = $("#searchForm").attr("action");
                        $("#searchForm").attr("target", "_blank");
                        $("#searchForm").attr("action", "${ctx}/order/order/order/exportstock");
                        $("#searchForm").submit();
                        $("#searchForm").attr("target", "_self");
                        $("#searchForm").attr("action", oldAction);
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });


        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"> 订单管理列表 </li>

</ul>
<form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/order/querystock" method="post"
           class="breadcrumb form-search">
    <input id="payWay" name="payWay" type="hidden" value="1"/>

    <ul class="ul-form">

        <li><label>物料号：</label>
            <form:input path="dmNo" htmlEscape="false" class="input-xxlarge"/>
        </li>

        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
            <input id="btnExport" class="btn btn-primary" type="button" value="导出 "/>

        </li>
        <li class="clearfix"></li>
    </ul>

</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>物料号</th>
        <th>名称</th>
        <th>库存量</th>
        <th>订单占用数量</th>
        <th>普通商品占用数量</th>
        <th>组合购占用数量</th>
        <th>抢购占用数量</th>
        <th>普通商品SKU占用数量</th>
        <th>抢购SKU占用数量</th>
        <th>组合购抢购占用数量</th>
        <th>OMS占用</th>
        <th>可用库存量</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${list}" var="order">
        <tr>
            <td>
                    ${order.itemCode}
            </td>
            <td>
                    ${order.name}
            </td>
            <td>
                    ${order.stock} </td>
            <td>
                    ${order.order} </td>
            <td>
                    ${order.product} </td>
            <td>
                    ${order.collocation} </td>
            <td>
                    ${order.promotion} </td>
            <td>
                    ${order.productSKU} </td>
            <td>
                    ${order.promotionSKU} </td>
            <td>
                    ${order.colloPromotion} </td>
            <td>     ${order.omsstock} </td>
            <td>
                    ${order.laststock} </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>