<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html lang="zh-cmn-Hans">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>单号查询结果</title>
    <meta name="Keywords" content="快递单号查询结果">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui">
    <link rel="stylesheet" href="${ctxStatic}/smart/mbase_v6.css?version=201908231700">
    <link rel="stylesheet" href="${ctxStatic}/smart/query_result.css?version=201908231700">
</head>

<body style="">

<div class="container">
    <div id="main" class="main">
        <div class="head">
            <div class="flex input"><input type="text" readonly value="${carriers}">
                <div class="clear"></div> <!---->
                <div class="search"></div>
            </div>
        </div>
        <section class="result-box">
            <div class="result-success" style="">
                <div id="resultTop" class="flex result-top">
                    <time class="up">时间</time>
                    <span>地点和跟踪进度</span></div>
                <ul class="result-list sortup">

                    <c:forEach items="${searchData.traces}" var="sd">
                        <li class="last finish">
                            <div class="time">
                                <div>${sd.acceptTime}</div>
                                    <%-- <div>16:38</div>--%>
                            </div>
                            <div class="dot"></div>
                            <div class="text">
                                    ${sd.acceptStation}
                            </div>
                        </li>
                    </c:forEach>


                </ul>
                <div class="more bottom5" style="display: none;"><span class="more-text"></span></div> <!---->

            </div>
            <c:if test="${not empty  searchData.reason   }">
            <div class="result-fail m-placeholder" ><img
                    src="https://cdn.kuaidi100.com/images/m/placeholders/empty.png" alt="" class="img">
                <p>${searchData.reason}</p></div>
            </c:if>
        </section>
    </div>
</div>


</body>
</html>