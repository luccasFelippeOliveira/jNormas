package analisador.sintatico;

import NormGenerator.Generator;
import analisador.lexico.*;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.norm.checker.norm.definition.Norm;

import java.time.LocalDate;
import java.util.*;

import static analisador.lexico.Token.*;

/**
 * Syntactic Analyser Class. Analyse Token Stream generated by Lexical Analysis.
 * Obs.: DIRTY WAY
 * Created by luccas on 3/19/16.
 */
public class SyntacticAnalyser {
    private TokenStream stream;
    private TokenMark token;
    private Iterator<TokenMark> iterator;
    private Multimap<String, String> normParameters = LinkedHashMultimap.create(); /*Create a multimap -- Guava collections*/
    private String currentKey;
    private Generator generator;

    private String actionName = null;
    private String objectName = null;
    private boolean madeAnalysis = false;
    /*Depending on type, next state can change*/
    private enum IdentifierType {
        ATTRIBUTE,
        VALUE
    }

    private enum DateType{
        ACTIVATION,
        DEACTIVATION
    }

    /*Constructor*/
    public SyntacticAnalyser(TokenStream inputStream) {
        this.stream = inputStream;
        this.iterator = inputStream.iterator();
        this.generator = new Generator();
    }

    public void doSyntaxAnalysis() throws SyntacticErrorException, UnexpectedEndOfInputException {
        madeAnalysis = true;
        if (iterator.hasNext()) {
            /*Start condition for action only*/
            token = iterator.next();

            /*Start analysis*/
            deonticConcept();
        }
    }
    /*Deontic Concept Stage*/
    private void deonticConcept() throws  SyntacticErrorException, UnexpectedEndOfInputException {
        if((token == TOKEN_OBLIGED) || (token == TOKEN_PERMISSION) || (token == TOKEN_FORBIDDEN) ) {
            generator.setDeonticConcept(token);
        } else {
           throw  new SyntacticErrorException(token);
        }

        /*Next token must be a identifier (action) */
        if(iterator.hasNext()) {
            token = iterator.next();

            /*Next is expected to be an action*/
            if(token instanceof IdentifierToken) {
                /*Go to identifier*/
                action();
            }
            else {
                throw  new SyntacticErrorException(token);
            }
        }
        else {
            throw new UnexpectedEndOfInputException();
        }
    }

    /*action method*/
    private void action() throws SyntacticErrorException, UnexpectedEndOfInputException {
        /*Process action token*/
        actionName = token.toString();

        if(iterator.hasNext()) {
            token = iterator.next();

            /*Next is expected to be a identifer(object) or context token*/

            if(token instanceof IdentifierToken) {
                /*Go to object*/
                object();
            }
            else if((token ==TOKEN_ENVIRONMENT) || (token == TOKEN_ORGANIZATION)) {
                /*Go to context*/
                context();
            }
            else {
                throw new SyntacticErrorException(token);
            }
        }
        else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void object() throws SyntacticErrorException, UnexpectedEndOfInputException {
        /*Process action token*/
        objectName = token.toString();

        if(iterator.hasNext()) {
            token = iterator.next();

            /*Next is expected to be a left parenthesis or context token*/

            if(token == TOKEN_LEFTPARENTHESIS) {
                /*Go to left parenthesis*/
                left_parenthesis();
            }
            else if((token ==TOKEN_ENVIRONMENT) || (token == TOKEN_ORGANIZATION)) {
                /*Go to context*/
                context();
            }
            else {
                throw new SyntacticErrorException(token);
            }
        }
        else {
            throw new UnexpectedEndOfInputException();
        }
    }

    /*Identifier method*/
    /*private void identifier() throws SyntacticErrorException, UnexpectedEndOfInputException {
        *//*Process identifier token --> Intermediate Code Generation &/or interpretation*//*
        if (iterator.hasNext()) {
            token = iterator.next();

            if (token instanceof IdentifierToken) {
                *//*Go to Identifier*//*
                *//*Is an object, add to normParameters*//*
                normParameters.put("_object", token.toString());
                identifier();

            } else if (token == Token.TOKEN_LEFTPARENTHESIS) {
                *//*Go to left parenthesis method*//*
                left_parenthesis();*//*Might throw UnexpectedEndOfInputExcpetion*//*
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            *//*For a identifier an end of input is a expected thing*//*
            *//*Cover options such as:
            *                       - action
            *                       - action object *//*
//            System.out.println("Input accepted");
        }

    }*/


    private void left_parenthesis() throws SyntacticErrorException, UnexpectedEndOfInputException {
        /*Process left parenthesis token*/

        /*Next token is expected to be an attribute -> identifier only*/
        if (iterator.hasNext()) {
            token = iterator.next();
            if (token instanceof IdentifierToken) {
                /*Go to identifier method with Attribute option*/

                attribute();
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void attribute() throws SyntacticErrorException, UnexpectedEndOfInputException {
        currentKey = token.toString();
        if (iterator.hasNext()) {
            token = iterator.next();
            /*Is expected equals token*/
            if (token == Token.TOKEN_EQUALS) {
                /*Go to euquals*/
                equals();
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void equals() throws SyntacticErrorException, UnexpectedEndOfInputException {
        /*Process equals token*/

        /*Next token is expected to be an right curly*/
        if (iterator.hasNext()) {
            token = iterator.next();

            if (token == Token.TOKEN_LEFTCURLY) {
                /*Go to left curly*/
                left_curly();
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void left_curly() throws SyntacticErrorException, UnexpectedEndOfInputException {
        if (iterator.hasNext()) {
            token = iterator.next();
            /*value type -> identifier is expected*/
            if (token instanceof IdentifierToken) {
                /*Go to value*/
                value();
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void value() throws SyntacticErrorException, UnexpectedEndOfInputException {
        normParameters.put(currentKey, token.toString());
        if (iterator.hasNext()) {
            /*Is expected comma or a right curly*/
            token = iterator.next();

            if (token == Token.TOKEN_COMMA) {
                /*Go to comma*/
                comma(IdentifierType.VALUE);

            } else if (token == Token.TOKEN_RIGHTCURLY) {
                /*Go to right curly*/
                right_curly();
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void comma(IdentifierType identifierType) throws SyntacticErrorException, UnexpectedEndOfInputException {
        if (iterator.hasNext()) {
            token = iterator.next();
            /*Is expected a identifier, which type is defined by identifierType*/
            if (token instanceof IdentifierToken) {
                if (identifierType == IdentifierType.VALUE) {
                    /*Go to value*/
                    value();
                }
                if (identifierType == IdentifierType.ATTRIBUTE) {
                    /*Go to Attribute*/
                    attribute();
                }
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }

    }

    private void right_curly() throws SyntacticErrorException, UnexpectedEndOfInputException {
        if (iterator.hasNext()) {
            token = iterator.next();
            /*Is expected a comma or right parenthesis*/
            if (token == Token.TOKEN_COMMA) {
                /*Goes to comma, attribute type*/
                comma(IdentifierType.ATTRIBUTE);
            } else if (token == Token.TOKEN_RIGHTPARENTHESIS) {
                /*Go to right parenthesis*/
                right_parenthesis();
            } else {
                throw new SyntacticErrorException(token);
            }
        } else {
            throw new UnexpectedEndOfInputException();
        }

    }

    /*right parenthesis method*/
    private void right_parenthesis() throws SyntacticErrorException, UnexpectedEndOfInputException {
        if(iterator.hasNext()) {
            token = iterator.next();

            /*Is expected a context token*/
            if((token ==TOKEN_ENVIRONMENT) || (token == TOKEN_ORGANIZATION)) {
                /*Go to context*/
                context();
            } else {
                throw new SyntacticErrorException(token);
            }
        }
        else {
            throw new UnexpectedEndOfInputException();
        }
    }
    /*context method*/
    private void context() throws SyntacticErrorException, UnexpectedEndOfInputException {
        generator.setContext(token);

        if(iterator.hasNext()) {
            token = iterator.next();

            /*Is expected a Entity token*/

            if(     (token == TOKEN_AGENT)        ||
                    (token == TOKEN_ROLE)         ||
                    (token == TOKEN_ORGANIZATION) ||
                    (token == TOKEN_ALL)) {

                 /*Go to entity */
                entity();
            }
            else {
                throw new SyntacticErrorException(token);
            }
        }
        else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void entity() throws SyntacticErrorException, UnexpectedEndOfInputException {
        generator.setEntity(token);
        /*MVP: Generate norm here*/
        //generateNorm();
        /*Is actually expected a activation_date -> deactivation_date */
        /*MVP: expected a end of input */
        if(iterator.hasNext()) {
            token = iterator.next();

            /*Must be a NumberToken*/
            if(token instanceof NumberToken) {
                /*Go to activationDate*/
                date(DateType.ACTIVATION);
            }
            else {
                throw new SyntacticErrorException(token);
            }
        }
        else {
            throw new UnexpectedEndOfInputException();
        }
    }

    private void date(DateType type) throws SyntacticErrorException, UnexpectedEndOfInputException {
        /*token is a number*/
        /*First element of iteration is day*/
        NumberToken numberToken = (NumberToken) token;
        int[] date = new int[3];
        /*date array:
            0 => day
            1 => Month
            2 => year
         */
        int dateIndex = 0;
        date[dateIndex] = numberToken.getValue();
        dateIndex ++;
        for(int i = 0; i < 4; i ++) {
            if(iterator.hasNext()) {
                token = iterator.next();

                if(i % 2 == 0) {
                    /*Even i means a forward slash*/
                    if(token != Token.TOKEN_FOWARDSLASH){
                        /*Error*/
                        throw new SyntacticErrorException(token);
                    }
                }
                else {
                    /*i is odd means a number*/
                    if(token instanceof NumberToken){
                        numberToken = (NumberToken) token;
                        date[dateIndex] = numberToken.getValue();
                        dateIndex ++;
                    }
                    else {
                        throw new SyntacticErrorException(token);
                    }
                }
            }
            else {
                throw new UnexpectedEndOfInputException();
            }
        }
        LocalDate localDate = LocalDate.of(date[2], date[1], date[0]);
        if(type == DateType.ACTIVATION){
            generator.setActivationConstraint(localDate);

            if(iterator.hasNext()){
                token = iterator.next();
                if(token instanceof NumberToken) {
                /*Go to deactivationDate*/
                    date(DateType.DEACTIVATION);
                }
                else {
                    throw new SyntacticErrorException(token);
                }
            }
            else {
                throw new UnexpectedEndOfInputException();
            }
        }
        else {
            generator.setDeactivationConstraint(localDate);
            if(iterator.hasNext()){
                /*End of input is expected*/
                throw new SyntacticErrorException(token);
            }
            else {
                System.out.println("input ok");
            }
        }
    }


    public Norm generateNorm() {
        /*Generate some random code number*/
        generator.setCode(1l);/*TODO: Actually generate*/

        generator.setBehaviorMultipleParameters(normParameters);

        return generator.generateNorm(actionName,objectName);

    }
    public Multimap<String, String> getNormParameters() {
        return normParameters;
    }

}
