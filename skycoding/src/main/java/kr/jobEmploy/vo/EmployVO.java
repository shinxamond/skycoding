package kr.jobEmploy.vo;
 
import java.sql.Date;

public class EmployVO {
	private int emp_id;
	private String emp_title;
	private String emp_content;
	private String emp_photo;
	private int emp_hit;
	private Date emp_reg_date;
	private Date emp_modify_date;
	private int mem_num;//관리자 회원번호
	private String mem_id2;//회원 아이디
	
	
	//테이블에 없어도 목록에 가져올 필요한 정보 추가(hmember/hmember_detail)
	private String mem_id;//관리자 아이디
	private String mem_photo;
	private int mem_auth;
	
	//이전글, 다음글 추가
	private int prev;
	private String prev_title;
	private int next;
	private String next_title;
	
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public String getPrev_title() {
		return prev_title;
	}
	public void setPrev_title(String prev_title) {
		this.prev_title = prev_title;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public String getNext_title() {
		return next_title;
	}
	public void setNext_title(String nwxt_title) {
		this.next_title = nwxt_title;
	}
	
	public int getMem_auth() {
		return mem_auth;
	} 
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_title() {
		return emp_title;
	}
	public void setEmp_title(String emp_title) {
		this.emp_title = emp_title;
	}
	public String getEmp_content() {
		return emp_content;
	}
	public void setEmp_content(String emp_content) {
		this.emp_content = emp_content;
	}
	public String getEmp_photo() {
		return emp_photo;
	}
	public void setEmp_photo(String emp_photo) {
		this.emp_photo = emp_photo;
	}
	public int getEmp_hit() {
		return emp_hit;
	}
	public void setEmp_hit(int emp_hit) {
		this.emp_hit = emp_hit;
	}
	public Date getEmp_reg_date() {
		return emp_reg_date;
	}
	public void setEmp_reg_date(Date emp_reg_date) {
		this.emp_reg_date = emp_reg_date;
	}
	public Date getEmp_modify_date() {
		return emp_modify_date;
	}
	public void setEmp_modify_date(Date emp_modify_date) {
		this.emp_modify_date = emp_modify_date;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id2() {
		return mem_id2;
	}
	public void setMem_id2(String mem_id2) {
		this.mem_id2 = mem_id2;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	
}
