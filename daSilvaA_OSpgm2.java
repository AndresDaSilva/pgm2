/***************************************
Author    : Andres Da Silva
Course    : CGS3767 U3 Tu-Th 6:25PM - 7:40PM
Professor : Michael Robinson
Program   : daSilvaA_OSpgm2
Purpose   : Read Hex RAM Error codes, convert to binary and decimal and identify the chip location
Due Date  : 09/27/2018

Certification:
I hereby certify that this work is my own and none of it is the work of any other person.

.........{Andres Da Silva}.........
***************************************/

import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

	
public class daSilvaA_OSpgm2 
{
    /**
     * returns the chip location of the RAM error location
     * @param bits is the RAM error location in decimal value
     */
    public static long getChip(long bits)
    {
        final long rate = 34359738368l; //Conversion rate from 4gig increases based on Robinson's example.
        long min = 0l;        //min & max range to check
        long max = rate; 
        int chip = 0; 
		
        for (int i = 0; i <= 8; i++) //8 should really be a variable that is the size of the amount of gigs the computer has
        {
            if (bits >= min && bits <= max) //starts off at min (0) and max (rate) where rate is (3435..)
                chip = i;   //if bits fits criteria, chip becomes current position

            min = max + 1; //Else it shifts all the ranges over, min becomes the previous max, and max becomes the previous max + rate again
            max = min + rate + 1; //Also both min and max need to increment by one to make the formula add up.
            //Note: The formula rate could theoritically be rate+1 to avoid having to increment both the min and max, but then the initial value
            //which starts at min 0 and max at rate, it would have to be max-1 for the first test case and I thought that looked ugly
        }
        return chip;
    }

    /**
     * Receives a Hexidecimal value and returns a Binary String conversion without parsing
     * @param heximal is the heximal input that will be converted
     */
    public static String getBinary(String heximal)
    {
        String hex = heximal;
        String binary = "";
		
        for (int i = 0; i < hex.length(); i++)
        {
            char x = hex.charAt(i); //Standard conversion to binary values, hard-coded for each possibility.
            if (x == '0')
                binary += "0000";
            if (x == '1')
                binary += "0001";
            if (x == '2')
                binary += "0010";
            if (x == '3')
                binary += "0011";
            if (x == '4') 
                binary += "0100";
            if (x == '5')
                binary += "0101";
            if (x == '6')
                binary += "0110";
            if (x == '7')
                binary += "0111";
            if (x == '8')
                binary += "1000";
            if (x == '9')
                binary += "1001";
            if (x == 'A')
                binary += "1010";
            if (x == 'B')
                binary += "1011";
            if (x == 'C')
                binary += "1100";
            if (x == 'D')
                binary += "1101";
            if (x == 'E')
                binary += "1110";
            if (x == 'F')
                binary += "1111";
            else
                System.out.printf("%s %c" , "Error: invalid input \"" + x + "\" is not a valid hex value")
        }	
            return binary;
    }
	/**
     * Recevies a binary value and converts it to a decimal value
     * @param s is a string of binary
     */
    public static long getDecimal(String s)
    {
        long decimal = 0l;
        //This for loop is a double loop that will both increment and decrease variables.
        //Variable x is going to start at 0 and increment until s.length. Variable x will test if the charracter in this position is a "1" or a "0"
        //Variable n is going to start at s.length and decrease until the for loop stops.
        for (int x = 0; x < s.length(); x++)
        {
            int n = s.length() - 1 - x;
            if (s.charAt(x) == '1')
            {
                decimal += Math.pow(2,n); //decimal is equal to 2 to the power of n where n is the position of the binary "1" plus itself again.  		
            }
        }
        return decimal;
    }

    public static void main (String[] args)
    {
        List<String> list = new ArrayList <String>();    //Holds all the Hex input
        long decimalNumber = 0l;
        String binaryNumber; //Both are the same binary string. Legacy code from previous iterations
        String binaryNibble;
        long location = 0l; //Chip location
        int size = 0; //Size of input (3 hex strings. aka 3 lines)

        try
        {
            FileReader fileReader = new FileReader("/root/pgm2/RAMerrors8x4b");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine())
            {
                list.add(scanner.nextLine());
                size++;
            }
            scanner.close();
        }
        catch (FileNotFoundException f) 
        {
            f.printStackTrace();
        }	
				
        System.out.printf("%s %74s", "Error", "Found at");
		
        for (int i = 0; i < size; i++) //Loops through all the conversions per each individual. Could add results to an object and expand on it
        {		
            binaryNumber = getBinary(list.get(i));
            binaryNibble = binaryNumber;
		
            decimalNumber = getDecimal(binaryNibble);
		
            location = getChip(decimalNumber);
            System.out.printf("\n%s = %s = %d = %d", list.get(i), binaryNibble, decimalNumber, location);
        }
        System.out.printf("\n");
    }
 }
