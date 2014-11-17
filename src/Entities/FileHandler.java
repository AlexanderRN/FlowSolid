package Entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

    /**
     * This class handles writing to and from file.
     *
     * The methods are static so they can be called without making an object of
     * the class. If the application is designed with frequent file writing and
     * reading the a non-static method should be used instead.
     *
     *  EXAMPLE OF SAVING TO FILE:       
     *  ArrayList<Person> ap = new ArrayList<Person>();
        ap.add("Peter,3,8,12,13");
        ap.add("Linda,18,2,4,5");
        ap.add("Bob,8,12,4,6");
         
        FileHandlerStat.savePersons(as, "persons.txt");  
        
        EXAMPLE OF LOADING FROM FILE:
        ArrayList<Person> outof = new ArrayList<Person>();
        outof = FileHandlerStat.load("persons.txt");
        for (Person person : outof) {
            System.out.println("Person: " + person.toString());
        }   
     * 
     * @author Peter Lorensen/Jette Kreiner-Møller
     */

public class FileHandler
{

    /**
     * The method loads strings from disk. The file resides in the project folder.
     *
     * Make sure to check for null before using the return value!
     *
     * @param filename String the name of the file (that is located in the project folder).
**/
    
    public static ArrayList<Wordpair> loadWordpairs(String filename)
    {
        Scanner file_scanner = null;
        ArrayList<Wordpair> wordPairArray = new ArrayList<Wordpair>();

        try {
            file_scanner = new Scanner(new File(filename));  //Connection to the file using the Scanner object
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find the file to load from! Returning null.");
            ex.printStackTrace();
            return null;  //If something goes wrong the method returns null
        }
        
        while (file_scanner.hasNext()) {  //File found. Reading one line. 
            String linje = file_scanner.nextLine();
            Scanner sc = new Scanner(linje).useDelimiter(",");
            String question = sc.next();
            String answer = sc.next();
            int priority = sc.nextInt();
            Wordpair wP = new Wordpair(question, answer, priority);
            wordPairArray.add(wP);  //Reading in a single line and saving in the ArrayList
        }

        file_scanner.close();  //Closing the file
        return wordPairArray;    //returning the arraylist
    }

    /**
     * This method saves an ArrayList of strings to disk.
     * Each string object in this array will be one line in the text file. 
     * The text file is overwritten, what ever was in there is lost. 
     *
     * @param stringArray ArrayList<String> Each String object in this array will be 
     * saved as one line in the text file. 
     * @params filename String the name of the file (that is located in the project folder).
     * @return true if everything went well. False if an exception was raised.
     */
       
    public static boolean saveWordpair(ArrayList<Wordpair> wordPairArray, String filename)
    {
        if( wordPairArray == null ) { 
            return false;
        }  //Checking parameter for null.
        FileWriter output;  //Creating reference for filewriter.
        
        try {
                output = new FileWriter(new File(filename));  //Opening connection to file.
                for (Wordpair wordpairline : wordPairArray) {   //running through the ArrayList.                    
                    output.write(wordpairline.toString() + "\n");  //Each String object is written as a line in file.
            }

            output.close();  //Closing the file
        } catch (IOException ex) {  //If something goes wrong everything is send to system out.
            System.out.println("Could not save to file!");
            System.out.println(ex.toString());
            ex.printStackTrace();
            return false;  //If something goes wrong false is returned!
        }

        return true;
    }

}//END CLASS
