<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />

<meta charset="UTF-8">


<title>ユーザ一覧</title>

</head>
<div class="container">

   <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />

   <div class="row">
      <div class="col-md-12">
         <p>
         <h1>ユーザ一覧</h1>
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
         <p>
            <a href="<spring:url value="addUser"/>" class="btn btn-success btn-lg">新規ユーザを追加する</a>
         </p>
         <table class="table table-bordered">
            <thead>
               <tr>
                  <th scope="col">ユーザ名</th>
                  <td scope="col">ふりがな</td>
                  <td scope="col">ユーザ種別</td>
                  <td scope="col">メールアドレス</td>
                  <td scope="col">詳細</td>
               </tr>
            </thead>
            <tbody>
               <c:forEach items="${usersList}" var="user">
                  <tr>
                     <th scope="row">
                        <c:out value="${user.name}" />
                     </th>
                     <td>
                        <c:out value="${user.kana}" />
                     </td>
                     <td>
                        <c:out value="${user.userType.name}" />
                     </td>
                     <td>
                        <c:out value="${user.mailAddress}" />
                     </td>

                     <td>
                        <a href="<spring:url value="/detailUser/${user.id}" />" class="btn btn-primary btn-sm active">
                           詳細 </a>
                     </td>
               </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
   <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
</div>
<script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
<script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>