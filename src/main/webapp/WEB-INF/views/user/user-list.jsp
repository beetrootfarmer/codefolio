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
            번호 : ${user.user_seq},
            아이디 : ${user.user_id},
            비번 : ${user.user_pw},
            이름 : ${user.user_name},
            이메일 : ${user.user_email},
            깃아이디 : ${user.user_gitId},
        </section>
        <hr>
    </c:forEach>

</body>
</html>
