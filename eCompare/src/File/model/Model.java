/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author herve
 */
public class Model {
    private final FacadeECompare fe;
    private List<String> status;
    
    public Model(String root_folder, String left_folder, String right_folder) throws IOException{
         fe = new FacadeECompare(root_folder, left_folder, right_folder);
         status = new ArrayList<>();
    }
    
    public File get_left_struct_folder(){
        return fe.get_file_structure_left();
    }
    
    public File get_right_struct_folder(){
        return fe.get_file_structure_right();
    }
    
    public void add_status_to_edit(String s){
        String well_formatted_stattus = status_converter(s);
        
        if(!status.contains(well_formatted_stattus)){
            this.status.add(well_formatted_stattus);
        }
    }
    
    public void remove_status_to_edit(String s){
        String well_formatted_stattus = status_converter(s);
        if(status.contains(well_formatted_stattus)){
            this.status.remove(well_formatted_stattus);
        }
    }
    
    public List<String> get_status(){
        return this.status;
    }
    
    
    // En gros, dès qu'on appuie sur un bouton, le label est récupéré et stocké dans une liste appartenant au model.
    // Cette méthode permet de convertir ce label dans un format qui va nous permettre de comparer la valeur des métadonnées d'un fichier.
    // Voir si tu la garde ou pas.
    public String status_converter(String s){
        String result="";
        
        if(s.equals("Newer Left") || s.equals("Newer right")){
            //result = "NEWER";
        }else if(s.equals("Orphans")){
            result = "ORPHAN";
        } else if(s.equals("Same")){
            result = "SAME";
        }else if(s.equals("Folders Only")){
            result = "D";
        }
        
        return result;
    }
    
    public void set_struct_selected_items(File file){
        // Cette méthode peut servir à éditer la valeur des attributs selected.
        // Reste à parcourir l'arborescence.
        // A toi de voir si tu veux la garder. Tu vas sûrement trouver mieux :).
    }
}
