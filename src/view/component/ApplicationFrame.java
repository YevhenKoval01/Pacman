package view.component;

import view.util.Palette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class ApplicationFrame extends JFrame {

    protected JPanel contentPanel;

    public ApplicationFrame(String title, Dimension minSize, Dimension preferredSize) {
        super(title);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
        this.contentPanel.setBackground(Palette.MAIN_BG);
        setBackground(Palette.MAIN_BG);

        setupWindowListeners();
        onComponentsInit();
        setLayout(new BorderLayout());
        add(this.contentPanel, BorderLayout.CENTER);

        setMinimumSize(minSize);
        setMinimumSize(preferredSize);

        pack();
        setLocationRelativeTo(null);
    }

    protected abstract void onComponentsInit();

    protected abstract void onWindowClose();

    private void setupWindowListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onWindowClose();
            }
        });
    }
}
