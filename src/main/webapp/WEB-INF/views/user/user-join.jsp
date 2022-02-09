<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
</head>
<body>
<h1>회원가입</h1>

    <!--js로 form 확인-->
    <script>
        function submitJoinForm(form){
            form.userName.value = form.userName.value.trim();
            if(form.userName.value.length==0){
                alert('이름을 입력해주세요.');
                form.userName.focus();
                return false;
            }

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

            form.userPwdConfirm.value = form.userPwdConfirm.value.trim();
            if(form.userPwd.value!=form.userPwdConfirm.value){
                alert('비밀번호가 일치하지 않습니다..');
                form.userPwdConfirm.focus();
                return false;
            }

            form.userEmail.value = form.userEmail.value.trim();
            if(form.userEmail.value.length==0){
                alert('email를 입력해주세요.');
                form.userEmail.focus();
                return false;
            }

            form.submit();
        }
    </script>

    <!--버튼을 누르면 createUser를 controller에서 처리-->
    <form action="./doJoin" method="POST" onsubmit="submitJoinForm(this); return false;">
        <div>
            <span>이름</span>
            <div>
                <input name="userName" type="text" placeholder="name" autofocus="autofocus">
            </div>
            <span>id</span>
            <div>
                <input name="userId" type="text" placeholder="id" autofocus="autofocus">
            </div>
            <span>password</span>
            <div>
                <input name="userPwd" type="text" placeholder="password" autofocus="autofocus">
            </div>
            <span>passwordConfirm</span>
            <div>
                <input name="userPwdConfirm" type="text" placeholder="password" autofocus="autofocus">
            </div>
            <span>email</span>
            <div>
                <input name="userEmail" type="text" placeholder="email" autofocus="autofocus">
            </div>
            <span>gitId</span>
            <div>
                <input name="userGitId" type="text" placeholder="github Id" autofocus="autofocus">
            </div>
            <div>
                <input type="submit" value="회원가입">
            </div>
            <div>
                <input type="reset" value="취소" onclick="history.back();">
            </div>
        </div>
    </form>

</body>
</html>
