<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>프로젝트 상세페이지</title>
    <style>
        .con {
            width:1000px;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<h1 class="con">프로젝트 상세페이지</h1>

<section class="con">
    ==카멜==<br>
    번호 : ${proj.projSeq}<br>
    작성자 : ${proj.projUser}<br>
    깃허브 아이디 : ${proj.projGitId}<br>
    프로젝트 제목 : ${proj.projTitle}<br>
    사용 스택 : ${proj.projStack}<br>
    좋아요 : ${proj.projView}<br>
    조회 : ${proj.projLike}<br>
    <hr>
    ==언더바는 에러남==<br>



</section>

<div class="btns con">
    <a href="./proj">프로젝트 리스트</a>
    <a href="./projnew">프로젝트 추가</a>
    <a href="./modify?seq=${proj.projSeq}">프로젝트 수정</a>
    <a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" href="./projdelete?projSeq=${proj.projSeq}">프로젝트 삭제</a>
</div>
</body>
</html>