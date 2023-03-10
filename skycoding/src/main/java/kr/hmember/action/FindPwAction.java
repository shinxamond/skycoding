package kr.hmember.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.hmember.dao.MemberDAO;

public class FindPwAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");

		//전송된 데이터 반환
		String id = request.getParameter("mem_id");
		String pwa = request.getParameter("mem_pwa");

		MemberDAO dao = MemberDAO.getInstance();
		String myPw = dao.findPw(id,pwa);

		request.setAttribute("myPw", myPw);
		request.setAttribute("id", id);

		return "/WEB-INF/views/hmember/findPw.jsp";
	}
}
