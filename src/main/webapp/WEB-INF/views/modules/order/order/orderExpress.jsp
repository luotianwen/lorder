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
            //initActiveX(); // 初始化控件方法
		});
        function initActiveX(){
            try {
                msComm1 = new ActiveXObject("MSCOMMLib.MSComm.1"); // 初始化MSCOMM控件
            }catch (err) {
                console.log(err); // 初始化失败，打印错误
            }
            if ((typeof (msComm1) == "undefined") || (msComm1 == null)) { // 未初始化成功
                alert("msComm1 is null");
                return false;
            }else{ // 初始化成功
                configPort(); // 配置端口信息
                searchAndOpenPort(); // 自动搜索端口并打开端口
                return false;
            }
        }
        function configPort(){ // 配置端口方法
            msComm1.settings = '9600,n,8,1'; // 9600：波特率 n：奇偶位 8：数据位 1：校验位
            msComm1.OutBufferCount =0; //清空发送缓冲区
            msComm1.InBufferCount = 0; //滑空接收缓冲区
            msComm1.RThreshold=14; //这个参数很重要，这个参数配置要符合硬件设备 该参数表示接收硬件设备多少位数据的时候触发onComm事件
        }
        var com = 0; // 端口变量
        var res = ''; // 接收硬件
        function searchAndOpenPort(){
            if(msComm1.PortOpen == true){
                toggglePort(); // 关闭或者打开端口
            }
            if(com > 16){ // 一般电脑只有16个端口
                com = 0;
                alert("未连接称重仪!")
                return;
            }
            if(res != ''){ // res为接收到称重仪的数据 后面会讲到如何接收
                msComm1.CommPort = com - 1; // 设置串口为搜索到的串口
                toggglePort();
                return;
            }
            msComm1.CommPort = com; // 设置串口
            var b = toggglePort(); // 打开串口 并返回打开结果
            if(b){
                send('27,112'); // 向串口发送信息
            }
            com += 1;
            setTimeout('searchAndOpenPort()', 500); // 间隔500ms等待接收数据 并实现循环，如果不间隔时间，受代码执行顺序影响，将接受不到数据
        }
        function toggglePort(){
            if(msComm1.PortOpen == false){
                try {
                    msComm1.PortOpen = true; // 开启串口
                    bindEvent(); //开启串口后绑定串口收发事件
                    return true;
                }catch(ex){
                    console.log(ex);
                    return false;
                }
            }else{
                try {
                    msComm1.PortOpen = false; // 关闭串口
                    return true;
                }catch(ex){
                    alert(ex.message);
                    return false;
                }
            }
        }

        var bindEvent = function () {
           // function msComm1::OnComm(){
                msComm1_event(); // 触发收发事件后的处理方法
            // }
        }
        function msComm1_event(){
            //console.log(msComm1.CommEvent); // 这个是在触发收发事件后返回的标志 1：表示发送；
            // 2：表示接受
            // 其他的标识线变等信息（线变具体信息需要参照MSCOMM的API）
            if(msComm1.CommEvent == 1){
                alert("Send OK！");
            }else if(msComm1.CommEvent == 2){
                Receive(); // 接收串口信息方法
            }
        }
        function Receive() {
            var inputvalue = msComm1.Input; // 接收串口信息
            // console.log("input:::" + inputvalue);
            if(inputvalue.indexOf("g")>0){ // 如果返回数据包含g
                res = $.trim(inputvalue.split("g")[0]); // 取得重量并赋值给res
            }
        }
        function send(str){
            var cmd_send = '';
            try{
                var results = str.split(',');
                for(var i=0;i<results.length;i++) {
                    cmd_send += String.fromCharCode(results[i]); // 十进制转char
                }
                msComm1.Output=cmd_send; // 向串口发送消息
            }catch(ex){
                console.log(ex.message);
            }
        }
        function getWeight(){
            if(com == '0'){
                alert("未连接称重仪!")
            }else{
                send('27,112'); // 向串口发送一次请求
                setTimeout("auto_weight_once()", 200); // 200ms后接收数据，这里设置200ms后再获取很有必要，这个是执行顺序问题
            }
        }
        function auto_weight_once(){
            $("#weight").val(res); // 填充获取的数据到页面
        }
        function auto_weight(){
            // if(!continue_auto_weight){ // 这里是结束持续输出的标志，至于什么时候结束， 可根据项目而定
            // return;
            // }
            $("#weight").val(res);
            send('27,112');
            setTimeout("auto_weight()", 200);
        }
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


		<div class="row control-group">
			<label class="span1 control-label">集成单号：</label>
			<div class="span3 ">
				<form:input path="poolTaskNo" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
			</div>

			<label class="span1 control-label">平台单号：</label>
			<div class="span3 ">
				<form:input path="taskNo" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
			</div>

				<%--<label class="span1 control-label">供应商单号：</label>
                <div class="span3 ">
                    <form:input path="supplierTaskNo" htmlEscape="false" maxlength="40" class="input-xlarge "/>
                </div>--%>

			<label class="span1 control-label">订单时间：</label>
			<div class="span2 ">
				<input name="taskGenDatetime" type="text" readonly="true" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${order.taskGenDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />

			</div>
				<%--

                            <label class="span1 control-label">付款渠道：</label>
                            <div class="span2 ">
                                <form:select path="payWay" class="input-medium ">
                                    <form:option value="" label=""/>
                                    <form:options items="${fns:getDictList('P_PAY_WAY')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                                </form:select>
                                <span class="help-inline"><font color="red">*</font> </span>
                            </div>
                --%>

			<label class="span1 control-label">订单状态：</label>
			<div class="span2 ">
				<form:select path="taskStatus" class="input-medium " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%--<div class="row control-group">

        </div>--%>
		<div class="row control-group">

			<label class="span1 control-label">实付总价：</label>
			<div class="span2 ">
				<form:input path="taskAmount" htmlEscape="false" class="input-medium  number" readonly="true"/>
			</div>
			<label class="span1 control-label">应付现金：</label>
			<div class="span2 ">
				<form:input path="payableAmount" htmlEscape="false" maxlength="40" class="input-medium  " readonly="true"/>

			</div>

			<label class="span1 control-label">减免金额：</label>
			<div class="span2 ">
				<form:input path="reductionAmount" htmlEscape="false" maxlength="512" class="input-medium  " readonly="true"/>
			</div>

			<label class="span1 control-label">莲香币：</label>
			<div class="span2 ">
				<form:input path="socre" htmlEscape="false"   class="input-medium " readonly="true"/>
			</div>
		</div>
		<div class="row control-group">
			<label class="span1 control-label">发货组织：</label>
			<div class="span2 ">

				<form:select path="saleGroup" class="input-medium " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('SALE_GROUP')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>

			<label class="span1 control-label">订单类型：</label>
			<div class="span2 ">
				<form:select path="taskType" class="input-medium " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_TASK_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
			<label class="span1 control-label">更新时间：</label>
			<div class="span2 ">
				<input name="statusChangeDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${order.statusChangeDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>

			<label class="span1 control-label">订单创建人：</label>
			<div class="span2 ">
				<form:input path="taskCreator" htmlEscape="false" maxlength="40" class="input-medium " readonly="true"/>
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
                    <form:select path="source" class="input-medium ">
                        <form:option value="" label=""/>
                        <form:options items="${fns:getDictList('P_SOURCE')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                    </form:select>
                    <span class="help-inline"><font color="red">*</font> </span>
                </div>--%>
				<%--
                            <label class="span1 control-label">SAP单号：</label>
                            <div class="span2 ">
                                <form:input path="ebTaskNo" htmlEscape="false" maxlength="40" class="input-medium "/>
                            </div>--%>
		</div>
		<%--	<div class="row control-group">--%>
		<%--<label class="span1 control-label">SAP交货单号：</label>
        <div class="span2 ">
            <form:input path="erpNo" htmlEscape="false" maxlength="40" class="input-medium "/>
        </div>--%>

		<%--	<label class="span1 control-label">紧急程度：</label>
            <div class="span2 ">
                <form:input path="emergentId" htmlEscape="false" maxlength="512" class="input-medium "/>
            </div>

            <label class="span1 control-label">失败原因：</label>
            <div class="span2 ">
                <form:input path="failreason" htmlEscape="false" maxlength="512" class="input-medium "/>
            </div>--%>

		<%--</div>--%>
		<div class="row control-group">


			<label class="span1 control-label">代理商：</label>
			<div class="span2 ">
				<form:input path="agentType" htmlEscape="false" maxlength="40" class="input-medium " readonly="true"/>
			</div>

			<label class="span1 control-label">SAP供应商：</label>
			<div class="span2 ">
				<form:input path="sapSupplierID" htmlEscape="false" maxlength="40" class="input-medium " readonly="true"/>
			</div>

			<label class="span1 control-label">客户手机：</label>
			<div class="span2 ">
				<form:input path="handPhone" htmlEscape="false" maxlength="40" class="input-medium " readonly="true"/>
			</div>

				<%--<label class="span1 control-label">电子邮件：</label>
                <div class="span2 ">
                    <form:input path="email" htmlEscape="false" maxlength="40" class="input-medium "/>
                </div>--%>

			<label class="span1 control-label">会员编号：</label>
			<div class="span2 ">
				<form:input path="customerNo" htmlEscape="false" maxlength="40" class="input-medium " readonly="true"/>
			</div>
		</div>
		<div class="row control-group">
				<%--	<label class="span1 control-label">证件名称：</label>
                    <div class="span2 ">
                        <form:input path="idCardName" htmlEscape="false" maxlength="20" class="input-medium "/>
                    </div>

                    <label class="span1 control-label">证件号码：</label>
                    <div class="span2 ">
                        <form:input path="idCard" htmlEscape="false" maxlength="20" class="input-medium "/>
                    </div>--%>

			<label class="span1 control-label">收货地址-省：</label>
			<div class="span2 ">
				<sys:treeselect id="province" name="province.name" disabled="true"  value="${order.province.name}" labelName="province.name" labelValue="${order.province.name}"
								title="区域" url="/sys/area/treeData" cssClass="input-medium " allowClear="true" notAllowSelectParent="true"/>
			</div>

			<label class="span1 control-label">收货地址-市：</label>
			<div class="span2 ">
				<sys:treeselect id="city" name="city.name" disabled="true" value="${order.city.name}" labelName="city.name" labelValue="${order.city.name}"
								title="区域" url="/sys/area/treeData" cssClass="input-medium " allowClear="true" notAllowSelectParent="true"/>
			</div>

			<label class="span1 control-label">收货地址-区县：</label>
			<div class="span2 ">
				<sys:treeselect id="county" name="county.name" disabled="true" value="${order.county.name}" labelName="county.name" labelValue="${order.county.name}"
								title="区域" url="/sys/area/treeData" cssClass="input-medium " allowClear="true" notAllowSelectParent="true"/>
			</div>

			<label class="span1 control-label">收货详细地址：</label>
			<div class="span2 ">
				<form:input path="addressDetail" htmlEscape="false" maxlength="512" class="input-medium "  readonly="true"/>
			</div>
				<%--	</div>--%>
				<%--<div class="row control-group">
                    <label class="span1 control-label">邮政编码：</label>
                    <div class="span2 ">
                        <form:input path="postcode" htmlEscape="false" maxlength="10" class="input-medium "/>
                    </div>
                </div>--%>
				<%--<div class="row control-group">--%>

		</div>
		<div class="row control-group"><label class="span1 control-label">收货人名称：</label>
			<div class="span2 ">
				<form:input path="consigneeName" htmlEscape="false" maxlength="100" class="input-medium " readonly="true"/>
			</div>

			<label class="span1 control-label">收货人电话：</label>
			<div class="span2 ">
				<form:input path="consigneePhone" htmlEscape="false" maxlength="100" class="input-medium " readonly="true"/>
			</div>




			<label class="span1 control-label">发货日期：</label>
			<div class="span2 ">
				<input name="sendStoreDatetime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					   value="<fmt:formatDate value="${order.sendStoreDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<%--<div class="row control-group">
                <label class="span1 control-label">发票发送地址：</label>
                <div class="span2 ">
                    <form:input path="invoiceSendAddress" htmlEscape="false" maxlength="512" class="input-medium " readonly="true"/>
                </div>


            <label class="span1 control-label">发票抬头：</label>
            <div class="span2 ">
                <form:input path="invoiceTitle" htmlEscape="false" maxlength="512" class="input-medium " readonly="true"/>
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
        </div>--%>

		<div class="row control-group">


				<%--<label class="span1 control-label">签收标准：</label>
                <div class="span2 ">
                    <form:input path="signStandard" htmlEscape="false" maxlength="200" class="input-medium "/>
                </div>--%>

			<label class="span1 control-label">是否签收：</label>
			<div class="span2 ">
				<form:select path="signResult" class="input-medium " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>

			<label class="span1 control-label">签收人：</label>
			<div class="span2 ">
				<form:input path="signName" htmlEscape="false" maxlength="200" readonly="true" class="input-medium "/>
			</div>

			<label class="span1 control-label">签收时间：</label>
			<div class="span2 ">
				<input name="signDate" type="text" readonly="readonly" maxlength="20"   class="input-medium Wdate "
					   value="<fmt:formatDate value="${order.signDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				/>
			</div>

			<label class="span1 control-label">签收状态：</label>
			<div class="span2 ">
				<form:select path="recallStatus" class="input-medium " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('P_RECALL_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
	<div class="row control-group">
		<label class="span1 control-label">发货地址：</label>
		<div class="span2 ">
			<form:select path="preSendAddress" class="input-medium required">
				<form:option value="" label="无"/>
				<form:options items="${addresss}" itemLabel="name" itemValue="id" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>

		<label class="span1 control-label">承运商：</label>
		<div class="span2 ">
			<form:select path="carriers" class="input-medium required">
				<form:option value="" label="无"/>
				<form:options items="${expresss}" itemLabel="name" itemValue="id" htmlEscape="false"/>
			</form:select>
			<span class="help-inline"><font color="red">*</font> </span>
		</div>
	</div>

		<div class="row control-group">
			<label class="span1 control-label">重量：</label>
			<div class="span2 ">
				<form:input path="weight"    maxlength="255" class="input-xxlarge "/>
			</div>
			<%--<label class="span1 control-label"></label>
			<div class="span2 ">
			<input   class="btn" type="button" value="获取称重" onclick="getWeight()"/>
			</div>--%>
		</div>

		<div class="row control-group">
			<label class="span1 control-label">备注信息：</label>
			<div class="span2 ">
				<form:textarea path="remarks" htmlEscape="false" rows="4" readonly="true"  maxlength="255" class="input-xxlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">订单行数据表：</label>
			<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>

								<th style="width: 12%"> 商品编号</th>
								<th style="width: 8%"> 商品类型</th>
								<th style="width: 8%"> 数量</th>
								<th style="width: 8%"> 物料名称</th>
								<th style="width: 8%"> 莲香币</th>
								<th style="width: 8%"> 实付单价</th>
								<th style="width: 8%"> 实付总价</th>
								<th style="width: 5%"> 应付总价</th>
								<th style="width: 5%"> 减免金额</th>
								<th style="width: 5%"> 代理商标识</th>
								<th style="width: 5%"> SAP供应商</th>
								<th style="width: 5%"> 批次号</th>

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

							<td>
								{{row.productNo}}	<a onclick="windowOpen('${ctx}/order/order/taskLineMoney/?lineId={{row.id}}','查看信息')">查看分润信息</a></td>
							<td>
							<select id="orderDetailList{{idx}}_productClass" disabled="true" name="orderDetailList[{{idx}}].productClass" data-value="{{row.productClass}}" class="input-small ">
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
			<shiro:hasPermission name="order:order:order:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>