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
            <div class="row">
               <form:form modelAttribute="word">
                  <form:hidden path="created" />
                  <form:hidden path="user.id" value="${loginUser.id}" />
                  <table class="table table-bordered">
                     <thead>
                        <tr>
                           <th>項目</th>
                           <td style="vertical-align: middle;">内容
                        </tr>
                     </thead>
                     <tbody>
                        <tr>
                           <th style="vertical-align: middle;">単語名</th>
                           <td style="vertical-align: middle;">
                              <p>
                                 <form:errors path="name" />
                              </p>
                              <form:input path="name" value="${word.name}" />
                        </tr>
                        <tr>
                           <th style="vertical-align: middle;">説明</th>
                           <td style="vertical-align: middle;">
                              <p>
                                 <form:errors path="detail" />
                              </p>
                              <form:input path="detail" value="${word.detail}" />
                        </tr>
                     </tbody>
                     <tr>
                        <th style="vertical-align: middle;">教科</th>
                        <td style="vertical-align: middle;">
                           <p>
                              <form:select path="subject.id" items="${subjects}" itemLabel="name" itemValue="id"
                                 selected="${word.subject.id}"
                              />
                           </p>
                           <p>
                              新規教科として登録する：
                              <input type="text" name="newSubject" placeholder="新規教科名">
                           </p>
                        </td>
                     </tr>
                     <!--           上位ユーザーなら必須項目にするか選べる -->
                     <c:if test="${loginUser.userType.id >= 2}">
                        <tr>
                           <th style="vertical-align: middle;">必修の有無</th>
                           <td style="vertical-align: middle;">
                              <input type="checkbox" name="compulsory" value="1" checked="checked">
                              必修扱いにする
                           </td>
                        </tr>
                     </c:if>
                  </table>
                  <input type="submit" class="btn btn-primary btn-block" value="保存" id="editWord" />
               </form:form>
            </div>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
      <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
      <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
   </div>
</body>
</html>