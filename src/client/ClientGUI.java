//
// GUI for the client side of the dictionary application.
// Written by Michael Yixiao Wu | StuID: 1388097
//

package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ClientGUI extends JFrame {
    private JLabel wordLabel;
    private JTextField wordField;
    private JLabel meaningsLabel;
    private JTextArea meaningsArea;
    private JButton queryButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton updateButton;
    private DictionaryClient client;
    private JLabel systemLabel;
    private JTextArea systemArea;
    private JPanel MainPanel;



    public ClientGUI(DictionaryClient client) {
        this.client = client;
        setContentPane(MainPanel);
        setTitle("Dictionary Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);
        setVisible(true);

        meaningsArea.setLineWrap(true);
        meaningsArea.setWrapStyleWord(true);

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = wordField.getText();
                if (input.contains(" ")) {
                    systemArea.setText("Error: Ensure there are no spaces and try again.");
                    return;
                }
                try {
                    client.sendRequest("QUERY " + wordField.getText());
                    String response = client.receiveResponse();
                    if (response.contains("Error")) {
                        meaningsArea.setText(" ");
                        systemArea.setText(response);
                    } else {
                        meaningsArea.setText(response);
                        systemArea.setText(" ");

                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = wordField.getText();
                if (input.contains(" ")) {
                    systemArea.setText("Error: Ensure there are no spaces and try again.");
                    return;
                }
                try {
                    client.sendRequest("ADD " + wordField.getText() + " " + meaningsArea.getText());
                    String response = client.receiveResponse();
                    systemArea.setText(response);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = wordField.getText();
                if (input.contains(" ")) {
                    systemArea.setText("Error: Ensure there are no spaces and try again.");
                    return;
                }
                try {
                    client.sendRequest("REMOVE " + wordField.getText());
                    String response = client.receiveResponse();
                    systemArea.setText(response);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendRequest("UPDATE " + wordField.getText() + " " + meaningsArea.getText());
                    String response = client.receiveResponse();
                    systemArea.setText(response);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }

//    public static void main(String[] args) {
//        new ClientGUI();
//    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MainPanel = new JPanel();
        MainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 5, new Insets(0, 0, 0, 0), -1, -1));
        wordLabel = new JLabel();
        wordLabel.setText("Word: ");
        MainPanel.add(wordLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        MainPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        wordField = new JTextField();
        MainPanel.add(wordField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        meaningsLabel = new JLabel();
        meaningsLabel.setText("Meaning(s): Separate meanings with ';' ");
        MainPanel.add(meaningsLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        systemLabel = new JLabel();
        systemLabel.setText("System:");
        MainPanel.add(systemLabel, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        MainPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        meaningsArea = new JTextArea();
        scrollPane1.setViewportView(meaningsArea);
        updateButton = new JButton();
        updateButton.setText("Update");
        MainPanel.add(updateButton, new com.intellij.uiDesigner.core.GridConstraints(7, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setText("Remove");
        MainPanel.add(removeButton, new com.intellij.uiDesigner.core.GridConstraints(6, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        MainPanel.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(5, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        queryButton = new JButton();
        queryButton.setText("Query");
        MainPanel.add(queryButton, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        MainPanel.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 2), null, 0, false));
        systemArea = new JTextArea();
        systemArea.setText("");
        scrollPane2.setViewportView(systemArea);
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        MainPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 25), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        MainPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(25, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        MainPanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(6, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(25, -1), null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        MainPanel.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 10), null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MainPanel;
    }

}