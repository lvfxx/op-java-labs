package ru.nsu.fit.g16205.semenov.jvmlang;

import org.parboiled.Parboiled;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import ru.nsu.fit.g16205.semenov.jvmlang.asm.ClassCreator;
import ru.nsu.fit.g16205.semenov.jvmlang.ast.AstNode;
import ru.nsu.fit.g16205.semenov.jvmlang.parser.JvmLangParser;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.parboiled.support.ParseTreeUtils.printNodeTree;

public class Main {
    public static void main(String[] args) {
        JvmLangParser parser = Parboiled.createParser(JvmLangParser.class);
        String input = "str = \"asndfg\"\n" +
                "bl = 3 == 4\n" +
                "digit = 23\n" +
                "print(str)\n" +
                "print(bl)\n" +
                "print(digit)\n";

        ParsingResult<AstNode> result = new ReportingParseRunner<AstNode>(parser.Program()).run(input);

        AstNode root = result.valueStack.pop();
        ClassCreator cc = new ClassCreator("Main");
        cc.writeToMain(root);
        byte[] bytecode = cc.getBytecode();

        System.out.println(root.toString());

        try (FileOutputStream out = new FileOutputStream("C:\\Users\\lvfx\\IdeaProjects\\untitled\\out\\production\\untitled\\Main.class")) {
            out.write(bytecode);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (!result.parseErrors.isEmpty())
            System.out.println(ErrorUtils.printParseError(result.parseErrors.get(0)));
        else
            System.out.println(printNodeTree(result));
    }
}
