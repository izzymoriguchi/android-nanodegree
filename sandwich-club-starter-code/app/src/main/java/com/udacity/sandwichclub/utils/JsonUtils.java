package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;

public class JsonUtils {
    private static int i = 0;
    private static final String TAG = "JsonUtils";
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        char[] jsonCharArray = json.toCharArray();

        Log.d(TAG, "parseSandwichJson: ");
        while (i < jsonCharArray.length) {
            if (jsonCharArray[i] == '"') {
                String currVal = getValueFromString(jsonCharArray);
                Log.d(TAG, "parseSandwichJson: currVal: " + currVal);
                if (currVal.equals("mainName")) {
                    String mainName = getValueFromString(jsonCharArray);
                    Log.d(TAG, "parseSandwichJson: sandwich mainName: " + mainName);
                    sandwich.setMainName(mainName);
                } else if (currVal.equals("alsoKnownAs")) {
                    ArrayList<String> alsoKnownAs = getArrayFromString(jsonCharArray);
                    for (String item : alsoKnownAs) {
                        Log.d(TAG, "parseSandwichJson: alsoKnownAs: " + item);
                    }
                    sandwich.setAlsoKnownAs(alsoKnownAs);
                } else if (currVal.equals("alsoKnownAs")) {

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
//        Log.d(TAG, "getArrayFromString: jsonCharArray[i] after skipLettersUntil(jsonCharArray, '\"');: " + jsonCharArray[i]); //

        while (jsonCharArray[i] != ']') {
            String item = getValueFromString(jsonCharArray);
//            Log.d(TAG, "getArrayFromString: item: " + item);
            arrayOfStrs.add(item);
            i++;
        }
        // at this point i is at ']'
        return arrayOfStrs;
    }


    public static String getValueFromString(char[] jsonCharArray) {
        skipLettersUntil(jsonCharArray, '"');
        //Log.d(TAG, "getValueFromString: jsonCharArray[i]: "+ jsonCharArray[i]);
        i++; // now i is at beginning of string
        if (jsonCharArray[i] == ':') {
            skipLettersUntil(jsonCharArray, '"');
            i++; // i is at beginning of string
        }
        Log.d(TAG, "getValueFromString: jsonCharArray[i]: "+ jsonCharArray[i]);
        StringBuilder stringBuilder = new StringBuilder();
        while (jsonCharArray[i] != '"') {
            stringBuilder.append(jsonCharArray[i]);
            i++;
        }
        Log.d(TAG, "getValueFromString: str: "+ stringBuilder.toString());
        // at this point, i is at " (closing string)
        return stringBuilder.toString();

    }

    public static void skipLettersUntil(char[] jsonCharArray, char targetChar) {
        while (jsonCharArray[i] != targetChar) {
            i++;
        }
    }
}
