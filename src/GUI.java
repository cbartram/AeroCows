import constants.Location;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

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
    private boolean showOutline = true;

    GUI() {
        mainDialog = new JDialog();
        mainDialog.setTitle("AeroCows");
        mainDialog.setModal(true);
        mainDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainDialog.getContentPane().add(mainPanel);

        // Location Selector (East vs West)
        JPanel locationSelectionPanel = new JPanel();
        locationSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel locationSelectionLabel = new JLabel("Select Location:");
        locationSelectionPanel.add(locationSelectionLabel);

        locationSelector = new JComboBox<>(Location.values());
        locationSelectionPanel.add(locationSelector);

        JPanel showPaintPanel = new JPanel();
        showPaintPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Show tile highlight checkbox
        JCheckBox outlineCheckbox = new JCheckBox("Outline Tiles");
        outlineCheckbox.setSelected(true);
        outlineCheckbox.addItemListener((e) -> showOutline = e.getStateChange() != ItemEvent.DESELECTED);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            started = true;
            close();
        });

        mainPanel.add(locationSelectionPanel);
        mainPanel.add(outlineCheckbox);
        mainPanel.add(startButton);
        mainDialog.pack();
    }

    /**
     * Returns true when the script should be started
     * @return Boolean
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Boolean which returns true if the GUI dialog is open
     * and false otherwise
     * @return Boolean
     */
    public boolean isOpen() {
        return mainDialog.isVisible();
    }

    /**
     * Returns the selected location object from the GUI
     * @return Location
     */
    public Location getSelectedLocation() {
        return (Location) locationSelector.getSelectedItem();
    }

    /**
     * Returns a boolean of true if the outlines should be shown and false otherwise
     * @return Boolean
     */
    public boolean shouldShowOutline() {
        return showOutline;
    }

    /**
     * Opens the GUI
     */
    public void open() {
        mainDialog.setVisible(true);
    }

    /**
     * Closes the GUI
     */
    public void close() {
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }
}
