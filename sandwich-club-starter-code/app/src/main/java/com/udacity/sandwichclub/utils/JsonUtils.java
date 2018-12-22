package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import java.util.ArrayList;

public class JsonUtils {
    private static int i = 0;
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        char[] jsonCharArray = json.toCharArray();

        while (i < jsonCharArray.length) {
            if (jsonCharArray[i] == '"') {
                String currVal = getValueFromString(jsonCharArray);
                switch (currVal) {
                    case "mainName":
                        sandwich.setMainName(getValueFromString(jsonCharArray));
                        break;
                    case "alsoKnownAs":
                        sandwich.setAlsoKnownAs(getArrayFromString(jsonCharArray));
                        break;
                    case "placeOfOrigin":
                        sandwich.setPlaceOfOrigin(getValueFromString(jsonCharArray));
                        break;
                    case "description":
                        sandwich.setDescription(getValueFromString(jsonCharArray));
                        break;
                    case "image":
                        sandwich.setImage(getValueFromString(jsonCharArray));
                        break;
                    case "ingredients":
                        sandwich.setIngredients(getArrayFromString(jsonCharArray));
                        break;
                }
            }
            i++;
        }

        return sandwich;
    }

    public static ArrayList<String> getArrayFromString(char[] jsonCharArray) {
        ArrayList<String> arrayOfStrs = new ArrayList<>();
        skipLettersUntil(jsonCharArray, '[');
        skipLettersUntil(jsonCharArray, '"');

        while (jsonCharArray[i] != ']') {
            String item = getValueFromString(jsonCharArray);
            arrayOfStrs.add(item);
            i++;
        }
        // at this point i is at ']'
        return arrayOfStrs;
    }


    public static String getValueFromString(char[] jsonCharArray) {
        skipLettersUntil(jsonCharArray, '"');
        i++; // now i is at beginning of string or ':'
        if (jsonCharArray[i] == ':') {
            skipLettersUntil(jsonCharArray, '"');
            i++; // i is at beginning of string
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (jsonCharArray[i] != '"') {
            stringBuilder.append(jsonCharArray[i]);
            i++;
        }
        // at this point, i is at " (closing string)
        return stringBuilder.toString();

    }

    public static void skipLettersUntil(char[] jsonCharArray, char targetChar) {
        while (jsonCharArray[i] != targetChar) {
            i++;
        }
    }
}
