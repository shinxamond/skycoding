package kr.jobEmploy.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.jobEmploy.dao.EmployDAO;
import kr.jobEmploy.vo.EmployVO;
import kr.util.FileUtil;

public class EmployWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer mem_auth = (Integer)session.getAttribute("mem_auth");
		if(mem_num==null) {//로그인 안 된 경우
			return "redirect:/hmember/loginForm.do";
		}
		//로그인이 된 경우
		//관리자가 아닌 경우
		if(mem_auth!=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		//관리자인 경우
		MultipartRequest multi = FileUtil.createFile(request);
		
		EmployVO employ = new EmployVO();
		employ.setEmp_title(multi.getParameter("title"));
		employ.setMem_id2(multi.getParameter("mem_id2"));
		employ.setEmp_content(multi.getParameter("content"));
		employ.setEmp_photo(multi.getFilesystemName("photo"));
		employ.setMem_num(mem_num);
		
		EmployDAO dao = EmployDAO.getInstance();
		dao.insertEmploy(employ);
		
		return "/WEB-INF/views/jobEmploy/employWrite.jsp";
	}

}
