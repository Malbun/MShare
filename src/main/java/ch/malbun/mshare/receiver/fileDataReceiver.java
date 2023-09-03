package ch.malbun.mshare.receiver;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class fileDataReceiver {
    public static void run(Socket client, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream out = new BufferedOutputStream(fos);
        byte[] buffer = new byte[1024];
        int count;
        InputStream in = client.getInputStream();
        while ((count = in.read(buffer)) > 0) {
            fos.write(buffer, 0, count);
        }
        fos.close();
        client.close();

    }
}
