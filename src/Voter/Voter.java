package Voter;

public class Voter {
    private int id;
    private String name;
    private String login;
    private String password;
    private boolean voted;

    public static Voter currentUser;

    public static Voter getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Voter user) {
        currentUser = user;
    }

    public Voter(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Voter(int id ,String name, String login, String password, boolean voted) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.voted = voted;
    }

    public Voter(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public int getVoterId() {
        return id;
    }

    public int getCandidateId() {
        return id;
    }


    // Конструкторы, геттеры и сеттеры
}
