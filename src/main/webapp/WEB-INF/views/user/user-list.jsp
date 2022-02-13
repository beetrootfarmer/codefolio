<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 리스트</title>
</head>
<body>

    <h1>사용자 리스트</h1>
    <!--el의 장점은 request.getAttribute("userList") 할 필요가 없다. -->
    <c:forEach var="user" items="${userList}" varStatus="status">

        <section>
            <a href="./detail?userSeq=${user.user_seq}">아이디 : ${user.user_id}</a>
            비번 : ${user.user_pwd},
            이름 : ${user.user_name},
            이메일 : ${user.user_email},
            깃아이디 : ${user.user_gitId}
        </section>
        <hr>
    </c:forEach>

    <div>
        <a href="join">회원가입</a>
        <input type="reset" value="뒤로" onclick="history.back();">
    </div>

</body>
</html>
