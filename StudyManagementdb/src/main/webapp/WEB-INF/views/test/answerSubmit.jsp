<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />
<meta charset="UTF-8">
<title></title>
</head>
<body>
   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <c:choose>
               <c:when test="${isCorrect}">
                  <h1 align="center" id="answer">〇</h1>
               </c:when>
               <c:otherwise>
                  <h1 align="center" id="answer">×</h1>
               </c:otherwise>
            </c:choose>
            <p>
               <c:out value="${sessionScope.countNow}" />
               問目 /
               <c:out value="${sessionScope.countTotal}" />
               問中
            </p>
            <p align="right">
               <a href="<spring:url value="/nextStudy"  />" class="btn btn-success btn-lg"> 次の問題へ</a>
            </p>
            <p>
            <a href="<spring:url value="/listWords" />" class="btn btn-danger"> 問題を中断してリスト一覧へ戻る </a>
       </p>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>