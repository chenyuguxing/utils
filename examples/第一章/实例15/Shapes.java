package 第一章.实例15;
//: Shapes.java

package javabasic;

class Shape {
  void draw() { System.out.println(this + ".draw()"); }
}

class Circle extends Shape {
  public String toString() { return "Circle"; }
}

class Square extends Shape {
  public String toString() { return "Square"; }
}

class Triangle extends Shape {
  public String toString() { return "Triangle"; }
}

public class Shapes {
  public static void main(String[] args) {
    // Object数组
    Object[] shapeList = {
      new Circle(),
      new Square(),
      new Triangle()
    };
    for(int i = 0; i < shapeList.length; i++)
      ((Shape)shapeList[i]).draw(); // 需要强制转型
  }
}
