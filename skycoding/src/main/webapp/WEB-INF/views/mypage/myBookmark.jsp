<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>관심강좌</title>

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/css/mypage-style.css" rel="stylesheet">
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

</head>

<script type="text/javascript">
	window.onload=function(){
		var checks = document.getElementsByName('mycheckbox');
		var allbtn = document.getElementById('allbtn');
		
		allbtn.onclick=function() {
			for(var i=0; checks.length; i++){
				if(checks[i].checked) checks[i].checked=false;
				else checks[i].checked=true;
			}	
		}
	}
</script>

<body>
  <jsp:include page="/WEB-INF/views/common/mybookmarkmain.jsp"/>

  <main id="main" class="main">    


		
	<section class="section">
	    <span class="likecourse-button">
          <button type="button" id="allbtn" class="btn btn-secondary" style="margin-right:0.5em;"><i class="bi bi-check-lg"></i>전체선택</button>
          <button type="button" class="btn btn-danger"><i class="bi bi-x-lg"></i>삭제</button>
        </span>	
	    <form class="search-form d-flex align-items-center" method="POST" action="#" style="float:right;">
	        <input type="text" name="query" style="width:200px;" placeholder="강의명 검색">
	        <button type="submit" title="Search"><i class="bi bi-search"></i></button>
	    </form> <!-- End Search Bar -->    
	</section><br>

    
    <!-- 여기서부터 본문 내용 시작 -->
    <section class="section clear:both"> 
      <div class="row align-items-top">
      	<c:forEach var="courselike" items="${courselikeList}">
	      	<div class="col-lg-4">
	          <input type="checkbox" name="mycheckbox" style="zoom:1.5;">
	          <div class="card">
	          	<a href="${pageContext.request.contextPath}/course/detail.do?course_id=${courselike.course_id}" target="_blank">
	          		<img src="${pageContext.request.contextPath}/upload/${courselike.course_photo}" class="card-img-top" style="height:300px;">
	          	</a>            
	          </div>      		
	      	</div>      		
      	</c:forEach>                                                  
      </div>
    </section>

    <div class="text-center">
      <!-- Pagination with icons -->
      <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center">
                  <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous" style="border:0px; color:black;">
                      <span aria-hidden="true">&laquo;</span>
                    </a>
                  </li>
                  <li class="page-item"><a class="page-link" href="#" style="border:0px; color:black;">1</a></li>
                  <li class="page-item"><a class="page-link" href="#" style="border:0px; color:black;">2</a></li>
                  <li class="page-item"><a class="page-link" href="#" style="border:0px; color:black;">3</a></li>
                  <li class="page-item"><a class="page-link" href="#" style="border:0px; color:black;">4</a></li>
                  <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next" style="border:0px; color:black;">
                      <span aria-hidden="true">&raquo;</span>
                    </a>
                  </li>
                </ul>
              </nav><!-- End Pagination with icons -->
    </div>
  </main><!-- End #main -->
  <jsp:include page="/WEB-INF/views/common/footer1.jsp" />
</body>

</html>