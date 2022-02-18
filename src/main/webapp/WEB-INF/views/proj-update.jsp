<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="프로젝트 수정" />
<%@ include file="/WEB-INF/views/layout/head.jspf"%>


<script>
    function submitAddForm(form) {
        form.title.value = form.title.value.trim();
        if (form.title.value.length == 0) {
            alert('프로젝트 제목을 입력해주세요.');
            form.title.focus();
            return false;
        }
        form.stack.value = form.stack.value.trim();
        if (form.stack.value.length == 0) {
            alert('프로젝트에 사용된 스택을 선택해주세요.');
            form.stack.focus();
            return false;
        }
        form.submit();
    }

</script>



<form class="con common-form" action="./doUpdate" method="POST"
      onsubmit="submitUpdateForm(this); return false;">
    <input type="hidden" name="projSeq" value="${proj.projSeq}">
    <div>
        <span> 작성자 </span>
        <div>
            <input name="user" type="text" placeholder="작성자"
                   value="${proj.user}">
        </div>
    </div>

    <div>
        <span> 프로젝트 제목 </span>
        <div>
            <input name="title" type="text" placeholder="프로젝트 제목"
                   autofocus="autofocus" value="${proj.title}">
        </div>
    </div>

    <div>
        <span> 깃허브 url </span>
        <div>
            <input name="url" type="text" placeholder="깃허브 url"
                   value="${proj.url}">
        </div>
    </div>

    <div>
        <span> 프로젝트 스택 </span>
        <div>
            <textarea name="stack" placeholder="프로젝트 스택"
                      value="${proj.stack}"
            ></textarea>
        </div>
    </div>

    <div>
        <span> 프로젝트 제작기간 </span>
        <div>
            <input name="period" placeholder="프로젝트 제작기간" value="${proj.period}"></input>
        </div>
    </div>

    <div>
        <span> 프로젝트 내용 </span>
        <div>
            <textarea name="content" placeholder="프로젝트 내용" value="${proj.content}"></textarea>
        </div>
    </div>

    <div>
        <span> 프로젝트 파일 </span>
        <div>
            <input name="file" multiple="multiple" type="file"></input>
        </div>
    </div>

    <div>
        <span> 수정 </span>
        <div>
            <input type="submit" value="수정완료">
            <input type="reset" value="뒤로가기" onclick="history.back();">
        </div>
    </div>
</form>


<div class="btns con">
    <a href="./proj">프로젝트 리스트</a>
    <a href="./projnew">프로젝트 추가</a>
    <a onclick="if ( confirm('삭제하시겠습니까?') == false ) return false;" href="./projdelete?projSeq=${proj.projSeq}">프로젝트 삭제</a>
</div>
<%@ include file="/WEB-INF/views/layout/foot.jspf"%>
