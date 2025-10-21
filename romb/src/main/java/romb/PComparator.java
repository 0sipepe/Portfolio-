package romb;
import java.util.Comparator;

import cn.hutool.core.convert.Convert;

public class PComparator implements Comparator<Figure> {
    public int compare(Figure a, Figure b){
        return Convert.toInt(a.getPerimeter() - b.getPerimeter());
    }
}