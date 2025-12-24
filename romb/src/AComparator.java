import java.util.Comparator;


public class AComparator implements Comparator<Figure> {

    public int compare(Figure a, Figure b){
        return (int)(a.getArea() - b.getArea());
    }
}


