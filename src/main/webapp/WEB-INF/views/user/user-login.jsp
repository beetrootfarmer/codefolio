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
<h1>로그인</h1>

<!--js로 form 확인-->
<script>
    function submitAddForm(form){

        form.userId.value = form.userId.value.trim();
        if(form.userId.value.length==0){
            alert('id를 입력해주세요.');
            form.userId.focus();
            return false;
        }

        form.userPwd.value = form.userPwd.value.trim();
        if(form.userPwd.value.length==0){
            alert('비밀번호를 입력해주세요.');
            form.userPwd.focus();
            return false;
        }

        form.submit();
    }
</script>

<!--버튼을 누르면 createUser를 controller에서 처리-->
<form action="./createUser" method="POST" onsubmit="submitAddForm(this); return false;">
    <div>
        <span>id</span>
        <div>
            <input name="userId" type="text" placeholder="id" autofocus="autofocus">
        </div>
        <span>password</span>
        <div>
            <input name="userPwd" type="text" placeholder="password" autofocus="autofocus">
        </div>
        <div>
            <input type="submit" value="로그인">
        </div>
    </div>
    <div>
        <input type="reset" value="회원가입">
    </div>
    </div>
</form>

</body>
</html>
