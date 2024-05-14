package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Expression e = new ExpressionBuilder("(2+3)*3(2)").build();

        System.out.println(e.evaluate());
        byte v = (((9)));
        String arithmetic = "(2)*(2+3)*";
        char x =';';
        System.out.println(arithmetic + " valid arithmeticExpression: "+ isValidArithmeticExpression(arithmetic) );
    }

    static boolean isValidArithmeticExpression(String exp){
        Stack<Character> s = new Stack<>();
        s.push('$');
        int state = 0;
        for(int i = 0; i < exp.length(); i++){
            //char top = s.pop();

            char input = exp.charAt(i);

            if(state == 0 && (state = state0(input,s)) != -1){
                //continue
            }

            else if( state == 1 && inputIsOperand(input)){
                state = 5;
            }else if( state == 1 && Character.isDigit(input)){
                state = 1;
            }

            else if( state == 5 && Character.isDigit(input) ){
                state = 1;
            }else if( state == 5 && input == '(' && s.peek() == '$'){
                state = 0;
                s.push('X');
            }

            else if(state == 2 && (state=state2(input,s)) != -1){
                //continue
            }
            else if(state == 3 && (state = state3(input,s)) != -1){
                //continue
            }
            else
                return false;
        }
        //System.out.println(s);
        return state == 1 || peek(s) == '$' && state == 2;
    }


    private static Character peek(Stack<Character> s) {
        return s.peek();
    }

    //Takes an input symbol push and pop appropriately and returns next state
    static int state3(char input, Stack<Character> s){

            if(Character.isDigit(input)){
                System.out.println("s3->s2");
                return 2;
            }

            else if(input == '(' && peek(s) == 'X'){
                System.out.println("s3->s0");
                s.push('X');
                return 0;
            }else if(input == '(' && peek(s) == '$'){
                System.out.println("s3->s0");
                s.push('X');
                return 0;
            }
            System.out.println("["+input+",s3->TRASH]");
            return -1;
    }
    static int state2(char input, Stack<Character> s){

        System.out.println("S2");
        if(Character.isDigit(input)){
            System.out.println("s2->s2");
            return 2;
        }
        else if(input == ')' && peek(s) == 'X'){
            System.out.println("s2->s2");
            s.pop();
            return 2;
        }else if(inputIsOperand(input)){
            System.out.println(input+",s2->s3");
            return 3;
        }
        return -1;
    }
    static int state0(char input, Stack<Character> s){
        if( input == '(' && peek(s) == '$'){
            s.push('X');
            return 0;
        }else if(input == '(' && peek(s) == 'X'){
            s.push('X');
            return  0;
        } else if (Character.isDigit(input) && peek(s) == 'X') {
            return 2;
        }else if(Character.isDigit(input) && peek(s) =='$'){
            return 1;
        }else if(input == '-' || input == '+'){
            System.out.println("["+input + "s0->s3]");
            return 3;
        }
        return -1;
    }


    static boolean inputIsOperand(char op){

        return op == '+' || op == '-' || op=='*' || op == '/' || op == '%';
        }

}
