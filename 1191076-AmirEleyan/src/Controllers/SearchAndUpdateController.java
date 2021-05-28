/**
 * Sample Skeleton for 'SearchAndUpdate.fxml' Controller Class
 */

package Controllers;

import FXML.Message;
import Project.Babys;
import Project.Frequency;
import Project.HashNode;
import Project.HeapNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchAndUpdateController implements Initializable {

    @FXML // fx:id="txtName"
    private TextField txtName; // Value injected by FXMLLoader

    @FXML // fx:id="rbMale"
    private RadioButton rbMale; // Value injected by FXMLLoader

    @FXML // fx:id="rbFemale"
    private RadioButton rbFemale; // Value injected by FXMLLoader

    @FXML // fx:id="txtFrequency"
    private TextField txtFrequency; // Value injected by FXMLLoader

    @FXML // fx:id="txtYear"
    private TextField txtYear; // Value injected by FXMLLoader

    @FXML // fx:id="frequencyTable"
    private TableView<Frequency> frequencyTable; // Value injected by FXMLLoader

    @FXML // fx:id="cmName"
    private TableColumn<Frequency, Integer> cmYear; // Value injected by FXMLLoader

    @FXML // fx:id="cmFrequency"
    private TableColumn<Frequency, Integer> cmFrequency; // Value injected by FXMLLoader

    private HashNode<Babys> babys;
    private int year;
    private int frequency;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        cmFrequency.setCellValueFactory(new PropertyValueFactory<>("frequency"));
    }


    @FXML
    void actionsInBtClear() {
        this.txtFrequency.clear();
        this.txtYear.clear();
    }

    @FXML
    void actionsInBtUpdate() {

        if (!this.txtYear.getText().isEmpty()) {

            if (this.isNumber(this.txtYear.getText().trim())) {

                if (!this.txtFrequency.getText().isEmpty()) {

                    if (this.isNumber(this.txtFrequency.getText().trim())) {

                        int newYear = Integer.parseInt(this.txtYear.getText().trim());
                        int newFrequency = Integer.parseInt(this.txtFrequency.getText().trim());

                        if (this.year == newYear && this.frequency == newFrequency)
                            return; // just update frequency in this year

                        // get index of the old year(same year and just update frequency)
                        int selectedObj = this.babys.getFrequencyMaxHeap().getIndex(new Frequency(this.year));

                        if (this.year == newYear) {
                            this.babys.getFrequencyMaxHeap().getHeap()[selectedObj].getData().setFrequency(newFrequency);

                        } else {

                            int search = this.babys.getFrequencyMaxHeap().getIndex(new Frequency(newYear));

                            if (search == -1) {
                                this.babys.getFrequencyMaxHeap().getHeap()[selectedObj].getData().setFrequency(newFrequency);
                                this.babys.getFrequencyMaxHeap().getHeap()[selectedObj].getData().setYear(newYear);

                            } else {

                                int newFreq = this.babys.getFrequencyMaxHeap().getHeap()[search].getData().getFrequency();
                                this.babys.getFrequencyMaxHeap().getHeap()[search].getData().setFrequency(newFreq + newFrequency);
                                this.babys.getFrequencyMaxHeap().shiftArray(selectedObj);
                            }
                        }
                        this.uploadDataToTable();

                    } else {
                        Message.displayMessage("Warning", " This frequency is invalid");
                    }
                } else {
                    Message.displayMessage("Warning", " Please enter the frequency");
                }

            } else {
                Message.displayMessage("Warning", " This year is invalid");
            }

        } else {
            Message.displayMessage("Warning", " Please enter the year");

        }
    }


    public void getSelected() {
        int index = this.frequencyTable.getSelectionModel().getSelectedIndex();
        if (index <= -1) return;
        this.txtYear.setText(cmYear.getCellData(index) + "");
        this.year = cmYear.getCellData(index);
        this.txtFrequency.setText(cmFrequency.getCellData(index) + "");
        this.frequency = cmFrequency.getCellData(index);
    }

    public void actionsInBtSearch() {
        if (!this.txtName.getText().isEmpty()) {
            if (this.rbFemale.isSelected() || this.rbMale.isSelected()) {
                if (isName(this.txtName.getText().trim())) {
                    char gender;
                    if (rbMale.isSelected()) gender = 'M';
                    else gender = 'F';

                    babys = MainInterfaceController.BABYS_QUADRATIC_HASH.search(new Babys(this.txtName.getText().trim(), gender));

                    if (babys != null) {
                        this.uploadDataToTable();
                    } else {
                        Message.displayMessage("Warning", this.txtName.getText() + " does not exist ");
                        this.txtName.clear();
                        this.frequencyTable.getItems().clear();
                        this.rbMale.setSelected(false);
                        this.rbFemale.setSelected(false);
                    }

                } else {
                    Message.displayMessage("Warning", " Invalid baby name, try again ");
                    this.txtName.clear();
                }

            } else {
                Message.displayMessage("Warning", " Please select the gender ");
            }
        } else {
            Message.displayMessage("Warning", " Please enter the name ");
        }
    }

    private void uploadDataToTable() {
        this.frequencyTable.getItems().clear();
        HeapNode<Frequency>[] frequencyHeapNode = babys.getFrequencyMaxHeap().getSorted();
        for (HeapNode<Frequency> heapNode : frequencyHeapNode) {
            this.frequencyTable.getItems().add(heapNode.getData());
        }
    }

    //To check the value of the entered company name that if contain only char ot not
    private boolean isName(String text) {
        return text.matches("[a-zA-Z]+");
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


}
