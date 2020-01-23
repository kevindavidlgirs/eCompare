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
public class SimpleFile extends File{
    private final int size;
    
    public SimpleFile(String name, int size) {
        super(name);
        this.size = size;
    }

    @Override
    public boolean isDirectory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSize() {
        return this.size;
    }
    
    @Override
    protected String displayFormat(int offset) {
        return super.displayFormat(offset) + getName() + " - size : " + getSize() + "\n";
    }
    
    @Override
    public void addFile(File f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
