<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单集成管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        var oldAction = $("#searchForm").attr("action");
                        $("#searchForm").attr("target", "_blank");
                        $("#searchForm").attr("action", "${ctx}/order/batch/poolBatch/export");
                        $("#searchForm").submit();
                        $("#searchForm").attr("target", "_self");
                        $("#searchForm").attr("action", oldAction);
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
		});
        function pagesize(a) {
            $("#pageSize").val(a);
        }
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
		<li class="active"><a href="${ctx}/order/batch/poolBatch/detaillist">订单集成明细列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="poolBatchLine" action="${ctx}/order/batch/poolBatch/detaillist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>

		<li><label>集成单号：</label>
			<form:input path="poolBatch.batchNum" htmlEscape="false"   class="input-medium"/>
		</li>
		<li><label>物料号：</label>
			<form:input path="productId" htmlEscape="false"   class="input-medium"/>
		</li>



		<li><label>sap状态：</label>
			<form:select path="poolBatch.isok" class="input-medium">
				<form:option value="" label="全部"/>
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
							  htmlEscape="false"/>
			</form:select>
		</li>
			<li><label>每页条数：</label>
				<input id="pageSize" name="pageSize" class="input-medium" value="${page.pageSize}"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>

			<li class="clearfix"></li>
		</ul>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>集成单号</th>
				<th>sap交货</th>
				<th>物料编码</th>
				<th>物料名称</th>
				<th>数量</th>
				<th>总价格</th>
				<th>发货方ID</th>
				<th>发货方名称</th>
				<th>类型</th>
				<th>仓库</th>
				<th>代理类型</th>
				<th>sap供应商</th>
				 <th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="poolBatch">
			<tr>
				<td>
						${poolBatch.poolBatch.batchNum}
				</td>
				<td>
						${fns:getDictLabel(poolBatch.poolBatch.isok, 'yes_no', '')}
				</td>
				<td >
					${poolBatch.productId}
				 </td>
				<td>
						${poolBatch.name}

				</td>
				<td>
					${poolBatch.amount}
				</td>

				<td>
						${poolBatch.sumPrice}
				</td>
				<td>
					${poolBatch.supplierid}
				</td>
				<td>
					${poolBatch.suppliername}
				</td>
				<td>
						${fns:getDictLabel(poolBatch.productClass, 'P_productClass', '')}
				</td>
				<td>
					${poolBatch.wharehouse}
				</td>
				<td >
						${poolBatch.agenttype}
				</td>
				<td >
						${poolBatch.sapsupplierid}
				</td>
				 <td>
					<a href="${ctx}/order/order/order/orderListByBatchNum?batchNum=${poolBatch.poolBatch.batchNum}" target="_blank">查看订单</a>
				 	</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>