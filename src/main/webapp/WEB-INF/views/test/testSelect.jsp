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
            <form action="<spring:url value="/test"  />" method="GET">


               <h1>テスト範囲を選択してください</h1>
               <div class="row">
                  <select name="compulsory" class="select">
                     <option value="true">必修単語のみ</option>
                     <option value="false">全ての単語</option>
                  </select>
               </div>
               <div class="row">
                  最大問題数： <select name="maxNumber" class="select">
                     <option value="10">10問</option>
                     <option value="20">20問</option>
                     <option value="30">30問</option>
                     <option value="40">40問</option>
                     <option value="50">50問</option>
                  </select>
               </div>
               <p>
               <div class="row">
                  教科選択:
                  <c:forEach items="${subjects}" var="subject">
                     <input type="checkbox" name="subject" value="${subject.id}" checked="checked">
                     <c:out value="${subject.name}" />
                  </c:forEach>
                  <p>
                     <input type="submit" class="btn btn-primary btn-block" value="テストを開始する" id="test" />
               </div>
               <div class="row">
                  <p>
                     <a href="<spring:url value="/listWords" />" class="btn btn-primary btn-block">一覧へ戻る</a>
                  </p>
               </div>
            </form>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>

   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>