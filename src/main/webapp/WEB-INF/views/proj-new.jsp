<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageName" value="새로운 프로젝트" />
<%@ include file="/WEB-INF/views/layout/head.jspf"%>

    <script>
        function submitAddForm(form) {
            form.title.value = form.title.value.trim();
            if (form.title.value.length == 0) {
                alert('프로젝트 제목을 입력해주세요.');
                form.projTitle.focus();
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

    <form class="con common-form" action="./doAdd"
          method="POST" onsubmit="submitAddForm(this); return false;">

<%--    게시글 번호 쿼리처리--%>

<%--        회원로그인 시 자동 입력되어서 고정란 cif사용--%>
        <div>
            <span> 작성자 </span>
            <div>
                <input name="user" type="text" placeholder="작성자">
            </div>
        </div>

        <div>
            <span> 프로젝트 제목 </span>
            <div>
                <input name="title" type="text" placeholder="프로젝트 제목" autofocus="autofocus">
            </div>
        </div>

        <div>
            <span> 깃허브 url </span>
            <div>
                <input name="url" type="text" placeholder="깃허브 url">
            </div>
        </div>

<%--    드롭다운 복수선택 혹은 라디오 버튼으로 --%>
        <div>
            <span> 프로젝트 스택 </span>
            <div>
                <textarea name="stack" placeholder="프로젝트 스택"></textarea>
            </div>
        </div>

        <div>
            <span> 프로젝트 제작기간 </span>
            <div>
                <input name="period" placeholder="프로젝트 제작기간"></input>
            </div>
        </div>

        <div>
            <span> 프로젝트 내용 </span>
            <div>
                <textarea name="content" placeholder="프로젝트 내용"></textarea>
            </div>
        </div>

        <div>
            <span> 프로젝트 파일 </span>
        <button id="btn-upload" type="button" style="border: 1px solid #ddd; outline: none;">파일 추가</button>
        <input id="input_file" name="file" multiple="multiple" type="file" style="display:none;">
        </div>

        <div>
            <span></span>
            <div class="btn">
                <input type="submit" value="완료">
                <input type="reset" value="뒤로가기" onclick="history.back();">
            </div>
        </div>
    </form>

<script>
<%--    ㅍㅏ일 업로드 시 fileCheck 함수 실행--%>
    $(document).ready(function()
        // input file 파일 첨부시 fileCheck 함수 실행
    {
        $("#input_file").on("change", fileCheck);
    });

    //첨부파일 로직
    $(function () {
        $('#btn-upload').click(function (e) {
            e.preventDefault();
            $('#input_file').click();
        });
    });

// 파일 현재 필드 숫자 totalCount랑 비교값
var fileCount = 0;
// 해당 숫자를 수정하여 전체 업로드 갯수를 정한다.
var totalCount = 10;
// 파일 고유넘버
var fileNum = 0;
// 첨부파일 배열
var content_files = new Array();

function fileCheck(e) {
    var files = e.target.files;

    // 파일 배열 담기
    var filesArr = Array.prototype.slice.call(files);

    // 파일 개수 확인 및 제한
    if (fileCount + filesArr.length > totalCount) {
        $.alert('파일은 최대 '+totalCount+'개까지 업로드 할 수 있습니다.');
        return;
    } else {
        fileCount = fileCount + filesArr.length;
    }

    // 각각의 파일 배열담기 및 기타
    filesArr.forEach(function (f) {
        var reader = new FileReader();
        reader.onload = function (e) {
            content_files.push(f);
            $('#articlefileChange').append(
                '<div id="file' + fileNum + '" onclick="fileDelete(\'file' + fileNum + '\')">'
                + '<font style="font-size:12px">' + f.name + '</font>'
                + '<img src="/img/icon_minus.png" style="width:20px; height:auto; vertical-align: middle; cursor: pointer;"/>'
                + '<div/>'
            );
            fileNum ++;
        };
        reader.readAsDataURL(f);
    });
    console.log(content_files);
    //초기화 한다.
    $("#input_file").val("");
}

// 파일 부분 삭제 함수
function fileDelete(fileNum){
    var no = fileNum.replace(/[^0-9]/g, "");
    content_files[no].is_delete = true;
    $('#' + fileNum).remove();
    fileCount --;
    console.log(content_files);
}

/*
 * 폼 submit 로직
 */
function registerAction(){

    var form = $("form")[0];
    var formData = new FormData(form);
    for (var x = 0; x < content_files.length; x++) {
        // 삭제 안한것만 담아 준다.
        if(!content_files[x].is_delete){
            formData.append("article_file", content_files[x]);
        }
    }
    /*
    * 파일업로드 multiple ajax처리
    */
    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/file-upload",
        data : formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if(JSON.parse(data)['result'] == "OK"){
                alert("파일업로드 성공");
            } else
                alert("서버내 오류로 처리가 지연되고있습니다. 잠시 후 다시 시도해주세요");
        },
        error: function (xhr, status, error) {
            alert("서버오류로 지연되고있습니다. 잠시 후 다시 시도해주시기 바랍니다.");
            return false;
        }
    });
    return false;
}
//
</script>
<%@ include file="/WEB-INF/views/layout/foot.jspf"%>

