package 第十二章.实例121;
package security;
public class Caesar{
	public static void main(String args[]){
		String s = args[0];
		int key = Integer.parseInt(args[1]); // 密钥
		String es="";
		for(int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if(c>='a' && c<='z'){ //是小写字母
				c += key%26;
				if(c<'a') c += 26; // 向左超界
				if(c>'z') c -= 26; // 向右超界
			}
			else if(c>='A' && c<='Z'){ //是大写字母
				c += key%26;
				if(c<'A') c += 26;
				if(c>'Z') c -= 26;
			}
			es += c;
		}
		System.out.println(es);
	}
}
