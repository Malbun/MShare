package ch.malbun.mshare;

import ch.malbun.mshare.receiver.receiver;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        final File[] file = {null};
        String currentIP;

        Thread receiver = new receiver();
        receiver.start();

        Group root = new Group();
        Scene scene = new Scene(root, 400, 350);

        //get local ip
        ArrayList<String> ip = null;
        try {
            ip = new ArrayList<String>();
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            for (; n.hasMoreElements(); ) {
                NetworkInterface e = n.nextElement();

                Enumeration<InetAddress> a = e.getInetAddresses();
                for (; a.hasMoreElements(); ) {
                    InetAddress addr = a.nextElement();
                    currentIP = addr.getHostAddress();
                    if (!((currentIP.contains(":") || currentIP.contains("127.0.0.1")))) {
                        ip.add(currentIP);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //UI
        scene.setFill(Color.web("#444444"));

        Text choosenFile = new Text();
        choosenFile.setFont(Font.font(18));
        choosenFile.setLayoutX(40);
        choosenFile.setLayoutY(110);
        choosenFile.setFill(Color.LIGHTGRAY);
        root.getChildren().add(choosenFile);

        //local ip
        VBox ipHBox = new VBox();
        for (String i : ip) {
            Text ti = new Text(i);
            ti.setFont(Font.font(16));
            ti.setFill(Color.LIGHTGRAY);
            ipHBox.getChildren().add(ti);
        }
        ipHBox.setLayoutX(200);
        ipHBox.setLayoutY(220);
        root.getChildren().add(ipHBox);

        Text localIpText = new Text("Local IPs:");
        localIpText.setFill(Color.LIGHTGRAY);
        localIpText.setFont(Font.font(18));
        localIpText.setLayoutX(200);
        localIpText.setLayoutY(210);
        root.getChildren().add(localIpText);

        //choose file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("öffnen");
        Button fileChooseButton = new Button("öffnen");
        fileChooseButton.setLayoutX(40);
        fileChooseButton.setLayoutY(40);
        fileChooseButton.setFont(Font.font(18));
        fileChooseButton.setOnAction(event -> {
            try {
                file[0] = fileChooser.showOpenDialog(stage);
                choosenFile.setText(file[0].getName());
            } catch (NullPointerException e) {
            }
        });
        root.getChildren().add(fileChooseButton);

        //ipChooser
        TextField ipchooser = new TextField();
        ipchooser.setLayoutX(70);
        ipchooser.setLayoutY(125);
        ipchooser.setFont(Font.font(18));
        root.getChildren().add(ipchooser);
        Text ipChooserText = new Text("IP:");
        ipChooserText.setFill(Color.LIGHTGRAY);
        ipChooserText.setLayoutX(40);
        ipChooserText.setLayoutY(150);
        ipChooserText.setFont(Font.font(18));
        root.getChildren().add(ipChooserText);

        //sendFile
        Button sendFile = new Button("Senden");
        sendFile.setFont(Font.font(18));
        sendFile.setLayoutX(40);
        sendFile.setLayoutY(200);
        sendFile.setOnAction(event -> {
            filesender.sendFile(ipchooser.getText(), file[0]);
        });
        root.getChildren().add(sendFile);

        //disable buttons
        AnimationTimer disableButtonTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if ((file[0] != null) && !(Objects.equals(ipchooser.getText(), ""))) {
                    sendFile.setDisable(false);
                } else {
                    sendFile.setDisable(true);
                }
            }
        };
        disableButtonTimer.start();

        stage.setTitle("MShare");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> {
            stage.close();
            System.exit(0);
            disableButtonTimer.stop();
            receiver.stop();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}