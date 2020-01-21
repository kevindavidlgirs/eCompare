/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

/**
 *
 * @author 2207hembilo
 */
public abstract class File {
    private String name;
    private String modificationDate;
    enum status {ORPHAN,SAME,NEWER,OLDER,PARTIAL_SAME}
 
    public File(String name){
        this.name = name;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the modificationDate
     */
    public String getModificationDate() {
        return modificationDate;
    }

    public abstract boolean isDirectory();
    
    public abstract int getSize();
}
