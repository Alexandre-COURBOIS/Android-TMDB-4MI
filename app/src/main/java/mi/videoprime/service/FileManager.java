package mi.videoprime.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.inject.Inject;

import mi.videoprime.service.interfaces.IFileManager;

public class FileManager implements IFileManager {

    @Inject
    public UtilsService _utils;

    @Inject
    public FileManager() {}


    @Override
    public void writeFile(File file, byte[] content) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        } catch (Exception e) {

        }
    }
    @Override
    public byte[] readFile(File file) throws Exception {
        byte[] fileContent;

        try (FileInputStream fis = new FileInputStream(file)) {
            fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
        } catch (Exception e) {
            throw new Exception();
        }

        return fileContent;
    }

    public boolean fileExist(String fileName) {
        File file = new File(_utils.getContextApplication().getFilesDir(), fileName);
        return file.exists();
    }
}