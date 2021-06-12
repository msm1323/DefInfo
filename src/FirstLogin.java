import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstLogin {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton OKButton;
    public javax.swing.JPanel JPanel;


    FirstLogin(User user) {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(new String(passwordField1.getPassword()).equals(new String(passwordField2.getPassword()))){
                    if(Lab1.check(user, new String(passwordField1.getPassword()))) {
                        user.password = new String(passwordField1.getPassword());
                        MainFrame.printList();
                        WriteAndRead.rewrite();
                        MainFrame.printList();
                        if (user.type) {
                            JPasswordField passwordField = new JPasswordField();
                            JOptionPane.showConfirmDialog(null, passwordField, "Введите ключ для доступа к данным!", JOptionPane.PLAIN_MESSAGE);
                            if (!(new String(passwordField.getPassword()).equals(Lab1.keyWord))) {
                                JOptionPane.showMessageDialog(new JFrame(), "Ключ введен неверно!");
                                System.exit(0);
                            }
                            Lab1.createInsideForAdmin(user);
                        } else {
                            Lab1.createInsideForUser(user);
                        }
                        Lab1.frameFirstL.setVisible(false);
                    }else{
                        passwordField1.setText("");
                        passwordField2.setText("");
                        JOptionPane.showMessageDialog(new JFrame(), "Пароль не соответствует наложенным на него ограничениям!");
                    }
                }else{
                    passwordField1.setText(""); //очистить поле
                    passwordField2.setText("");   //очистить поле
                    JOptionPane.showMessageDialog(new JFrame(), "Пароль повторен неверно! Начните заново.");
                }
            }
        });
    }
}
