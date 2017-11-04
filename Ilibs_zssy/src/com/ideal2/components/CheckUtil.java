 package com.ideal2.components;
/**
 * 
 * @author Administrator
 *
 */
public class CheckUtil {
	//身份证验证
	public static String checkID(String str){
		String regex = "\\d{15}$|^\\d{17}([0-9]|X|x)";
		if(!str.matches(regex)){
			return "身份证格式不正确";
		}
		if(str.length()==15){
			return "Y";
		}
		if(str.length()==18){
			int sum=0;
			int[] ary=new int[17];
			String code=str.substring(str.length()-1, str.length());
			System.out.println("code:"+code);
			for(int i=0;i<str.length()-1;i++){
				String newStr=str.substring(i, i+1);
				int n=Integer.parseInt(newStr);
				ary[i]=n;
			}
			sum=ary[0]*7+ary[1]*9+ary[2]*10+ary[3]*5+ary[4]*8+ary[5]*4+ary[6]*2+ary[7]*1+ary[8]*6+
				+ary[9]*3+ary[10]*7+ary[11]*9+ary[12]*10+ary[13]*5+ary[14]*8+ary[15]*4+ary[16]*2;	
			int mod=sum%11;
			switch (mod) {
			case 0:
				if(code.equals("1"))
					return "Y";	
			case 1:
				if(code.equals("0"))
					return "Y";	
			case 2:
				if(code.equalsIgnoreCase("x"))
					return "Y";	
			case 3:
				if(code.equals("9"))
					return "Y";
			case 4:
				if(code.equals("8"))
					return "Y";
			case 5:
				if(code.equals("7"))
					return "Y";
			case 6:
				if(code.equals("6"))
					return "Y";	
			case 7:
				if(code.equals("5"))
					return "Y";
			case 8:
				if(code.equals("4"))
					return "Y";	
					
			case 9:
				if(code.equals("3"))
					return "Y";
					
			case 10:
				if(code.equals("2"))
					return "Y";	
			default :
				return "身份证输入不正确";
			}							
		}
		return 	"身份证输入不正确";	
		
	}
	//手机号码验证
	public static String checkPHONE(String str){
		String regex = "[1][3|4|5|8][\\d]{9}";
		if(str.matches(regex)){
			return "Y";
		}
		return "手机号码格式不正确(例如:13812341234)";
	}
	//邮箱验证
	public static String checkEMAIL(String str){
		String regex = "([0-9]*\\w*[.-]*){3,18}@([0-9]{3}|[a-z]{2,8})(\\.[\\w]{2,3})+";
		if(str.matches(regex)){
			return "Y";
		}
		return "邮箱格式不正确(例如:zhangsan@sina.com)";
	}
	//邮编验证
	public static String checkPOSTCODE(String str){
		String regex = "\\d{6}";
		if(str.matches(regex)){
			return "Y";
		}
		return "邮编格式不正确";
	}
	//日期验证
	public static String checkDATE(String str){
		String regex = "\\d{4}-\\d{1,2}-\\d{1,2}";
		if(str.matches(regex)){
			return "Y";
		}
		return "日期格式不正确(例如:1900-01-01)";
	}
	//固话验证
	public static String checkTEL(String str){
		String regex = "\\d{3,4}-\\d{7,8}";
		if(str.matches(regex)){
			return "Y";
		}
		return "固话格式不正确(例如:010-12345678)";
	}
	//是否空值验证
	public static String isEditNull(String str){
		if(null==str||str.equals("")){
			return "Y";	
		}else{
			return "N";
		}
	}
	
	//IP验证
	public static String checkIP(String str){
		String regex="^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                     + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                     + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                     + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		if(str.matches(regex)){
			return "Y";
		}
		return "IP格式不正确";
	}
}
