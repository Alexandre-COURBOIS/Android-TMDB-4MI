package mi.videoprime.service.interfaces;

import java.io.File;

public interface IFileManager {
    void writeFile(File file, byte[] content);

    byte[] readFile(File file) throws Exception;

    boolean fileExist(String fileName);

}
