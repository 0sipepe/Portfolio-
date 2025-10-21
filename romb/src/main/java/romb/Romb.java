package romb;

import java.lang.Math;

import cn.hutool.core.convert.Convert;
public class Romb implements Figure, Comparable<Romb> /*, Comparable<Figure>*/ {

    int d1, d2;
    double a;
    int x1 = 5, y1 = 0, x2 = 10, y2 = 5, x3 = 5, y3 = 10, x4 = 0, y4 = 5; 
    String name;
    String fillColor;
    String lineColor;
    int transparrency;
 
    
    public Romb(){
        this.d1 = 20;
        this.d2 = 15;
        this.a = Math.sqrt((d1*d1 + d2*d2) /4);
         
    }



    public Romb(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, String name, String fillColor, String lineColor, int transparrency) throws IncorrectInputException{
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;

        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.y4 = y4;
        
        this.name = name;
        this.fillColor = fillColor;
        this.lineColor = lineColor;
        this.transparrency = transparrency;

        double a1 = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double a2 = Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));
        double a3 = Math.sqrt(Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2));
        double a4 = Math.sqrt(Math.pow((x1 - x4), 2) + Math.pow((y1 - y4), 2));

        if(!((a1 == a2) && (a2 == a3) && (a3 == a4))){
            throw new IncorrectInputException("длины прямых из этих точек не равны");
        }
        else{
            this.a = a1;
            this.d1 = Convert.toInt(this.a * Math.sqrt(3));
            this.d2 = Convert.toInt(this.a * Math.sqrt(2));
        }
    }
    public Romb(int d1, int d2) throws IncorrectInputException{

        if ((d1 <= 0) || (d2 <= 0)){
            throw new IncorrectInputException("Диагонали должны быть положительными числами");
        }      
        this.d1 = d1;
        this.d2 = d2;
        this.a = Math.sqrt((d1*d1  + d2*d2) /4);
    }
    
    public double getA(){
        return a;
    }

    @Override
    public double getPerimeter(){
        return a * 4;
    }

    @Override
    public double getArea(){
        return d1 * d2 / 2;
    } 

    // public int compareTo(Figure f){
    //     return Convert.toInt(this.getArea() - f.getArea());
    // }

    public int compareTo(Romb r){
        return Convert.toInt(this.a - r.getA());
    }
    @Override
    public String toString() {
        return String.format("Ромб [сторона=%.2f, диагонали=%d,%d, площадь=%.2f, периметр=%.2f]", a, d1, d2, getArea(), getPerimeter());
    }
}