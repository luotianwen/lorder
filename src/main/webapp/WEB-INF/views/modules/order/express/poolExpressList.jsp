<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>快递配置管理</title>
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
		<li class="active"><a href="${ctx}/order/express/poolExpress/">快递配置列表</a></li>
		<shiro:hasPermission name="order:express:poolExpress:edit"><li><a href="${ctx}/order/express/poolExpress/form">快递配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="poolExpress" action="${ctx}/order/express/poolExpress/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>简称：</label>
				<form:input path="abbr" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>简称</th>
				<th>公司</th>
				<th>模板</th>
			<%--	<th>更新者</th>--%>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="order:express:poolExpress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="poolExpress">
			<tr>
				<td><a href="${ctx}/order/express/poolExpress/form?id=${poolExpress.id}">
					${poolExpress.name}
				</a></td>
				<td>
					${poolExpress.abbr}
				</td>
				<td>
					${poolExpress.sendsite}
				</td>
				<td>
					${poolExpress.templatesize}
				</td>
				<%--<td>
					${poolExpress.updateBy.name}
				</td>--%>
				<td>
					<fmt:formatDate value="${poolExpress.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${poolExpress.remarks}
				</td>
				<shiro:hasPermission name="order:express:poolExpress:edit"><td>
    				<a href="${ctx}/order/express/poolExpress/form?id=${poolExpress.id}">修改</a>
					<a href="${ctx}/order/express/poolExpress/delete?id=${poolExpress.id}" onclick="return confirmx('确认要删除该快递配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>