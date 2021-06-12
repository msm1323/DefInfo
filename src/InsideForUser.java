import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsideForUser {
    private JButton changeYourPasswordButton;
    protected JPanel JPanel;
    private JButton exitButton;


    public InsideForUser(User user) {
        changeYourPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lab1.createChangePassword(user);
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteAndRead.users.clear();     //UPDATE writeAndRead = new
                Lab1.frameInFUs.setVisible(false);
                Lab1.frameMain.setVisible(true);
            }
        });
    }
}
