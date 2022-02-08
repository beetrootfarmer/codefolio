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
    <style>
        .con {
            width:1000px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
    <h1 class="con">프로젝트 리스트</h1>
    <h2 class="con">전체 게시물 개수 : ${totalProj}</h2>
    <div class="con">
        <c:forEach var="proj" items="${projList}" varStatus="status">
            <section>
                <a href="./projdetail?projSeq=${proj.proj_seq}">
                번호 : ${proj.proj_seq},
                작성자 : ${proj.proj_user},
                제목 : ${proj.proj_title},
                깃허브 아이디 : ${proj.proj_gitId},
                좋아요 : ${proj.proj_like},
                조회수 : ${proj.proj_view},
                사용스택 : ${proj.proj_stack}
                </a>
            </section>
            <hr>
        </c:forEach>
    </div>
    <div class="btns con">
        <a href="./projnew">프로젝트 등록</a>
    </div>
</body>
</html>
