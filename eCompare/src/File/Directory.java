/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public class Directory extends File {
    private final List<File> files = new ArrayList<>();
    
    Directory(String s){
        super(s);
    }   

    @Override
    public boolean isDirectory() {
       return true;
    }

    @Override
    public int getSize() {
        int res = 0;
        for (File f : files)
            res += f.getSize();
        return res;
    }

    @Override
    public void addFile(File f) {
       files.add(f);
    }

}
