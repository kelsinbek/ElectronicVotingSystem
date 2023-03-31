package Base;

import Candidate.Candidate;
import Voter.Voter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Voter.Voter.currentUser;

public class Database {
    private Connection connection;

    public Database() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/elections", "postgres", "suiunduk77");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerVoter(Voter voter) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO voter (name, login, password) VALUES (?, ?, ?)");
            statement.setString(1, voter.getName());
            statement.setString(2, voter.getLogin());
            statement.setString(3, voter.getPassword());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int findVoterId() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM voter WHERE login = ? AND password = ?");
            statement.setString(1, currentUser.getLogin());
            statement.setString(2, currentUser.getPassword());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int voterId = resultSet.getInt("id");
                currentUser.setId(voterId);
                return voterId;
            } else {
                return -1; // пользователь не найден
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // произошла ошибка при выполнении запроса
        }
    }



    public boolean loginVoter(String login, String password) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM voter WHERE login = ? AND password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Candidate[] getCandidates() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM candidate");
            List<Candidate> candidates;
            candidates = new ArrayList<>();
            while (resultSet.next()) {
                Candidate candidate = new Candidate(resultSet.getString("name"), resultSet.getString("party"));
                candidate.setId(resultSet.getInt("id"));
                candidates.add(candidate);
            }
            return candidates.toArray(new Candidate[candidates.size()]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



//    public boolean vote(Vote vote, int candidateIndex) {
//        try {
//            int CurenUser = 2;
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO vote (voter_id, candidate_id) VALUES (?, ?)");
//            statement.setInt(1, vote.getVoterId());
//            statement.setInt(2, vote.getCandidateId());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public int[] countVotes() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT candidate_id, COUNT(*) FROM vote GROUP BY candidate_id");
            Map<Integer, Integer> counts = new HashMap<>();
            while (resultSet.next()) {
                counts.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            int[] result = new int[counts.size()];
            int i = 0;
            for (int key : counts.keySet()) {
                result[i++] = counts.get(key);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}

    public boolean vote(Voter voter, int candidateIndex) {
        if (voter == null) {
            return false;
        }
        Candidate[] candidates = getCandidates();
        if (candidates == null || candidateIndex < 0 || candidateIndex >= candidates.length) {
            return false;
        }
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO vote (voter_id, candidate_id) VALUES (?, ?)");
            statement.setInt(1, findVoterId());
            statement.setInt(2, candidates[candidateIndex].getId());
            int rows = statement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addCandidate(Candidate candidate) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO candidate (name, party) VALUES (?, ?)");
            preparedStatement.setString(1, candidate.getName());
            preparedStatement.setString(2, candidate.getParty());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCandidate(int candidateId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM candidate WHERE id = ?");
            preparedStatement.setInt(1, candidateId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


//    public boolean deleteCandidate(int candidateIndex) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM candidate WHERE id = ?");
//            preparedStatement.setInt(1, candidateIndex + 1); // индексы в БД начинаются с 1
//            int rowsDeleted = preparedStatement.executeUpdate();
//            return rowsDeleted > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public boolean hasVoted(Voter voter) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT voter_id FROM vote");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int voterId = result.getInt("voter_id");
                if (voterId == findVoterId()) {
                    System.out.println("Вы уже проголосовали");
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }









    public void close() {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }



}



