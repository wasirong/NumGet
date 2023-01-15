package com.dhl.MainApp;

import com.dhl.Util.GetConfig;
import com.dhl.Util.LoadCurrentNumList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("出口滞留拦截工具 Version.1.0.0.2");
        primaryStage.setScene(new Scene(root, 795, 450));
        LoadCurrentNumList loadCurrentNumList = new LoadCurrentNumList();
        loadCurrentNumList.ReadExcelForNumList(new GetConfig().GetConfigByKey("BASEDATA", "filePath"));
        LazySingleton.getInstance().filePathWrongNum = new GetConfig().GetConfigByKey("SOUND", "filePathWrongNum");
        LazySingleton.getInstance().filePathblockNum = new GetConfig().GetConfigByKey("SOUND", "filePathblockNum");
        primaryStage.show();
    }


    public static void main(String[] args) {
        System.out.println("Main+++++++++++++++++++++++");
        launch(args);
    }
}
