package view.component;

import view.util.Palette;

import javax.swing.*;
import java.awt.*;

public class FormTextFieldPanel extends JPanel {

    private final JLabel label;
    private final FormTextField input;
    private final JLabel errorMessage;
    private Runnable onValidate;

    public FormTextFieldPanel(
            int column,
            String text,
            Runnable onValidate
    ) {
        this.onValidate = onValidate;
        this.input = new FormTextField(column);

        this.label = new DefaultLabel(text);

        this.errorMessage = new DefaultLabel("", 12);
        this.errorMessage.setForeground(Palette.ERROR_FG);
        this.errorMessage.setPreferredSize(new Dimension(0, 20));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(Palette.MAIN_BG);
        setMaximumSize(new Dimension(400, 0));
        add(this.label);
        add(Box.createVerticalStrut(10));
        add(this.input);
        add(Box.createVerticalStrut(5));
        add(this.errorMessage);
    }

    public JLabel getLabel() {
        return this.label;
    }

    public String getText() {
        return this.input.getText();
    }

    public FormTextField getTextField() {
        return this.input;
    }

    public void showError(String message) {
        this.errorMessage.setText(message);
    }

    public void hideError() {
        this.errorMessage.setText("");
    }

    public boolean doValidation() {
        if (this.onValidate != null)
            this.onValidate.run();
        return this.errorMessage.getText().isEmpty();
    }

    public void setOnValidate(Runnable runnable) {
        this.onValidate = runnable;
    }

}
