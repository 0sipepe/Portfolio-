
import org.json.JSONObject;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;


public class Romb implements Figure, Comparable<Romb>, ISerializableJson /*, Comparable<Figure>*/ {

    int d1, d2;
    int side;
    int[] pointsX = new int[4];
    int[] pointsY = new int[4];
   // int x1 = 5, y1 = 0, x2 = 10, y2 = 5, x3 = 5, y3 = 10, x4 = 0, y4 = 5;
    int[] center = new int[2];
    String name;
    String fillColor;
    String lineColor;
    int transparrency;


    public Romb(){
//        this.d1 = 20;
//        this.d2 = 15;
//        this.a = Math.sqrt((d1*d1 + d2*d2)/4);

    }

    public int[] GetPointsX() {
        return pointsX;
    }

    public int[] GetPointsY(){
        return pointsY;
    }

    public Romb(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, String name, String fillColor, String lineColor, float transparrency) throws IncorrectInputException{

        try{
            this.writeDate(x1,y1,x2,y2,x3,y3,x4,y4,name, fillColor, lineColor, transparrency);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
    public Romb(int d1, int d2) throws IncorrectInputException{

        if ((d1 <= 0) || (d2 <= 0)){
            throw new IncorrectInputException("Диагонали должны быть положительными числами");
        }
        this.d1 = d1;
        this.d2 = d2;
        this.side = (int)(Math.sqrt((d1*d1  + d2*d2) /4));
    }

    public String getName() {
        return this.name;
    }
    public void SetUniqueName(){
        this.name = name + "(1)";
    }



    public double getSide(){
        return side;
    }

    @Override
    public double getPerimeter(){
        return side * 4;
    }

    @Override
    public double getArea(){
        return d1 * d2 / 2;
    }

     public int compareTo(Figure f){
         return (int)(this.getArea() - f.getArea());
     }

    public int compareTo(Romb r){
        return (int)(this.side - r.getSide());
    }


    @Override
    public String toString() {
        return String.format("Ромб: " + this.name + " [сторона=%.2f, диагонали=%d,%d, площадь=%.2f, периметр=%.2f]", side, d1, d2, getArea(), getPerimeter());
    }

    private void writeDate(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, String name, String fillColor, String lineColor,float transparrency) throws IncorrectInputException{

        this.pointsX[0] = x1;
        this.pointsX[1] = x2;
        this.pointsX[2] = x3;
        this.pointsX[3] = x4;

        this.pointsY[0] = y1;
        this.pointsY[1] = y2;
        this.pointsY[2] = y3;
        this.pointsY[3] = y4;


        this.name = name;
        this.fillColor = fillColor;
        this.lineColor = lineColor;
        if(transparrency <= 1){
            this.transparrency = (int) (transparrency * 255);
        }
        else{
            this.transparrency = (int)transparrency;
        }

        double a1 = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double a2 = Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));
        double a3 = Math.sqrt(Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2));
        double a4 = Math.sqrt(Math.pow((x1 - x4), 2) + Math.pow((y1 - y4), 2));

        if(!((a1 == a2) && (a2 == a3) && (a3 == a4))){

            throw new IncorrectInputException("Bведенные данные не соответствуют ромбу");
        }
        else{
            this.side = (int) a1;
            this.d1 = (int)Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
            this.d2 = (int)Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));

            System.out.println("d1 " + d1);
            System.out.println("d2 " + d2);

            center[0] = x1 + d1/2;
            center[1] = y1 + d1/2;;
            System.out.println("cX " + center[0]);
            System.out.println("cY " + center[1]);

        }

    }

    public String GetFullType(){
        return "Figure.Romb";
    }
    public String GetFillColor(){
        return fillColor;
    }

    public String GetLineColor(){
        return lineColor;
    }

    public int GetTransparrency(){
        //System.out.println("transparrency " + transparrency);
        return transparrency;
    }
    public void Accept(IVisitor visitor){
        visitor.Visit(this);
    }
    public boolean Contains(int x, int y){

        int intersections = 0;
        int n = pointsY.length;

        for (int i = 0; i < n; i++) {

            int[] p1 = {pointsX[i], pointsY[i]};
            int[] p2 = {pointsX[(i + 1) % n], pointsY[(i + 1) % n]};

            // Проверяем пересечение луча (идущего вправо от точки) с ребром
            if (((p1[1] > y) != (p2[1] > y)) &&
                    (x < (p2[0] - p1[0]) * (y - p1[1]) / (p2[1] - p1[1]) + p1[0])) {
                intersections++;
            }
        }

        return intersections % 2 == 1; // Нечетное число пересечений = точка внутри

    }
    public void SetShift(int x, int y){

        for(int i = 0; i < pointsX.length; i++){

            pointsX[i] += x;
            pointsY[i] += y ;
        }

    }
    public JSONObject SerializeJson(){
        JSONObject jso = new JSONObject();
        jso.put("fullName" , "Figure.Romb");
        jso.put("name" , this.name);
        jso.put("fillColor" ,this.fillColor);
        jso.put("lineColor" , this.lineColor);
        jso.put("transparrency", this.transparrency);

        JSONObject jCenter = new JSONObject();
        jCenter.put("x", center[0]);
        jCenter.put("y", center[1]);
        jso.put("center", jCenter);

        JSONObject jDiagonals = new JSONObject();
        jDiagonals.put("d1", this.d1);
        jDiagonals.put("d2", this.d2);
        jso.put("diagonals", jDiagonals);

        return jso;

    }

    public Boolean DeserializeJson(JSONObject o){
        try{
            name = o.getString("name");
            transparrency = o.getInt("transparrency");


            fillColor = o.getString("fillColor");
            lineColor = o.getString("lineColor");

            // 3. Центр
            JSONObject centerJson = o.getJSONObject("center");
            int x = centerJson.getInt("x");
            int y = centerJson.getInt("y");
            center = new int[]{x, y};

            // 4. Диагонали
            JSONObject diagonalsJson = o.getJSONObject("diagonals");
            d1 = diagonalsJson.getInt("d1");
            d2 = diagonalsJson.getInt("d2");

            pointsX = new int[] {center[0] - d1/2, center[0], center[0] + d2/2, center[0]};
            pointsY = new int[] {center[1],center[1] + d1/2, center[1] , center[1] - d2/2};

            double a1 = Math.sqrt(Math.pow((pointsX[1] - pointsX[0]), 2) + Math.pow((pointsY[1] - pointsY[0]), 2));
            double a2 = Math.sqrt(Math.pow((pointsX[2] - pointsX[1]), 2) + Math.pow((pointsY[2] - pointsY[1]), 2));
            double a3 = Math.sqrt(Math.pow((pointsX[3] - pointsX[2]), 2) + Math.pow((pointsY[3] - pointsY[2]), 2));
            double a4 = Math.sqrt(Math.pow((pointsX[0] - pointsX[3]), 2) + Math.pow((pointsY[0] - pointsY[3]), 2));

            if(!((a1 == a2) && (a2 == a3) && (a3 == a4))){

                System.out.println(pointsX[0]);
                System.out.println(pointsX[1]);
                System.out.println(pointsX[2]);
                System.out.println(pointsX[3]);
                System.out.println("\n");
                System.out.println(pointsY[0]);
                System.out.println(pointsY[1]);
                System.out.println(pointsY[2]);
                System.out.println(pointsY[3]);
                System.out.println("\n");
                System.out.println(a1);
                System.out.println(a2);
                System.out.println(a3);
                System.out.println(a4);
                throw new IncorrectInputException("Bведенные данные не соответствуют ромбу");
            }
            else{

                this.side = (int) a1;
            }

            System.out.println(o);
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }



}