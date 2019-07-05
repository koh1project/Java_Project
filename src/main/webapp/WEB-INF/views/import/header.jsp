<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- <!DOCTYPE html> -->
<!-- <html lang="ja"> -->
<!-- <head> -->
<!-- <meta charset="UTF-8"> -->
<%-- <link href="<spring:url value="/css/bootstrap.min.css" />" /> --%>
<%-- <link href=" <spring:url value="/css/style.css" />" rel="stylesheet" /> --%>
<!-- <title>Insert title here</title> -->

<!-- </head> -->

<!-- <body> -->
<!--   <div class="container"> -->

<nav class="navbar navbar-default">
   <div class="container-fluid">
      <div class="col-md-12">
         <div class="row">
            <%--       <a href="<spring:url value="/listWordsToStudyToday" />" class="navbar-text"> --%>
            <span class="navbar-text"> 学習管理システム</span>

            <ul class="nav navbar-nav navbar-left">
               <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                     aria-haspopup="true" aria-expanded="false"
                  >
                     単語一覧
                     <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                     <li><a href="<spring:url value="/listWordsToStudyToday" />" class="navbar-text">本日学習タイミングの単語一覧</a>
                     <li><a href="<spring:url value="/listWords" />" class="navbar-text">単語一覧</a>
                     <li><a href="<spring:url value="/listStudyingWords" />" class="navbar-text">学習中の単語一覧</a></li>
                  </ul></li>
            </ul>
            <a href="<spring:url value="/studySelect" />" class="navbar-text">学習選択</a>
            <a href="<spring:url value="/testSelect" />" class="navbar-text">テスト</a>
            <%--       <c:if test="${sessionScope.loginUser.userType.id >= 2 }"> --%>
            <%--          <a href="<spring:url value="/listUsers" />" class="navbar-text">ユーザー管理</a> --%>
            <%--       </c:if> --%>
            <ul class="nav navbar-nav navbar-right">
               <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                     aria-haspopup="true" aria-expanded="false"
                  >
                     <c:out value="${loginUserName}" />
                     <span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                     <c:choose>
                        <c:when test="${ sessionScope.loginUser.userType.id >= 2}">
                           <li><a href="<spring:url value="/listUsers" />" class="navbar-text">ユーザー管理</a>
                        </c:when>
                        <c:otherwise>
                           <li><a href="<spring:url value="/editOwn/${sessionScope.loginUserId}" />"
                                 class="navbar-text"
                              >登録情報管理</a>
                        </c:otherwise>
                     </c:choose>
                     <li><a href="<spring:url value="/logout" />" class="navbar-text">ログアウト</a></li>
                  </ul></li>
            </ul>
         </div>
      </div>
   </div>
</nav>

<!--   </div> -->
<%--   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script> --%>
<%--   <script src="<spring:url value="/js/bootstrap.min.js" />"></script> --%>
<!-- </body> -->
<!-- </html> -->