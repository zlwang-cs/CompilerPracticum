import java.io.*;
import java.util.HashMap;

import visitor.*;
import syntaxtree.*;
import symbol.*;


public class Main {
    public static void main(String[] args){
        try {
            String filename = "./test_files/TreeVisitor.java";
            InputStream in = new FileInputStream(filename);
            if (in == null)
                System.out.print("it is null");
            Node root = new MiniJavaParser(in).Goal();
            MType allClassList = new MClassList();
            root.accept(new BuildSymbolTableVisitor(), allClassList);
            ((MClassList) allClassList).printSymbolList(0);
            root.accept(new TypeCheckVisitor(), allClassList);
            PrintStream ps=new PrintStream(new FileOutputStream("./test1.txt"));
            System.setOut(ps);
            root.accept(new ToPigletVisiter((MClassList) allClassList), null);
            //System.out.println("All Finish!");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TokenMgrError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

