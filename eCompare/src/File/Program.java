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
        Path path = Paths.get("TestBC\\TestBC\\RootBC_Left").toRealPath();
        Path path1 = Paths.get("TestBC\\TestBC\\RootBC_Right").toRealPath();
        File f = FileBuilder.make(path);
        File f1 = FileBuilder.make(path1);
        f.compare(f1);
    }

}
