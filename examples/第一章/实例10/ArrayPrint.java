package 第一章.实例10;
//ArrayPrint.java
package javabasic;
import java.io.*;
public class ArrayPrint
{
	private int N;  //矩阵的维数
	private int[][] array;
	ArrayPrint(){
		N=5;
		array=new int[N][N];
	}
	ArrayPrint(int n){
		N=n;
		array=new int[N][N];
	}
	public void Print(){
		int i=0,j=0,i0=0,j0=0,count=1,Num=N-1;
		while(Num>0) {
			for (i=i0,j=j0;j<Num;j++,count++) {
				array[i][j]=count;
			}
			for (i=i0;i<Num;i++,count++) {
				array[i][j]=count;
			}
			for (j=Num;j>j0;j--,count++) {
				array[i][j]=count;
			}
			for (i=Num;i>i0;i--,count++) {
				array[i][j]=count;
			}
			i0++;
			j0++;
			Num--;
		}
		if (N%2!=0) {  //矩阵的维数为奇数时，给矩阵中间的位置赋值
			array[N/2][N/2]=count;
		}
		for (i=0;i<N;i++) {
			for (j=0;j<N;j++) {
				String ar="0";
				if (array[i][j]/10<1) ar="  "+array[i][j];
				else if (array[i][j]/100<1 && array[i][j]/10>=1) {
					ar=" "+array[i][j];
				}
				System.out.print(ar+" ");
			}
			System.out.println();
		}
	}
	public static void main(String args[]) throws IOException{
		int order=0;
		System.out.print("Please input the order of the square(<10):");
		order = System.in.read();
		order = order-'0';
		ArrayPrint arr=new ArrayPrint(order);
		arr.Print();
	}
}
