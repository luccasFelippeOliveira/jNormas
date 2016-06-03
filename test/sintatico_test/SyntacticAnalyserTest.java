package sintatico_test;

import analisador.lexico.*;
import analisador.sintatico.SyntacticAnalyser;
import analisador.sintatico.SyntacticErrorException;
import analisador.sintatico.UnexpectedEndOfInputException;
import com.google.common.collect.Multimap;
import com.norm.checker.norm.definition.Norm;

import java.util.*;

/**
 * Created by luccas on 3/19/16.
 */
public class SyntacticAnalyserTest {

    public static void main(String[] args)  {
        TokenStream ts;
        String input = "OBLIGED action object (attribute1 ={v1, v2,v3}, attribute2 ={v4,v5,v6}) ORGANIZATION AGENT 12/04/2016 13/04/2016";
        LexicalAnalyser la = new LexicalAnalyser(input);
        ts = la.getTokenStream();
        System.out.println(ts);
//        TokenMark token = null;
//        Iterator<TokenMark> iterator = ts.iterator();
//
//        if(iterator.hasNext()) {
//            token = iterator.next(); /*Get First Token*/
//
//            if(token == Token.TOKEN_LEFTPARENTHESIS) {
//                /*Start Analysis*/
//                left_parenthesis(token, iterator);
//            }
//            else {
//                /*SYNTAX ERROR*/
//                System.out.println("Error: '(' Token expected at token = " + token);
//                return;
//            }
//
//        }
//        else {
//            System.out.println("No tokens .. ");
//            return;
//        }
        SyntacticAnalyser sa = new SyntacticAnalyser(ts);
        try {
            sa.doSyntaxAnalysis();
        } catch (SyntacticErrorException e) {
            e.printStackTrace();
            return;
        } catch (UnexpectedEndOfInputException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("MApa: ");

        Multimap<String,String> map = sa.getNormParameters();
        /*map.put("_action", "outro");
        map.put("_object", "carro");*/

        /*Each put() method call, inserts element at the beginning, instead of end.*/

        Set<String> keys = map.keySet();

        for(String key : keys) {
            System.out.println("Key: " + key);
            System.out.println("Values :" + map.get(key));
        }

        Norm norma = sa.generateNorm();

        /*PRINT NORM*/
        System.out.println(norma.toString());
        System.out.println(norma.getActivationConstraint());
        System.out.println(norma.getDeactivationConstraint());


    }

    /*left parenthesis method*/
    public static void left_parenthesis(TokenMark token, Iterator<TokenMark> iterator) {
        System.out.println("token is a  left parenthesis");
        /*Next token is expected to be a right parenthesis or an identifier*/
        if(iterator.hasNext()) {
            token = iterator.next();
            if(token instanceof IdentifierToken) {
                identifier(token, iterator);
            }
            else if(token == Token.TOKEN_RIGHTPARENTHESIS) {
                right_parenthesis(token, iterator);
            }
            else {
                /*Is not an expected token syntactic error*/
                System.out.println("Syntax ERROR on token = " + token);
            }
        }
        else {
            /*Expected a closing parenthesis*/
            System.out.println("Expected a ')' token");
            return;
        }

    }
    /*identifier Method*/
    public static void identifier(TokenMark token, Iterator<TokenMark> iterator) {
        System.out.println("token is identifier token = " + token);
        /*Next token is expected to be a right parenthesis or an comma*/
        if(iterator.hasNext()) {
            token = iterator.next();
            if(token == Token.TOKEN_COMMA) {
                /*Go to comma function*/
                    comma(token, iterator);
                }
            else if(token == Token.TOKEN_RIGHTPARENTHESIS) {
                /*Go to right parenthesis*/
                right_parenthesis(token, iterator);
            }
            else {
                /*Not an expected identifier*/
                System.out.println("SYNTAX ERROR on token = " + token);
                return;
            }

        }
        else {
            /*Unexpected end of input*/
            System.out.println("Unexpected end of input");
            return;
        }
    }

    /*right parenthesis method*/
    public static void right_parenthesis(TokenMark token, Iterator<TokenMark> iterator) {
        System.out.println("Right parenthesis token");
        if(iterator.hasNext()) {
            /*ERROR expected end of input*/
            System.out.println("Expected end of input");
        }
        else {
            System.out.println("Correct sequence!");
        }
        return;

    }

    public static void comma(TokenMark token, Iterator<TokenMark> iterator) {
        System.out.println("Comma is token");
        if(iterator.hasNext()) {
            /*Only identifier is expected*/
            token = iterator.next();

            if(token instanceof IdentifierToken) {
                /*Go to identifier state*/
                identifier(token, iterator);
            }
            else {
                /*SYNTAX ERROR*/
                System.out.println("Syntax ERROR on token = " + token);
                return;
            }
        }
        else {
            /*Unexpected end of input*/
            System.out.println("ERROR: Unexpected END of input");
            return;
        }
    }
}
