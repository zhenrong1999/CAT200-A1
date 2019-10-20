/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CAT200;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

//import java.io.FileNotFoundException;

public class File_Handling {
    private LinkedList<String> temporary = new LinkedList<String>();
    private String filename;

    public File_Handling(String filename){this.filename=filename;}

    public void ReadFromFile() {
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
    
    public void SaveToFile(Student_Database student_database){
        File fmanager = new File(filename);
        try (PrintWriter writer = new PrintWriter(new FileWriter(fmanager, true))) {
            for (Student i:student_database
                 ) {
                writer.println(i.getMatric_no()+" "+i.getName()+" "+i.getCubic_id()+" "+i.getCheckdate()+" "+i.getSupervisor());
            }

        } catch (IOException e) {


        }
    }
}
