<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />
<meta charset="UTF-8">
<title>ユーザ詳細</title>
</head>
<body>
   <script>
				var url = location.href;

				function deleteConfirm() {
					var name = "<c:out value="${word.name}" />";
					if (confirm( name + "さんの登録情報を削除してもよろしいですか？" )) {
						return true;
					} else {
						return false;
					}
				}
			</script>

   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />

      <div class="row">
         <div class="col-md-12">
            <div class="row">
               <h1>
                  <c:out value="${user.name}" />
               </h1>
            </div>
            <table class="table table-bordered">
               <thead>
                  <tr>
                     <th class="col-md-2">項目</th>
                     <td class="col-md-10">詳細</td>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <th>氏名</th>
                     <td>
                        <c:out value="${user.name }" />
                     </td>
                  </tr>
                  <tr>
                     <th>ふりがな</th>
                     <td>
                        <c:out value="${user.kana }" />
                     </td>
                  </tr>
                  <tr>
                     <th>ログイン名</th>
                     <td>
                        <c:out value="${user.loginNameId}" />
                     </td>
                  </tr>
                  <tr>
                     <th>メールアドレス</th>
                     <td>
                        <c:out value="${user.mailAddress}" />
                     </td>
                  </tr>
                  <tr>
                     <th>ユーザ種別</th>
                     <td>
                        <c:out value="${user.userType.name}" />
                     </td>
                  </tr>
                  <tr>
                     <th>登録日</th>
                     <td>
                        <c:out value="${user.created}" />
                     </td>
                  </tr>
               </tbody>
            </table>
         </div>
         <p>
            <a href="<spring:url value="/userProgress/${user.id}" />" class="btn btn-success btn-lg "> 学習状況を確認する </a>
         </p>
         <p>
            <a href="<spring:url value="/editUser/${user.id}" />" class="btn btn-primary"> 編集 </a>
            <a href="<spring:url value="/deleteUser/${user.id}" />" class="btn btn-danger"
               onclick="return deleteConfirm();"
            >削除</a>
         </p>
         <p>
            <a href=" <spring:url value="/listUsers" />" class="btn btn-primary btn-block">ユーザ一覧へ戻る</a>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>
   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>