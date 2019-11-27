<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        var oldAction = $("#searchForm").attr("action");
                        $("#searchForm").attr("target", "_blank");
                        $("#searchForm").attr("action", "${ctx}/order/order/taskLineMoney/export");
                        $("#searchForm").submit();
                        $("#searchForm").attr("target", "_self");
                        $("#searchForm").attr("action", oldAction);
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
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
		<li class="active"><a href="${ctx}/order/order/taskLineMoney/">分润列表</a></li>
<%--
		<shiro:hasPermission name="order:order:taskLineMoney:edit"><li><a href="${ctx}/order/order/taskLineMoney/form">分润添加</a></li></shiro:hasPermission>
--%>
	</ul>
	<form:form id="searchForm" modelAttribute="taskLineMoney" action="${ctx}/order/order/taskLineMoney/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="lineId"/>
		<ul class="ul-form">

			<li><label>平台单号：</label>
				<form:input path="od.taskNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>集成单号：</label>
				<form:input path="od.batchNum" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>分账方ID：</label>
				<form:input path="userid" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>分账方名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<%--<li><label>oms单号：</label>
				<form:input path="od.poolTaskNo" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>--%>
			<li><label>类型：</label>
				<form:input path="usertype" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>类型名称：</label>
				<form:input path="typename" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--
				<th>oms单号</th>--%>
				<th>平台单号</th>
				<th>集成单号</th>
				<th>分账方ID</th>
				<th>分账方名称</th>
				<th>物料号</th>
				<th>物料名称</th>
				<th>应付总价</th>
				<th>实付总价</th>
				<th>类型</th>
				<th>类型名称</th>
				<th>分账金额</th>
				<th>分账比例</th>

				 <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="taskLineMoney">
			<tr>
				<td>
						${taskLineMoney.od.taskNo}
				</td>

				<td>
						${taskLineMoney.od.batchNum}
				</td>
				<td><a href="${ctx}/order/order/taskLineMoney/form?id=${taskLineMoney.id}">
					${taskLineMoney.userid}
				</a></td>
				<td>
					${taskLineMoney.name}
				</td>

				<td>
						${taskLineMoney.od.productNo}
				</td>
				<td>
						${taskLineMoney.od.name}
				</td>
				<td>
						${taskLineMoney.od.priceSum}
				</td>
				<td>
						${taskLineMoney.od.payAmountSum}
				</td>

				<td>
					${taskLineMoney.usertype}
				</td>
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
					${taskLineMoney.shippertype}
				</td>
				<td>
					${taskLineMoney.shipperid}
				</td>
				<td>
					${taskLineMoney.shippername}
				</td>--%>
				<shiro:hasPermission name="order:order:taskLineMoney:edit"><td>
    				<a href="${ctx}/order/order/taskLineMoney/form?id=${taskLineMoney.id}" target="_blank">查看</a>
					<%--<a href="${ctx}/order/order/taskLineMoney/delete?id=${taskLineMoney.id}" onclick="return confirmx('确认要删除该分润吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>