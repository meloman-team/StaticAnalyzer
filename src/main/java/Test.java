import module.ModuleThreadAndSharedResources;
import module.ModuleWaitAndNotify;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilya
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList();
        //TODO изменить на заполнение из параметров
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\Reader.java");
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\Writer.java");
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\syn.java");
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\ReaderWriter.java");

        ModuleWaitAndNotify n = new ModuleWaitAndNotify(files);
        ModuleThreadAndSharedResources mtar = new ModuleThreadAndSharedResources(files);
        try {
            n.main();
            mtar.main();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}