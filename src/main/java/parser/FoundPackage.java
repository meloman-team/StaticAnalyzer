package parser;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 *
 * @author melom
 */
public class FoundPackage extends VoidVisitorAdapter {

    private String packageName;

    @Override
    public void visit(PackageDeclaration n, Object arg) {
        packageName = n.getName().toString();
    }

    public String getPackageName() {
        return packageName;
    }
    
}