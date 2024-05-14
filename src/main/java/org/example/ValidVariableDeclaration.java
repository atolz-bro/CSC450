package org.example;

import net.objecthunter.exp4j.ExpressionBuilder;

import javax.lang.model.SourceVersion;

public class ValidVariableDeclaration {
    public static void main(String[] args) {
        byte age= -(-((20)));
        byte a = '-';
        String declaration = "byte  intt= (-1-1-1-1);";
        System.out.println(declaration + "->is valid: " + isValidVariableDeclaration(declaration));
    }

    static boolean isValidVariableDeclaration(String
                                                      dec){
        String [] tokens = new String[2];
        boolean valid = isValidVariableDeclarationPattern(dec, tokens);

        if(valid){
            System.out.println("type: "+tokens[0]);
            System.out.println("identifier: "+tokens[1]);
            if(identifierCheck(tokens[1]) && literalValueCheck(dec,tokens[0]))
                return true;
        }
        return false;
    }

    private static boolean identifierCheck(String identifier) {
        System.out.println(identifier);
        return !SourceVersion.isKeyword(identifier);
    }

    private static boolean literalValueCheck(String dec,String literalType) {
        //get literal value
        String literal = getDeclarationLiteral(dec).trim();
        if(!literal .equals("")){
            if(literalType == "byte"){
                System.out.println("byte check!!!");
                return isValidByteLiteral(literal);
            }else if(literalType == "int"){

            }else if(literalType == "char"){
                return isValidCharLiteral(literal) || isValidCharLiteralNumber(literal);
            }

        }
        //true if no literal with its assignment symbol is found
        return true;
    }

    private static String getDeclarationLiteral(String dec) {
        if(dec.indexOf('=') != -1){
            return dec.substring(dec.indexOf('=')+1,dec.indexOf(';'));
        }
        return "";
    }

    static boolean isValidByteLiteral(String lit){
        //System.out.println("literal byte to evaluate"+lit);
        //Check if it's a valid whole number arithmetic expression
        if(Main.isValidArithmeticExpression(lit)){
            //Evaluate the expression
            double v = new ExpressionBuilder(lit).build().evaluate();
            //System.out.println("expression val: "+v);
            //Check range of values of byte
            if(v < 128 && v >= -128 )
                return true;
            else
                return false;
        }else{
            System.out.println("Not a valid Arithmetic Expression");
        }
        if(isValidCharLiteral(lit)){
            return true;
        }else{
            System.out.println("Not a valid Char literal");
        }
        return false;
    }

     static boolean isValidCharLiteral(String lit) {

        return lit.length() == 3 && lit.charAt(0) == '\'' && lit.charAt(2) == '\'';
    }
     static boolean isValidCharLiteralNumber(String lit) {
        //Check if it's a valid whole number arithmetic expression
        if(Main.isValidArithmeticExpression(lit)){
            //Evaluate the expression
            double v = new ExpressionBuilder(lit).build().evaluate();
            //System.out.println("expression val: "+v);
            //Check range of values of byte
            return  v > -1 && v < 65536;
        }
        return false;
    }



    static boolean isValidVariableDeclarationPattern(String express,String[] tokens){
        String state = "s0";
        tokens[0] = "";
        tokens[1] = "";
        for(int i = 0; i < express.length(); i++){
            char input = express.charAt(i);
            if(state == "s0")
               state =  s0(input);
            else if(state == "b1")
                state = b1(input);
            else if(state == "b2")
                state = b2(input);
            else if(state == "b3")
                state = b3(input,tokens);
            else if(state  == "b4")
                state = b4(input);
            else if(state == "s1")
                state = s1(input,tokens);
            else if(state == "i1")
                state = i1(input,tokens);
            else if(state == "s2")
                state = s2(input);
            else if(state == "as1")
                state = as1(input);
            else if(state == "v1")
                state = v1(input);
            else if(state == "sc")
                state = sc(input);
            else {
                //TRASH
                System.out.println(state);
                return false;
            }
        }

        return state == "sc";
    }
    static String s0(char input){
        if(input == 'b'){
            System.out.print("s0->b1");
            return "b1";
        }

        return "TRASH";
    }
    static String b1(char input){
        if(input == 'y'){
            System.out.print("b1->b2, ");
            return "b2";
        }

        return "TRASH";
    }
    static String b2(char input){
        if(input == 't'){
            System.out.print("b2->b3, ");
            return "b3";
        }

        return "TRASH";
    }
    static String b3(char input, String [] tokens){
        if(input == 'e'){
            //pos 0 is for type
            tokens[0] = "byte";
            System.out.println("b3->b4");
            return "b4";
        }
        return "TRASH";
    }
    static String b4(char input){
        if(input == ' '){
            System.out.print("b4->s1, ");
            return "s1";
        }

        return "TRASH";
    }
    static String s1(char input,String [] tokens){
        if(input == ' '){
            System.out.print("s1->s1, ");
            return "s1";
        }

        if(isValidIdentifierStartChar(input)){
            System.out.println("s1->i1");
            tokens[1] = tokens[1]+input;
            System.out.println("identifier: "+tokens[1]);
            return "i1";
        }

        return "TRASH";
    }
    static String i1(char input,String [] tokens){
        if(isValidIdentifierBodyChar(input)){
            System.out.print("i1->i1, ");
            tokens[1] = tokens[1]+input;
            System.out.println("identifier: "+tokens[1]);
            return "i1";
        }
        if(input == '='){
            System.out.println("i1->as1");
            return "as1";
        }
        if(input == ';'){
            System.out.println("i1->sc");
            return "sc";
        }
        if(input == ' '){
            System.out.println("i1->s2");
            return "s2";
        }
        return "TRASH";
    }
    static String s2(char input){
        if(input == ' '){
            System.out.print("s2->s2, ");
            return "s2";
        }
        if(input == ';'){
            System.out.println("s2->sc");
            return "s2";
        }
        if(input == '='){
            System.out.println("s2->as1");
            return "as1";
        }
        return "TRASH";
    }
    static String as1(char input){
        if(input == ' '){
            System.out.print("as1->as1, ");
            return "as1";
        }
        System.out.println("as1->v1");
        return "v1";
    }
    static String v1(char input){
        if(input == ';'){
            System.out.println("v1->sc");
            return "sc";
        }
        if(input != ';')
            return "v1";
        return "TRASH";
    }
    static String sc(char input){
        if(input == ';')
            return "sc";
        return "TRASH";
    }

    static boolean isValidIdentifierStartChar(char input){
        return input == '_' || input == '$' || Character.isAlphabetic(input);
    }
    static boolean isValidIdentifierBodyChar(char input){
        return input == '_' || input == '$' || Character.isAlphabetic(input) || Character.isDigit(input);
    }

}
