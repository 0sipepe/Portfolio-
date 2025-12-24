import java.util.ArrayList;

public interface Figure extends ISerializable, ISerializableJson {

    double getArea();
    double getPerimeter();
    String toString();

    String getName();
    void SetUniqueName();
    String GetFullType();
    int[] GetPointsX();
    int[] GetPointsY();
    String GetLineColor();
    String GetFillColor();
    int GetTransparrency();
    void Accept(IVisitor visitor);
    boolean Contains(int x, int y);
    void SetShift(int x, int y);

}
