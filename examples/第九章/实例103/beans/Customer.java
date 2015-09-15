package 第九章.实例103.beans;
public class Customer{

	private String name="";
	private String tel="";
	private String addr="";
	
	public Customer(){
	}
	
	public void setName( String name ){
		this.name = name;			
	}
	
	public String getName(){
		return this.name;			
	}
	
	public void setTel( String tel ){
		this.tel = tel;			
	}
	
	public String getTel(){
		return this.tel;			
	}
	
	public void setAddr( String addr ){
		this.addr = addr;	
	}
	
	public String getAddr(){
		return this.addr;			
	}
	
}	
