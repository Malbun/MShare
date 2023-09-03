package ch.malbun.mshare;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class filesender {
    public static void sendFile(String ip, File file) {
        Socket FileNameClient;
        Socket FileDataClient;
        try {
            //send filename
            FileNameClient = new Socket(ip, 8118);
            DataOutputStream os = new DataOutputStream(FileNameClient.getOutputStream());
            os.writeUTF(file.getName());
            FileNameClient.close();

            TimeUnit.SECONDS.sleep(2);

            //send file
            FileDataClient = new Socket(ip, 8119);

            OutputStream out = FileDataClient.getOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int count;

            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
                out.flush();
            }
            FileDataClient.close();

        } catch (IOException | InterruptedException ignored) {}
    }
}
