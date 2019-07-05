<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href=" <spring:url value="/css/style.css" />" rel="stylesheet" />
<title>会員管理</title>
</head>
<body>
   <div class="container">
      <%--       <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" /> --%>
      <div class="row">
         <div class="col-md-12">
            <div class="row">



               <div class="col-md-10">
                  <div class="panel panel-default">
                     <div class="panel-heading">学習管理システム</div>
                     <h1>ログアウトしました</h1>
                     <p>
                        <a href="login" class="btn btn-primary btn-block">ログイン画面に戻る</a>
                     </p>
                  </div>
               </div>

               <div class="col-md-2">
                  <img alt="" src=" <spring:url value="/photo/tsuba.jpg" />" height="150" width="200" />
               </div>
            </div>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>