/*
    Author: Jordan Arroyo
    Purpose: Transforms file contents into sql import file
 */
package TransformFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TransformFile {
    public static void transformFile(File file) throws FileNotFoundException {

        Scanner scan = new Scanner(file);

        try {
            try (FileWriter writer = new FileWriter("TransformedFile.sql")) {

                while (scan.hasNext()) {
                    writer.write("insert into words(word) values('" + scan.next() + "');" + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

    }


}