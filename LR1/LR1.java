import java.util.Stack;
import java.util.StringTokenizer;
import java.io.IOException;

public class LR1{


    static StringTokenizer tokens;
    static String currentToken;
    static Stack<String> tokenStack = new Stack<String>();
    static Stack<Double> numStack = new Stack<Double>();
    static Stack<Integer> stateStack = new Stack<Integer>();
    static String input = "(9-2)*3+(3/5)*9/2+11$";
    static boolean valid = false;


    static final int ET = 0;
    static final int TF = 1;
    static final int FN = 2;
    static final int EET = 3;
    static final int TTF = 4;
    static final int FE = 5;


    static void chomp(){
        if(currentToken != null){
            tokenStack.push(currentToken);
        }
        currentToken = tokens.nextToken();
        try{
            numStack.push(Double.parseDouble(currentToken));
            currentToken = "n";
        } catch(NumberFormatException nfe){}
    }

    static void shift(int state){
        stateStack.push(state);
        chomp();
        iterate();
    }

    static void reduceState(int num){
        for(int i = 0; i<num;i++){
            stateStack.pop();
        }
        switch(stateStack.peek()){
            case 0:{
                switch(tokenStack.peek()){
                    case "E": stateStack.push(1);break;
                    case "T": stateStack.push(2);break;
                    case "F": stateStack.push(3);break;
                }break;
            }
            case 4:{
                switch(tokenStack.peek()){
                    case "E": stateStack.push(8);break;
                    case "T": stateStack.push(2);break;
                    case "F": stateStack.push(3);break;
                }break;
            }
            case 6:{
                switch(tokenStack.peek()){
                    case "T": stateStack.push(9);break;
                    case "F": stateStack.push(3);break;
                }break;
            }
            case 7:{
                switch(tokenStack.peek()){
                    case "F": stateStack.push(10);break;
                }break;
            }
        }
    }

    static void reduce(int rule){

        switch(rule){
            //E->T
            case 0:{tokenStack.pop(); tokenStack.push("E"); reduceState(1);break;}
            //T->F
            case 1:{tokenStack.pop(); tokenStack.push("T");reduceState(1);break;}
            //F->N
            case 2:{tokenStack.pop(); tokenStack.push("F");reduceState(1);break;}
            //E->E & T
            case 3:{
                tokenStack.pop(); String op = tokenStack.pop(); tokenStack.pop(); 
                tokenStack.push("E");
                switch(op){
                    case "+": numStack.push(numStack.pop()+numStack.pop());break;
                    case "-": numStack.push(0-numStack.pop()+numStack.pop());break;
                }
                reduceState(3);
                break;
            }
            //T->T & F pops 3 and adds "T", also calculates in numstack
            case 4:{
                tokenStack.pop(); String op = tokenStack.pop(); tokenStack.pop(); 
                tokenStack.push("T");
                switch(op){
                    case "*": numStack.push(numStack.pop()*numStack.pop());break;
                    case "/": numStack.push(1/numStack.pop()*numStack.pop());break;
                }
                reduceState(3);
                break;
            }
            //F->(E) pops 3 and adds "F"
            case 5:{tokenStack.pop(); tokenStack.pop(); tokenStack.pop(); tokenStack.push("F"); reduceState(3);
            break;
            }
        }
        iterate();

    }

    static void printDebug(){
        Stack<Double> tempStack = (Stack<Double>)numStack.clone();
        Stack<Double> tempStack2 = new Stack<Double>();
        Stack<Integer> intStack = (Stack<Integer>)stateStack.clone();
        Stack<Integer> intStack2 = new Stack<Integer>();
        while(!tempStack.empty()){
            tempStack2.push(tempStack.pop());
        }
        while(!intStack.empty()){
            intStack2.push(intStack.pop());
        }
        intStack2.pop();
        System.out.print("Stack:[(-:0) ");
        for(int i = 0; i<tokenStack.size(); i++){
            if(tokenStack.elementAt(i)=="n" || tokenStack.elementAt(i)=="E" || tokenStack.elementAt(i)=="T" || tokenStack.elementAt(i)=="F"){
                System.out.print("("+tokenStack.elementAt(i)+"="+tempStack2.pop()+":"+intStack2.pop()+") ");
            }
            else{
                System.out.print("("+tokenStack.elementAt(i)+":"+intStack2.pop()+") ");
            }
        }
        System.out.println("]     Input Queue: "+currentToken);
    }

    static void iterate(){
        printDebug();

        switch(stateStack.peek()){
            case 0:{
                switch(currentToken){
                    case "n": shift(5);break;
                    case "(": shift(4);break;
                }break;
            }
            case 1:{
                switch(currentToken){
                    case "+": shift(6);break;
                    case "-": shift(6);break;
                    case "$": valid = true; return;
                }break;
            }
            case 2:{
                switch(currentToken){
                    case "+": reduce(ET);break;
                    case "-": reduce(ET);break;
                    case "*": shift(7);break;
                    case "/": shift(7);break;
                    case ")": reduce(ET);break;
                    case "$": reduce(ET);break;
                }break;
            }
            case 3:{
                switch(currentToken){
                    case "+": reduce(TF);break;
                    case "-": reduce(TF);break;
                    case "*": reduce(TF);break;
                    case "/": reduce(TF);break;
                    case ")": reduce(TF);break;
                    case "$": reduce(TF);break;
                }break;
            }
            case 4:{
                switch(currentToken){
                    case "n": shift(5);break;
                    case "(": shift(4);break;
                }break;
            }
            case 5:{
                switch(currentToken){
                    case "+": reduce(FN);break;
                    case "-": reduce(FN);break;
                    case "*": reduce(FN);break;
                    case "/": reduce(FN);break;
                    case ")": reduce(FN);break;
                    case "$": reduce(FN);break;
                }break;
            }
            case 6:{
                switch(currentToken){
                    case "n": shift(5);break;
                    case "(": shift(4);break;
                }break;
            }
            case 7:{
                switch(currentToken){
                    case "n": shift(5);break;
                    case "(": shift(4);break;
                }break;
            }
            case 8:{
                switch(currentToken){
                    case "+": shift(6);break;
                    case "-": shift(6);break;
                    case ")": shift(11);break;
                }break;
            }
            case 9:{
                switch(currentToken){
                    case "+": reduce(EET);break;
                    case "-": reduce(EET);break;
                    case "*": shift(7);break;
                    case "/": shift(7);break;
                    case ")": reduce(EET);break;
                    case "$": reduce(EET);break;
                }break;
            }
            case 10:{
                switch(currentToken){
                    case "+": reduce(TTF);break;
                    case "-": reduce(TTF);break;
                    case "*": reduce(TTF);break;
                    case "/": reduce(TTF);break;
                    case ")": reduce(TTF);break;
                    case "$": reduce(TTF);break;
                }break;
            }
            case 11:{
                switch(currentToken){
                    case "+": reduce(FE);break;
                    case "-": reduce(FE);break;
                    case "*": reduce(FE);break;
                    case "/": reduce(FE);break;
                    case ")": reduce(FE);break;
                    case "$": reduce(FE);break;
                }break;
            }
        }
        if(!tokens.hasMoreTokens()){return;}
    }


    public static void main(String[] args) throws IOException{

        
        tokens = new StringTokenizer(args[0]+"$", "+-*/$()", true);
        stateStack.push(0);
        chomp();
        iterate();
        if(valid){
            System.out.println("Valid Expression, value = "+numStack.pop());
        }
        else{
            System.out.println("Invalid Expression");
        }


    }

}