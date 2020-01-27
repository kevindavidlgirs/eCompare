/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 2207hembilo
 */
public class Directory extends File {

    private final List<File> files = new ArrayList<>();

    public Directory(String name, LocalDateTime date, long size, String path) {
        super(name, date, size, path);

    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    @Override
    public long getSize() {
        int res = 0;
        for (File f : files) {
            res += f.getSize();
        }
        return res;
    }

    @Override
    public void addFile(File f) {
        files.add(f);
    }

    @Override
    public List<File> getList() {
        return Collections.unmodifiableList(files);
    }

    //Est sensé donner le même statut à un segment déterminé de l'arborescence
    //A tester !!!
    private void set_status_for_all(File f, Status s) {
        if (f.isDirectory()) {
            for (File f1 : f.getList()) {
                set_status_for_all(f1, s);
            }
        }
        f.set_status(s);
    }

    //Est sensé scanner l'ensemble des enfants d'un dossier pour déterminer son statut
    //Devrait aussi vérifier que le correspondant à le même nombre de "fichier" 
    //dans un dossier donné ? Pour assigner l'état SAME notamment...
    public void scan_child_and_set_status(File f) {
        for (File f1 : f.getList()) {
            //........
        }
    }

    //L'idée est d'assigner ORPHAN en premier lieu à chaque dossier/fichier pour se concentrer 
    //par la suite sur la comparaison.
    @Override
    public void compare(File f) {
        if (this.isDirectory() && f.isDirectory() && this.getSize() > 0 && f.getSize() > 0) {
            for (File f1 : this.getList()) {
                if (f1.isOrphan(f.getList())) {
                    set_status_for_all(f1, Status.ORPHAN);
                }
            }
            for (File f2 : f.getList()) {
                if (f2.isOrphan(this.getList())) {
                    set_status_for_all(f, Status.ORPHAN);
                }
            }
            
            //Troisème boucle  prévue pour la récursion
            for (File f3 : this.getList()) {
                for (File f4 : f.getList()) {
                    if (f3.getName().compareTo(f4.getName()) == 0) {
                        if (f3.getSize() == f4.getSize()) {
                            f3.compare(f4);
                            //......
                            /*
                                je voulais utiliser ici la méthode "scan_child_and_set_status"
                                Pour que les dossiers parents puissent appeler cette 
                                fonction et déterminé quel est son statut en 
                                sortant de toutes ses récursions sur ses sous-dossiers et fichiers
                             */
                        }
                    }
                }
            }
        } else {
            //Je pensais faire la comparaison de fichier ici ou appeler la fonction "compare" de "SimpleFile"
            //et donc la redéfinir.
            //ATTENTION De nouvelles méthodes sont disponibles dans la classe "File" pour la comparaison.
        }
    }

}
