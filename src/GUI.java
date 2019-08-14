import constants.Location;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 * GUI.java
 * Creates and shows the AeroCows GUI for collecting user input.
 * Created by cbartram on 2019-08-14.
 *
 * http://github.com/cbartram
 */

public class GUI {
    private final JDialog mainDialog;
    private final JComboBox<Location> locationSelector;

    private boolean started;

    public GUI() {
        mainDialog = new JDialog();
        mainDialog.setTitle("AeroCows");
        mainDialog.setModal(true);
        mainDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainDialog.getContentPane().add(mainPanel);

        JPanel treeSelectionPanel = new JPanel();
        treeSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel treeSelectionLabel = new JLabel("Select tree:");
        treeSelectionPanel.add(treeSelectionLabel);

        locationSelector = new JComboBox<>(Location.values());
        treeSelectionPanel.add(locationSelector);

        mainPanel.add(treeSelectionPanel);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            started = true;
            close();
        });
        mainPanel.add(startButton);

        mainDialog.pack();
    }

    public boolean isStarted() {
        return started;
    }

    public Location getSelectedLocation() {
        return (Location) locationSelector.getSelectedItem();
    }

    public void open() {
        mainDialog.setVisible(true);
    }

    public void close() {
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }
}
