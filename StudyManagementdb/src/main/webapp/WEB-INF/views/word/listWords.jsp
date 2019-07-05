<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />
<meta charset="UTF-8">
<title>単語一覧</title>
</head>
<div class="container">
   <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
   <div class="row">
      <div class="col-md-12">
         <div class="row">
            <p>
            <h1>単語一覧</h1>
            <p>
               <a href="<spring:url value="addWord" />" class="btn btn-success btn-lg">単語を追加する</a>
            </p>
            <a href="<spring:url value="/studySelect" />" class="btn btn-warning btn-lg">学習をする</a>
            <form action="" method="get">
               <p align="right">
                  <c:forEach items="${subjects}" var="subject">
                     <input type="checkbox" name="subject" value="${subject.id}">
                     <c:out value="${subject.name}" />
                  </c:forEach>
                  <select name="compulsory" class="select">
                     <option value="false" selected="selected">全ての単語</option>
                     <option value="true">必修単語のみ</option>
                  </select>
                  <input type="text" name="keyWord" placeholder="キーワード">
                  <input type="submit" value="検索">
               <p>
            </form>
            <nav class="pagination-sm">
               <ul class="pagination">
                  <c:choose>
                     <c:when test="${currentPage > 4}">
                        <c:set var="startPage" value="${currentPage -3 }" />
                     </c:when>
                     <c:otherwise>
                        <c:set var="startPage" value="1" />
                     </c:otherwise>
                  </c:choose>
                  <c:choose>
                     <c:when test="${currentPage+3 <= totalPages }">
                        <c:set var="endPage" value="${currentPage +3 }" />
                     </c:when>
                     <c:otherwise>
                        <c:set var="deduction" value="${totalPages+0 - currentPage}" />
                        <c:set var="endPage" value="${currentPage + deduction}" />
                     </c:otherwise>
                  </c:choose>
                  <c:if test="${totalPages > 1}">
                     <c:choose>
                        <c:when test="${currentPage == 1}">
                           <li class="page-item disabled"><a class="page-link-disabled" href="#">前のページ</a></li>
                        </c:when>
                        <c:otherwise>
                           <li class="page-item"><a class="page-link"
                                 href="?p=<c:out value="${currentPage - 1}"/><c:out value="${appendixtURL}"/>"
                              >前のページ</a></li>
                        </c:otherwise>
                     </c:choose>
                     <c:forEach begin="${startPage}" end="${endPage}" var="p">
                        <c:choose>
                           <c:when test="${currentPage == p}">
                              <li class="page-item"><a class="page-link" style="color: #000000;">
                                    <c:out value="${p}" />
                                 </a></li>
                           </c:when>
                           <c:otherwise>
                              <li class="page-item"><a class="page-link"
                                    href="?p=<c:out value="${p}"/><c:out value="${appendixtURL}"/>"
                                 >
                                    <c:out value="${p}" />
                                 </a></li>
                           </c:otherwise>
                        </c:choose>
                     </c:forEach>
                     <c:choose>
                        <c:when test="${currentPage == totalPages}">
                           <li class="page-item disabled"><a class="page-link" href="#">次のページ</a></li>
                        </c:when>
                        <c:otherwise>
                           <li class="page-item"><a class="page-link"
                                 href="?p=<c:out value="${currentPage + 1}" /><c:out value="${appendixtURL}"/>"
                              >次のページ</a></li>
                        </c:otherwise>
                     </c:choose>
                  </c:if>
               </ul>
            </nav>
         </div>
      </div>
      <div class="col-md-12">
         <div class="row">
            <div class="col-md-10">
               <table class="table table-bordered">
                  <thead>
                     <tr>
                        <th scope="col" class="col-xs-2">単語名</th>
                        <th scope="col" class="col-xs-2">教科</th>
                        <td scope="col" class="col-xs-4">説明</td>
                        <td scope="col" class="col-xs-2">詳細</td>
                     </tr>
                  </thead>
                  <tbody>
                     <c:forEach items="${wordsList}" var="word">
                        <tr>
                           <th scope="row">
                              <c:out value="${word.name}" />
                              <!--             学習中だったら表示するループ -->
                              <c:forEach items="${studiesList}" var="study">
                                 <c:if test="${word.id == study.word.id && study.user.id == sessionScope.loginUserId }">
                                    <span class="label label-default">学習中</span>
                                 </c:if>
                              </c:forEach>
                           </th>
                           <td>
                              <c:out value="${word.subject.name}" />
                           </td>
                           <td class="col-xs-2">
                              <c:out value="${word.detail}" />
                           </td>
                           <td>
                              <a href="<spring:url value="/detailWord/${word.id}" />"
                                 class="btn btn-primary btn-sm active"
                              > 詳細 </a>
                              <c:if test="${not empty word.compulsory}">
                                 <span style="color: red;"> ※必修</span>
                              </c:if>
                           </td>
                     </c:forEach>
                  </tbody>
               </table>
            </div>
            <div class="col-md-2">
               <div class="card text-white bg-default" style="font-size: 15px;">
                  <h5 class="card-header"></h5>
                  <div class="card-body">
                     <p class="card-text">・学習中の単語は単語名の右に「学習中」と表示されます。</p>
                     <p class="card-text">・必修の単語は詳細の横に「詳細」と表示されます</p>
                     <p class="card-text">・学習開始するには、単語の詳細ページから「学習を開始する」とクリックしてください</p>
                  </div>
               </div>
               <div class="col-md-4">
                  <div class="row">
                     <img alt="" src=" <spring:url value="/photo/doala.jpg" />" height="200" width="160" />
                  </div>
               </div>
            </div>
         </div>
      </div>
      <!--       <p> -->
      <%--          <a href="<spring:url value="/listWordsToStudyToday"  />" class="btn btn-primary btn-lg active" role="button">本日学習タイミングの単語一覧へ戻る</a> --%>
      <!--       </p> -->
   </div>
   <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
</div>
<script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
<script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>