<%@page import="java.util.List"%>
<%@page import="com.example.javacluborm.domain.Study"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.ArrayList"%>
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
<body>
   <%
   	Object correctStudiesConverter = request.getAttribute("correctStudies");
   	Object tfSheetConverter = request.getAttribute("tfSheet");

   	ArrayList<Study> correctStudies = (ArrayList) correctStudiesConverter;
   	ArrayList<String> tfSheet = (ArrayList<String>) tfSheetConverter;
   %>

   <div class="container">
      <c:import url="/WEB-INF/views/import/header.jsp" charEncoding="utf-8" />
      <div class="row">
         <div class="col-md-12">
            <div class="row">
               <div class="col-md-8">
                  <table class="table table-bordered">
                     <thead>
                        <tr>
                           <th scope="col">単語名</th>
                           <th scope="col">正誤</th>
                           <th scope="col">単語の説明</th>
                        </tr>
                     </thead>
                     <tbody>
                        <%
                        	for (int index = 0; index < tfSheet.size(); index++) {
                        %>
                        <tr>
                           <th class="col-xs-2">
                              <%
                              	out.println(correctStudies.get(index).getWord().getName());
                              %>
                           </th>
                           <td class="col-xs-2" id="answer">
                              <%
                              	out.println(tfSheet.get(index));
                              %>
                           </td>
                           <td class="col-md-6">
                              <%
                              	out.println(correctStudies.get(index).getWord().getDetail());
                              %>
                           </td>
                        </tr>
                        <%
                        	}
                        %>
                     </tbody>
                  </table>
               </div>
               <div class="col-md-4">
                  <p style="font-size: 30px;">
                     <span style="color: red;">
                        <c:out value="${score}" />
                        点
                     </span>
                     <span>
                        /
                        <c:out value="${countNow }" />
                        問中
                     </span>
                  </p>
               </div>
            </div>
         </div>
      </div>
      <div class="row">
         <div class="col-sm-12">
            <p>
               <a href="<spring:url value="/testSelect" />" class="btn btn-primary btn-block">テスト範囲選択画面へ戻る</a>
            </p>

            <p>
               <a href="<spring:url value="/listWords" />" class="btn btn-primary btn-block">一覧へ戻る</a>
            </p>
         </div>
      </div>
      <c:import url="/WEB-INF/views/import/footer.jsp" charEncoding="utf-8" />
   </div>




   <script src="<spring:url value="/js/jquery-2.2.4.min.js" />"></script>
   <script src="<spring:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>
