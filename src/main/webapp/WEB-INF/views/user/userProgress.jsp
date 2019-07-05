<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />

<meta charset="UTF-8">


<title>学習状況一覧</title>
</head>
<div class="container">
   <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />

   <div class="row">
      <div class="col-md-12">
         <p>
         <h1>
            <c:out value="${user.name}" />
            の学習中単語一覧
         </h1>
         <h2>
            学習総数：
            <span style="color: red;">
               <c:out value="${countTotalStudy }" />
            </span>
         </h2>
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
                  <td scope="col">最終学習日</td>
                  <td scope="col">学習開始日</td>
               </tr>
            </thead>
            <tbody>
               <c:forEach items="${studyList}" var="study">
                  <tr>
                     <th scope="row">
                        <c:out value="${study.word.name}" />
                        <!--             学習中だったら表示するループ -->
                     <td>
                        <c:out value="${study.lastStudyDate}" />
                     </td>
                     <td>
                        <c:out value="${study.created}" />
                     </td>
                  </tr>
                  <tr>
                     <th scope="row">達成度:</th>
                     <td colspan="2" class="graph">
                        <c:choose>
                                 <c:when test="${study.achievement >5 }">
                                    <span class="bar" style="width: ${study.achievement}%;">
                                       <c:out value="${study.achievement }" />
                                       %
                                    </span>
                                 </c:when>
                                 <c:otherwise>
                                    <c:out value="${study.achievement }" />%
                                </c:otherwise>
                              </c:choose>
                     </td>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
      </div>
      <p>
         <a href="<spring:url value="/listUsers"  />" class="btn btn-primary btn-lg active" role="button">ユーザ一覧へ戻る</a>
      </p>
      <p></p>
   </div>
   <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
</div>
<script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
<script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>



