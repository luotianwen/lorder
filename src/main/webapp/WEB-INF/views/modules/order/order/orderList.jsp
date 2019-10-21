<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function checkAll(e, itemName){
            var flag=e.checked;
            $(":checkbox[name="+itemName+"]").attr('checked',flag);
        }
		$(document).ready(function() {
            $("#btnExport").click(function () {
                top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function (v, h, f) {
                    if (v == "ok") {
                        var oldAction = $("#searchForm").attr("action");
                        $("#searchForm").attr("target","_blank");
                        $("#searchForm").attr("action", "${ctx}/order/order/order/export");
                        $("#searchForm").submit();
                        $("#searchForm").attr("target","_self");
                        $("#searchForm").attr("action", oldAction);
                    }
                }, {buttonsFocus: 1});
                top.$('.jbox-body .jbox-icon').css('top', '55px');
            });
            $(":checkbox[name='orderIds']").click(function () {
                $("#checkId").attr('checked', $(":checkbox[name='orderIds']").length == $(":checkbox[name='orderIds']:checked").length);
            });
            $(":checkbox[name='checkId']").click(function () {
                checkAll(this, 'orderIds');
            });
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }


        function checkprint() {
            var num = $("input[type='checkbox']:checked").length;
            if (num == 0) {
                top.$.jBox.alert("请选择你要批量打印的数据");
            } else {
                confirmx('确定要批量打印已选中的数据吗？', allprint);
            }

        }
        function checkReDeliver() {
            var num = $("input[type='checkbox']:checked").length;
            if (num == 0) {
                top.$.jBox.alert("请选择你要批量重新发货的数据");
            } else {
                confirmx('确定要批量重新发货已选中的数据吗？', allReDeliver);
            }

        }
        function allReDeliver() {
            var ids = [];
            $("input[name='orderIds']:checked").each(function () {
                ids.push($(this).val());
            });
            var delIds = ids.join(",");
            var oldAction = $("#searchForm").attr("action");
            //$("#searchForm").attr("target","_blank");
            $("#searchForm").attr("action", "${ctx}/order/order/order/allReDeliver?ids=" + delIds);
            $("#searchForm").submit();
            //$("#searchForm").attr("target","_self");
           // $("#searchForm").attr("action", oldAction);
        }
        function checkDeliver() {
            var num = $("input[type='checkbox']:checked").length;
            if (num == 0) {
                top.$.jBox.alert("请选择你要批量发货的数据");
            } else {
                confirmx('确定要批量发货已选中的数据吗？', allDeliver);
            }

        }

        function allDeliver() {
            var ids = [];
            $("input[name='orderIds']:checked").each(function () {
                ids.push($(this).val());
            });
            var delIds = ids.join(",");
            var oldAction = $("#searchForm").attr("action");
            //$("#searchForm").attr("target","_blank");
            $("#searchForm").attr("action", "${ctx}/order/order/order/allDeliver?ids=" + delIds);
            $("#searchForm").submit();
            //$("#searchForm").attr("target","_self");
           // $("#searchForm").attr("action", oldAction);
        }
        function allprint() {
            var ids = [];
            $("input[name='orderIds']:checked").each(function () {
                ids.push($(this).val());
            });
            var delIds = ids.join(",");
            var oldAction = $("#searchForm").attr("action");
            $("#searchForm").attr("target","_blank");
            $("#searchForm").attr("action", "${ctx}/order/order/order/allprint?ids=" + delIds);
            $("#searchForm").submit();
            $("#searchForm").attr("target","_self");
            $("#searchForm").attr("action", oldAction);
        }

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/order/order/order/">订单管理列表</a></li>
		<shiro:hasPermission name="order:order:order:edit"><li><a href="${ctx}/order/order/order/form">订单管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="order" action="${ctx}/order/order/order/" method="post" class="breadcrumb form-search"  >
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>集成单号：</label>
				<form:input path="poolTaskNo" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>平台单号：</label>
				<form:input path="taskNo" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>供应商单号：</label>
				<form:input path="supplierTaskNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>订单时间：</label>
				<input name="beginTaskGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginTaskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endTaskGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endTaskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>订单状态：</label>
				<form:select path="taskStatus" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>是否有货：</label>
				<form:select path="haveAmount" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单类型：</label>
				<form:select path="taskType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>订单来源：</label>
				<form:select path="source" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_SOURCE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>SAP单号：</label>
				<form:input path="ebTaskNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>客户编号：</label>
				<form:input path="customerNo" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="512" class="input-medium"/>
			</li>
			<li><label>客户手机：</label>
				<form:input path="handPhone" htmlEscape="false" maxlength="40" class="input-medium"/>
			</li>
			<li><label>收货人名称：</label>
				<form:input path="consigneeName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>收货人电话：</label>
				<form:input path="consigneePhone" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>发货日期：</label>
				<input name="beginSendStoreDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginSendStoreDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSendStoreDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endSendStoreDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>是否签收：</label>
				<form:select path="signResult" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>签收时间：</label>
				<input name="beginSignDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginSignDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endSignDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endSignDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${order.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
				<a href="#" onclick="checkDeliver()" class="btn btn-primary">批量发货</a>
				<a href="#" onclick="checkReDeliver()" class="btn btn-primary">批量重新发货</a>
				<a href="#" onclick="checkprint()" class="btn btn-primary">批量打印</a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th><input type=checkbox name="checkId" id="checkId"></th>
				<th>集成单号</th>
				<th>平台单号</th>
				<th>订单时间</th>
				<th>订单状态</th>
				<th>订单金额</th>
				<th>是否能发货</th>
				<th>发货组织</th>
				<th>发货人名称</th>
				<th>订单来源</th>
				<th>SAP单号</th>
				<%--<th>订单创建人</th>--%>
				<%--<th>客户编号</th>
				<th>客户名称</th>
				<th>客户手机</th>--%>
				<th>收货人名称</th>
				<th>收货人电话</th>
				<th>发货日期</th>
				<th>快递信息</th>
				<th>创建时间</th>
				<shiro:hasPermission name="order:order:order:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="order">
			<tr>
				<td><input type="checkbox" name="orderIds" value="${order.id}"/></td>
				<td><a href="${ctx}/order/order/order/form?id=${order.id}">
					${order.poolTaskNo}
				</a></td>
				<td>
					${order.taskNo}
				</td>
				<%--<td>
					${order.supplierTaskNo}
				</td>--%>
				<td>
					<fmt:formatDate value="${order.taskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(order.taskStatus, 'P_TASK_STATUS', '')}
				</td>
				<td>
					${order.taskAmount}
				</td>
				<td>
						${fns:getDictLabel(order.haveAmount, 'yes_no', '')}

				</td>
			   <td>
				${fns:getDictLabel(order.saleGroup, 'SALE_GROUP', '')}

				</td>
				<td>
						${order.shippername}
				</td>
				<td>
					${fns:getDictLabel(order.source, 'P_SOURCE', '')}
				</td>
				<td>
					${order.ebTaskNo}
				</td>
				<%--<td>
					${order.taskCreator}
				</td>--%>
				<%--<td>
					${order.customerNo}
				</td>
				<td>
					${order.customerName}
				</td>
				<td>
					${order.handPhone}
				</td>--%>
				<td>
					${order.consigneeName}
				</td>
				<td>
					${order.consigneePhone}
				</td>
				<td>
					<fmt:formatDate value="${order.sendStoreDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>	<c:if test="${not empty  order.carriers   }">
				        <a target="_blank" href="${ctx}/order/order/order/search?id=${order.id}">${order.carriers}</a>
				      </c:if>
					<c:if test="${empty  order.carriers}">
						${order.carriers}
					</c:if>
				</td>
			<%--	<td>
					${fns:getDictLabel(order.signResult, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${order.signDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>--%>
				<td>
					<fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="order:order:order:edit"><td>
    				 <a href="${ctx}/order/order/order/form?id=${order.id}">查看</a>
				<c:if test="${empty  order.carriers}">
					<a href="${ctx}/order/order/order/express?id=${order.id}">发货</a>
					</c:if>
						<c:if test="${order.carriers}">
							<a href="${ctx}/order/order/order/express?id=${order.id}">重新发货</a>
						</c:if>
						<%--&lt;%&ndash;<c:if test="${empty  order.carriers}">
						<a href="${ctx}/order/order/order/wbexpress?id=${order.id}">外部发货</a>
					</c:if>&ndash;%&gt;
					<c:if test="${not empty  order.carriers   }">
						<a href="${ctx}/order/order/order/print?id=${order.id}"   target="_blank">打印面单</a>
					</c:if>--%>
					<%--<a href="${ctx}/order/order/order/delete?id=${order.id}" onclick="return confirmx('确认要删除该订单管理吗？', this.href)">删除</a>--%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>