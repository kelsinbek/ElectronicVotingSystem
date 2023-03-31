package Candidate;

public class Candidate {
    private int id;
    private String name;
    private String party;

    // Конструкторы, геттеры и сеттеры


    public Candidate(String name, String party) {
        this.name = name;
        this.party = party;
    }

    public Candidate(String name) {
        this.name = name;
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

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Party: " + party;
    }
}