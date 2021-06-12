import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame {
    private JTextField textField;
    private JButton OKButton;
    private JPasswordField passwordField1;
    protected JPanel JPanelMain;
    int counter = 0;

    public MainFrame() {

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteAndRead.DECRYPTCreateList(); //UPDATE
                System.out.println("Lab1.users.size() = " + WriteAndRead.users.size());
                for( User user : WriteAndRead.users){
                    System.out.println("name "+user.name);
                }
                System.out.println("OkButton");
                if( !textField.getText().equals("") ){      //поле логина не пустое
                    System.out.println("поле логина не пустое");
                    for(int i = 0; i< WriteAndRead.users.size(); i++){
                        User user = Lab1.writeAndRead.users.get(i);
                        if(user.name.equals(textField.getText())){      //имя существует в списке
                            System.out.println("имя существует в списке");
                            if(user.lockEntire){
                                textField.setText("");
                                JFrame frame = new JFrame();
                                JOptionPane.showMessageDialog(frame, "Вы заблокированы!");
                                break;
                            }
                            System.out.println("Before user.firstEntire = "+ user.firstEntire);
                            if(!user.firstEntire){      //если первый вход имени
                                System.out.println("первый вход имени");
                                user.firstEntire=true;
                                WriteAndRead.rewrite();
                                MainFrame.printList();
                                counter=0;
                                Lab1.createFirstLogin(user);
                                closeMainFrame();
                                break;
                            }else{      //вход имени не первый
                                if ( (new String(passwordField1.getPassword()) ).equals("")) {  //не введен пароль
                                    JFrame frame = new JFrame();
                                    JOptionPane.showMessageDialog(frame, "Введите пароль!");
                                    break;
                                }else{
                                    if(user.password.equals( new String(passwordField1.getPassword()) )){   //пароль введен верно
                                        if(Lab1.check(user, new String(passwordField1.getPassword()))) {
                                            counter=0;
                                            if(user.type){
                                                Lab1.createInsideForAdmin(user);
                                            }else{
                                                Lab1.createInsideForUser(user);
                                            }
                                            closeMainFrame();
                                            break;
                                        }else{
                                            JOptionPane.showMessageDialog(new JFrame(), "Пароль не соответствует наложенным на него ограничениям!\n" +
                                                    "Пароль должен содержать латинские буквы, кириллицу и цифры.");
                                            Lab1.createChangePassword(user);
                                            closeMainFrame();
                                            break;
                                        }
                                    }else{      //пароль введен неверно
                                        counter++;
                                        if(counter==3){
                                            System.out.println("выход");
                                            System.exit(0);
                                        }
                                        System.out.println(counter);
                                        printList();
                                        JFrame frame = new JFrame();
                                        JOptionPane.showMessageDialog(frame, "Неверный пароль! Попробуте снова.");
                                        passwordField1.setText("");
                                        break;
                                    }
                                }
                            }
                        }
                        if(i==(WriteAndRead.users.size()-1)){
                            JFrame frame = new JFrame();
                            JOptionPane.showMessageDialog(frame, "Неверное имя пользователя! Попробуйте снова.");
                            textField.setText("");
                        }
                    }
                }else{
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Заполните поле имени пользователя!");
                }
            }
        });
    }

    public static void printList(){
        for(User u : WriteAndRead.users){
            System.out.println("User:");
            System.out.println(u.type);
            System.out.println(u.name);
            System.out.println(u.password);
            System.out.println(u.restrictionOnPass);
            System.out.println(u.lockEntire);
            System.out.println(u.firstEntire);
        }
    }

    private void closeMainFrame(){
        textField.setText("");
        passwordField1.setText("");
        Lab1.frameMain.setVisible(false);
    }

}
