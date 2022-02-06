<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>프로젝트 리스트</title>
</head>
<body>
    <h1>프로젝트 리스트</h1>
    <h2>${test}</h2>

    <c:forEach var="proj" items="${projList}" varStatus="status">
        <section>
            번호 : ${proj.proj_seq},
            작성자 : ${proj.proj_user},
            제목 : ${proj.proj_title},
            깃허브 아이디 : ${proj.proj_gitId},
            좋아요 : ${proj.proj_like},
            조회수 : ${proj.proj_view},
            사용스택 : ${proj.proj_stack}
        </section>
        <hr>
    </c:forEach>
    
<%--    테이블 형태로 사용할 경우--%>
<%--    <h2>table</h2>--%>
<%--        <table border="1">--%>
<%--            <tr>--%>
<%--                <th>번호</th>--%>
<%--                <th>작성자</th>--%>
<%--                <th>제목</th>--%>
<%--                <th>깃허브 아이디</th>--%>
<%--                <th>좋아요</th>--%>
<%--                <th>조회수</th>--%>
<%--                <th>사용스택</th>--%>
<%--            </tr>--%>
<%--            <c:forEach items="${projList}" var="proj">--%>
<%--                <tr>--%>
<%--                    <td>${proj.projSeq}</td>--%>
<%--                    <td>${proj.projUser}</td>--%>
<%--                    <td>${proj.projTitle}</td>--%>
<%--                    <td>${proj.projGitId}</td>--%>
<%--                    <td>${proj.projLike}</td>--%>
<%--                    <td>${proj.projView}</td>--%>
<%--                    <td>${proj.projStack}</td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--        </table>--%>

</body>
</html>
