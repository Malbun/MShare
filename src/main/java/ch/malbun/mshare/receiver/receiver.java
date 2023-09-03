package ch.malbun.mshare.receiver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class receiver extends Thread{
    public void run() {
        ServerSocket FileNameServer;
        ServerSocket FileDataServer;

        String fileName;

        try {
            FileNameServer = new ServerSocket(8118);
            FileNameServer.setSoTimeout(360000000);

            FileDataServer = new ServerSocket(8119);
            FileDataServer.setSoTimeout(360000000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Socket FileNameClient;
        Socket FileDataClient;

        while (true) {
            try {
                FileNameClient = FileNameServer.accept();
                fileName = fileNameReseiver.run(FileNameClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                FileDataClient = FileDataServer.accept();
                fileDataReceiver.run(FileDataClient, fileName);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileName = null;
            FileNameClient = null;
            FileDataClient = null;
        }
    }
}

