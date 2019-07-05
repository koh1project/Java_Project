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
               <c:when test="${not empty test}">
                  <!--                テスト終了時 -->
                  <h1>回答が終了しました</h1>
                  <p>
                     <a href="<spring:url value="/result" />" class="btn btn-success btn-lg">結果を確認する</a>
                  </p>
               </c:when>
               <c:otherwise>
                  <!--                学習終了時 -->
                  <h1>問題が終了しました</h1>
                  <p>指定範囲の学習を終了しました。</p>
                  <p>
                     <a href="<spring:url value="/studySelect" />" class="btn btn-primary btn-block">問題選択へ戻る</a>
                  </p>
               </c:otherwise>
            </c:choose>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>