/**
 * @autor: Amir Eleyan
 * ID: 1191076
 * At: 5/5/2021 2:10 AM
 */
package Project;


import Controllers.GUI;

import FXML.Message;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Utilities {
/*
    public final QuadraticHash<Babys> BABYS_QUADRATIC_HASH = new QuadraticHash<>(64019);

    public void uploadFiles() {

        // File Chooser
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Files", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        List<File> fileList = fileChooser.showOpenMultipleDialog(GUI.window.getScene().getWindow());

        if (fileList != null) {
            for (File file : fileList) {
                int year = Utilities.getYearFromFileName(file.getName());
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
    /*private void readBabyRecordFromFile(File fileName, int year) {
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
   /* public static void updateTable() {

        if (!Utilities.BABYS_AVL_TREE.isEmpty()) {
            babysTableView.getItems().clear(); // clear data from table
            // get first node in the queue
            Node<BabyForTraverse> current = Utilities.BABYS_AVL_TREE.traverseLevelOrder().getHead();

            // total frequency in the table
            int totalFrequency = 0;
            txtTotalFrequencyForTable.setVisible(true);
            lblTotalFrequencyForTable.setVisible(true);
            txtTotalRecord.setVisible(true);
            lblTotalRecord.setVisible(true);
            while (current != null) {
                // add tempNode to the table
                babysTableView.getItems().add(current.getData()); // upload data to the table
                // increment totalFrequency
                totalFrequency += current.getData().getFrequency();

                current = current.getNext();
            }
            txtTotalFrequencyForTable.setText(totalFrequency + "");
            txtTotalRecord.setText(Utilities.BABYS_AVL_TREE.size() + "");
        } else {
            txtTotalRecord.clear();
            txtTotalFrequencyForTable.clear();
            babysTableView().getItems().clear(); // clear data from table
        }

    }

    // get year from file name
    public static int getYearFromFileName(String fileName) {
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
/*
    // Attribute
    // AVL tree of babys
    public final static AVL_Tree<Babys> BABYS_AVL_TREE = new AVL_Tree<>();
    public static LinkedList<Integer> years = new LinkedList<>();

    private Utilities() {
    }


    // Search for a specific node and return his linkedList
    public static LinkedList<Frequency> searchForBabys(Babys data) {
        TNode<Babys> searcherForBaby = BABYS_AVL_TREE.search(data);
        if (searcherForBaby != null) {
            return searcherForBaby.getFrequencyLinkedList();
        } else { // node not found
            return null;
        }
    }

    // get average frequency for a specific node
    public static float averageFrequency(Babys data) {
        TNode<Babys> searcherForBaby = BABYS_AVL_TREE.search(data);// log n
        if (searcherForBaby != null) {
            int length = searcherForBaby.getFrequencyLinkedList().length(); // n
            return totalFrequency(searcherForBaby.getFrequencyLinkedList().getHead()) / (float) length; // n
        }
        return -1; // not found
    }

    //  return info for tha baby hsa max frequency over all years
    public static Babys nameOfMaxFrequency(Frequency frequency) {
        TNode<Babys> rootOfTree = BABYS_AVL_TREE.getRoot();
        LinkedQueue<TNode<Babys>> tempQueue = new LinkedQueue<>();
        int max = 0;
        Babys maxBaby = new Babys();
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {

                // store first element in the queue and remove it
                TNode<Babys> tempNode = tempQueue.dequeue();

                int tempMax = totalFrequency(tempNode.getFrequencyLinkedList().getHead()); // get total frequency for current node

                if (tempMax > max) { // get the max
                    max = tempMax;
                    maxBaby = tempNode.getDate();
                    frequency.setFrequency(tempMax);
                }

                // insert first.left to tempQueue
                if (tempNode.getLeft() != null) {
                    tempQueue.enqueue(tempNode.getLeft());
                }

                // insert first.right to tempQueue
                if (tempNode.getRight() != null) {
                    tempQueue.enqueue(tempNode.getRight());
                }
            }
            return maxBaby;
        }
        return null; // no data in the tree
    }

    // return total frequency for a specific node
    public static int totalFrequency(Node<Frequency> head) {
        Node<Frequency> current = head;
        int sum = 0;
        while (current != null) {
            sum += current.getData().getFrequency();
            current = current.getNext();
        }
        return sum;
    }

    // get total number of babys in a selected year and travers it
    public static int traverseSelectedYear(int year, LinkedList<BabyForTraverse> babyForTraverseLinkedList) {

        TNode<Babys> rootOfTree = BABYS_AVL_TREE.getRoot();
        LinkedQueue<TNode<Babys>> tempQueue = new LinkedQueue<>();
        int totalBabysFrequency = 0;
        if (rootOfTree != null) {

            tempQueue.enqueue(rootOfTree);
            while (!tempQueue.isEmpty()) {

                // store first element in the queue and remove it
                TNode<Babys> tempNode = tempQueue.dequeue();

                // get frequency for current node in a selected year
                Frequency frequency = tempNode.getFrequencyLinkedList().search(new Frequency(year));

                if (frequency != null) {
                    totalBabysFrequency += frequency.getFrequency();
                    babyForTraverseLinkedList.insertAtLast(new BabyForTraverse(tempNode.getDate().getName(), tempNode.getDate().getGender(), frequency.getFrequency()));

                }

                // insert first.left to tempQueue
                if (tempNode.getLeft() != null) {
                    tempQueue.enqueue(tempNode.getLeft());
                }

                // insert first.right to tempQueue
                if (tempNode.getRight() != null) {
                    tempQueue.enqueue(tempNode.getRight());
                }
            }
            return totalBabysFrequency;
        }
        return -1;

    }


    // get year from file name
    public static int getYearFromFileName(String fileName) {
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

}
