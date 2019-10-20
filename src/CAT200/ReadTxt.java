/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CAT200;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

//import java.io.FileNotFoundException;

public class ReadTxt {
    private LinkedList<String> temporary = new LinkedList<String>();

    public ReadTxt(String filename) {
        try {
            File fmanager = new File(filename);
            if (fmanager.createNewFile()) {
                System.out.println("File created: " + fmanager.getName());
            } else {
                System.out.println("File found.");
            }
            System.out.println("Absolute path: " + fmanager.getAbsolutePath());
            System.out.println("Readable " + fmanager.canRead());
            System.out.println("File size in bytes " + fmanager.length());
            Scanner myReader = new Scanner(fmanager);
            String data;
            while(myReader.hasNextLine()) {

                    data = myReader.nextLine();
                    System.out.println(data);
                    temporary.add(data);
                }

            myReader.close();
        } catch (IOException error) {
            System.out.println("An error ocurred.");
            error.printStackTrace();
        }
    }

    public LinkedList<String> getData() {
        return temporary;
    }
}
