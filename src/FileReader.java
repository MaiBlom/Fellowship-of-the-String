package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import src.Media.*;

public class FileReader {
    public FileReader(String path){
        Scanner sc = new Scanner(getClass().getClassLoader().getResourceAsStream(path));

        List<Media> media = new ArrayList<>();

        sc.useDelimiter("\\s*;\\s*,\\s*");
        sc.useLocale(Locale.ENGLISH);

        String[] input = sc.nextLine().split("; ");

        while(sc.hasNext()){
            String title = sc.next();
            int release = sc.nextInt();
            double rating = sc.nextDouble();
        }
    }
}