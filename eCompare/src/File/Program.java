/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author 2207hembilo
 */
public class Program {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("TestBC","RootBC_Left").toRealPath();
        Path path1 = Paths.get("TestBC","RootBC_Right").toRealPath();
        File f = FileBuilder.make(path);
        File f1 = FileBuilder.make(path1);
        
        f.compare(f1);
        System.out.println(f);
        System.out.println(f1);
        
        /**
         * Le code ci-dessus sera remplacé par celui du design pattern Façade.
         * J'ai commencé à l'implémenter dans la classe Façade. Vois ce que tu peux faire. 
         * N'hésite pas à y apporter tes idées.
         * Peux tu, avant d'implémenter Façade, executer le main et me dire si tout est OK au niveau de la comparaison des fichiers et dossiers ?
         * Amuses-toi bien Kévin, Ce n'est pas facile, mais c'est super kiffant la programmation !!!
         */
       
    }

}
