package front.end;

import NormGenerator.Generator;
import analisador.lexico.LexicalAnalyser;
import analisador.sintatico.SyntacticAnalyser;
import com.norm.checker.norm.definition.Norm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A sample norm interpreter application
 * Created by Luccas Oliveira on 23/04/2016.
 */
public class SampleProgram {
    List<Norm> normList = new ArrayList<>();
    public SampleProgram() {
        programLogic();
    }
    private void programLogic() {
        /*Ask user for norms then verifies it using conflict checker*/
        /*Create menu*/
        menu();

    }
    private int menu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----MENU-----");
        System.out.println("1) Criar nova Norma");
        System.out.println("2) Listar Normas");
        System.out.println("3) Sair");
        int selection = scanner.nextInt();

        switch (selection) {
            case 1:
                /*Create norm*/
                System.out.println("-----Criar nova Norma-----");
                createNorm();
                break;
            case 2:
                /*List norms*/
                System.out.println("-----Listar Normas-----");
                listNorm();
                break;
            case 3:
                /*Exits program*/
                return 0;
            default:
                System.out.println("Operação não suportada. Entre com opção correta");
                menu(); /*Stack overflow????*/
        }
        return selection;
    }
    private void createNorm() {
        System.out.println("Insira Norma: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        /*Makes interpretation*/
        try {
            LexicalAnalyser la = new LexicalAnalyser(input);
            SyntacticAnalyser sa = new SyntacticAnalyser(la.getTokenStream());
            sa.doSyntaxAnalysis();
            Norm norm = sa.generateNorm();
            /*Add norm to list*/
            normList.add(norm);
        }catch (Exception e) {
            System.out.println("Erro!!!" + e.toString());
        }
        /*Go back to menu*/
        menu();
    }
    private void listNorm() {
        if(normList.size() <= 0) {
            System.out.println("Não há normas");
        }
        else {
            for (Norm n: normList) {
                System.out.println("Norma: ");
                System.out.println(n.toString());
                System.out.println("--------------------");
            }
        }
        menu(); /*Goes back to menu*/
    }
}
