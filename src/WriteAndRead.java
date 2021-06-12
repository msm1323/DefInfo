import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class WriteAndRead {

    public static ArrayList<User> users = new ArrayList<>();

    private static Cipher cipher;

    static void DECRYPTCreateList(){            // записать расшифрованную инфу в оперативную память
        System.out.println("______DECRYPTCreateList______");
        try(FileInputStream fin = new FileInputStream("DefInfo.txt"))
        {

            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, buffer.length);

            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // Как и в записи файла
            cipher.init(Cipher.DECRYPT_MODE, Lab1.key); //режим DECRYPT (разшифровка)

            byte[] data = cipher.doFinal(buffer, 0, buffer.length);
            String str = new String(data, "UTF-8"); // Биты в строку
            String[] strings = str.split("\n");
            //Запись в оперативную память
            users.clear();
            System.out.println("DECRYPTCreateList: users.clear()");
            System.out.println("strings.length = "+strings.length);
            for (int i = 0; i < strings.length; i+=6) {
                System.out.println("i = "+i);
                users.add(new User(Boolean.parseBoolean(strings[i]),strings[i+1],strings[i+2],
                        Boolean.parseBoolean(strings[i+3]),Boolean.parseBoolean(strings[i+4]),Boolean.parseBoolean(strings[i+5])));
            }
            MainFrame.printList();
            System.out.println("DECRYPTCreateList: users.size() = "+users.size());
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    static void rewrite(){          //записывает и шифрует в файл из оперативной памяти
        System.out.println("______rewrite______");
        try {
            File file = new File("DefInfo.txt");
            file.delete();

            ArrayList<byte[]> cipherTexts = new ArrayList<>();
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, Lab1.key); //зашифровка

            for(int i=0; i<users.size(); i++){
                User user = users.get(i);
                byte[] cipherText;
                byte[] data = (user.type+"\n"+user.name+"\n"+user.password+"\n"+user.restrictionOnPass
                        +"\n"+user.lockEntire+"\n"+user.firstEntire+"\n").getBytes("UTF-8");
                if( i == (users.size()-1) ){
                    cipherText = cipher.doFinal(data);
                    System.out.println(i+" doFinal");
                }else{
                    cipherText = cipher.update(data);
                    System.out.println(i + " go " + users.size());
                }
                cipherTexts.add(cipherText);
            }
            try (FileOutputStream fos = new FileOutputStream("DefInfo.txt", true)) {
                //запись битов с листа в файл
                for (byte[] cipherText : cipherTexts) {
                    fos.write(cipherText);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

    }
}
