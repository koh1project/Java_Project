<%@page import="org.springframework.ui.Model"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />


<%--  <link href="<spring:url value="/css/font-face.css" />" rel="stylesheet"> --%>
<meta charset="UTF-8">
<title></title>
</head>
<body>
   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <h1>${correctWordName }</h1>
            <form action="<spring:url value="/nextTest" />" method="POST">
               <input type="hidden" name="correctId" value="${correctId}" />
               <input type="hidden" name="countTotal" value="${countTotal}" />
               <p class="aleart" align="right">
                  ※１０秒経過すると現在の結果を<b>不正解</b>とみなし、次の問題に移動します。
               <table class="table table-bordered">
                  <tr>
                     <th class="col-xs-1">選択</th>
                     <th class="col-lg-4">説明文</th>
                  </tr>
                  <c:forEach items="${question}" var="question">
                     <tr>
                        <th style="text-align: center;">
                           <input type="radio" name="selectId" value="${question.id}" />
                        </th>
                        <td>
                           <c:out value="${question.word.detail}" />
                        </td>
                     </tr>
                  </c:forEach>
               </table>
               <p align="right">
                  <button class="btn btn-warning" onclick="return stopTimeout();">タイマーを止める</button>
                  残り
                  <span id="timer" style="color: red">10</span>
                  秒
               </p>

               <c:out value="${sessionScope.countNow}" />
               問目 /
               <c:out value="${sessionScope.countTotal}" />
               問中

               <p>
               <input type="submit" value="選択" class="btn btn-success btn-lg" style="align: center;"
                  onclick="return checkSelectRadio();"
               />
               </p>
            </form>

         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script>
				var timer = 10

				function startTimeOut() {
					timeOuter = setInterval( function() {
						to( );
					} , 10000 );
				}

				var to = function timeOut() {
					window.location.href = '<spring:url value="/nextTest" />';
				}

				function startTimer() {
					testTimer = setInterval( function() {
						timer--;
						document.getElementById( "timer" ).innerHTML = timer;
					} , 1000 );
				}

				function stopTimeout() {
					clearInterval( timeOuter );
					clearInterval( testTimer );
					return false
				}

				startTimer( );
				startTimeOut( );

				function checkSelectRadio() {
					var selected_flg = false;
					var elm = document.getElementsByName( 'selectId' );
					for ( i = 0 ; i < elm.length ; i++) {
						if (elm[i].checked) {
							selected_flg = true;
							break;
						}
					}
					if (!selected_flg) {
						alert( '回答を選択してください' );
						return false
					}
				}
			</script>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>



</body>
</html>