<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>打款信息管理</title>
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
		<li><a href="${ctx}/order/givemoney/givemoney/">打款信息列表</a></li>
		<li class="active"><a href="${ctx}/order/givemoney/givemoney/form?id=${givemoney.id}">打款信息<shiro:hasPermission name="order:givemoney:givemoney:edit">${not empty givemoney.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:givemoney:givemoney:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="givemoney" action="${ctx}/order/givemoney/givemoney/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">银行账号：</label>
			<div class="controls">
				<form:input path="accountnumber" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户名：</label>
			<div class="controls">
				<form:input path="accountname" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打款金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				<form:input path="bankname" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分账用户类型：</label>
			<div class="controls">
				<form:select path="usertype" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SALE_GROUP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分账用户ID：</label>
			<div class="controls">
				<form:input path="userid" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分账用户类型名称：</label>
			<div class="controls">
				<form:input path="typename" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">订单打款信息明细：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>订单id</th>
								<th>订单日期</th>
								<th>金额</th>
								<th>商品名称</th>
								<th>数量</th>
								<th>物料id</th>
								<shiro:hasPermission name="order:givemoney:givemoney:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="givemoneyOrderList">
						</tbody>
						<shiro:hasPermission name="order:givemoney:givemoney:edit"><tfoot>
							<tr><td colspan="8"><a href="javascript:" onclick="addRow('#givemoneyOrderList', givemoneyOrderRowIdx, givemoneyOrderTpl);givemoneyOrderRowIdx = givemoneyOrderRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="givemoneyOrderTpl">//<!--
						<tr id="givemoneyOrderList{{idx}}">
							<td class="hide">
								<input id="givemoneyOrderList{{idx}}_id" name="givemoneyOrderList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="givemoneyOrderList{{idx}}_delFlag" name="givemoneyOrderList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="givemoneyOrderList{{idx}}_orderid" name="givemoneyOrderList[{{idx}}].orderid" type="text" value="{{row.orderid}}" maxlength="50" class="input-small "/>
							</td>
							<td>
								<input id="givemoneyOrderList{{idx}}_orderdate" name="givemoneyOrderList[{{idx}}].orderdate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
									value="{{row.orderdate}}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							</td>
							<td>
								<input id="givemoneyOrderList{{idx}}_amount" name="givemoneyOrderList[{{idx}}].amount" type="text" value="{{row.amount}}" class="input-small "/>
							</td>
							<td>
								<input id="givemoneyOrderList{{idx}}_productname" name="givemoneyOrderList[{{idx}}].productname" type="text" value="{{row.productname}}" maxlength="200" class="input-small "/>
							</td>
							<td>
								<input id="givemoneyOrderList{{idx}}_productnumber" name="givemoneyOrderList[{{idx}}].productnumber" type="text" value="{{row.productnumber}}" maxlength="11" class="input-small "/>
							</td>
							<td>
								<input id="givemoneyOrderList{{idx}}_itemcode" name="givemoneyOrderList[{{idx}}].itemcode" type="text" value="{{row.itemcode}}" maxlength="200" class="input-small "/>
							</td>
							<shiro:hasPermission name="order:givemoney:givemoney:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#givemoneyOrderList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var givemoneyOrderRowIdx = 0, givemoneyOrderTpl = $("#givemoneyOrderTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(givemoney.givemoneyOrderList)};
							for (var i=0; i<data.length; i++){
								addRow('#givemoneyOrderList', givemoneyOrderRowIdx, givemoneyOrderTpl, data[i]);
								givemoneyOrderRowIdx = givemoneyOrderRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:givemoney:givemoney:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>