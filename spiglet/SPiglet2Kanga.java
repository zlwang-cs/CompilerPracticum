package spiglet;
import spiglet.syntaxtree.Node;
import spiglet.visitor.BuildGraphVisitor;
import spiglet.visitor.ToKangaVisitor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class SPiglet2Kanga {
    public static void main(String[] args) {

        String[] fileNames = {"BinaryTree", "BubbleSort", "Factorial", "LinearSearch",
                            "LinkedList", "MoreThan4", "QuickSort", "TreeVisitor"};

        try {
            String fileName = "_MyTest";
            String filePath = "./temp/" + fileName + ".spg";
            String outputPath = "./temp/" + fileName + ".kg";
            if (args.length != 0) {
                filePath = args[0];
                outputPath = args[0] + ".kg";
            }
            InputStream in = new FileInputStream(filePath);
            SpigletParser spigletParser = new SpigletParser(in);
            Node root = spigletParser.Goal();
            BuildGraphVisitor buildGraphVisitor = new BuildGraphVisitor();
            root.accept(buildGraphVisitor);
            ToKangaVisitor toKangaVisitor = new ToKangaVisitor(buildGraphVisitor.label2flowGraph);

            PrintStream ps = new PrintStream(new FileOutputStream(outputPath));
            System.setOut(ps);

            root.accept(toKangaVisitor, null);
//            System.out.println("All Finish!");

//            for (String file : fileNames) {
//                fileName = file;
//                filePath = "./outputs/" + fileName + ".spg";
//                in = new FileInputStream(filePath);
//                spigletParser.ReInit(in);
//                root = spigletParser.Goal();
//                buildGraphVisitor = new BuildGraphVisitor();
//                root.accept(buildGraphVisitor);
//                toKangaVisitor = new ToKangaVisitor(buildGraphVisitor.label2flowGraph);
//
//                ps = new PrintStream(new FileOutputStream("./outputs/" + fileName + ".kg"));
//                System.setOut(ps);
//                root.accept(toKangaVisitor, null);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

