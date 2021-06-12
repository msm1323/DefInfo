import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword {
    public javax.swing.JPanel JPanel;
    private JPasswordField newPassField;
    private JPasswordField renewPassField;
    private JButton OKButton;
    private JPasswordField curPassField;
    private JButton cancelButton;

    public ChangePassword(User user) {
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(! new String(curPassField.getPassword()).equals(user.password) ){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Неверно введен старый пароль!");
                }else{      //если старый пароль ввден верно
                    if(new String(newPassField.getPassword()).equals(new String(renewPassField.getPassword()))){    //поля нового пароля равны
                        if(Lab1.check(user, new String(newPassField.getPassword()))) {
                            user.password = new String(newPassField.getPassword());
                            WriteAndRead.rewrite();
                            Lab1.createMainFrame();
                            closeChPassFrame();
                        }else{
                            JOptionPane.showMessageDialog(new JFrame(), "Пароль не соответствует наложенным на него ограничениям!");
                        }
                    }else{
                        newPassField.setText("");
                        renewPassField.setText("");
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Пароль повторен неверно! Начните заново.");
                    }
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeChPassFrame();
            }
        });

    }

    private void closeChPassFrame(){
        curPassField.setText("");
        newPassField.setText("");
        renewPassField.setText("");
        Lab1.frameChP.setVisible(false);
    }

}
