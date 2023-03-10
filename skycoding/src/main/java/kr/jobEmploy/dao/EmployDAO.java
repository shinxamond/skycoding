package kr.jobEmploy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.jobEmploy.vo.EmployVO;
import kr.jobReview.vo.ReviewVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class EmployDAO {
	//싱글턴패턴
	private static EmployDAO instance = new EmployDAO();
	
	public static EmployDAO getInstance() {
		return instance;
	}
	//외부에서 객체 생성 못하게 함
	private EmployDAO() {}
	
	//글등록
	public void insertEmploy(EmployVO employ)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		 
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			//                                   (mem_num은 session에서 구함)
			sql = "INSERT INTO job_employ (emp_id,mem_num,emp_title,mem_id2,emp_content,emp_photo) "
					+ "VALUES (job_employ_seq.nextval,?,?,?,?,?)";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, employ.getMem_num());
			pstmt.setString(2, employ.getEmp_title());
			pstmt.setString(3, employ.getMem_id2());
			pstmt.setString(4, employ.getEmp_content());
			pstmt.setString(5, employ.getEmp_photo());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//총 레코드 수(검색 레코드 수)          검색조건           검색내용
	public int getEmployCount(String keyfield,String keyword)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";//검색할 때 사용
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색글 개수
				if(keyfield.equals("1")) sub_sql += "WHERE j.emp_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE j.emp_content LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE h.mem_id LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM job_employ j JOIN hmember h USING(mem_num) " + sub_sql;
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword+"%");//keyword가 포함된 내용 검색 
			}
			//SQL문 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);//데이터가 1개라 컬럼인덱스 1을 넣음
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//검색글 목록
	public List<EmployVO> getEmployList(int start,int end,
					String keyfield,String keyword,String sort)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EmployVO> list = null;
		String sql = null;
		String sub_sql = "";//검색시 사용
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색글 보기
				if(keyfield.equals("1")) sub_sql += "WHERE j.emp_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE j.emp_content LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "WHERE h.mem_id LIKE ?";
			}
			//dropdown sort 변수 추가
			if(sort.equals("1")){
				sort = "j.emp_id DESC";
			}else if(sort.equals("2")) {
				sort = "j.emp_hit DESC";
			}else {
				sort = "j.emp_id DESC";
			}
			
			//SQL문 작성
			//hmember에서는 id, hmember_detail에서는 photo
			sql = "SELECT * FROM (SELECT a.*,rownum rnum FROM (SELECT * FROM job_employ j JOIN hmember h USING(mem_num) "
					+sub_sql+" ORDER BY "+sort+")a) WHERE rnum>= ? AND rnum <= ?";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<EmployVO>();
			while(rs.next()) {
				EmployVO employ = new EmployVO();
				employ.setEmp_id(rs.getInt("emp_id"));
				employ.setEmp_title(StringUtil.useNoHtml(rs.getString("emp_title")));
				employ.setEmp_photo(rs.getString("emp_photo"));
				employ.setMem_id(rs.getString("mem_id"));
				employ.setEmp_reg_date(rs.getDate("emp_reg_date"));
				employ.setEmp_hit(rs.getInt("emp_hit"));
				
				list.add(employ);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	 
	
	//글상세정보
	public EmployVO getEmployDetail(int emp_id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployVO employ = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			//hmember에서는 id, hmember_detail에서는 photo
			sql = "SELECT * FROM job_employ j JOIN hmember h USING(mem_num) "//mem_num은 관리자번호
					+ "JOIN hmember_detail d USING(mem_num) WHERE j.emp_id=?";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, emp_id);
			//SQL문 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				employ = new EmployVO();
				employ.setEmp_id(rs.getInt("emp_id"));
				employ.setEmp_title(rs.getString("emp_title"));
				employ.setEmp_content(rs.getString("emp_content"));
				employ.setEmp_photo(rs.getString("emp_photo"));
				employ.setEmp_reg_date(rs.getDate("emp_reg_date"));
				employ.setEmp_modify_date(rs.getDate("emp_modify_date"));
				employ.setEmp_hit(rs.getInt("emp_hit"));
				employ.setMem_num(rs.getInt("mem_num"));
				employ.setMem_id(rs.getString("mem_id"));
				employ.setMem_id2(rs.getString("mem_id2"));
				employ.setMem_photo(rs.getString("mem_photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return employ;
	} 
	
	//글상세정보(입력한 회원아이디의 회원번호 반환)
	public int getEmployDetail2(String mem_id2)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int mem_num = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT h.mem_num FROM job_employ j JOIN hmember h ON (j.mem_id2=h.mem_id) "//mem_num은 회원번호
					+ "WHERE j.mem_id2=?";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, mem_id2);
			//SQL문 실행하고 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mem_num = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return mem_num;
	} 
	
	//이전글,다음글
	public EmployVO prevNext(int emp_id)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmployVO pnEmploy = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();	
			//SQL문 작성
			sql = "SELECT * FROM (" 
				+ "SELECT emp_id,emp_title,"
			    + "lag(emp_id,1) over(order by emp_id) prev,"
			    + "lag(emp_title,1) over(order by emp_id) prev_title,"
			    + "lead(emp_id,1) over(order by emp_id) next,"
			    + "lead(emp_title,1) over(order by emp_id) next_title "
			    + "FROM job_employ)j WHERE j.emp_id=?";
					
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, emp_id);
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();

			if(rs.next()) {
				pnEmploy = new EmployVO();
				pnEmploy.setEmp_id(rs.getInt("emp_id"));
				pnEmploy.setEmp_title(rs.getString("emp_title"));
				pnEmploy.setPrev(rs.getInt("prev"));
				pnEmploy.setPrev_title(rs.getString("prev_title"));
				pnEmploy.setNext(rs.getInt("next"));
				pnEmploy.setNext_title(rs.getString("next_title"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return pnEmploy;
	}
	
	
	
	//조회수 증가
	public void UpdateReadCount(int emp_id)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try{
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE job_employ SET emp_hit=emp_hit+1 WHERE emp_id=?";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, emp_id);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//파일 삭제
	public void deleteFile(int emp_id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE job_employ SET emp_photo='' WHERE emp_id=?";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, emp_id);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글수정
	public void UpdateEmploy(EmployVO employ)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//전송된 파일 여부 체크
			if(employ.getEmp_photo()!=null) {
				sub_sql += ",emp_photo=?";
			}
			//SQL문 작성
			sql = "UPDATE job_employ SET emp_title=?,emp_content=?,emp_modify_date=SYSDATE"+sub_sql+" WHERE emp_id=?";
			//preparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, employ.getEmp_title());
			pstmt.setString(++cnt, employ.getEmp_content());
			if(employ.getEmp_photo()!=null) {
				pstmt.setString(++cnt, employ.getEmp_photo());
			}
			pstmt.setInt(++cnt, employ.getEmp_id());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글삭제
	public void deleteEmploy(int emp_id)throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			//SQL문 작성
			//부모글 삭제
			sql = "DELETE FROM job_employ WHERE emp_id=?";
			//preparedStatement 객체 생성
			pstmt3 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt3.setInt(1, emp_id);
			//SQL문 실행
			pstmt3.executeUpdate();
			//예외 발생 없이 정상적으로 SQL문이 실행
			conn.commit();
			
		}catch(Exception e) {
			//예외 발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
