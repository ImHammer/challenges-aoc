package com.hammerdev.challengeaoc;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    static Logger logger;

    public static void main( String[] args )
    {
        BasicConfigurator.configure();
        logger = LogManager.getLogger(App.class);
        logger.setLevel(Level.ALL);

        // firstPart(); // COMPLETED
        
        // secondPart(); // COMPLETED
    }

    protected static void secondPart()
    {
        Map<String, Integer> listOfNumbers = new LinkedHashMap<>();
        listOfNumbers.put("one", 1);
        listOfNumbers.put("two", 2);
        listOfNumbers.put("three",3);
        listOfNumbers.put("four", 4);
        listOfNumbers.put("five", 5);
        listOfNumbers.put("six", 6);
        listOfNumbers.put("seven", 7);
        listOfNumbers.put("eight", 8);
        listOfNumbers.put("nine", 9);

        InputStream slapow = App.class.getResourceAsStream("p2_data.txt");
        InputStreamReader reader = new InputStreamReader(slapow);
        Scanner scanner = new Scanner(reader);
        
        int totalResult = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().toLowerCase();

            int firstNumber = -1;
            int lastNumber = -1;

            int lastFirstIndex = -1;
            int lastLastIndex  = -1;

            String[] chars = line.split("");
            for (var i = 0; i < chars.length; i++) {
                if (isNumericNumber(chars[i])) {

                    if (firstNumber == -1) {
                        firstNumber = Integer.valueOf(chars[i]);
                        lastFirstIndex = i;
                    } else {
                        lastNumber = Integer.valueOf(chars[i]);
                        lastLastIndex = i;
                    }
                } 
            }

            if (lastLastIndex == -1) lastLastIndex = lastFirstIndex;
            if (lastNumber == -1) lastNumber = firstNumber;

            logger.info(String.format("INC: %s - %d:%d", line, firstNumber, lastNumber)); // VALORES DUVIDOSOS!
            
            Iterator<Entry<String, Integer>> listNumbersIterator = listOfNumbers.entrySet().iterator();
            while(listNumbersIterator.hasNext()) {
                Entry<String, Integer> listOfEntry = listNumbersIterator.next();

                String numberInStr = listOfEntry.getKey();
                Integer numberInInt = listOfEntry.getValue();

                int findForFirstIndex = line.indexOf(numberInStr);
                int findForLastIndex = line.lastIndexOf(numberInStr);

                // FIRST
                if (findForFirstIndex != -1) {
                    if ((firstNumber == -1) || (findForFirstIndex < lastFirstIndex)) {
                        firstNumber = numberInInt;
                        lastFirstIndex = findForFirstIndex;   
                    }
                }
                // LAST
                if (findForLastIndex != -1) {
                    if ((lastNumber == -1) || (findForLastIndex > lastLastIndex)) {
                        lastNumber = numberInInt;
                        lastLastIndex = findForLastIndex;
                    }
                }
            }

            logger.info(String.format("COR: %s - %d:%d", line, firstNumber, lastNumber)); // VALORES CORRETOS

            totalResult += Integer.valueOf(String.format("%d%d", firstNumber, lastNumber));
        }
        scanner.close();
        logger.info("RESULT: " + totalResult);
    }

    protected static void firstPart()
    {
        InputStream slapow = App.class.getResourceAsStream("data.txt");
        InputStreamReader reader = new InputStreamReader(slapow);
        Scanner scanner = new Scanner(reader);

        int totalResult = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            int firstNumber = -1;
            int lastNumber = -1;

            String[] chars = line.split("");
            for (var i = 0; i < chars.length; i++) {
                if (isNumericNumber(chars[i])) {
                    if (firstNumber == -1) {
                        firstNumber = Integer.valueOf(chars[i]);
                    } else {
                        lastNumber = Integer.valueOf(chars[i]);
                    }
                } 
            }

            if (lastNumber == -1) lastNumber = firstNumber;

            logger.info(String.format("%s - %d:%d", line, firstNumber, lastNumber));

            totalResult += Integer.valueOf(String.format("%d%d", firstNumber, lastNumber));
        }
        scanner.close();

        logger.info("RESULT: " + totalResult);
    }

    protected static boolean isNumericNumber(String ch)
    {
        try {
            Integer.parseInt(ch);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
