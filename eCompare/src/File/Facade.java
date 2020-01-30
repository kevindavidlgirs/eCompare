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
 * @author herve
 */
public class Facade {
    private final File file_structure_left;
    private final File file_structure_right;
    
    //L'idée ici est qu'en créant une instance de Façace on génère les arboréscences virtuelles.
    // Je ne sais pas si c'est la meilleure façon de faire. N'hésite pas à l'améliorer.
    public Facade() throws IOException {
        file_structure_left = FileBuilder.make(Paths.get("TestBC","RootBC_Left").toRealPath());
        file_structure_right = FileBuilder.make(Paths.get("TestBC","RootBC_Left").toRealPath());
    }
    
    //Pour la comparaison des arborescences
    public void compare_files_structures(File file_sys_left, File file_sys_right){
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public File get_file_structure_left(){
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public File get_file_structure_right(){
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
            
    //On affiche le tout        
    public void display_comparaison(){
        System.out.println(this.file_structure_left);
        System.out.println(this.file_structure_right);
    }
}
