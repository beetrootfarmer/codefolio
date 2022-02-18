<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="프로젝트 상세 페이지" />
<%@ include file="/WEB-INF/views/layout/head.jspf"%>

<section class="con">
    번호 : ${proj.projSeq}<br>
    작성자 : ${proj.user}<br>
    깃레포 url : ${proj.url}<br>
    프로젝트 제목 : ${proj.title}<br>
    사용 스택 : ${proj.stack}<br>
    조회수 : ${proj.view}<br>
    내용 : ${proj.content}<br>
    제작기간 : ${proj.period}<br>
    파일 : ${proj.file}<br>
    글 등록 날짜 : ${proj.regDate}<br>
    <hr>

</section>

<div class="btns con">
    <a href="./proj">프로젝트 리스트</a>
    <a href="./projnew">프로젝트 추가</a>
    <a href="./projupdate?projSeq=${proj.projSeq}">프로젝트 수정</a>
    <a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" href="./projdelete?projSeq=${proj.projSeq}">프로젝트 삭제</a>
</div>
<%@ include file="/WEB-INF/views/layout/foot.jspf"%>
