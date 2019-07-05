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
<title>単語追加完了</title>
</head>
<body>
   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <div class="row">
               <h1>
                  「
                  <c:out value="${word.name}" />
                  」を追加しました。
               </h1>
            </div>
            <div class="row">
               <a href="<spring:url value="addWord" />" class="btn btn-primary btn-block">単語を更に追加する</a>
               <a href="<spring:url value="/detailWord/${word.id}" />" class="btn btn-primary btn-block">詳細を確認する</a>
               <a href=" <spring:url value="/listWords" />" class="btn btn-primary btn-block">単語一覧へ戻る</a>
               <a href="<spring:url value="/listWordsToStudyToday" />" class="btn btn-primary btn-block">本日学習タイミングの単語一覧へ戻る</a>
            </div>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>