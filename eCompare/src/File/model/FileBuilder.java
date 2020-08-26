/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File.model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

public class FileBuilder {

    public static File make(Path path) throws IOException{
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime ldt = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);//withNano pour enlever les millisecondes
        File result;
        if (Files.isDirectory(path)) {
            LocalDateTime dldt = lastModificationTime(path);
            result = new Directory(path.getFileName().toString(), dldt, attrs.size(), path.toAbsolutePath().toAbsolutePath());
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    result.addFile(make(p));
                }
            }
        } else {
            //Selection des fichier .txt pour l'édition, permet quand même de comparer d'autres types de fichiers.
            //Si j'ai bien compris seul les fichiers textes sont éditables..
            if(path.getFileName().toString().endsWith(".txt")){
                String contents =  new String(Files.readAllBytes(path));
                return result = new SimpleFile(path.getFileName().toString(), ldt, contents.length(), path.toAbsolutePath().toAbsolutePath(), contents);
            }else{
                return result = new SimpleFile(path.getFileName().toString(), ldt, attrs.size(), path.toAbsolutePath().toAbsolutePath());
            }
        }
        return result;
    }
    
    static LocalDateTime lastModificationTime(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime result = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withNano(0);
        if (Files.isDirectory(path)) {
            // Par defaut le lastModifiedTime est pour les repertoires non vide la date Unix. Ceci afin de faire fonctionner isAfter
            //dans le cas ou le directory serait plus récent que les éléments qu'il contient.
            if(!isDirEmpty(path)){
                result = LocalDateTime.of(1900, Month.JANUARY, 1, 0, 0, 0);
            }
            
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    LocalDateTime tmp = lastModificationTime(p);
                    
                    if (tmp.isAfter(result)) {
                        result = tmp;
                    }
                }
            }
        }
        return result;
    }
    
    //Permet de checker si un directory est vide.
    private static boolean isDirEmpty(final Path directory) throws IOException {
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
            return !dirStream.iterator().hasNext();
        }
    }
}
