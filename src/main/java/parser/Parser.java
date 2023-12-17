package parser;

import ast.StmtListNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Parser {
    private static JavaCCParserFromC parser = null;

    public static StmtListNode parse(String programFile) throws ParseException {
        InputStream is = new ByteArrayInputStream(programFile.getBytes());
        if (parser == null) {
            parser = new JavaCCParserFromC(is);
        } else {
            JavaCCParserFromC.ReInit(is);
        }
        return JavaCCParserFromC.start();
    }
}
