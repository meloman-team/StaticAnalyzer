package parser;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;

/**
 *
 * @author Ilya-pc
 */
public class FoundImport extends VoidVisitorAdapter {
    
    private ArrayList<String> ImportNames;
    
    public FoundImport(){
        ImportNames = new ArrayList<>();
    }
    
    @Override
    public void visit(ImportDeclaration n, Object arg) {
        ImportNames.add(n.getName().getName());
    }

    public ArrayList<String> getImportNames() {
        return ImportNames;
    }
}
