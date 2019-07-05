<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="ja">
<head>

<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />

<!-- <link href="../css/bootstrap.min.css" rel="stylesheet" /> -->
<!-- <link href="../css/style.css" rel="stylesheet" /> -->
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>

   <form:form modelAttribute="user" method="POST">
      <div class="container">
         <div class="row">
            <div class="col-md-12">
               <div class="row">
                  <div class="col-md-8">
                     <div class="panel panel-default">
                        <div class="panel-heading">学習管理システム ログイン</div>
                        <c:if test="${not empty nameIdError}">

                           <c:out value="${nameIdError}" />

                        </c:if>
                        <div class="form-group">
                           <form:input path="loginNameId" placeholder="ログインID" class="form-control" />
                        </div>
                        <c:if test="${not empty passError}">

                           <c:out value="${passError}" />

                        </c:if>


                        <div class="form-group">
                           <form:password path="login.pass" placeholder="パスワード" class="form-control" />
                        </div>

                        <c:if test="${not empty errorEither}">

                           <c:out value="${errorEither}" />

                        </c:if>
                        <div class="form-group">
                           <input type="submit" class="btn btn-primary btn-block" value="ログイン" id="login" />
                        </div>
                     </div>
                  </div>
                  <div class="col-md-4">
                     <div class="card text-white bg-default">
                        <h5 class="card-header">お知らせ</h5>
                        <div class="card-body">
                           <p class="card-text">・新着のお知らせはありません。</p>
                           <p class="card-text"></p>
                           <p class="card-text"></p>
                        </div>
                     </div>
                     <div class="col-md-4">
                        <div class="row">
                           <img alt="" src=" <spring:url value="/photo/doala-photo.jpg" />" height="150" width="250" />
                        </div>
                     </div>
                  </div>
               </div>
            </div>
            <c:import url="import/footer.jsp" charEncoding="utf-8" />

         </div>
      </div>
   </form:form>

   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>

   <!--     <script src="../js/jquery-2.2.4.min.js"></script> -->
   <!--    <script src="../js/bootstrap.min.js"></script> -->

</body>
</html>