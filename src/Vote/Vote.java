package Vote;

public class Vote {
    private int voterId;
    private int candidateId;

    // Конструкторы, геттеры и сеттеры


    public Vote(int voterId, int candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }
}
