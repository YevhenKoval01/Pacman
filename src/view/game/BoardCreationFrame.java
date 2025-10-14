package view.game;

import controller.GameController;
import controller.util.MapType;
import view.component.*;
import view.component.Image;
import view.menu.MenuFrame;

import javax.swing.*;
import java.awt.*;

public class BoardCreationFrame extends ApplicationFrame {

    private final static int MIN_ROWS_COLS = 10;
    private final static int MAX_ROWS_COLS = 100;

    private static BoardCreationFrame instance;

    private BoardCreationFrame() {
        super(
                "Create a board",
                new Dimension(650, 650),
                new Dimension(1200, 800)
        );
    }

    @Override
    protected void onComponentsInit() {
        JLabel titleLabel = new DefaultLabel("CREATE A BOARD", 64);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mapOptions = new JPanel(new GridLayout(1, 3, 20, 0));
        mapOptions.setOpaque(false);

        for (MapType map : MapType.values()) {
            if (map != MapType.USER_GENERATED) {
                JPanel mapOption = getMapOptionPanel(
                        map.IMAGE_PATH,
                        map.DIMENSION,
                        () -> {
                            BoardCreationFrame.this.dispose();
                            GameController.initGame(4, map, 0, 0);
                        }
                );
                mapOptions.add(mapOption);
            }
        }

        JPanel formPanel = getFormPanel();

        this.contentPanel.add(Box.createVerticalStrut(20));
        this.contentPanel.add(titleLabel);
        this.contentPanel.add(mapOptions);
        this.contentPanel.add(Box.createVerticalStrut(40));
        this.contentPanel.add(formPanel);
        this.contentPanel.add(Box.createVerticalStrut(20));
    }

    @Override
    protected void onWindowClose() {
        BoardCreationFrame.this.dispose();
        MenuFrame.getInstance().setVisible(true);
    }

    private JPanel getMapOptionPanel(String imagePath, String buttonText, Runnable onClick) {
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BoxLayout(mapPanel, BoxLayout.Y_AXIS));
        mapPanel.setOpaque(false);
        mapPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        Image mapImage = new Image(imagePath);
        mapImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        FormButton btn = new FormButton(buttonText);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addActionListener(ev -> onClick.run());
        mapPanel.add(mapImage);
        mapPanel.add(Box.createVerticalStrut(10));
        mapPanel.add(btn);
        return mapPanel;
    }

    private JPanel getFormPanel() {
        FormTextFieldPanel rowsInput = new FormTextFieldPanel(10, "Rows amount:", null);
        rowsInput.setOnValidate(() -> {
            try {
                int n = Integer.parseInt(rowsInput.getText());

                if (n > MAX_ROWS_COLS) {
                    rowsInput.showError("Number of rows should be lass than " + MAX_ROWS_COLS);
                } else if (n < MIN_ROWS_COLS) {
                    rowsInput.showError("Number of rows should be at least " + MIN_ROWS_COLS);
                } else {
                    rowsInput.hideError();
                }

            } catch (NumberFormatException nfe) {
                rowsInput.showError("Number of rows should be a decimal number");
            }
        });

        FormTextFieldPanel columnsInput = new FormTextFieldPanel(10, "Columns amount:", null);
        columnsInput.setOnValidate(() -> {
            try {
                int n = Integer.parseInt(columnsInput.getText());

                if (n > MAX_ROWS_COLS) {
                    columnsInput.showError("Number of columns should be lass than " + MAX_ROWS_COLS);
                } else if (n < MIN_ROWS_COLS) {
                    columnsInput.showError("Number of columns should be at least " + MIN_ROWS_COLS);
                } else {
                    columnsInput.hideError();
                }

            } catch (NumberFormatException nfe) {
                columnsInput.showError("Number of columns should be a decimal number");
            }
        });

        FormButton formBtn = new FormButton("CREATE & START");

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);

        JPanel inputRow = new JPanel(new GridLayout(1, 2, 20, 0));
        inputRow.setOpaque(false);
        inputRow.setMaximumSize(new Dimension(1000, 400));
        inputRow.add(rowsInput);
        inputRow.add(columnsInput);

        formBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        formBtn.setMaximumSize(new Dimension(300, 40));
        formBtn.addActionListener((ev) -> {
            boolean validRows = rowsInput.doValidation();
            boolean validCols = columnsInput.doValidation();

            if (validRows && validCols) {
                int rows = Integer.parseInt(rowsInput.getText());
                int cols = Integer.parseInt(columnsInput.getText());
                GameController.initGame(4, MapType.USER_GENERATED, rows, cols);
                BoardCreationFrame.this.dispose();
            }

        });
        formPanel.add(inputRow);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(formBtn);

        return formPanel;
    }

    public static BoardCreationFrame getInstance() {
        if (instance == null) instance = new BoardCreationFrame();
        return instance;
    }

}
