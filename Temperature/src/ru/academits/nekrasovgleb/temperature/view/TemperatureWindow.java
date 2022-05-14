package ru.academits.nekrasovgleb.temperature.view;

import ru.academits.nekrasovgleb.temperature.converter.TemperatureConverter;
import ru.academits.nekrasovgleb.temperature.converter.TemperatureScale;

import javax.swing.*;
import java.awt.*;

public class TemperatureWindow implements View {
    private final TemperatureConverter temperatureConverter;

    public TemperatureWindow(TemperatureConverter temperatureConverter) {
        this.temperatureConverter = temperatureConverter;
    }

    public void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            JFrame frame = new JFrame("Конвертер температуры");

            frame.setSize(800, 500);
            frame.setMinimumSize(new Dimension(700, 400));
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            frame.add(panel);

            GridBagConstraints c = new GridBagConstraints();

            c.gridx = 1;
            c.gridy = 1;

            Font labelsFont = new Font("Arial", Font.PLAIN, 18);
            Font textFieldsFont = new Font("Arial", Font.PLAIN, 16);
            Font buttonsFont = new Font("Arial", Font.BOLD, 19);

            JLabel chooseScaleFromLabel = new JLabel("Выберите шкалу, из которой нужно перевести температуру:");
            chooseScaleFromLabel.setFont(labelsFont);
            panel.add(chooseScaleFromLabel, c);

            JComboBox<TemperatureScale> scaleFromComboBox = new JComboBox<>(TemperatureConverter.SCALES);
            scaleFromComboBox.setFont(textFieldsFont);
            scaleFromComboBox.addActionListener(e ->
                    temperatureConverter.setScaleFrom((TemperatureScale) scaleFromComboBox.getSelectedItem()));

            c.gridy = 2;
            panel.add(scaleFromComboBox, c);

            c.gridy = 3;
            panel.add(new JLabel(" "), c);

            JLabel enterTemperatureLabel = new JLabel("Введите значение температуры:");
            enterTemperatureLabel.setFont(labelsFont);
            c.gridy = 4;
            panel.add(enterTemperatureLabel, c);

            JTextField enterTemperatureField = new JTextField(10);
            enterTemperatureField.setFont(textFieldsFont);
            c.gridy = 5;
            panel.add(enterTemperatureField, c);

            c.gridy = 6;
            panel.add(new JLabel(" "), c);

            JLabel chooseScaleToLabel = new JLabel("Выберите шкалу, в которую нужно перевести температуру:");
            chooseScaleToLabel.setFont(labelsFont);
            c.gridy = 7;
            panel.add(chooseScaleToLabel, c);

            JComboBox<TemperatureScale> scaleToComboBox = new JComboBox<>(TemperatureConverter.SCALES);
            scaleToComboBox.setFont(textFieldsFont);
            scaleToComboBox.addActionListener(e ->
                    temperatureConverter.setScaleTo((TemperatureScale) scaleToComboBox.getSelectedItem()));

            c.gridy = 8;
            panel.add(scaleToComboBox, c);

            JLabel convertedTemperatureLabel = new JLabel();
            convertedTemperatureLabel.setFont(labelsFont);

            JButton convertTemperatureButton = new JButton("Конвертировать температуру");
            convertTemperatureButton.setFont(buttonsFont);
            convertTemperatureButton.addActionListener(e -> {
                try {
                    double enteredTemperature = Double.parseDouble(enterTemperatureField.getText());
                    double convertedTemperature = temperatureConverter.convertTemperature(enteredTemperature);
                    convertedTemperatureLabel.setText("Значение температуры = " + convertedTemperature);
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(frame, "Температура должна быть числом!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    convertedTemperatureLabel.setText("");
                }
            });

            c.gridy = 9;
            panel.add(new JLabel(" "), c);

            c.gridy = 10;
            panel.add(convertTemperatureButton, c);

            c.gridy = 11;
            panel.add(convertedTemperatureLabel, c);

            frame.setVisible(true);
        });
    }
}