package kr.jobReview.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.jobReview.dao.ReviewDAO;
import kr.jobReview.vo.ReviewCommentVO;

public class WriteCommentAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		 
		if(mem_num==null) {//로그인 x
			mapAjax.put("result", "logout");
		}else {//로그인 o
			request.setCharacterEncoding("utf-8");
			
			ReviewCommentVO reviewComment = new ReviewCommentVO();
			reviewComment.setMem_num(mem_num);
			reviewComment.setCom_content(request.getParameter("com_content"));
			reviewComment.setRev_id(Integer.parseInt(request.getParameter("rev_id")));
			
			ReviewDAO dao =  ReviewDAO.getInstance();
			dao.insertReviewComment(reviewComment);
			
			mapAjax.put("result", "success");
		}
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
