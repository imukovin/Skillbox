import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm {
    private static final int NUM_OF_WORDS_FIO = 3;

    private boolean isCollapseFrame = true;
    private JPanel panel1;
    private JTextField nameTf;
    private JTextField lastNameTf;
    private JTextField secondNameTf;
    private JButton collapseButton;

    MainForm() {
        collapseButton.addActionListener(new Action() {
            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (isCollapseFrame) {
                    String name = nameTf.getText();
                    String secondName = secondNameTf.getText();
                    String lastName = lastNameTf.getText();
                    if (name.length() != 0 && secondName.length() != 0 && lastName.length() != 0) {
                        secondNameTf.setVisible(false);
                        lastNameTf.setVisible(false);
                        nameTf.setText(name + " " + secondName + " " + lastName);
                        collapseButton.setText("Expand");
                        isCollapseFrame = false;
                    } else {
                        incorrectData();
                    }
                } else {
                    String[] name = nameTf.getText().split("\\s+");
                    if (name.length == NUM_OF_WORDS_FIO) {
                        secondNameTf.setVisible(true);
                        lastNameTf.setVisible(true);
                        nameTf.setText(name[0]);
                        secondNameTf.setText(name[1]);
                        lastNameTf.setText(name[2]);
                        collapseButton.setText("Collapse");
                        isCollapseFrame = true;
                    } else {
                        incorrectData();
                    }
                }
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    private void incorrectData() {
        JOptionPane.showMessageDialog(panel1, "Неверные данные!",
                "Ошибка", JOptionPane.PLAIN_MESSAGE);
    }
}
