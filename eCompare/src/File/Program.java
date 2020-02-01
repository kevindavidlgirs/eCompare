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
        String s = "C:\\Users\\kevin\\Documents\\ANC3\\anc3_2g_hervekevin\\eCompare\\TestBC\\RootBC_Left";
        String s1 = "C:\\Users\\kevin\\Documents\\ANC3\\anc3_2g_hervekevin\\eCompare\\TestBC\\RootBC_Right";
        FacadeECompare Fe = new FacadeECompare(s, s1);
        Fe.compare();
    }

}
