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
    private final JCheckBox outlineCheckbox;

    private boolean started;
    private boolean showOutline = true;
    private JCheckBox checkBox1;

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
        outlineCheckbox = new JCheckBox("Outline Tiles");
        outlineCheckbox.setSelected(true);
        outlineCheckbox.addItemListener((e) -> showOutline = e.getStateChange() != ItemEvent.DESELECTED);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            started = true;
            close();
        });

        mainPanel.add(locationSelectionPanel);
        mainPanel.add(startButton);
        mainPanel.add(outlineCheckbox);
        mainDialog.pack();
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isOpen() {
        return mainDialog.isVisible();
    }

    public Location getSelectedLocation() {
        return (Location) locationSelector.getSelectedItem();
    }

    public boolean shouldShowOutline() {
        return showOutline;
    }

    public void open() {
        mainDialog.setVisible(true);
    }

    public void close() {
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }
}
