/**
 * A class to handle file loading and saving
 * 
 * @author Paulius Kuzmickas
 * @version 1.0
 */
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
public class FileHandling
{
    /**
     * A method to open a file to read
     * 
     * @param fileName the name of the file to open
     * @return returns a bufferedReader object to use for reading the file
     */
    public static BufferedReader openFileToRead(String fileName)
    {
        FileReader fileReader = null;
        BufferedReader bufferedReader=null;
        if(isUsable(fileName)) {
            try {
                fileReader = new FileReader(fileName);
                bufferedReader = new BufferedReader(fileReader);
            }
            catch (IOException e)
            {
                System.out.println("Sorry, there was a problem opening the file to read");
            }
        }
        return bufferedReader;
    }
    
    /**
     * A method to test if the file is usable
     * 
     * @param fileName the name of the file to open
     * @return returns true if the file is readable and it exists, otherwise returns false
     */
    public static boolean isUsable(String fileName)
    {
        File file = new File(fileName);
        boolean usable = file.exists();
        if(usable) usable = file.canRead();
        return usable;
    }
    /**
     * A method to save information to a file
     * 
     * @param slot the slot the user wants to write the save to
     * @param board the 2d array of the board the user wants to save
     * @param turn the current turn
     * @param player1 the name of player 1
     * @param player2 the name of player 2
     * @param player1Color the number of the player 1's color 
     * @param player2Color the number of the player 2's color 
     * @param player1Pieces the number of the player 1's pieces
     * @param player2Pieces the number of the player 2's pieces
     */
    public static void writeToFile(int slot, int[][] board, int turn, String player1, String player2, int player1Color, int player2Color, int player1Pieces, int player2Pieces)
    {
        FileOutputStream outputStream = null;
        PrintWriter printWriter = null;
        try
        {
            if(slot==0)
             outputStream = new FileOutputStream("slot0.txt");
             else if(slot==1)
             outputStream = new FileOutputStream("slot1.txt");
             else if(slot==2)
             outputStream = new FileOutputStream("slot2.txt");
             else if(slot==3)
             outputStream = new FileOutputStream("slot3.txt");
             else if(slot==4)
             outputStream = new FileOutputStream("slot4.txt");
            
             printWriter = new PrintWriter(outputStream); 
    
             for(int i=0; i<new Board().getRows(); i++)
             {
                 for(int j=0; j<new Board().getColumns(); j++)
                 {
                     printWriter.print(board[i][j] + " ");
                 }
                 printWriter.println();
             }
             printWriter.println(turn);
             printWriter.println(player1 + " " + player1Color + " " + player1Pieces);
             printWriter.println(player2 + " " + player2Color + " " + player2Pieces);
             writeFileName(slot, player1, player2);
             
        }
        catch (IOException e)
        {
             System.out.println("Sorry, there has been a problem opening or writing to the file");
        }
        finally
        {
   
             if (printWriter != null)
             {
                 printWriter.close();    
             }
    
        }
 
    }
    /**
     * A method to put and write the slot's name and date in
     * 
     * @param slot the slot the user wants to write the save to
     * @param player1 the name of player 1
     * @param player2 the name of player 2
     */
    private static void writeFileName(int slot, String player1, String player2)
    {
        FileOutputStream outputStream = null;
        PrintWriter printWriter = null;
        try
        {
            if(slot==0)
             outputStream = new FileOutputStream("slot0name.txt");
             else if(slot==1)
             outputStream = new FileOutputStream("slot1name.txt");
             else if(slot==2)
             outputStream = new FileOutputStream("slot2name.txt");
             else if(slot==3)
             outputStream = new FileOutputStream("slot3name.txt");
             else if(slot==4)
             outputStream = new FileOutputStream("slot4name.txt");
            
             printWriter = new PrintWriter(outputStream); 
             Date date = new Date();
             DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
             printWriter.println(player1 + " vs " + player2 + "   " + dateFormat.format(date));
             
             
        }
        catch (IOException e)
        {
             System.out.println("Sorry, there has been a problem opening or writing to the file");
        }
        finally
        {
   
             if (printWriter != null)
             {
                 printWriter.close();    
             }
    
        }
    }
    /**
     * A method read and return the name of the slot
     * 
     * @param slot the slot the user wants to get the saved information from
     * @return returns the name of the slot
     */
    public static String readName(int slot)
    {
        String nextLine="SLOT " + slot, fileName="";
        if(slot==0) fileName = "slot0name.txt";
        if(slot==1) fileName = "slot1name.txt";
        if(slot==2) fileName = "slot2name.txt";
        if(slot==3) fileName = "slot3name.txt";
        if(slot==4) fileName = "slot4name.txt";
        BufferedReader bufferedReader = openFileToRead(fileName);
        if(isUsable(fileName)) {
            try
            {
                 nextLine = bufferedReader.readLine();
                 
            }
            catch (IOException e)
            {
                System.out.println("Sorry, there has been a problem writing to the file");
            }
            finally
            {
                 if (bufferedReader != null)
                 {
                     try
                     {
                         bufferedReader.close();    
                     }
                     catch (IOException e)
                     {
                         System.out.println("An error occurred when attempting to close the file");
                     }
                 }  

    
            }
        }
        return nextLine;
    }
}
