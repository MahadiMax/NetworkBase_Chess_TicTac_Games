package utils;

public class StringJoin {


    public static String joinWithCommas(Object...args){
        String line = "";
        for (Object arg : args) {
            line += arg + ",";
        }
        line = line.substring(0,line.length()-1);
        return line;
    }
}
