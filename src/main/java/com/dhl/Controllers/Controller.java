package com.dhl.Controllers;

import com.dhl.Data.InputCSVData;
import com.dhl.MainApp.LazySingleton;
import com.dhl.PDFConvertToCSV.TurnitInPDFReportProcessor;
import com.dhl.Threads.MyThread;
import com.dhl.Util.MakeSound;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.io.File;

public class Controller {
    @FXML
    public Label OKStatus;
    @FXML
    public Label NGStatus;
    @FXML
    public Label NGCount;
    @FXML
    private Button SelectInputDicID;
    @FXML
    private Button SelectHWBDicID;
    @FXML
    private Button SelectOutputDicID;
    @FXML
    private Button GenerateCsvID;
    @FXML
    private TextField inputDirPathID;
    @FXML
    private TextField batchHWBQueryID;
    @FXML
    private TextField outputDirPathID;
    @FXML
    private Label ActionTarget;

    public static String inputDirPath;
    public static String hwbDirPath;
    public static String outputDirPath;

//    Logger LOG = LoggerFactory.getLogger(Controller.class);

    @FXML
    public void SelectInputDicAction() {
        DirectoryChooser inputDirChooser = new DirectoryChooser();
        inputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDirectory = inputDirChooser.showDialog((Stage) SelectInputDicID.getScene().getWindow());
        if (selectedDirectory != null) {
            inputDirPath = selectedDirectory.getAbsolutePath();
            if (!inputDirPath.equals("")) {
                inputDirPathID.setText(inputDirPath);
//                LOG.info("选择PDF路径:" + inputDirPath);
            }
        }
    }

    @FXML
    public void SelectHWBDicAction() {
        inputDirPathID.setDisable(false);
//        inputDirPathID.textProperty().addListener(this::textChange);
        MakeSound makeSound = new MakeSound();
        inputDirPathID.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    if (inputDirPathID.getText().length() > 10) {
                        NGStatus.setText("单号不正, 请确认 : " + inputDirPathID.getText());
                        NGStatus.setVisible(true);
                        OKStatus.setVisible(false);
                        MyThread myThread = new MyThread(LazySingleton.getInstance().filePathWrongNum);
                        myThread.start();
                        inputDirPathID.clear();
                    } else if (LazySingleton.getInstance().getM_NumberList().contains(inputDirPathID.getText())) {
                        LazySingleton.getInstance().AddNgcount();
//                        java.awt.Toolkit.getDefaultToolkit().beep();
                        NGStatus.setText("拦截 : " + inputDirPathID.getText());
                        NGCount.setText("拦截总数 : " + LazySingleton.getInstance().getNgcount());
                        NGStatus.setVisible(true);
                        OKStatus.setVisible(false);
                        MyThread myThread = new MyThread(LazySingleton.getInstance().filePathblockNum);
                        myThread.start();
                        inputDirPathID.clear();
                    } else {
                        LazySingleton.getInstance().AddOkcount();
                        OKStatus.setText("放行 : " + inputDirPathID.getText());
                        NGStatus.setVisible(false);
                        OKStatus.setVisible(true);
                        inputDirPathID.clear();
                    }
                }
            }
        });
    }

    private void textChange(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        try {
            if (inputDirPathID.getText().toString().contains("/r/n")) {
                if (LazySingleton.getInstance().getM_NumberList().contains(inputDirPathID.getText())) {
                    LazySingleton.getInstance().AddNgcount();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    NGStatus.setText("拦截 : " +  inputDirPathID.getText());
                    NGStatus.setVisible(true);
                    OKStatus.setVisible(false);
                    inputDirPathID.clear();
                } else {
                    LazySingleton.getInstance().AddOkcount();
                    OKStatus.setText("放行 : " + inputDirPathID.getText());
                    NGStatus.setVisible(false);
                    OKStatus.setVisible(true);
                    inputDirPathID.clear();
                }

            }
        }catch (Exception e){
            System.out.println("");
        }
    }

    @FXML
    public void SelectOutputDicAction() {
        DirectoryChooser outputDirChooser = new DirectoryChooser();
        outputDirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedDirectory = outputDirChooser.showDialog((Stage) SelectOutputDicID.getScene().getWindow());
        if (selectedDirectory != null) {
            outputDirPath = selectedDirectory.getAbsolutePath();
            if (!outputDirPath.equals("")) {
                outputDirPathID.setText(outputDirPath);
//                LOG.info("选择CSV路径:" + inputDirPath);
            }
        }
    }

    @FXML
    public void ConvertPDFTOCSV() {
        if (!inputDirPath.equals("") && !hwbDirPath.equals("") && !outputDirPath.equals("")) {
//            LOG.info("开始转换");
            ActionTarget.setText("Processing...");
            TurnitInPDFReportProcessor pdfProcessor = new TurnitInPDFReportProcessor(inputDirPath, hwbDirPath, outputDirPath);
            pdfProcessor.startProcess();
            ActionTarget.setText("Done!");
        } else {
            ActionTarget.setText("Please specify input/hwbDirPath/output directories.");
        }
    }
}
