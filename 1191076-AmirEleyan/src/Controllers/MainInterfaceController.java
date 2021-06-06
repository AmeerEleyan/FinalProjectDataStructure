/**
 * @autor: Amir Eleyan
 * 1191076
 * At: 29/5/2021  6:17 AM
 */
package Controllers;

import FXML.Message;
import Project.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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

    public static QuadraticHash<Babys> BABYS_QUADRATIC_HASH;

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

    @FXML
    private VBox vBox;
    @FXML
    private GridPane gridPaneButton;
    @FXML
    private GridPane gridPaneText;

    @FXML
    private TextField txtSizeOfData;

    @FXML
    private Button brStart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cmTotalFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
    }

    // browse files and upload them
    public void actionsInBtBrowse() {
        this.uploadFiles();
    }

    // display max frequency in all records in any year
    public void actionsInBtMaxFrequency() {

        if (!BABYS_QUADRATIC_HASH.isEmpty()) { // check if table is empty

            HashNode<Babys>[] records = BABYS_QUADRATIC_HASH.getTable();

            int maxFreq = 0; // max frequency overall year
            Frequency maxFrequency = new Frequency();
            Babys maxBabys = new Babys();

            for (HashNode<Babys> hashNode : records) { // get max from all recorde

                if (hashNode != null) {

                    if (hashNode.getFrequencyMaxHeap().isEmpty()) continue;
                    Frequency frequency = hashNode.getFrequencyMaxHeap().getSorted()[0].getData(); // get first element which represent the max element

                    if (frequency.getFrequency() > maxFreq) { // compare old max with current max
                        maxFreq = frequency.getFrequency();
                        maxFrequency = frequency;
                        maxBabys = hashNode.getData();
                    }
                }
            }

            // display recorde info

            this.txtName.setText(maxBabys.getName());

            if (maxBabys.isFemale()) this.rbFemale.setSelected(true);
            else this.rbMale.setSelected(true);

            this.txtFrequency.setText(maxFrequency.getFrequency() + "");
            this.txtYear.setText(maxFrequency.getYear() + "");
        } else {
            Message.displayMessage("Warning", " There are no data ");
        }

    }

    // display search stage
    public void actionsInBtSearch() throws Exception {
        if (!BABYS_QUADRATIC_HASH.isEmpty()) {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../FXML/SearchAndUpdate.fxml")));
            window.setTitle("Search for an Baby");
            window.setScene(new Scene(root));
            window.setOnCloseRequest(e -> { // update table when close the interface to get last changing
                this.updateTable();
            });
            window.setResizable(false);
            window.show();
        } else {
            Message.displayMessage("Warning", " There are no data ");
        }

    }

    // add new recorde
    public void actionsInBtAdd() {

        if (!this.txtName.getText().isEmpty()) { // name not empty

            if (isName(this.txtName.getText())) { // valid name

                if (rbFemale.isSelected() || rbMale.isSelected()) { // gender not null

                    char gander;
                    if (rbMale.isSelected()) gander = 'M';
                    else gander = 'F'; // get gender

                    if (!this.txtFrequency.getText().isEmpty()) { // frequency not empty

                        if (isNumber(this.txtFrequency.getText())) { // valid frequency

                            int frequency = Integer.parseInt(this.txtFrequency.getText().trim()); // get frequency

                            if (!this.txtYear.getText().isEmpty()) { // year not empty

                                if (isNumber(this.txtYear.getText().trim())) { // valid year

                                    int year = Integer.parseInt(this.txtYear.getText().trim()); // get year

                                    HashNode<Babys> babys = BABYS_QUADRATIC_HASH.search(new Babys("" + this.txtName.getText() + "," + gander + "," + frequency, year));

                                    if (babys == null) {// name not exist
                                        BABYS_QUADRATIC_HASH.insert(new Babys("" + this.txtName.getText() + "," + gander + "," + frequency, year));

                                    } else { // name exist
                                        int search = babys.getFrequencyMaxHeap().getIndex(new Frequency(year, frequency));

                                        if (search == -1) {
                                            // name is exist but year does not exist
                                            BABYS_QUADRATIC_HASH.insert(new Babys("" + babys.getData().getName() + "," + babys.getData().getGender() + "," + frequency, year));

                                        } else {
                                            // name is exist and year is exist
                                            int newFrequency = babys.getFrequencyMaxHeap().getHeap()[search].getData().getFrequency();
                                            babys.getFrequencyMaxHeap().getHeap()[search].getData().setFrequency(newFrequency + frequency);
                                        }
                                    }
                                    this.updateTable();
                                    actionsInBtClear();
                                    Message.displayMessage("Successfully", " A new recorde has been added successfully ");
                                } else {
                                    Message.displayMessage("Warning", " The year is invalid");
                                    this.txtYear.clear();
                                }

                            } else {
                                Message.displayMessage("Warning", " Please enter the year");
                            }

                        } else {
                            Message.displayMessage("Warning", " The frequency is invalid");
                            this.txtFrequency.clear();
                        }

                    } else {
                        Message.displayMessage("Warning", " Please enter the frequency ");
                    }

                } else {
                    Message.displayMessage("Warning", " Please select the gander ");
                }

            } else {
                Message.displayMessage("Warning", " The name is invalid ");
                this.txtName.clear();
            }
        } else {
            Message.displayMessage("Warning", " Please enter the name ");
        }

    }

    // delete selected recorde
    public void actionsInBtDelete() {
        if (!BABYS_QUADRATIC_HASH.isEmpty()) {
            if (!this.txtName.getText().isEmpty()) { // name not empty
                if (isName(this.txtName.getText())) { // valid name
                    if (rbFemale.isSelected() || rbMale.isSelected()) { // gender not null

                        char gander;
                        if (rbMale.isSelected()) gander = 'M';
                        else gander = 'F'; // get gender

                        // delete this record if exist
                        HashNode<Babys> babys = BABYS_QUADRATIC_HASH.delete(new Babys(this.txtName.getText(), gander));

                        if (babys == null) { // record not exist
                            Message.displayMessage("Warning", this.txtName.getText() + " Does not exist ");
                        } else {// record exist
                            Message.displayMessage("Successfully", " Record deleted successfully ");
                            babys.getFrequencyMaxHeap().clear();
                            this.updateTable();
                            this.actionsInBtClear();
                        }
                    } else {
                        Message.displayMessage("Warning", " Please select the gander ");
                    }
                } else {
                    Message.displayMessage("Warning", " The name is invalid ");
                    this.txtName.clear();
                }
            } else {
                Message.displayMessage("Warning", " Please enter the name ");
            }
        } else {
            Message.displayMessage("Warning", " There are no data ");
        }
    }

    // clear data from textFiled
    public void actionsInBtClear() {
        this.txtName.clear();
        this.txtYear.clear();
        this.txtFrequency.clear();
        this.rbMale.setSelected(false);
        this.rbFemale.setSelected(false);
    }

    // upload files using a browser
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
                Message.displayMessage("Warning", "  There are no records in the file " + fileName + " ");
            } else {
                int line = 1; // represent line on the file to display in which line has problem If that happens

                while (input.hasNext()) { // read line of data
                    try {
                        String temp = input.nextLine();
                        BABYS_QUADRATIC_HASH.insert(new Babys(temp, year));
                        line++; // increment the line by one

                    } catch (Exception ex) {
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

    public void handleBtStart() {
        if (!txtSizeOfData.getText().trim().isEmpty()) {
            if (isNumber(txtSizeOfData.getText().trim())) {
                int size = Integer.parseInt(txtSizeOfData.getText().trim());
                BABYS_QUADRATIC_HASH = new QuadraticHash<>(getPrime(size * 2));
                this.txtSizeOfData.setVisible(false);
                this.brStart.setVisible(false);
                this.gridPaneButton.setDisable(false);
                this.gridPaneText.setDisable(false);
                this.vBox.setDisable(false);
                this.babyTable.setDisable(false);
            } else {
                Message.displayMessage("Warning", "The size is invalid");
            }
        } else {
            Message.displayMessage("Warning", "Please enter the size of the data");
        }
    }

    //to view data in table view
    public void updateTable() {

        if (!BABYS_QUADRATIC_HASH.isEmpty()) {
            babyTable.getItems().clear(); // clear data from table
            // get first node in the queue
            HashNode<Babys>[] records = BABYS_QUADRATIC_HASH.getTable();

            // total frequency in the table
            int totalFrequency = 0;
            txtTotalFrequency.setVisible(true);
            lblTotalFrequency.setVisible(true);
            txtTotalRecorde.setVisible(true);
            lblTotalRecorde.setVisible(true);

            for (HashNode<Babys> record : records) {
                if (record != null && record.isExist()) {
                    int total = this.getTotalFrequency(record.getFrequencyMaxHeap());
                    // add tempNode to the table
                    this.babyTable.getItems().add(new BabyForTraverse(record.getData().getName(), record.getData().getGender(), total)); // upload data to the table
                    // increment totalFrequency
                    totalFrequency += total;
                }
            }

            txtTotalFrequency.setText(totalFrequency + "");
            txtTotalRecorde.setText(BABYS_QUADRATIC_HASH.size() + "");
        } else {
            txtTotalRecorde.clear();
            txtTotalFrequency.clear();
            this.babyTable.getItems().clear(); // clear data from table
        }

    }

    // get total frequency from specific name
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

    // To check the value of the entered numberOfShares if contain only digits or not
    private boolean isNumber(String number) {
        /* To check the entered number of shares, that it consists of
           only digits
         */
        try {
            int temp = Integer.parseInt(number);
            return number.matches("\\d+") && temp > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // get first prime number after specific number
    private static int getPrime(int size) {
        int temp = size;
        while (true) {
            boolean result = true;
            for (int i = 2; i <= temp / 2; ++i) {
                if (temp % i == 0) {
                    result = false;
                    break;
                }
            }
            if (result) return temp;
            else temp++;
        }
    }


}

