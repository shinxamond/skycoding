<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty eventVo || empty mem_num}">
<script type="text/javascript">
	alert('로그인 후 이용해주세요');
	location.href='../member/loginForm.do';
</script>
</c:if>
<script type="text/javascript">
	alert('이벤트가 등록되었습니다');
	location.href='eventList.do';
	//location.href='상세강의 페이지';
</script>