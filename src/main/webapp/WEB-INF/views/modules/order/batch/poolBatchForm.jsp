<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单集成管理</title>
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
		<li><a href="${ctx}/order/batch/poolBatch/">订单集成列表</a></li>
		<li class="active"><a href="${ctx}/order/batch/poolBatch/form?id=${poolBatch.id}">订单集成<shiro:hasPermission name="order:batch:poolBatch:edit">${not empty poolBatch.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="order:batch:poolBatch:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="poolBatch" action="${ctx}/order/batch/poolBatch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">批次号：</label>
			<div class="controls">
				<form:input path="poolBatch" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成时间：</label>
			<div class="controls">
				<input name="batchGenDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${poolBatch.batchGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">总金额：</label>
			<div class="controls">
				<form:input path="sumAmt" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">批次创建人：</label>
			<div class="controls">
				<form:input path="batchCreator" htmlEscape="false" maxlength="40" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">SAP交货单号：</label>
			<div class="controls">
				<form:input path="erpNo" htmlEscape="false" maxlength="40" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">订单集成行表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>ERP物料编码</th>
								<th>物料名称</th>
								<th>数量</th>
								<th>总价格</th>
								<shiro:hasPermission name="order:batch:poolBatch:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="poolBatchLineList">
						</tbody>
						<shiro:hasPermission name="order:batch:poolBatch:edit"><tfoot>
							<tr><td colspan="6"><a href="javascript:" onclick="addRow('#poolBatchLineList', poolBatchLineRowIdx, poolBatchLineTpl);poolBatchLineRowIdx = poolBatchLineRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="poolBatchLineTpl">//<!--
						<tr id="poolBatchLineList{{idx}}">
							<td class="hide">
								<input id="poolBatchLineList{{idx}}_id" name="poolBatchLineList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="poolBatchLineList{{idx}}_delFlag" name="poolBatchLineList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="poolBatchLineList{{idx}}_productId" name="poolBatchLineList[{{idx}}].productId" type="text" value="{{row.productId}}" maxlength="80" class="input-small required"/>
							</td>
							<td>
								<input id="poolBatchLineList{{idx}}_name" name="poolBatchLineList[{{idx}}].name" type="text" value="{{row.name}}" maxlength="52" class="input-small required"/>
							</td>
							<td>
								<input id="poolBatchLineList{{idx}}_amount" name="poolBatchLineList[{{idx}}].amount" type="text" value="{{row.amount}}" maxlength="11" class="input-small required digits"/>
							</td>
							<td>
								<input id="poolBatchLineList{{idx}}_sumPrice" name="poolBatchLineList[{{idx}}].sumPrice" type="text" value="{{row.sumPrice}}" class="input-small required number"/>
							</td>
							<shiro:hasPermission name="order:batch:poolBatch:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#poolBatchLineList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var poolBatchLineRowIdx = 0, poolBatchLineTpl = $("#poolBatchLineTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(poolBatch.poolBatchLineList)};
							for (var i=0; i<data.length; i++){
								addRow('#poolBatchLineList', poolBatchLineRowIdx, poolBatchLineTpl, data[i]);
								poolBatchLineRowIdx = poolBatchLineRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="order:batch:poolBatch:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>