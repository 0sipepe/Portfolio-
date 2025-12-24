import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class FileManager {

    private HashMap<String, Figure> figures;

    public static FileManager Instance;

    public static FileManager getInstance() {
        if (Instance == null) {
            Instance = new FileManager();
        }
        return Instance;
    }

    private String readFileToString(String filePath) {
        try {
            //System.out.println("Current working directory: " + System.getProperty("user.dir"));
            return Files.readString(Paths.get(filePath));
        } catch (Exception e) {

            return e.getMessage() + "\nНе удалось получить файл";
        }
    }



    private HashMap<String, Figure> _Deserialize(String path){

        HashMap<String, Figure> figuresHashMap = new HashMap<String, Figure>();
        String wholeString = readFileToString(path);


        String[] figuresStrings = wholeString.split("\n");
        for(int i = 0; i < figuresStrings.length; i++){
            String[] figureDetails = figuresStrings[i].split(":");

            String figureType = figureDetails[0].split("\\.")[1];

            Figure figure;

            switch(figureType){
                case ("Romb"):
                    figure = new Romb();
                    ISerializable f = (ISerializable) figure;


                    Boolean canBeDeserialized = ((ISerializable) figure).Deserialize(figureDetails[1]);
                    if(!canBeDeserialized){
                        break;
                    }

                    String name = figure.getName();
                    while(figuresHashMap.containsKey(name)){
                        figure.SetUniqueName();
                        name = figure.getName();
                        //System.out.println(figure.getName() + ": Имя фигуры повторялось и было изменено");
                    }

                    figuresHashMap.put(figure.getName(), figure);
                    break;

                default:
                    System.out.println("Не удалось найти такой тип фигур");

            }
        }
        return figuresHashMap;

    }
    private Boolean writeStringToFile(String s, Path path){
        try{
            Files.writeString(path, s);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public Boolean Serialize (ArrayList<Figure> figures, Path path){
        try{
            String serializedStringFinal = "";
            for(Figure f: figures){
                ISerializable fS = (ISerializable) f;
                serializedStringFinal += fS.Serialize() + "\n";
            }
            return writeStringToFile(serializedStringFinal, path);
        }
        catch (Exception e){
            return false;
        }

    }
    public  ArrayList<Figure> Deserialize (String filePath){
        this.figures = _Deserialize(filePath);

        ArrayList<Figure> figuresArray = new ArrayList<>();
        for(Figure f: figures.values()){
            System.out.println(f.toString());
            figuresArray.add(f);
        }
        return figuresArray;

    }
    public ArrayList<Figure> DeserializeJson(String filePath){
        String wholeString = readFileToString(filePath);

        JSONArray jsonArray = new JSONArray(wholeString);
        ArrayList<String> fullNames = new ArrayList<>();
        ArrayList<Figure> figures = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jso = jsonArray.getJSONObject(i);

            String fullName = jso.getString("fullName");
            Figure figure;
            switch (fullName){
                case "Figure.Romb":

                    figure = new Romb();
                    ISerializableJson f = (ISerializableJson) figure;
                    Boolean canBeSerialized = f.DeserializeJson(jso);

                    if(!canBeSerialized){
                        break;
                    }
                    while (fullNames.contains(fullName)){
                        figure.SetUniqueName();
                        fullName = figure.getName();
                    }
                    fullNames.add(fullName);
                    figures.add(figure);
                    break;

                default:
                    System.out.println("Не удалось найти такой тип фигур");

            }
        }

        return figures;
    }

    public Boolean SerializeJson(ArrayList<Figure> figures, Path filePath){
        JSONArray jarr = new JSONArray();
        for(Figure figure: figures){
            ISerializableJson f = (ISerializableJson) figure;
            JSONObject jSer = f.SerializeJson();
            jarr.put(jSer);

        }
        try{

            writeStringToFile(jarr.toString(), filePath);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
