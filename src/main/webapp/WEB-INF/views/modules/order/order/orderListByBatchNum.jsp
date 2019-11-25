<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>订单集成管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
  
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th  > 商品编号</th>
        <th  > 商品类型</th>
        <th  > 数量</th>
        <th  > 物料名称</th>
        <th  > 莲香币</th>
        <th  > 实付单价</th>
        <th  > 实付总价</th>
        <th  > 应付总价</th>
        <th  > 减免金额</th>
        <th  > 代理商标识</th>
        <th  > SAP供应商</th>
        <th  > 批次号</th>
         <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${os}" var="poolBatch">
        <tr>
            <td>
                    ${poolBatch.productNo}
             	<a style="cursor:pointer" onclick="windowOpen('${ctx}/order/order/taskLineMoney/?lineId=${poolBatch.id}','查看信息')">查看分润信息</a></td>
            <td>
                    ${fns:getDictLabel(poolBatch.productClass, 'P_productClass', '')}

            </td>
            <td>${poolBatch.amount}</td>
            <td>${poolBatch.name}</td>
            <td>
                ${poolBatch.socre}
            </td>
            <td>
                ${poolBatch.payAmount}
            </td>
            <td>
                ${poolBatch.payAmountSum}
            </td>
            <td>
                ${poolBatch.priceSum}
            </td>
            <td>
                ${poolBatch.reductionAmount}
            </td>
            <td>
                ${poolBatch.agentType}
            </td>
            <td>
                ${poolBatch.sapSupplierID}
            </td>
            <td>
                ${poolBatch.batchNum}
            </td>
            <td>
                <a href="${ctx}/order/order/order/form?id=${poolBatch.poolTask.id}" target="_blank" >${poolBatch.poolTaskNo}查看订单</a>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table> 
</body>
</html>