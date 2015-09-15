package 第九章.实例103.beans;
import java.util.Vector;

public class Price{

	private String[] name;
	private int[] price;
	private String[] item;
	
	public Price(){
	}
	
	public void setName( String[] name ){
		this.name = name;			
	}
	
	public String[] getName(){
		return this.name;	
	}
	
	public void setPrice( int[] price ){
		this.price = price;			
	}
	
	public int[] getPrice(){
		return this.price;			
	}
	
	public void setItem( String[] item ){
		this.item = item;	
	}
	
	public String[] getItem(){
		return this.item;			
	}
	
	// 获取商品的列表
	public Vector getItemList(){
		Vector itemVec = new Vector();
		for( int num=0; num<item.length; num++	)
		  if( !item[num].trim().equals( "" ) )
		    try{
		    	int items = Integer.parseInt(item[num].trim());
		    	if( items > 0 ){
		      	itemVec.addElement( name[num] + " X " + item[num].trim() + 
		      	" = $" + (items*price[num]) );
		      }	
		    }	
		    catch( NumberFormatException numex ){
		    }
				
		return itemVec;	
	}
	
	//  获取商品的总价
	public int getTotalPrice(){
		int totalprice=0;
		for( int num=0; num<item.length; num++	)
		  if( !item[num].trim().equals( "" ) )
		    try{
		    	int items = Integer.parseInt(item[num].trim());
		    	if( items > 0 )
		      	totalprice += items*price[num];
		    }	
		    catch( NumberFormatException numex ){
		    }
		return totalprice;		
	}
	
}	
