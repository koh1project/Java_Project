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
            <form:form modelAttribute="user">
               <form:hidden path="created" />
               <table class="table table-bordered">
                  <thead>
                     <tr>
                        <th>項目</th>
                        <td>内容
                     </tr>
                  </thead>
                  <tbody>
                     <tr>
                        <th style="vertical-align: middle;">氏名</th>
                        <td style="vertical-align: middle;">
                           <p>
                              <form:errors path="name" />
                           </p>
                           <form:input path="name" value="${user.name}" />
                     </tr>
                     <tr>
                        <th style="vertical-align: middle;">ふりがな ※ひらがな入力</th>
                        <td style="vertical-align: middle;">
                           <p>
                              <form:errors path="kana" />
                           </p>
                           <form:input path="kana" value="${user.kana}" />
                     </tr>
                     <tr>
                        <th style="vertical-align: middle;">ログイン名</th>
                        <td style="vertical-align: middle;">
                           <p>
                              <form:errors path="loginNameId" />
                           </p>
                           <form:input path="loginNameId" value="${user.loginNameId}" />
                     </tr>
                     <tr>
                        <th style="vertical-align: middle;">パスワード</th>
                        <td style="vertical-align: middle;">
                           <p>
                              <form:form modelAttribute="login">
                                 <form:errors path="pass" />
                           </p>
                           <form:password path="pass" />
                           </form:form>
                     </tr>
                     <tr>
                        <th style="vertical-align: middle;">メールアドレス※半角入力</th>
                        <td style="vertical-align: middle;">
                           <p>
                              <form:errors path="mailAddress" />
                           </p>
                           <form:input path="mailAddress" value="${user.mailAddress}" />
                     </tr>
                     <tr>
                        <th style="vertical-align: middle;">ユーザ種別</th>
                        <td style="vertical-align: middle;">
                           <form:select path="userType.id" items="${userTypes}" itemLabel="name" itemValue="id" />
                        </td>
                     </tr>
                  </tbody>
               </table>

               <div class="row">

                  <input type="submit" class="btn btn-primary btn-block" value="保存" id="editWord" />
               </div>
            </form:form>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
      <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
      <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
   </div>
</body>
</html>