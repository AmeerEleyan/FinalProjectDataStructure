package Controllers;

import FXML.Message;
import Project.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainInterfaceController implements Initializable {

    public final static QuadraticHash<Babys> BABYS_QUADRATIC_HASH = new QuadraticHash<>(64019);

    @FXML // fx:id="babyTable"
    private TableView<BabyForTraverse> babyTable; // Value injected by FXMLLoader

    @FXML // fx:id="cmName"
    private TableColumn<BabyForTraverse, String> cmName; // Value injected by FXMLLoader

    @FXML // fx:id="cmGender"
    private TableColumn<BabyForTraverse, String> cmGender; // Value injected by FXMLLoader

    @FXML // fx:id="cmTotalFrequency"
    private TableColumn<BabyForTraverse, Integer> cmTotalFrequency; // Value injected by FXMLLoader

    @FXML // fx:id="txtTotalRecorde"
    private TextField txtTotalRecorde; // Value injected by FXMLLoader

    @FXML // fx:id="txtTotalFrequency"
    private TextField txtTotalFrequency; // Value injected by FXMLLoader

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="rbMale"
    private RadioButton rbMale; // Value injected by FXMLLoader

    @FXML // fx:id="rbFemale"
    private RadioButton rbFemale; // Value injected by FXMLLoader

    @FXML // fx:id="txtYear"
    private TextField txtYear; // Value injected by FXMLLoader

    @FXML // fx:id="txtFrequency"
    private TextField txtFrequency; // Value injected by FXMLLoader

    @FXML // fx:id="lblTotalRecorde"
    private Label lblTotalRecorde; // Value injected by FXMLLoader

    @FXML // fx:id="lblTotalFrequency"
    private Label lblTotalFrequency; // Value injected by FXMLLoader

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cmTotalFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
    }

    public void actionsInBtBrowse() {
        this.uploadFiles();
    }

    public void actionsInBtMaxFrequency() {

    }

    public void actionsInBtSearch() throws Exception {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/SearchAndUpdate.fxml")));
        window.setTitle("Search for an Baby");
        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }

    public void actionsInBtAdd() {

    }

    public void actionsInBtDelete() {

    }

    public void uploadFiles() {

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(GUI.window.getScene().getWindow());

        if (fileList != null) {
            for (File file : fileList) {
                int year = getYearFromFileName(file.getName());
                if (year != 0)
                    readBabyRecordFromFile(file, year);
                else
                    Message.displayMessage("Warning", "The year is not defined in file " + file.getName());
            }
            updateTable();


        }
    }

    /**
     * Methode to read data from file iteratively
     */
    private void readBabyRecordFromFile(File fileName, int year) {
        try {
            Scanner input = new Scanner(fileName); // instance of scanner for read data from file
            if (fileName.length() == 0) {
                // no data in file
                Message.displayMessage("Warning", "  There are No records in the file " + fileName + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        BABYS_QUADRATIC_HASH.insert(new Babys(temp, year));
                        line++; // increment the line by one

                    } catch (Exception ex) {
                        System.out.println("Error " + line);
                        // the record in the file has a problem
                        Message.displayMessage("Warning", " Invalid format in line # " + line + " in file " + fileName + "  ");
                    }
                }
                input.close(); // prevent(close) scanner to read data
            }

        } catch (FileNotFoundException e) {
            // The specific file for reading data does not exist
            Message.displayMessage("Error", " The system can NOT find the file " + fileName + "  ");
        }
    }

    /**
     * to view data in table view
     */
    public void updateTable() {

        if (!BABYS_QUADRATIC_HASH.isEmpty()) {
            babyTable.getItems().clear(); // clear data from table
            // get first node in the queue
            HashNode<Babys>[] records = this.BABYS_QUADRATIC_HASH.getTable();

            // total frequency in the table
            int totalFrequency = 0;
            txtTotalFrequency.setVisible(true);
            lblTotalFrequency.setVisible(true);
            txtTotalRecorde.setVisible(true);
            lblTotalRecorde.setVisible(true);

            for (HashNode<Babys> record : records) {
                if (record != null) {
                    int total = this.getTotalFrequency(record.getFrequencyMaxHeap());
                    // add tempNode to the table
                    this.babyTable.getItems().add(new BabyForTraverse(record.getData().getName(), record.getData().getGender(), total)); // upload data to the table
                    // increment totalFrequency
                    totalFrequency += total;
                }
            }

            txtTotalFrequency.setText(totalFrequency + "");
            txtTotalRecorde.setText(this.BABYS_QUADRATIC_HASH.size() + "");
        } else {
            txtTotalRecorde.clear();
            txtTotalFrequency.clear();
            this.babyTable.getItems().clear(); // clear data from table
        }

    }

    public int getTotalFrequency(MaxHeap<Frequency> frequencyMaxHeap) {
        HeapNode<Frequency>[] records = frequencyMaxHeap.getHeap();
        int sum = 0;
        for (HeapNode<Frequency> record : records) {
            if (record != null)
                sum += record.getData().getFrequency();
        }
        return sum;
    }

    // get year from file name
    private int getYearFromFileName(String fileName) {
        int count = 0;
        String year = "";
        for (int i = 0; i < fileName.length(); ++i) {
            if (Character.isDigit(fileName.charAt(i))) {
                year += fileName.charAt(i);
                count++;
                if (count == 4) break;
            } else {
                count = 0;
            }
        }
        if (count == 0) return 0;
        return Integer.parseInt(year.trim());
    }

    /**
     * To check the value of the entered company name that if contain only char ot not
     */
    public static boolean isName(String companyN) {
        return companyN.matches("[a-zA-Z]+");
    }


}

