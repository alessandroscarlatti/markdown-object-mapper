import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

public class MarkdownTest {

    @Test
    public void canExtractCodeBlock() throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/resources/scenario/a/setup.md"));

        Parser parser = Parser.builder().build();
        Node document = parser.parse(new String(bytes));

        CodeBlockVisitor codeBlockVisitor = new CodeBlockVisitor();

        document.accept(codeBlockVisitor);

        System.out.println("header:");
        System.out.println(codeBlockVisitor.header);
        System.out.println("body:");
        System.out.println(codeBlockVisitor.body);
    }

    private static class CodeBlockVisitor extends AbstractVisitor {

        private String header;
        private String body;

        @Override
        public void visit(FencedCodeBlock fencedCodeBlock) {
            System.out.println("visiting code " + fencedCodeBlock.getInfo());

            switch (fencedCodeBlock.getInfo()) {
                case "yaml":
                    header = fencedCodeBlock.getLiteral();
                case "xml":
                    body = fencedCodeBlock.getLiteral();
            }

            super.visit(fencedCodeBlock);
        }
    }
}