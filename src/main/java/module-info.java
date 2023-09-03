module ch.malbun.mshare {
    requires javafx.controls;
    requires javafx.fxml;


    opens ch.malbun.mshare to javafx.fxml;
    exports ch.malbun.mshare;
    exports ch.malbun.mshare.receiver;
    opens ch.malbun.mshare.receiver to javafx.fxml;
}