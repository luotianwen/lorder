<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/order/order/order/">订单管理列表</a></li>
		<li class="active"><a href="${ctx}/order/order/order/express?id=${order.id}">订单管理发货</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/order/order/order/saveExpress" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">集成单号：</label>
			<div class="controls">
				<form:input path="poolTaskNo" htmlEscape="false"  class="input-xlarge "/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台单号：</label>
			<div class="controls">
				<form:input path="taskNo" htmlEscape="false"  class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单时间：</label>
			<div class="controls">
				<input name="taskGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.taskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款渠道：</label>
			<div class="controls">
				<form:select path="payWay" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_PAY_WAY')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:select path="taskStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<form:input path="taskAmount" htmlEscape="false" class="input-xlarge  number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单类型：</label>
			<div class="controls">
				<form:select path="taskType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单来源：</label>
			<div class="controls">
				<form:select path="source" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_SOURCE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单创建人：</label>
			<div class="controls">
				<form:input path="taskCreator" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>



		<div class="control-group">
			<label class="control-label">收货地址-省：</label>
			<div class="controls">
				<sys:treeselect id="province" name="province.name" value="${order.province.name}" labelName="province.name" labelValue="${order.province.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货地址-市：</label>
			<div class="controls">
				<sys:treeselect id="city" name="city.name" value="${order.city.name}" labelName="city.name" labelValue="${order.city.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货地址-区县：</label>
			<div class="controls">
				<sys:treeselect id="county" name="county.name" value="${order.county.name}" labelName="county.name" labelValue="${order.county.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货详细地址：</label>
			<div class="controls">
				<form:input path="addressDetail" htmlEscape="false" maxlength="512" class="input-xlarge "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">收货人名称：</label>
			<div class="controls">
				<form:input path="consigneeName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货人电话：</label>
			<div class="controls">
				<form:input path="consigneePhone" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货地址：</label>
			<div class="controls">
				<form:input path="preSendAddress" htmlEscape="false" maxlength="512" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">送货方式：</label>
			<div class="controls">
				<form:select path="sendWay" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_SEND_WAY')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">承运商：</label>
			<div class="controls">
				<form:select path="carriers" class="input-medium ">
					<form:option value="" label="无"/>
					<form:options items="${expresss}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>


		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:input path="remark" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">订单行数据表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>集成单号</th>
								<th>订单号</th>
								<th>商品名称</th>
								<th>商品编号</th>
								<th>产品线/产品分类</th>
								<th>数量</th>
								<th>ERP物料编码</th>
								<th>物料名称</th>

								<th>备注信息</th>
								<shiro:hasPermission name="order:order:order:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderDetailList">
						</tbody>
						<shiro:hasPermission name="order:order:order:edit"><tfoot>
							<tr><td colspan="35"><a href="javascript:" onclick="addRow('#orderDetailList', orderDetailRowIdx, orderDetailTpl);orderDetailRowIdx = orderDetailRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderDetailTpl">//<!--
						<tr id="orderDetailList{{idx}}">
							<td class="hide">
								<input id="orderDetailList{{idx}}_id" name="orderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderDetailList{{idx}}_delFlag" name="orderDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_poolTaskNo" name="orderDetailList[{{idx}}].poolTaskNo" type="text" value="{{row.poolTaskNo}}"  class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_taskNo" name="orderDetailList[{{idx}}].taskNo" type="text" value="{{row.taskNo}}"  class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productName" name="orderDetailList[{{idx}}].productName" type="text" value="{{row.productName}}" maxlength="512" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productNo" name="orderDetailList[{{idx}}].productNo" type="text" value="{{row.productNo}}" maxlength="40" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productClass" name="orderDetailList[{{idx}}].productClass" type="text" value="{{row.productClass}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_amount" name="orderDetailList[{{idx}}].amount" type="text" value="{{row.amount}}" maxlength="5" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productId" name="orderDetailList[{{idx}}].productId" type="text" value="{{row.productId}}" maxlength="80" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_name" name="orderDetailList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="512" class="input-small "/>
							</td>

							<td>
								<textarea id="orderDetailList{{idx}}_remarks" name="orderDetailList[{{idx}}].remarks" rows="4" maxlength="255" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="order:order:order:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#orderDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var orderDetailRowIdx = 0, orderDetailTpl = $("#orderDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(order.orderDetailList)};
							for (var i=0; i<data.length; i++){
								addRow('#orderDetailList', orderDetailRowIdx, orderDetailTpl, data[i]);
								orderDetailRowIdx = orderDetailRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:order:order:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>