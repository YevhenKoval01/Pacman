package view.util;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {

    public static ImageIcon DEFAULT_DIALOG_ICON = new ImageIcon("assets/icons/common/black_ghost.jpeg");

    public static boolean confirmationDialog(Component parentComponent, String title, String question) {
        int userChoice = JOptionPane.showConfirmDialog(
                parentComponent,
                question,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                DEFAULT_DIALOG_ICON
        );

        return userChoice == JOptionPane.YES_OPTION;
    }

    public static void messageDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                DEFAULT_DIALOG_ICON
        );
    }

    public static String saveGameDialog(Component parent) {
        JTextField inputField = new JTextField();

        Object[] message = {"Enter file name to save a game:\n", inputField};
        String[] options = {"Save", "Do Not Save"};
        String userInput = "";
        int userChoice;
        boolean isSaving = true;
        do {
            userChoice = JOptionPane.showOptionDialog(
                    parent,
                    message,
                    "You win",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    DEFAULT_DIALOG_ICON,
                    options,
                    options[0]
            );

            String input = inputField.getText().trim();

            if (userChoice == JOptionPane.YES_OPTION) {
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            parent,
                            "File name cannot be empty!",
                            "File name error",
                            JOptionPane.WARNING_MESSAGE,
                            DEFAULT_DIALOG_ICON
                    );
                } else {
                    userInput = input;
                    isSaving = false;
                }
            } else {
                userInput = "";
                isSaving = false;
            }
        } while (isSaving);

        userInput = userInput.replaceAll(" ", "_");

        return userInput;
    }

}
