import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class InsideForAdmin {
    private JButton changeYourPasswordButton;
    private JCheckBox accountLockoutCheckBox;
    private JCheckBox passwordRestrictionCheckBox;
    private JButton addUserButton;
    private JTextField newUserField;
    public javax.swing.JPanel JPanel;
    private JComboBox comboBoxUsers;
    private JCheckBox adminCheckBox;
    private JCheckBox passwordRestrictionNewUser;
    private JButton saveChangesButton;
    private JButton exitButton;

    private void upDateCB(){
        comboBoxUsers.removeAllItems();
        for(User el : WriteAndRead.users){
            if(!el.type){
                comboBoxUsers.addItem(el.name);
            }
        }
    }

    InsideForAdmin(User user){
        System.out.println("InsideForAdmin!");
        System.out.println("user.firstEntire = "+user.firstEntire);
        //отсюда идет в лабе 1
        upDateCB();
        accountLockoutCheckBox.setSelected(WriteAndRead.users.get(0).lockEntire);
        passwordRestrictionCheckBox.setSelected(WriteAndRead.users.get(0).restrictionOnPass);

        changeYourPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lab1.createChangePassword(user);
            }
        });


        comboBoxUsers.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for(User el : WriteAndRead.users){
                    if(e.getItem().equals(el.name)){
                        accountLockoutCheckBox.setSelected(el.lockEntire);
                        passwordRestrictionCheckBox.setSelected(el.restrictionOnPass);
                    }
                }
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i< WriteAndRead.users.size(); i++){
                    //System.out.println(i);
                    User el = WriteAndRead.users.get(i);
                    if(el.name.equals(newUserField.getText())){
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "Пользователь с таким именем уже существует!\nПопробуйте снова.");
                        newUserField.setText("");
                        break;
                    }
                    if(i==(WriteAndRead.users.size()-1)){
                        System.out.println("passwordRestrictionNewUser.isSelected()"+passwordRestrictionNewUser.isSelected());
                        User user = new User(adminCheckBox.isSelected(), newUserField.getText(), "", passwordRestrictionNewUser.isSelected(), false, false);
                        WriteAndRead.users.add(user);
                        MainFrame.printList();
                        WriteAndRead.rewrite();
                        MainFrame.printList();
                        upDateCB();
                        newUserField.setText("");
                        break;
                    }
                }
            }
        });

        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //не работает
                for(int i = 0; i< WriteAndRead.users.size(); i++){
                    System.out.println(i);
                    User el = WriteAndRead.users.get(i);
                    if(el.name.equals(comboBoxUsers.getSelectedItem())){
                        System.out.println("CBUsers "+comboBoxUsers.getSelectedItem());
                        User us = new User(el.type, el.name, el.password, passwordRestrictionCheckBox.isSelected(), accountLockoutCheckBox.isSelected(), el.firstEntire);
                        WriteAndRead.users.set(i, us);
                        for(User usr : WriteAndRead.users){
                            System.out.println(usr.name);
                        }
                        break;
                    }
                }
                WriteAndRead.rewrite();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteAndRead.users.clear();     //UPDATE
                System.out.println("exitButton: WriteAndRead.users.clear()");
                Lab1.frameInFAd.setVisible(false);
                Lab1.frameMain.setVisible(true);
            }
        });
    }

}
