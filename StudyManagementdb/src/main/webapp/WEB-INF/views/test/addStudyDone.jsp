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
<title>編集完了</title>
</head>
<body>
   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <c:choose>
               <c:when test="${empty exsit}">
                  <h1>学習を開始します</h1>
                  <p>
                     <c:out value="${wordName}" />
                     の学習を開始します。
                  </p>
                  <p>リストへ戻って確認をしてください。</p>
               </c:when>
               <c:otherwise>
                  <h1>既に学習をしています</h1>
                  <p>
                     <c:out value="${wordName}" />
                     はすでに学習をしています。
                  </p>
                  <p>リストへ戻って確認をしてください。</p>
               </c:otherwise>
            </c:choose>

            <a href=" <spring:url value="/listWords" />" class="btn btn-primary btn-block">単語一覧へ戻る</a>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>

   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>

</body>
</html>