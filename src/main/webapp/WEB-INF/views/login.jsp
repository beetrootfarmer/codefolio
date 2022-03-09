<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <div>
    <section >
    <div class="container">
              <form action="/pawmap/login" method="post">
                  <div>
                      <input type="text"name="id" placeholder="Id" required>
                  </div>
                <div>
                <input  id="password-field" type="password"  name="pwd" placeholder="Password" required>
                <span toggle="#password-field" class="fa fa-fw fa-eye field-icon toggle-password"></span>
                </div>
                <div class="form-group">
                    <button type="submit" class="form-control btn btn-primary submit px-3">로그인</button>
                </div>
          </form>
          <p class="w-100 text-center" style="padding-bottom: 15px; padding-top: 15px;">&mdash; 소셜 계정으로 이용하기  &mdash;</p>
         
          <div class="social d-flex text-center" style="padding-left: 70px;">
            <a href="/codefolio/oauth2/authorization/kakao" class=" circle ion-logo-kakaotalk"></a>
            <a href="/codefolio/oauth2/authorization/naver" class="circle ion-logo-naver"></a>
              <a href="/codefolio/oauth2/authorization/google" class=" circle ion-logo-google"></a>
              <a href="/codefolio/oauth2/authorization/facebook" class="circle ion-logo-facebook"></a>
            
        </div>
</section>
</div>