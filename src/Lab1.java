import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Lab1 {

    // var 10
    //латинские буквы, символы кириллицы и цифры

    //Секретная фраза для генерации ключа к файлу. Будет проверена на совпадение.
    public static String keyWord = "microtra";
    //Сам ключ, создаётся ниже.
    public static SecretKey key;

    public static WriteAndRead writeAndRead;

    static JFrame frameMain = new JFrame("ENTRANCE");
    static JFrame frameChP = new JFrame("Change password");
    static JFrame frameFirstL = new JFrame();
    static JFrame frameInFUs = new JFrame("WELCOME!");
    static JFrame frameInFAd = new JFrame("WELCOME!");

    public static void main(String[] args) {

        try {
            // Создание ключа и шифрование
            byte[] keyByte = keyWord.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(keyByte); //результат
            byte[] digestFinal = new byte[8]; //результат
            for (int i = 0; i < 8; i++) {
                digestFinal[i] = digest[i];
            }
            String algorithm  = "DES";//Алгоритм шифрования, а точнее класс алгоритмов, должен совпадать с Cipher
            key = new SecretKeySpec(digestFinal, algorithm); // Ключ создан
        }catch(UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        File file = new File("DefInfo.txt");
        System.out.println("file.length() = " + file.length());
        if( !( file.isFile()) ){
            System.out.println(" file.isFile() = false (не существует) ");
            WriteAndRead.users.add(new User(true, "Admin", "", false, false, false));
            WriteAndRead.rewrite();
            WriteAndRead.users.clear();
        }
        createMainFrame();

    }

    public static void createMainFrame(){
        MainFrame mainFrame = new MainFrame();
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameMain.setContentPane(mainFrame.JPanelMain);
        frameMain.setSize(400, 160);
        frameMain.setLocationRelativeTo(null);
        frameMain.setResizable(false);
        frameMain.setVisible(true);
    }

    public static void createChangePassword(User user){
        ChangePassword changePassword = new ChangePassword(user);
        frameChP.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameChP.setContentPane(changePassword.JPanel);
        frameChP.setSize(400, 160);
        frameChP.setLocationRelativeTo(null);
        frameChP.setResizable(false);
        frameChP.setVisible(true);
    }

    public static void createFirstLogin(User user){
        FirstLogin firstL = new FirstLogin(user);
        frameFirstL.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameFirstL.setContentPane(firstL.JPanel);
        frameFirstL.setSize(400,160);
        frameFirstL.setLocationRelativeTo(null);
        frameFirstL.setResizable(false);
        frameFirstL.setVisible(true);
    }

    public static void createInsideForUser(User user){
        InsideForUser insFUser = new InsideForUser(user);
        frameInFUs.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameInFUs.setContentPane(insFUser.JPanel);
        frameInFUs.setSize(400,160);
        frameInFUs.setLocationRelativeTo(null);
        frameInFUs.setResizable(false);
        frameInFUs.setVisible(true);
    }

    public static void createInsideForAdmin(User user){
        InsideForAdmin insFadm = new InsideForAdmin(user);
        frameInFAd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameInFAd.setContentPane(insFadm.JPanel);
        frameInFAd.setSize(500,300);
        frameInFAd.setLocationRelativeTo(null);
        frameInFAd.setResizable(false);
        frameInFAd.setVisible(true);
    }

    public static boolean keyChecker(byte[] dig) {
        //Хеширование MD4
        try {
            byte[] keyByte = keyWord.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(keyByte);
            if (Arrays.equals(dig, digest)) {
                return true;
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    static boolean check(User user, String s) {       //есть ли ограничения на пароль и отвечает ли им введенная строка
        System.out.println("check!!!");
        int counter = 0;
        System.out.println("user.lockEntire"+user.restrictionOnPass);
        if(user.restrictionOnPass) {
            System.out.println("if(user.restrictionOnPass)");
            for (char a : s.toCharArray()) {
                if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.BASIC_LATIN) {
                    counter++;
                    System.out.println("LATIN_1_SUPPLEMENT counter = "+counter);
                    break;
                }
            }
            for (char a : s.toCharArray()) {
                if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                    counter++;
                    System.out.println("CYRILLIC counter = "+counter);
                    break;
                }
            }
            for (char a : s.toCharArray()) {
                if ( (a>47)&(a<58) ) {
                    counter++;
                    System.out.println("NUMBER_FORMS counter = "+counter);
                    break;
                }
            }
            System.out.println("counter = "+counter);
            return counter == 3;
        }else{
            return true;
        }
    }

}
