/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author herve
 */
public class FileBuilder {

    public static File make(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LocalDateTime ldt = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        File result;
        if (Files.isDirectory(path)) {
            System.out.println(path.getParent().toString());
            result = new Directory(path.getFileName().toString(), ldt, attrs.size(), path.toAbsolutePath().toString());
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {
                for (Path p : dir) {
                    result.addFile(make(p));
                }
            }
        } else {
           return result = new SimpleFile(path.getFileName().toString(), ldt, attrs.size(), path.toAbsolutePath().toString());
        }
        return result;
    }

}
