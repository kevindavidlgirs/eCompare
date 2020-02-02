/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;


/**
 *
 * @author 2207hembilo
 */
public class Program {

    public static void main(String[] args) throws IOException {
        String r = "TestBC";
        String s = "RootBC_Left";
        String s1 = "RootBC_Right";
        
        FacadeECompare Fe = new FacadeECompare(r,s, s1);
        Fe.compare();
    }

}
