package 第十章.实例108;
public class StudentBean{
	private String sex;  //学生性别
	private String name; //学生姓名
	private int age;    // 学生年龄
	private String phone;  // 电话号码
	
	public void setSex(String s){ 
        sex  = s; 
    }
    
    public void setName(String s){
    	name = s;
    }
    
    public void setAge(int a){
    	age = a;
    }
    
    public void setPhone(String s){
    	phone = s;
    }
    
    public String getSex(){
    	return sex;
    }
    
    public String getName(){
    	return name;
    }
    
    public int getAge(){
    	return age;
    }
    
    public String getPhone(){
    	return phone;
    }
}
