<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>내학습</title>

  <!-- Google Fonts -->
  <link href="https://fonts.gstatic.com" rel="preconnect">
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/css/mypage-style.css" rel="stylesheet">
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

</head>


<body>
  <jsp:include page="/WEB-INF/views/common/mystudymain.jsp"/>

  <main id="main" class="main">    

	<c:if test="${not empty courseList}">	
	<section class="section">
		<h6>정렬기준</h6>
		<!-- 검색폼 시작 -->
	    <select class="form-select" id="list_sort"  aria-label="form-select" name="sort" style="width:160px; float:left;">
	      <option value="1" <c:if test="${param.sort==1}">selected</c:if>>최근신청순</option>
	      <option value="2" <c:if test="${param.sort==2}">selected</c:if>>가나다순</option>
	    </select>
	    
	    <script>
			$(function(){
				$('#list_sort').change(function(){
					location.href='myStudy.do?sort='+$(this).val();
				});
			});
		</script>
			
	    <form action="${pageContext.request.contextPath}/mypage/myStudy.do" class="search-form d-flex align-items-center" style="float:right;">
	        <input type="text" name="query" value="${param.keyword}" style="width:200px;" placeholder="강의명 검색">
	        <button type="submit" title="Search"><i class="bi bi-search"></i></button>
	    </form> <!-- End Search Bar -->    
	</section><br><br><br>
	</c:if>
    
    <section class="section clear:both"> 
      <div class="row align-items-top">
      	<!-- 수강중 강의 없을 때 처리 -->
      	<c:if test="${empty courseList}"><div style="height:350px;"><br><h4><b>학습중인 강좌가 없습니다.</b></h4></div></c:if>
      	<c:forEach var="courselist" items="${courseList}">
	        <div class="col-lg-4">
	          <div class="card">
	          	<a href="${pageContext.request.contextPath}/course/detail.do?course_id=${courselist.course_id}">
	          		<img src="${pageContext.request.contextPath}/upload/${courselist.course_photo}" class="card-img-top" style="height:300px;">
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