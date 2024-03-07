module vcs.testvcs1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens vcs.testvcs1 to javafx.fxml;
    exports vcs.testvcs1;
}