package org.example;

import javax.lang.model.SourceVersion;

public class ValidVariableDeclaration2 {
    public static void main(String[] args) {
        String dec = "char x ;";
        double x = 9.0*(9);
        //System.out.println(dec + " access modifier: "+getAccessModifier(dec));
       // System.out.println(dec.substring(getAccessModifier(dec).length()));
       // System.out.println(dec + " dataType: " + getDataType(dec.substring(getAccessModifier(dec).length())) );
        //System.out.println(dec + " identifier: " + getIdentifier("x;"));
        System.out.println(dec + " isValidVariableDeclaration: "+ validVariableDeclaration(dec));

    }

    static boolean validVariableDeclaration(String dec){
        dec = dec.trim();

        System.out.println(dec);
        String accessModifier = getAccessModifier(dec);
        System.out.println("accessModifier: "+accessModifier);
        if(accessModifier != ""){
            dec = dec.substring(accessModifier.length());
            dec = dec.trim();
        }

        System.out.println(dec);
        String dataType = getDataType(dec);
        System.out.println("dataType: "+dataType);
        if (dataType != ""){
            dec = dec.substring(dataType.length());
            dec = dec.trim();
        }else{
            System.out.println(dec);
            return false;
        }

        System.out.println(dec);
        String identifier = getIdentifier(dec);
        System.out.println("identifier: "+ identifier);
        if(identifier != ""){
            if(dec.indexOf('=') != -1 && dec.indexOf(';') != -1
                    && dec.indexOf('=') < dec.indexOf(';')){
                dec = dec.substring(dec.indexOf('=')+1);
                dec = dec.trim();
            }else if( dec.indexOf(";") != -1){
                dec= dec.substring(dec.indexOf(';')+1);
                return containsOnlySemicolon(dec);
            }else{
                return false;
            }
        }else {
            System.out.println(dec);
            return false;
        }

        System.out.println(dec);
        String literal = getLiteral(dec,dataType);
        System.out.println("Literal: "+literal);
        if(literal != ""){
            dec = dec.substring(dec.indexOf(";")+1);

        }else{
            //Invalid literal
            return false;
        }
        return containsOnlySemicolon(dec);

    }

    private static String getLiteral(String dec, String dataType) {
        dec = dec.trim();
        String literal = dec.substring(0, dec.indexOf(';'));
        if(dataType.equals("byte") && ValidVariableDeclaration.isValidByteLiteral(literal)){
            //System.out.println("byte check");
            return literal;
        }
        else if(dataType.equals("char") &&
                (ValidVariableDeclaration.isValidCharLiteral(literal) || ValidVariableDeclaration.isValidCharLiteralNumber(literal))){
            return literal;
        }

        return "";
    }

    private static boolean containsOnlySemicolon(String dec) {
        dec = dec.trim();
        for(int i = 0; i < dec.length(); i++){
            if(dec.charAt(i) != ';')
                return false;
        }
        return true;
    }


    private static String getIdentifier(String dec) {
        dec = dec.trim();
        String identifier = "";
        if(dec.indexOf('=') != -1){
            identifier = dec.substring(0,dec.indexOf('='));
        }else if(dec.indexOf(';') != -1){
            identifier = dec.substring(0,dec.indexOf(';'));
        }else{
            return "";
        }
        if(identifierIsValid(identifier))
            return identifier;

        return "";
    }

    private static boolean identifierIsValid(String identifier) {
        identifier = identifier.trim();
         if(SourceVersion.isKeyword(identifier))
             return false;
         if(SourceVersion.isIdentifier(identifier))
             return true;

         return false;
    }

    private static String getDataType(String dec) {
        dec = dec.trim();
        String dataType = dec.substring(0,dec.indexOf(' '));
        //System.out.println(dataType);
        if(dataType.equals("byte") || dataType.equals("char") ||
                dataType.equals("int") || dataType.equals("long"))
            return dataType;

        return "";
    }

    private static String getAccessModifier(String dec) {
        String accessModifier = dec.substring(0,dec.indexOf(' '));
        //System.out.println(accessModifier);
        if(accessModifier.equals("public") || accessModifier.equals("private")
                || accessModifier.equals("protected"))
            return accessModifier;
        return "";
    }
}
