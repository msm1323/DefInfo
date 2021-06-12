public class User {
    boolean type;
    String name;
    String password;
    boolean restrictionOnPass;
    boolean lockEntire;
    boolean firstEntire;

    public User(boolean type, String name, String password, boolean restrictionOnPass, boolean lockEntire, boolean firstEntire){
        this.type = type;
        this.name = name;
        this.password = password;
        this.restrictionOnPass = restrictionOnPass;
        this.lockEntire = lockEntire;
        this.firstEntire = firstEntire;
    }

}
