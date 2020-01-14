<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>订单管理管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function checkAll(e, itemName) {
            var flag = e.checked;
            $(":checkbox[name=" + itemName + "]").attr('checked', flag);
        }

        $(document).ready(function () {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        var oldAction = $("#searchForm").attr("action");
                        $("#searchForm").attr("target", "_blank");
                        $("#searchForm").attr("action", "${ctx}/order/order/order/exportDetail");
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




        function pagesize(a) {
            $("#pageSize").val(a);
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/order/order/order/">订单管理列表</a></li>

</ul>
<form:form id="searchForm" modelAttribute="orderDetail" action="${ctx}/order/order/order/orderDetaillist" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>

    <ul class="ul-form">

        <li><label>订单号：</label>
            <form:input path="taskNo" htmlEscape="false"   class="input-medium"/>
        </li>
        <li><label>批次号：</label>
            <form:input path="batchNum" htmlEscape="false"   class="input-medium"/>
        </li>
        <li><label>物料号：</label>
            <form:input path="productNo" htmlEscape="false"   class="input-medium"/>
        </li>


          <li><label>类型：</label>
                <form:select path="productClass" class="input-medium">
                    <form:option value="" label=""/>
                    <form:options items="${fns:getDictList('P_productClass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                </form:select>
            </li>

        <li><label>供应商名称：</label>
            <form:input path="suppliername" htmlEscape="false"   class="input-medium"/>
        </li>


        <li><label>每页条数：</label>
            <form:select path="page.pageSize" class="input-medium" onchange="pagesize(this.value)">
                <form:option value="20" label="20"/>
                <form:option value="30" label="30"/>
                <form:option value="50" label="50"/>
                <form:option value="100" label="100"/>
                <form:option value="200" label="200"/>
                <form:option value="300" label="300"/>
                <form:option value="500" label="500"/>
                <form:option value="1000" label="1000"/>
            </form:select>
        </li>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>

            <input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
        </li>
        <li class="clearfix"></li>
    </ul>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
</form:form>

<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>订单号</th>
        <th>商品编号</th>
        <th>商品类型</th>
        <th>数量</th>
        <th>物料名称</th>
        <th>莲香币</th>
        <th>实付单价</th>
        <th>实付总价</th>
        <th>应付总价</th>
        <th>减免金额</th>
        <th>代理商标识</th>
         <th>SAP供应商</th>
         <th>批次号</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="order">
        <tr>
            <td>
                    ${order.taskNo}
            </td>
            <td>
                    ${order.productNo}
            </td>
            <td>
                    ${fns:getDictLabel(order.productClass, 'P_productClass', '')}
            </td>
            <td>
                    ${order.amount}
            </td>
            <td>
                    ${order.name}
            </td>
            <td>
                    ${order.socre}

            </td>
            <td>
                    ${order.payAmount}

            </td>
            <td>
                    ${order.payAmountSum}
            </td>

            <td>
                    ${order.priceSum}
            </td>

            <td>
                    ${order.reductionAmount}
            </td>
            <td>
                    ${order.agentType}
            </td>
            <td>
                    ${order.sapSupplierID}
            </td>
            <td>
                    ${order.batchNum}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>