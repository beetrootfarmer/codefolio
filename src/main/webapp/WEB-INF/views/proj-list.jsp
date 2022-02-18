<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="프로젝트 리스트" />
<%@ include file="/WEB-INF/views/layout/head.jspf"%>

    <h2 class="con">전체 게시물 개수 : ${totalProj}</h2>
    <div class="con">
        <c:forEach var="proj" items="${projList}" varStatus="status">
            <section>
                <a href="./projdetail?projSeq=${proj.proj_seq}">
                    번호 : ${proj.projSeq},
                    작성자 : ${proj.user},
                    프로젝트 제목 : ${proj.title},
                    사용 스택 : ${proj.stack},
                    조회수 : ${proj.view},
                    파일 : ${proj.file}<br>
                </a>
            </section>
            <hr>
        </c:forEach>
    </div>
    <div class="btns con">
        <a href="./projnew">프로젝트 등록</a>
    </div>

<%@ include file="/WEB-INF/views/layout/foot.jspf"%>

