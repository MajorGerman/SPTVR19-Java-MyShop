package sptvr19.java.myshop;

class User {
    private String login;
    private String password;
    private Person pers;

    public User(String login, String password, Person pers) {
        this.login = login;
        this.password = password;
        this.pers = pers;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Person getPers() {
        return pers;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPers(Person pers) {
        this.pers = pers;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", password=" + password + ", pers=" + pers + '}';
    }
       
}
