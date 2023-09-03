package ch.malbun.mshare.receiver;

import ch.malbun.mshare.MShareException;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class fileNameReseiver {
    public static String  run(Socket client) throws IOException {
        String outputPath = null;

        DataInputStream is = new DataInputStream(client.getInputStream());
        outputPath = System.getenv("USERPROFILE") + "/downloads/" + is.readUTF();
        File file = new File(outputPath);

        if (file.createNewFile()) {

        } else {
            outputPath = System.getenv("USERPROFILE") + "/downloads/" + is.readUTF() + ".mshare";
            file = new File(outputPath);
            try {
                file.createNewFile();
            } catch (MShareException e) {
                throw new MShareException("File already exists!", e);
            }
        }
        return outputPath;
    }
}
