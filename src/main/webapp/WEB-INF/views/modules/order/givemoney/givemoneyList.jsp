<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打款信息管理</title>
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
		<li class="active"><a href="${ctx}/order/givemoney/givemoney/">打款信息列表</a></li>
		<shiro:hasPermission name="order:givemoney:givemoney:edit"><li><a href="${ctx}/order/givemoney/givemoney/form">打款信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="givemoney" action="${ctx}/order/givemoney/givemoney/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>银行账号：</label>
				<form:input path="accountnumber" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>账户名：</label>
				<form:input path="accountname" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>银行名称：</label>
				<form:input path="bankname" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>分账用户类型：</label>
				<form:select path="usertype" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SALE_GROUP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>分账用户ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>分账用户类型名称：</label>
				<form:input path="typename" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>银行账号</th>
				<th>账户名</th>
				<th>银行名称</th>
				<th>分账用户类型</th>
				<th>分账用户ID</th>
				<th>分账用户类型名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="order:givemoney:givemoney:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="givemoney">
			<tr>
				<td><a href="${ctx}/order/givemoney/givemoney/form?id=${givemoney.id}">
					${givemoney.accountnumber}
				</a></td>
				<td>
					${givemoney.accountname}
				</td>
				<td>
					${givemoney.bankname}
				</td>
				<td>
					${fns:getDictLabel(givemoney.usertype, 'SALE_GROUP', '')}
				</td>
				<td>
					${givemoney.userid}
				</td>
				<td>
					${givemoney.typename}
				</td>
				<td>
					<fmt:formatDate value="${givemoney.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${givemoney.remarks}
				</td>
				<shiro:hasPermission name="order:givemoney:givemoney:edit"><td>
    				<a href="${ctx}/order/givemoney/givemoney/form?id=${givemoney.id}">修改</a>
					<a href="${ctx}/order/givemoney/givemoney/delete?id=${givemoney.id}" onclick="return confirmx('确认要删除该打款信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>