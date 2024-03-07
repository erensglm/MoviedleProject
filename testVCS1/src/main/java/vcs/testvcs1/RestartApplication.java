package vcs.testvcs1;

import javafx.application.Application;
import javafx.stage.Stage;

public class RestartApplication extends Application {
    public void start(Stage primaryStage) throws Exception {
        // Yeniden başlatılacak uygulama başlatılıyor
        App mainClass = new App();
        mainClass.start(new Stage());

        // Mevcut aşama kapatılıyor
        primaryStage.close();
    }

}
