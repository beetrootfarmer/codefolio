<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>새로운 프로젝트</title>
    <style>
        .con {
            width:1000px;
            margin: 0 auto;
        }
        .common-form > div > * {
            float:left;
        }
        .common-form > div::after {
            content: "";
            display: block;
            clear: both;
        }
        .common-form > div >span {
            width:100px;
            margin-top: 1%;
        }
        .common-form > div > div {
            width: calc(100% - 50px);
        }
        .common-form > div > div > input[type="text"], .common-form > div > div > textarea {
            width:90%;
        }
    </style>
</head>
<body>
    <h1>새로운 프로젝트</h1>

    <script>
        function submitAddForm(form) {
            form.projTitle.value = form.projTitle.value.trim();
            if (form.projTitle.value.length == 0) {
                alert('프로젝트 제목을 입력해주세요.');
                form.projTitle.focus();
                return false;
            }
            form.projStack.value = form.projStack.value.trim();
            if (form.projStack.value.length == 0) {
                alert('프로젝트에 사용된 스택을 선택해주세요.');
                form.projStack.focus();
                return false;
            }
            form.submit();
        }

    </script>

    <form class="con common-form" action="./doAdd"
          method="POST" onsubmit="submitAddForm(this); return false;">

<%--    게시글 번호 쿼리처리--%>

<%--        회원로그인 시 자동 입력되어서 고정란 cif사용--%>
        <div>
            <span> 작성자 </span>
            <div>
                <input name="projUser" type="text" placeholder="작성자">
            </div>
        </div>

        <div>
            <span> 프로젝트 제목 </span>
            <div>
                <input name="projTitle" type="text" placeholder="프로젝트 제목" autofocus="autofocus">
            </div>
        </div>

<%--    회원 로그인 시 자동 입력--%>
        <div>
            <span> 깃허브 아이디 </span>
            <div>
                <input name="projGitId" type="text" placeholder="깃허브 아이디">
            </div>
        </div>

<%--    라디오 버튼으로 --%>
        <div>
            <span> 프로젝트 스택 </span>
            <div>
                <textarea name="projStack" placeholder="프로젝트 스택"></textarea>
            </div>
        </div>

        <div>
            <span></span>
            <div class="btn">
                <input type="submit" value="완료">
                <input type="reset" value="뒤로가기" onclick="history.back();">
            </div>
        </div>
    </form>

</body>
</html>
