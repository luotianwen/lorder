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
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/batch/poolBatch/">订单集成列表</a></li>
		<shiro:hasPermission name="order:batch:poolBatch:edit"><li><a href="${ctx}/order/batch/poolBatch/form">订单集成添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="poolBatch" action="${ctx}/order/batch/poolBatch/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>批次号：</label>
				<form:input path="batchNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>生成时间：</label>
				<input name="beginBatchGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${poolBatch.beginBatchGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endBatchGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${poolBatch.endBatchGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>SAP交货单号：</label>
				<form:input path="erpNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>批次号</th>
				<th>生成时间</th>
				<th>总金额</th>
				<th>SAP交货单号</th>
				<%--<shiro:hasPermission name="order:batch:poolBatch:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="poolBatch">
			<tr>
				<td><a href="${ctx}/order/batch/poolBatch/form?id=${poolBatch.id}">
					${poolBatch.batchNum}
				</a></td>
				<td>
					<fmt:formatDate value="${poolBatch.batchGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${poolBatch.sumAmt}
				</td>
				<td>
					${poolBatch.erpNo}
				</td>
			<%--	<shiro:hasPermission name="order:batch:poolBatch:edit"><td>
    				<a href="${ctx}/order/batch/poolBatch/form?id=${poolBatch.id}">修改</a>
					<a href="${ctx}/order/batch/poolBatch/delete?id=${poolBatch.id}" onclick="return confirmx('确认要删除该订单集成吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>