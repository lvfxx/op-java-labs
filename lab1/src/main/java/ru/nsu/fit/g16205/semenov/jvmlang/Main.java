package ru.nsu.fit.g16205.semenov.jvmlang;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.ClassCreator;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.AstNode;
import ru.nsu.fit.g16205.semenov.jvmlang.parser.JvmLangParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final String CLASS_FCN = "Main";
    private static final String HELP_OPT = "--help";
    private static final String USAGE = "Usage: <util-name> {<path-to-source-file> | " + HELP_OPT + " }";
    private static final String DOC_URL = "https://github.com/lvfxx/op-java-labs/tree/master/lab1";

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println(USAGE);
            System.exit(1);
        }
        if (args[0].equals(HELP_OPT)) {
            System.out.println(USAGE);
            System.out.println("Read documentation here: " + DOC_URL);
        }

        final String sourceCode;
        try {
            sourceCode = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Unable to read source file: " + e.getMessage());
            System.exit(1);
            return;
        }

        JvmLangParser parser = Parboiled.createParser(JvmLangParser.class);
        final ParsingResult<AstNode> result;
        try {
            result = new ReportingParseRunner<AstNode>(parser.Program()).run(sourceCode);
        } catch (Exception e) {
            System.out.println("Error while parsing: " + e.getMessage());
            System.exit(1);
            return;
        }
        AstNode root = result.valueStack.pop();

        final byte[] bytecode;
        try {
            ClassCreator cc = new ClassCreator(CLASS_FCN);
            cc.writeToMain(root);
            bytecode = cc.getBytecode();
        } catch (Exception e) {
            System.out.println("Error while generating bytecode: " + e.getMessage());
            System.exit(1);
            return;
        }

        Path srcParent = Paths.get(args[0]).getParent();
        Path outputFile;
        if (srcParent != null) {
            outputFile = Paths.get(srcParent.toString(), CLASS_FCN + ".class");
        } else {
            outputFile = Paths.get(CLASS_FCN + ".class");
        }

        try (FileOutputStream out = new FileOutputStream(outputFile.toString())) {
            out.write(bytecode);
        } catch (IOException e) {
            System.err.println("Unable to write .class file: " + e.getMessage());
            System.exit(1);
        }
    }

}
