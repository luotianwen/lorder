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
		<li class="active"><a href="${ctx}/order/order/order/form?id=${order.id}">订单管理<shiro:hasPermission name="order:order:order:edit">${not empty order.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:order:order:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="order" action="${ctx}/order/order/order/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">集成单号：</label>
			<div class="controls">
				<form:input path="poolTaskNo" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台单号：</label>
			<div class="controls">
				<form:input path="taskNo" htmlEscape="false" maxlength="10" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应商单号：</label>
			<div class="controls">
				<form:input path="supplierTaskNo" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单时间：</label>
			<div class="controls">
				<input name="taskGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${order.taskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">付款渠道：</label>
			<div class="controls">
				<form:select path="payWay" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_PAY_WAY')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单状态：</label>
			<div class="controls">
				<form:select path="taskStatus" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更新时间：</label>
			<div class="controls">
				<input name="statusChangeDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${order.statusChangeDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<form:input path="taskAmount" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货组织：</label>
			<div class="controls">
				<form:input path="saleGroup" htmlEscape="false" maxlength="16" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单类型：</label>
			<div class="controls">
				<form:select path="taskType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档期编码：</label>
			<div class="controls">
				<form:input path="dmNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">档期名称：</label>
			<div class="controls">
				<form:input path="dmName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单来源：</label>
			<div class="controls">
				<form:select path="source" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_SOURCE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SAP单号：</label>
			<div class="controls">
				<form:input path="ebTaskNo" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SAP交货单号：</label>
			<div class="controls">
				<form:input path="erpNo" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">紧急程度：</label>
			<div class="controls">
				<form:input path="emergentId" htmlEscape="false" maxlength="512" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">失败原因：</label>
			<div class="controls">
				<form:input path="failreason" htmlEscape="false" maxlength="512" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">订单创建人：</label>
			<div class="controls">
				<form:input path="taskCreator" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户编号：</label>
			<div class="controls">
				<form:input path="customerNo" htmlEscape="false" maxlength="40" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户性别：</label>
			<div class="controls">
				<form:input path="sex" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭电话：</label>
			<div class="controls">
				<form:input path="homePhone" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位电话：</label>
			<div class="controls">
				<form:input path="companyPhone" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户手机：</label>
			<div class="controls">
				<form:input path="handPhone" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮件：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">会员编号：</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="40" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件名称：</label>
			<div class="controls">
				<form:input path="idCardName" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="idCard" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货地址-省：</label>
			<div class="controls">
				<sys:treeselect id="province" name="province.id" value="${order.province.id}" labelName="province.name" labelValue="${order.province.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货地址-市：</label>
			<div class="controls">
				<sys:treeselect id="city" name="city.id" value="${order.city.id}" labelName="city.name" labelValue="${order.city.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货地址-区县：</label>
			<div class="controls">
				<sys:treeselect id="county" name="county.id" value="${order.county.id}" labelName="county.name" labelValue="${order.county.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收货详细地址：</label>
			<div class="controls">
				<form:input path="addressDetail" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码：</label>
			<div class="controls">
				<form:input path="postcode" htmlEscape="false" maxlength="10" class="input-xlarge "/>
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
				<form:input path="carriers" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票抬头：</label>
			<div class="controls">
				<form:input path="invoiceTitle" htmlEscape="false" maxlength="512" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票号：</label>
			<div class="controls">
				<form:input path="invoiceNo" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票类型：</label>
			<div class="controls">
				<form:input path="invoiceType" htmlEscape="false" maxlength="16" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票发送方式：</label>
			<div class="controls">
				<form:input path="invoiceSendId" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发票发送地址：</label>
			<div class="controls">
				<form:input path="invoiceSendAddress" htmlEscape="false" maxlength="512" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发货日期：</label>
			<div class="controls">
				<input name="sendStoreDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.sendStoreDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签收标准：</label>
			<div class="controls">
				<form:input path="signStandard" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否签收：</label>
			<div class="controls">
				<form:select path="signResult" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签收人：</label>
			<div class="controls">
				<form:input path="signName" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签收时间：</label>
			<div class="controls">
				<input name="signDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${order.signDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签收状态：</label>
			<div class="controls">
				<form:select path="recallStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_RECALL_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
								<th>赠品标识</th>
								<th>赠品备注</th>
								<th>赠品类型</th>
								<th>分期数</th>
								<th>分期价格</th>
								<th>是否原始订单</th>
								<th>货币</th>
								<th>分摊价格</th>
								<th>莲香币额</th>
								<th>莲香币分摊价格</th>
								<th>金币额</th>
								<th>金币分摊价格</th>
								<th>积分额</th>
								<th>积分分摊价格</th>
								<th>批次号</th>
								<th>备注</th>
								<th>莲香岛科技分润金额</th>
								<th>莲香岛科技分润税率</th>
								<th>莲香岛信息技术分润金额</th>
								<th>莲香岛信息技术分润税率</th>
								<th>门店分润金额</th>
								<th>门店分润税率</th>
								<th>供应商分润金额</th>
								<th>供应商分润税率</th>
								<shiro:hasPermission name="order:order:order:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="orderDetailList">
						</tbody>
						<shiro:hasPermission name="order:order:order:edit"><tfoot>
							<tr><td colspan="34"><a href="javascript:" onclick="addRow('#orderDetailList', orderDetailRowIdx, orderDetailTpl);orderDetailRowIdx = orderDetailRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="orderDetailTpl">//<!--
						<tr id="orderDetailList{{idx}}">
							<td class="hide">
								<input id="orderDetailList{{idx}}_id" name="orderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="orderDetailList{{idx}}_delFlag" name="orderDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_poolTaskNo" name="orderDetailList[{{idx}}].poolTaskNo" type="text" value="{{row.poolTaskNo}}" maxlength="10" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_taskNo" name="orderDetailList[{{idx}}].taskNo" type="text" value="{{row.taskNo}}" maxlength="10" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productName" name="orderDetailList[{{idx}}].productName" type="text" value="{{row.productName}}" maxlength="512" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productNo" name="orderDetailList[{{idx}}].productNo" type="text" value="{{row.productNo}}" maxlength="40" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productClass" name="orderDetailList[{{idx}}].productClass" type="text" value="{{row.productClass}}" maxlength="20" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_amount" name="orderDetailList[{{idx}}].amount" type="text" value="{{row.amount}}" maxlength="5" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_productId" name="orderDetailList[{{idx}}].productId" type="text" value="{{row.productId}}" maxlength="80" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_name" name="orderDetailList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="512" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_isPresent" name="orderDetailList[{{idx}}].isPresent" type="text" value="{{row.isPresent}}" maxlength="1" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_presentNotes" name="orderDetailList[{{idx}}].presentNotes" type="text" value="{{row.presentNotes}}" maxlength="2000" class="input-small "/>
							</td>
							<td>
								<select id="orderDetailList{{idx}}_giftType" name="orderDetailList[{{idx}}].giftType" data-value="{{row.giftType}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('P_GIFT_TYPE')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_times" name="orderDetailList[{{idx}}].times" type="text" value="{{row.times}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_perTimes" name="orderDetailList[{{idx}}].perTimes" type="text" value="{{row.perTimes}}" class="input-small "/>
							</td>
							<td>
								<select id="orderDetailList{{idx}}_isOrig" name="orderDetailList[{{idx}}].isOrig" data-value="{{row.isOrig}}" class="input-small ">
									<option value=""></option>
									<c:forEach items="${fns:getDictList('yes_no')}" var="dict">
										<option value="${dict.value}">${dict.label}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_currency" name="orderDetailList[{{idx}}].currency" type="text" value="{{row.currency}}" maxlength="6" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_relievePrice" name="orderDetailList[{{idx}}].relievePrice" type="text" value="{{row.relievePrice}}" class="input-small required"/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_lxbAmount" name="orderDetailList[{{idx}}].lxbAmount" type="text" value="{{row.lxbAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_lxbPrice" name="orderDetailList[{{idx}}].lxbPrice" type="text" value="{{row.lxbPrice}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_goldenAmount" name="orderDetailList[{{idx}}].goldenAmount" type="text" value="{{row.goldenAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_goldenPrice" name="orderDetailList[{{idx}}].goldenPrice" type="text" value="{{row.goldenPrice}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_wentAmount" name="orderDetailList[{{idx}}].wentAmount" type="text" value="{{row.wentAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_wentPrice" name="orderDetailList[{{idx}}].wentPrice" type="text" value="{{row.wentPrice}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_batchNum" name="orderDetailList[{{idx}}].batchNum" type="text" value="{{row.batchNum}}" maxlength="64" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_lineMemo" name="orderDetailList[{{idx}}].lineMemo" type="text" value="{{row.lineMemo}}" maxlength="512" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitLsdtechAmount" name="orderDetailList[{{idx}}].profitLsdtechAmount" type="text" value="{{row.profitLsdtechAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitLsdtechRates" name="orderDetailList[{{idx}}].profitLsdtechRates" type="text" value="{{row.profitLsdtechRates}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitLsdinfoAmount" name="orderDetailList[{{idx}}].profitLsdinfoAmount" type="text" value="{{row.profitLsdinfoAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitLsdinfoRates" name="orderDetailList[{{idx}}].profitLsdinfoRates" type="text" value="{{row.profitLsdinfoRates}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitStoreAmount" name="orderDetailList[{{idx}}].profitStoreAmount" type="text" value="{{row.profitStoreAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitStoreRatesv" name="orderDetailList[{{idx}}].profitStoreRatesv" type="text" value="{{row.profitStoreRatesv}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitSupplierAmount" name="orderDetailList[{{idx}}].profitSupplierAmount" type="text" value="{{row.profitSupplierAmount}}" class="input-small "/>
							</td>
							<td>
								<input id="orderDetailList{{idx}}_profitSupplierRates" name="orderDetailList[{{idx}}].profitSupplierRates" type="text" value="{{row.profitSupplierRates}}" class="input-small "/>
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