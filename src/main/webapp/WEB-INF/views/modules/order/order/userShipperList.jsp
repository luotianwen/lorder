<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发货方管理</title>
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
		<li class="active"><a href="${ctx}/order/order/userShipper/">发货方列表</a></li>
		<shiro:hasPermission name="order:order:userShipper:edit"><li><a href="${ctx}/order/order/userShipper/form">发货方添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userShipper" action="${ctx}/order/order/userShipper/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<sys:treeselect id="user" name="user.id" value="${userShipper.user.id}" labelName="user.name" labelValue="${userShipper.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>发货方id：</label>
				<form:input path="shipperid" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户</th>
				<th>发货方id</th>
				<shiro:hasPermission name="order:order:userShipper:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userShipper">
			<tr>
				<td><a href="${ctx}/order/order/userShipper/form?id=${userShipper.id}">
					${userShipper.user.name}
				</a></td>
				<td>
					${userShipper.shipperid}
				</td>
				<shiro:hasPermission name="order:order:userShipper:edit"><td>
    				<a href="${ctx}/order/order/userShipper/form?id=${userShipper.id}">修改</a>
					<a href="${ctx}/order/order/userShipper/delete?id=${userShipper.id}" onclick="return confirmx('确认要删除该发货方吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>