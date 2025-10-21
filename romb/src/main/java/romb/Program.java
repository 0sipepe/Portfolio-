package romb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Comparator;



public class Program{

   
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        
        ArrayList<Romb> rombs = new ArrayList<Romb>();
  
        System.out.println("введите количество ромбов: ");
        int countRombs = scanner.nextInt();

        System.out.println("ромбы до сортировки: ");
        rombs = CreateRombs(countRombs);

        Print(rombs);

        //Collections.sort(rombs);
        // System.out.println("ромбы после сортировки по стороне: ");
        // Print(rombs);

        System.out.println("ромбы после сортировки по площади а потом периметру: ");
        Comparator<Figure> comp = new AComparator().thenComparing(new PComparator());
    
        Collections.sort(rombs, comp);
        Print(rombs);




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
                System.out.println("wrong input: " + e.getMessage() );
            }
        }
        return rombs;
    }

    public static void Print(ArrayList<Romb> rombs)
    {
        for(int i = 0; i < rombs.size(); i++)
        {
            System.out.println(rombs.get(i).toString());
        }
    }


}