package com.ideal.zsyy.entity;

/**
 * 预约实体
 * @author Administrator
 *
 */
public class TopVisitResInfo {

	private String seqn;		// 唯一序号（20位，以“CT”+yy+mm+dd+nnnnnnnnnnnn）
	private String vst_card_no;	//病人卡号
	private String pat_name;	//病人姓名
	private String pat_sex;		//病人性别(01男 02女)
	private String pat_dob;		//病人生日
	private String pat_id_card;	//病人省份证
	private String pat_add;		//病人住址
	private String pat_phone;	//病人手机号码
	private String reg_date;	//就诊日期
	private String reg_time;	//就诊时间 
	private String reg_dept_id;	//就诊科室ID
	private String reg_dept_name;//就诊科室名称
	private String reg_locate;	//就诊院（南北院）
	private String reg_doc_id;	//就诊专家工号
	private String reg_doc_name;//就诊专家姓名
	private String status;
//	private String res_date;
//	private String res_time;
	public String getSeqn() {
		return seqn;
	}
	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}
	public String getVst_card_no() {
		return vst_card_no==null?"":this.vst_card_no;
	}
	public void setVst_card_no(String vstCardNo) {
		vst_card_no = vstCardNo;
	}
	public String getPat_name() {
		return pat_name==null?"":this.pat_name;
	}
	public void setPat_name(String patName) {
		pat_name = patName;
	}
	public String getPat_sex() {
		return pat_sex==null?"":this.pat_sex;
	}
	public void setPat_sex(String patSex) {
		pat_sex = patSex;
	}
	public String getPat_dob() {
		return pat_dob==null?"":this.pat_dob;
	}
	public void setPat_dob(String patDob) {
		pat_dob = patDob;
	}
	public String getReg_date() {
		return reg_date==null?"":this.reg_date;
	}
	public void setReg_date(String regDate) {
		reg_date = regDate;
	}
	public String getReg_time() {
		return reg_time==null?"":this.reg_time;
	}
	public void setReg_time(String regTime) {
		reg_time = regTime;
	}
	public String getReg_dept_id() {
		return reg_dept_id==null?"":this.reg_dept_id;
	}
	public void setReg_dept_id(String regDeptId) {
		reg_dept_id = regDeptId;
	}
	public String getReg_dept_name() {
		return reg_dept_name==null?"":this.reg_dept_name;
	}
	public void setReg_dept_name(String regDeptName) {
		reg_dept_name = regDeptName;
	}
	public String getReg_doc_id() {
		return reg_doc_id==null?"":this.reg_doc_id;
	}
	public void setReg_doc_id(String regDocId) {
		reg_doc_id = regDocId;
	}
	public String getReg_doc_name() {
		return reg_doc_name==null?"":this.reg_doc_name;
	}
	public void setReg_doc_name(String regDocName) {
		reg_doc_name = regDocName;
	}
	public String getStatus() {
		return status==null?"":this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPat_id_card() {
		return pat_id_card==null?"":this.pat_id_card;
	}
	public void setPat_id_card(String patIdCard) {
		pat_id_card = patIdCard;
	}
	public String getPat_add() {
		return pat_add==null?"":this.pat_add;
	}
	public void setPat_add(String patAdd) {
		pat_add = patAdd;
	}
	public String getReg_locate() {
		return reg_locate==null?"":this.reg_locate;
	}
	public void setReg_locate(String regLocate) {
		reg_locate = regLocate;
	}
	public String getPat_phone() {
		return pat_phone==null?"":this.pat_phone;
	}
	public void setPat_phone(String pat_phone) {
		this.pat_phone = pat_phone;
	}
	
	
}
