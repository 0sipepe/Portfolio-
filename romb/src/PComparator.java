import java.util.Comparator;


public class PComparator implements Comparator<Figure> {
    public int compare(Figure a, Figure b){
        return (int)(a.getPerimeter() - b.getPerimeter());
    }
}