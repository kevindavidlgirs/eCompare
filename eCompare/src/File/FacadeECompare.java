/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 * @author herve
 */
public class FacadeECompare {
    private final File file_structure_left;
    private final File file_structure_right;
   
    public FacadeECompare(String root,String path, String path1) throws IOException {
        file_structure_left = FileBuilder.make(Paths.get(root,path).toRealPath());
        file_structure_right = FileBuilder.make(Paths.get(root,path1).toRealPath());
    }
    public void compare(){
        //Je pense que ceci est à changer
        file_structure_left.compare(file_structure_left, file_structure_right);
        //Je pense que ceci est à changer
        System.out.println(file_structure_left);
        System.out.println(file_structure_right);
    }
    
    public File get_compared_left_struct_file(){
        return file_structure_left;
    }
    
    public File get_compared_right_struct_file(){
        return file_structure_right;
    }
}
