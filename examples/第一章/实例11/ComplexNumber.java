package 第一章.实例11;
package javabasic;

public class ComplexNumber {
    private double x, y;
    
    public ComplexNumber(double real, double imaginary) {
        this.x = real;
        this.y = imaginary;
    }

    public double real() { return x; } //返回实部
    public double imaginary() { return y; }  //返回虚部
    public double magnitude() { return Math.sqrt(x*x + y*y); }  //返回共轭值
    public String toString() { return "{" + x + "," + y + "}"; }
    
    //两个复数相加
    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.x + b.x, a.y + b.y);
    }
    
    //this对象与另一个复数相加
    public ComplexNumber add(ComplexNumber a) {
        return new ComplexNumber(this.x + a.x, this.y+a.y);
    }
    
    //两个复数相乘
    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.x*b.x - a.y*b.y, a.x*b.y + a.y*b.x);
    }
    
    //this对象与另外一个复数相乘
    public ComplexNumber multiply(ComplexNumber a) {
        return new ComplexNumber(x*a.x - y*a.y, x*a.y + y*a.x);
    }
}
