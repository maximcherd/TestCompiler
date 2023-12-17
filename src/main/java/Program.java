import ast.StmtListNode;
import printer.Printer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Program {
    static Parser parser = null;

    public static void execute(String programFile, boolean msilOnly, boolean jbcOnly, String fileName) {
        StmtListNode program = null;
        try {
//            System.out.println(Printer.printList(programFile.getBytes(), "\n"));
            InputStream is = new ByteArrayInputStream(programFile.getBytes());
            if (parser == null) {
                parser = new Parser(is);
            } else {
                Parser.ReInit(is);
            }
            program = Parser.start();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        if (program == null) {
            System.exit(1);
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println("ast:");
            System.out.println(printer.Printer.printTree(program.tree(), "\n"));
        }
        System.out.println("end of program for now");
        System.exit(0);

        if (!(msilOnly || jbcOnly)) {
            System.out.println("semantic-check:");
        }
        try {
//            checker = semantic_checker.SemanticChecker()
//            scope = semantic_checker.prepare_global_scope()
//            checker.semantic_check(prog, scope)
            if (!(msilOnly || jbcOnly)) {
//                System.out.println(printer.Printer.printList(program.Tree));
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            System.exit(2);
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println("msil:");
        }
        if (!jbcOnly) {
            try {
//                gen = msil.MsilCodeGenerator()
//                gen.gen_program(prog)
//                print(*gen.code, sep=os.linesep)
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println("jbc:");
        }
        if (!msilOnly) {
            try {
//                gen = jbc.JbcCodeGenerator(file_name)
//                gen.gen_program(prog)
//                print(*gen.code, sep=os.linesep)
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }
}
