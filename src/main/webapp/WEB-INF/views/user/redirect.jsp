<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
    alertMsg='${alertMsg}';
    historyBack='${historyBack}'=='true';
</script>
<script>
    if(alertMsg){
        alert((alertMsg));
    }
    if(historyBack){
        history.back();
    }
</script>

