import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Comparator;


public class Main{


    private static ArrayList<Romb> rombs;
    private final static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args){


        rombs = new ArrayList<Romb>();
        MyFrame screen = new MyFrame();
//

    }




    //возможно переделать под разные фигуры
    private void GetInputRombSorted(){
        System.out.println("введите количество ромбов: ");
        int countRombs = scanner.nextInt();
        rombs = CreateRombs(countRombs);


       // SortFigures(rombs);


    }

    private void SortFigures(ArrayList<Figure> figures){
        System.out.println("ромбы до сортировки: ");
        PrintRombs(rombs);
        Comparator<Figure> comp = new AComparator().thenComparing(new PComparator());
        Collections.sort(figures, comp);
        Print(figures);

        System.out.println("ромбы после сортировки по площади а потом периметру: ");
        PrintRombs(rombs);
    }
    public static int[] getDiagonal(int i){

        System.out.println("введите диагонали " + i +  " ромба через пробел: ");
        int[] diagonals =  new int[2];

        int d1 = scanner.nextInt();
        int d2 = scanner.nextInt();

        diagonals[0] = d1;
        diagonals[1] = d2;

        return diagonals;
    }
    public static ArrayList<Romb> CreateRombs(int count){

        ArrayList<Romb> rombs = new ArrayList<Romb>();

        for(int i = 0; i < count; i++){
            int[] ds = getDiagonal(i);

            try{
                Romb romb = new Romb(ds[0], ds[1]);
                rombs.add(romb);
            }
            catch (Exception e){
                i--;
                System.out.println("неверный ввод " + e.getMessage() );
            }
        }
        return rombs;
    }

    public static void Print(ArrayList<Figure> figures)
    {
        System.out.println("Количество фигур = " + figures.size());
        for (Figure figure : figures) {
            System.out.println(figure.toString());
        }
    }

    public static void PrintRombs(ArrayList<Romb> figures)
    {
        for (Figure romb : figures) {
            System.out.println(romb.toString());
        }
    }



}