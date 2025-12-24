import java.awt.*;

public class Drawer implements IVisitor {

    Graphics g;
    public Drawer(Graphics g){
        this.g = g;
    }
    public void Visit(Romb romb){
        g.setXORMode(Color.white);

        Color decodedLineC = Color.decode(romb.GetLineColor());
        Color lineColor = new Color(decodedLineC.getRed(),decodedLineC.getGreen(),decodedLineC.getBlue(), romb.GetTransparrency());

        Color decodedFillC= Color.decode(romb.GetFillColor());
        Color fillColor = new Color(decodedFillC.getRed(), decodedFillC.getGreen(), decodedFillC.getBlue(), romb.GetTransparrency());


        g.setColor(fillColor);
        g.fillPolygon(romb.GetPointsX(), romb.GetPointsY(),  romb.GetPointsY().length);

        g.setColor(lineColor);
        g.drawPolygon(romb.GetPointsX(), romb.GetPointsY(),  romb.GetPointsY().length);

        g.setPaintMode();
    }
//    public void Visit(Circle circle){
//        TOdo: logic for circle
//   }
    public void ChangeGraphics(Graphics g){
        this.g = g;
    }




}
