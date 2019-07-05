<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html lang="ja">
<head>
<link href="<spring:url value="/css/bootstrap.min.css" />" rel="stylesheet" />
<link href="<spring:url value="/css/style.css" />" rel="stylesheet" />
<meta charset="UTF-8">
<title>単語詳細</title>
</head>
<body>
   <script>
				var url = location.href;

				function deleteConfirm() {
					//確認ダイアログを表示する
					var name = "<c:out value="${word.name}" />";
					if (confirm( "本当によろしいですか？" )) {
						return true;
					}
					//    キャンセル時の処理を作る
					else {
						return false;
					}
				}
			</script>

   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <div class="row">
               <p>
               <h1>
                  <c:out value="${word.name}" />
                  <!--             ユーザーが学習をしていなければ -->
                  <c:if test="${empty study}">
                     <p align="right">
                        <a href=" <spring:url value="/addStudy/${word.id}" />" class="btn btn-success">学習を開始する</a>
                     </p>
                  </c:if>
               </h1>
            </div>
            <div class="row">
               <table class="table table-bordered">
                  <thead>

                     <tr>
                        <th scope="col" class="col-md-2">項目</th>
                        <td scope="col" class="col-md-10">詳細</td>
                     </tr>
                  <tbody>
                    <tr>
                        <th>単語名</th>
                        <td>
                           <c:out value="${word.name}" />
                        </td>
                     </tr>
                     <tr>
                        <th>説明</th>
                        <td>
                           <c:out value="${word.detail}" />
                        </td>
                     </tr>
                     <tr>
                        <th>教科</th>
                        <td>
                           <c:out value="${word.subject.name}" />
                        </td>
                     </tr>

                     <c:choose>
                        <c:when test="${empty study}">
                           <tr>
                              <th>
                                 <!--  未学習の場合wordのみ -->
                                 <p id="highlight">達成度：</p>
                              </th>
                              <td>未学習</td>
                           </tr>
                           <tr>
                              <th>
                                 <p id="highlight">次回学習日：</p>
                              </th>
                              <td>未設定(学習を始めることで設定されます)</td>
                           </tr>
                           <tr>
                              <th>登録者</th>
                              <td>
                                 <c:out value="${word.user.name}" />
                              </td>
                           </tr>
                        </c:when>
                        <c:otherwise>
                           <tr>
                              <th>
                                 <p id="highlight">達成度</p>
                              </th>
                              <td class="graph">
                                 <%--                               <span class="bar" style="width: ${study.achievement}%;"> --%>
                                 <%--                                 <c:when test="${study.achievement >10 }"> --%>
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
                           <tr>
                              <!--     学習歴がある -->
                              <th>
                                 <p id="highlight">次回学習日：</p>
                              </th>
                              <td>
                                 <c:out value="${study.nextStudyDate}" />
                              </td>
                           </tr>
                           <tr>
                              <th>登録者</th>
                              <td>
                                 <c:out value="${word.user.name}" />
                              </td>
                           </tr>
                        </c:otherwise>
                     </c:choose>
                  </tbody>
               </table>
            </div>
            <c:choose>
               <c:when test="${empty study}">
                  <c:if test="${loginUserId == word.user.id}">

                        <div class="row">
                           <p>
                              <a href="<spring:url value="/editWord/${word.id}" />" class="btn btn-primary"> 編集 </a>
                              <a href="<spring:url value="/deleteWord/${word.id}" />" class="btn btn-danger"
                                 onclick="return deleteConfirm()"
                              > 削除</a>
                           </p>
                        </div>

                  </c:if>
               </c:when>
               <c:otherwise>
                  <div class="row">
                     <p>
                        <a href="<spring:url value="/resetStudy/${study.id}" />" class="btn  btn-warning">学習をリセット</a>
                        <a href="<spring:url value="/quitStudy/${study.word.id}" />" class="btn btn-danger"
                           onclick="return deleteConfirm();"
                        > 学習を辞める</a>
                     </p>
                  </div>
                  <c:if test="${study.word.user.id == loginUserId}">

                     <div class="row">
                        <p>
                           <a href="<spring:url value="/editWord/${study.word.id}" />" class="btn btn-primary"> 編集 </a>
                           <a href="<spring:url value="/deleteWord/${study.word.id}" />" class="btn btn-danger"
                              onclick="return deleteConfirm();"
                           > 削除</a>
                        </p>
                     </div>

                  </c:if>
               </c:otherwise>
            </c:choose>
            <div class="row">
               <a href=" <spring:url value="/listWords" />" class="btn btn-primary btn-block">単語一覧へ戻る</a>
               <a href="<spring:url value="/listWordsToStudyToday" />" class="btn btn-primary btn-block">本日学習タイミングの単語一覧へ戻る</a>
               <a href="/logout" class="btn btn-primary btn-block">ログアウト</a>
            </div>
            <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
         </div>
      </div>
      <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
      <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>