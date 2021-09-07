import java.util.*;


public class parser{

    static StringTokenizer tokenList;
    static String currentToken;
    static boolean validity = true;

    static void chomp(){
        if(tokenList.hasMoreTokens()){
            currentToken = tokenList.nextToken();

        }
    }


    static String isNumeric(String token){

        if (Character.isDigit(token.charAt(0))){
            return "n";
        }
        return token;
    }

    static double E(){
        double tempVal = 0;
        System.out.println("E called with: " + currentToken);
        switch(isNumeric(currentToken)){
            case "n": 
                tempVal = T() + Ep(); 
                break;
            case "(": 
                tempVal = T() + Ep();
                break;
            default: validity = false;
        }
        if(tokenList.hasMoreTokens()){
            validity = false;
        }
        return tempVal;
        
    }

    static double Ep(){
        double tempVal = 0;
        System.out.println("Ep called with: " + currentToken);
        switch(isNumeric(currentToken)){
            case "+": 
                chomp(); 
                tempVal = T() + Ep();
                break;
            case "-": 
                chomp();  
                tempVal = (T() + Ep()) * -1;
                break;
            case ")": chomp();  return 0;
            case "$": chomp();  return 0;
            default: validity = false;
        }
        return tempVal;
    }

    static double T(){
        double tempVal = 0;
        System.out.println("T called with: " + currentToken);
        switch(isNumeric(currentToken)){
            case "n": 
                tempVal =F() * Tp(); break;
            case "(": 
                tempVal = F() * Tp(); break;
            default: validity = false;
        }
        return tempVal;
    }

    static double Tp(){
        double tempVal = 0;
        System.out.println("Tp called with: " + currentToken);
        switch(isNumeric(currentToken)){
            case "+": return 1;
            case "-": return 1;
            case ")": return 1;
            case "$": chomp();return 1;
            case "*": 
                chomp(); 
                tempVal = F() * Tp();break;
            case "/": 
                chomp();    
                tempVal = 1/(F() * Tp());break;
            default: validity = false;
        }
        return tempVal;
    }

    static double F(){
        double tempVal = 0;
        System.out.println("F called with: " + currentToken);
        switch(isNumeric(currentToken)){
            case "n": 
                tempVal = Double.parseDouble(currentToken);
                chomp(); break;
            case "(": 
                chomp(); 
                tempVal = E();break;
            default: validity = false;
        }

        return tempVal;
    }


    public static void main(String[] args){

        tokenList = new StringTokenizer("((3*7)$", "+-*/()$", true);
        
        chomp(); 

        double value = E();

        if(validity){
            System.out.println("Valid; " + value);
        }
        else{
            System.out.println("Invalid Expression");
        }

    }

}