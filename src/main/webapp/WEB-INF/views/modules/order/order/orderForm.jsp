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
		<li class="active"> 订单管理查看</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order"   method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>


		<div class="row control-group">
			<label class="span1 control-label">集成单号：</label>
			<div class="span3 ">
				<form:input path="poolTaskNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">平台单号：</label>
			<div class="span3 ">
				<form:input path="taskNo" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">供应商单号：</label>
			<div class="span3 ">
				<form:input path="supplierTaskNo" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">订单时间：</label>
			<div class="span2 ">
				<input name="taskGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${order.taskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">付款渠道：</label>
			<div class="span2 ">
				<form:select path="payWay" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_PAY_WAY')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">订单状态：</label>
			<div class="span2 ">
				<form:select path="taskStatus" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">更新时间：</label>
			<div class="span2 ">
				<input name="statusChangeDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${order.statusChangeDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">实付总价：</label>
			<div class="span2 ">
				<form:input path="taskAmount" htmlEscape="false" class="input-medium required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">发货组织：</label>
			<div class="span2 ">
				<form:select path="saleGroup" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SALE_GROUP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">订单类型：</label>
			<div class="span2 ">
				<form:select path="taskType" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		<%--</div>
		<div class="row control-group">--%>
			<%--<label class="span1 control-label">档期编码：</label>
			<div class="span2 ">
				<form:input path="dmNo" htmlEscape="false" maxlength="100" class="input-medium "/>
			</div>

			<label class="span1 control-label">档期名称：</label>
			<div class="span2 ">
				<form:input path="dmName" htmlEscape="false" maxlength="100" class="input-medium "/>
			</div>

			<label class="span1 control-label">订单来源：</label>
			<div class="span2 ">
				<form:select path="source" class="input-medium required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_SOURCE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>--%>

			<label class="span1 control-label">SAP单号：</label>
			<div class="span2 ">
				<form:input path="ebTaskNo" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">SAP交货单号：</label>
			<div class="span2 ">
				<form:input path="erpNo" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>

			<label class="span1 control-label">紧急程度：</label>
			<div class="span2 ">
				<form:input path="emergentId" htmlEscape="false" maxlength="512" class="input-medium "/>
			</div>

			<label class="span1 control-label">失败原因：</label>
			<div class="span2 ">
				<form:input path="failreason" htmlEscape="false" maxlength="512" class="input-medium "/>
			</div>

			<label class="span1 control-label">订单创建人：</label>
			<div class="span2 ">
				<form:input path="taskCreator" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">应付现金：</label>
			<div class="span2 ">
				<form:input path="payableAmount" htmlEscape="false" maxlength="40" class="input-medium  "/>

			</div>

			<label class="span1 control-label">减免金额：</label>
			<div class="span2 ">
				<form:input path="reductionAmount" htmlEscape="false" maxlength="512" class="input-medium  "/>
			</div>

			<label class="span1 control-label">莲香币：</label>
			<div class="span2 ">
				<form:input path="socre" htmlEscape="false" maxlength="2" class="input-medium "/>
			</div>

			<label class="span1 control-label">代理商：</label>
			<div class="span2 ">
				<form:input path="agentType" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">SAP供应商：</label>
			<div class="span2 ">
				<form:input path="sapSupplierID" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>

			<label class="span1 control-label">客户手机：</label>
			<div class="span2 ">
				<form:input path="handPhone" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>

			<label class="span1 control-label">电子邮件：</label>
			<div class="span2 ">
				<form:input path="email" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>

			<label class="span1 control-label">会员编号：</label>
			<div class="span2 ">
				<form:input path="fax" htmlEscape="false" maxlength="40" class="input-medium "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">证件名称：</label>
			<div class="span2 ">
				<form:input path="idCardName" htmlEscape="false" maxlength="20" class="input-medium "/>
			</div>

			<label class="span1 control-label">证件号码：</label>
			<div class="span2 ">
				<form:input path="idCard" htmlEscape="false" maxlength="20" class="input-medium "/>
			</div>

			<label class="span1 control-label">收货地址-省：</label>
			<div class="span2 ">
				<sys:treeselect id="province" name="province.name" value="${order.province.name}" labelName="province.name" labelValue="${order.province.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-medium required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">收货地址-市：</label>
			<div class="span2 ">
				<sys:treeselect id="city" name="city.name" value="${order.city.name}" labelName="city.name" labelValue="${order.city.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-medium required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">收货地址-区县：</label>
			<div class="span2 ">
				<sys:treeselect id="county" name="county.name" value="${order.county.name}" labelName="county.name" labelValue="${order.county.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-medium required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>

			<label class="span1 control-label">收货详细地址：</label>
			<div class="span2 ">
				<form:input path="addressDetail" htmlEscape="false" maxlength="512" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
	<%--	</div>--%>
		<%--<div class="row control-group">
			<label class="span1 control-label">邮政编码：</label>
			<div class="span2 ">
				<form:input path="postcode" htmlEscape="false" maxlength="10" class="input-medium "/>
			</div>
		</div>--%>
		<%--<div class="row control-group">--%>
			<label class="span1 control-label">收货人名称：</label>
			<div class="span2 ">
				<form:input path="consigneeName" htmlEscape="false" maxlength="100" class="input-medium "/>
			</div>

			<label class="span1 control-label">收货人电话：</label>
			<div class="span2 ">
				<form:input path="consigneePhone" htmlEscape="false" maxlength="100" class="input-medium "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">发货地址：</label>
			<div class="span2 ">
				<form:input path="preSendAddress" htmlEscape="false" maxlength="512" class="input-medium "/>
			</div>

			<label class="span1 control-label">送货方式：</label>
			<div class="span2 ">
				<form:select path="sendWay" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_SEND_WAY')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>

			<label class="span1 control-label">承运商：</label>
			<div class="span2 ">
				<form:input path="carriers" htmlEscape="false" maxlength="50" class="input-medium "/>
			</div>
				<label class="span1 control-label">发票发送地址：</label>
				<div class="span2 ">
					<form:input path="invoiceSendAddress" htmlEscape="false" maxlength="512" class="input-medium "/>
				</div>

		</div>
		<div class="row control-group">
			<label class="span1 control-label">发票抬头：</label>
			<div class="span2 ">
				<form:input path="invoiceTitle" htmlEscape="false" maxlength="512" class="input-medium "/>
			</div>

			<label class="span1 control-label">发票号：</label>
			<div class="span2 ">
				<form:input path="invoiceNo" htmlEscape="false" maxlength="100" class="input-medium "/>
			</div>

			<label class="span1 control-label">发票类型：</label>
			<div class="span2 ">
				<form:input path="invoiceType" htmlEscape="false" maxlength="16" class="input-medium "/>
			</div>

			<label class="span1 control-label">发票发送方式：</label>
			<div class="span2 ">
				<form:input path="invoiceSendId" htmlEscape="false" class="input-medium "/>
			</div>
		</div>

		<div class="row control-group">
			<label class="span1 control-label">发货日期：</label>
			<div class="span2 ">
				<input name="sendStoreDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.sendStoreDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>

			<label class="span1 control-label">签收标准：</label>
			<div class="span2 ">
				<form:input path="signStandard" htmlEscape="false" maxlength="200" class="input-medium "/>
			</div>

			<label class="span1 control-label">是否签收：</label>
			<div class="span2 ">
				<form:select path="signResult" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>

			<label class="span1 control-label">签收人：</label>
			<div class="span2 ">
				<form:input path="signName" htmlEscape="false" maxlength="200" class="input-medium "/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">签收时间：</label>
			<div class="span2 ">
				<input name="signDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.signDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>

			<label class="span1 control-label">签收状态：</label>
			<div class="span2 ">
				<form:select path="recallStatus" class="input-medium ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_RECALL_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="row control-group">
			<label class="span1 control-label">备注：</label>
			<div class="span2 ">
				<form:input path="remark" htmlEscape="false" maxlength="200" class="input-medium "/>
			</div>
		</div>--%>
		<div class="row control-group">
			<label class="span1 control-label">备注信息：</label>
			<div class="span2 ">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="row control-group">
				<label class="span1 control-label">订单行数据表：</label>
				<div class="span2 ">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>


								<th>商品编号</th>
								<th>商品类型</th>
								<th>数量</th>
								<th>物料名称</th>
								<th>莲香币</th>
								<th>实付单价</th>
								<th>实付总价</th>
								<th>应付总价</th>
								<th>减免金额</th>
								<th>代理商标识</th>
								<th>SAP供应商</th>
								<th>批次号</th>

							</tr>
						</thead>
						<tbody id="orderDetailList">
						</tbody>

					</table>
					<script type="text/template" id="orderDetailTpl">//<!--
						<tr id="orderDetailList{{idx}}">

<td>
							{{row.productNo}}	<a onclick="windowOpen('${ctx}/order/order/taskLineMoney/?lineId={{row.id}}','查看信息')">查看分润信息</a></td>
							<td>
							<select id="orderDetailList{{idx}}_productClass" name="orderDetailList[{{idx}}].productClass" data-value="{{row.productClass}}" class="input-small ">
									<c:forEach items="${fns:getDictList('P_productClass')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>{{row.amount}}</td>
							<td>{{row.name}}</td>
							<td>
								 {{row.score}}
							</td>
							<td>
								 {{row.payAmount}}
							</td>
							<td>
								{{row.payAmountSum}}
							</td>
							<td>
								{{row.priceSum}}
							</td>
							<td>
								{{row.reductionAmount}}
							</td>
							<td>
								{{row.agentType}}
							</td>
							<td>
								{{row.sapSupplierID}}
							</td>
							<td>
							{{row.batchNum}}
							</td>


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
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>