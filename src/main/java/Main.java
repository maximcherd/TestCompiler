import org.apache.commons.cli.*;
import org.apache.commons.cli.ParseException;
import printer.Printer;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();

        // параметры вызова функции
        options.addOption(getOption("s", "source", true, "source code file", true));
        options.addOption(getOption("msil", "msil-only", false, "print only msil code (no ast)", false));
        options.addOption(getOption("jbc", "jbc-only", false, "print only java byte code (no ast)", false));

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }

        String src = cmd.getOptionValue("source");
        boolean msilOnly = cmd.hasOption("msil-only");
        boolean jbcOnly = cmd.hasOption("jbc-only");
        String srcText = Printer.readFile(src);

        Program.execute(srcText, msilOnly, jbcOnly, src);
    }

    private static Option getOption(String option, String longOption, Boolean hasArg,
                                    String description, Boolean required) {
        Option o = new Option(option, longOption, hasArg, description);
        o.setRequired(required);
        return o;
    }
}
