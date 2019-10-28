<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润管理</title>
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
	<%--<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/order/taskLineMoney/">分润列表</a></li>
		<shiro:hasPermission name="order:order:taskLineMoney:edit"><li><a href="${ctx}/order/order/taskLineMoney/form">分润添加</a></li></shiro:hasPermission>
	</ul>--%>
	<form:form id="searchForm" modelAttribute="taskLineMoney" action="${ctx}/order/order/taskLineMoney/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<%--	<ul class="ul-form">
			<li><label>订单行数据：</label>
				<form:input path="lineId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>--%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>类型</th>
				<th>类型名称</th>
				<th>分账金额</th>
				<th>分账比例</th>
				<%--<th>税率</th>
				<th>发货类型</th>
				<th>发货方id</th>
				<th>发货方名称</th>
				<th>sap接口结果</th>--%>
			<%--	<shiro:hasPermission name="order:order:taskLineMoney:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="taskLineMoney">
			<tr>
				<td><a href="${ctx}/order/order/taskLineMoney/form?id=${taskLineMoney.id}">
					${taskLineMoney.usertype}
				</a></td>
				<td>
					${taskLineMoney.typename}
				</td>
				<td>
					${taskLineMoney.amount}
				</td>
				<td>
					${taskLineMoney.proportion}
				</td>
				<%--<td>
					${taskLineMoney.rate}
				</td>
				<td>
					${taskLineMoney.shippertype}
				</td>
				<td>
					${taskLineMoney.shipperid}
				</td>
				<td>
					${taskLineMoney.shippername}
				</td>
				<td>
					${taskLineMoney.isok}
				</td>--%>
				<%--<shiro:hasPermission name="order:order:taskLineMoney:edit"><td>
    				<a href="${ctx}/order/order/taskLineMoney/form?id=${taskLineMoney.id}">修改</a>
					<a href="${ctx}/order/order/taskLineMoney/delete?id=${taskLineMoney.id}" onclick="return confirmx('确认要删除该分润吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>