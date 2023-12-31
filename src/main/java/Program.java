import ast.StmtListNode;
import codeGenBase.msil.MsilCodeGenerator;
import parser.Parser;
import printer.Printer;
import semanticBase.IdentScope;
import semanticChecker.SemanticChecker;

public class Program {
    public static void execute(String programFile, boolean msilOnly, boolean jbcOnly, String fileName) {
        StmtListNode prog = null;
        try {
            prog = Parser.parse(programFile);
        } catch (Exception e) {
            System.out.println(String.format("Ошибка: %s", e.getMessage()));
            System.exit(1);
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println("ast:");
            System.out.println(Printer.printTree(prog.tree(), System.lineSeparator()));
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println();
            System.out.println("semantic-check:");
        }
        try {
            SemanticChecker checker = new SemanticChecker();
            IdentScope scope = SemanticChecker.prepareGlobalScope();
            checker.semanticCheck(prog, scope);
            if (!(msilOnly || jbcOnly)) {
                System.out.println(Printer.printTree(prog.tree(), System.lineSeparator()));
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(String.format("Ошибка: %s", e.getMessage()));
            System.exit(2);
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println();
            System.out.println("msil:");
        }
        if (!jbcOnly) {
            try {
                MsilCodeGenerator gen = new MsilCodeGenerator();
                gen.genProgram(prog);
                String resultMsil = Printer.printTree(gen.code(), System.lineSeparator());
                System.out.println(resultMsil);
                Printer.writeToFile(fileName.split("[.]")[0] + ".msil", resultMsil, false);
            } catch (Exception e) {
                System.out.println(String.format("Ошибка: %s", e.getMessage()));
                System.exit(3);
            }
        }

//        todo реализовать аналогично генерации msil кода генерацию java byte code
//        if (!(msilOnly || jbcOnly)) {
//            System.out.println("jbc:");
//        }
//        if (!msilOnly) {
//            try {
//                gen = jbc.JbcCodeGenerator(file_name)
//                gen.gen_program(prog)
//                print(*gen.code, sep=os.linesep)
//            } catch (Exception e) {
//                System.out.println(String.format("Ошибка: %s", e.getMessage()));
//            }
//        }
    }
}
