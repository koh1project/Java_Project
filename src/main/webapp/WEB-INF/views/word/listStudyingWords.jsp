<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />
<meta charset="UTF-8">
<title>本日学習タイミング</title>
</head>
<div class="container">
   <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
   <div class="row">
      <div class="col-md-12">
         <p>
         <h1>学習中の単語一覧</h1>
         <a href="<spring:url value="/studySelect" />" class="btn btn-warning btn-lg">学習をする</a>
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
               <c:if test="${currentPage > totalPage - 4 }">
                  <c:set var="endPages" value="${currentPage +3 }" />
               </c:if>
               <c:if test="${totalPages > 1}">
                  <c:choose>
                     <c:when test="${currentPage == 1}">
                        <li class="page-item disabled"><a class="page-link-disabled" href="#">前のページ</a></li>
                     </c:when>
                     <c:otherwise>
                        <li class="page-item"><a class="page-link" href="?p=<c:out value="${currentPage - 1}" />">前のページ</a></li>
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
                           <li class="page-item"><a class="page-link" href="?p=<c:out value="${p}" />">
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
                        <li class="page-item"><a class="page-link" href="?p=<c:out value="${currentPage + 1}" />">次のページ</a></li>
                     </c:otherwise>
                  </c:choose>
               </c:if>
            </ul>
         </nav>
         <table class="table table-bordered">
            <thead>
               <tr>
                  <th scope="col">単語名</th>
                  <th scope="col">教科</th>
                  <td scope="col">最終学習日</td>
                  <td scope="col">詳細</td>
               </tr>
            </thead>
            <tbody>
               <c:forEach items="${studiesList}" var="study">
                  <tr>
                     <th scope="row">
                        <c:out value="${study.word.name}" />
                     </th>
                     <td>
                        <c:out value="${study.word.subject.name}" />
                     </td>
                     <td>
                        <c:out value="${study.lastStudyDate}" />
                     </td>
                     <td>
                        <a href="<spring:url value="/detailWord/${study.word.id}" /> "
                           class="btn btn-primary btn-sm active"
                        > 詳細 </a>
                        <c:if test="${not empty study.word.compulsory}">
                           <span style="color: red;"> ※必修</span>
                        </c:if>
                     </td>
                     <c:choose>
                        <c:when test="${study.achievement == 0}">
                           <tr>
                              <th scope="row">達成度</th>
                              <td colspan="3">0%</td>
                           </tr>
                        </c:when>
                        <c:otherwise>
                           <tr>
                              <th scope="row">達成度</th>
                              <td colspan="3" class="graph">
                                 <span class="bar" style="width:${study.achievement}%;">
                                    <c:out value="${study.achievement }" />
                                    %
                                 </span>
                              </td>
                           </tr>
                        </c:otherwise>
                     </c:choose>
               </c:forEach>
            </tbody>
         </table>
      </div>
      <%--       <a href="<spring:url value="/listWords" />">一覧へ戻る</a> --%>
      <%--       <a href="<spring:url value="/logout" />">ログアウト</a> --%>
   </div>
   <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
</div>
<script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
<script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>