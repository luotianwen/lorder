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
			<li><label>交货单号：</label>
				<form:input path="erpNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>是否交货：</label>
				<form:select path="isok" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单类型：</label>
				<form:select path="orderclass" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>代理类型：</label>
				<form:input path="agenttype" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>sap供应商：</label>
				<form:input path="sapsupplierid" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>发货方：</label>
				<form:input path="shipperid" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sort-column batchNum">批次号</th>
				<th class="sort-column batchGenDatetime">生成时间</th>
				<th>总金额</th>
				<th class="sort-column erpNo">SAP交货单号</th>
				<th>是否交货</th>
				<th>订单类型</th>
				<th>代理类型</th>
				<th>sap供应商</th>
				<th>发货方</th>
				<shiro:hasPermission name="order:batch:poolBatch:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="poolBatch">
			<tr>
				<td ><a href="${ctx}/order/batch/poolBatch/form?id=${poolBatch.id}">
					${poolBatch.batchNum}
				</a></td>
				<td>
					<fmt:formatDate value="${poolBatch.batchGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${poolBatch.sumAmt}
				</td>
				<td >
					${poolBatch.erpNo}
				</td>
				<td>
					${fns:getDictLabel(poolBatch.isok, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(poolBatch.orderclass, 'P_TASK_TYPE', '')}
				</td>
				<td>
					${poolBatch.agenttype}
				</td>
				<td>
					${poolBatch.sapsupplierid}
				</td>
				<td>
					${poolBatch.shipperid}
				</td>
				<shiro:hasPermission name="order:batch:poolBatch:edit"><td>
    				<a href="${ctx}/order/batch/poolBatch/form?id=${poolBatch.id}">查看</a>
				<%--	<a href="${ctx}/order/batch/poolBatch/delete?id=${poolBatch.id}" onclick="return confirmx('确认要删除该订单集成吗？', this.href)">删除</a>
			--%>	</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>