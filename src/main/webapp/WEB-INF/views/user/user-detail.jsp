<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 상세</title>
</head>
<body>
<!--el의 장점은 request.getAttribute("userList") 할 필요가 없다. -->
    <section>
        번호 : ${user.userSeq},
        아이디 : ${user.userId},
        비번 : ${user.userPwd},
        이름 : ${user.userName},
        이메일 : ${user.userEmail},
        깃아이디 : ${user.userGitId}
    </section>
    <hr>

    <div>
        <a href="list">유저 리스트</a>
        <a href="./update?userSeq=${user.userSeq}">유저 수정</a>
        <a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" href="./delete?userSeq=${user.userSeq}">유저 삭제</a>
    </div>
</body>
</html>
