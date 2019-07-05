<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />
<title>ユーザ登録</title>
</head>
<body>
   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <h1>ユーザ新規登録</h1>
            <form:form modelAttribute="user">
               <table class="table table-bordered">
                  <thead>
                     <tr>
                        <th>項目</th>
                        <td>内容
                     </tr>
                  </thead>
                  <tbody>
                     <tr>
                        <th>氏名</th>
                        <td>
                           <p>
                              <form:errors path="name" />
                           </p>
                           <form:input path="name" />
                     </tr>
                     <tr>
                        <th>ふりがな ※ひらがな入力</th>
                        <td>
                           <p>
                              <form:errors path="kana" />
                           </p>
                           <form:input path="kana" />
                     </tr>
                     <tr>
                        <th>ログイン名</th>
                        <td>
                           <p>
                              <form:errors path="loginNameId" />
                           </p>
                           <form:input path="loginNameId" />
                     </tr>
                     <form:form modelAttribute="login">
                        <tr>
                           <th>パスワード</th>
                           <td>
                              <p>
                                 <form:errors path="pass" />
                              </p>
                              <form:input path="pass" />
                        </tr>
                     </form:form>
                     <tr>
                        <th>メールアドレス※半角入力</th>
                        <td>
                           <p>
                              <form:errors path="mailAddress" />
                           </p>
                           <form:input path="mailAddress" />
                     </tr>
                     <tr>
                        <th>ユーザ種別</th>
                        <td>
                           <form:select path="userType.id" items="${userTypes}" itemLabel="name" itemValue="id" />
                        </td>
                     </tr>
                  </tbody>
               </table>
               <input type="submit" class="btn btn-primary btn-block" value="追加" id="addUser" />
            </form:form>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>