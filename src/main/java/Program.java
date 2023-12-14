public class Program {
    public static void execute(String programFile, boolean msilOnly, boolean jbcOnly, String fileName) {
        try {
//            StatementListNode program = Parser.parse(programFile);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        if (!(msilOnly || jbcOnly)) {
            System.out.println("ast:");
//            System.out.println(printer.Printer.printList(program.Tree));
        }
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
