package Vote;

import Base.Database;
import Candidate.Candidate;
import Voter.Voter;

import java.util.Scanner;

public class UserInterface {
    private Database database;
    private Scanner scanner;
    private Voter voter;


    public UserInterface() {
        database = new Database();
        scanner = new Scanner(System.in);
        voter = null;
    }

    public void run() {
        while (true) {
            System.out.println("1. Зарегистрироваться");
            System.out.println("2. Войти");
            System.out.println("3. Просмотреть список кандидатов");
            System.out.println("4. Проголосовать");
            System.out.println("5. Посмотреть результаты");
            System.out.println("0. Выход");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    registerVoter();
                    break;
                case 2:
                    loginVoter();
                    break;
                case 3:
                    viewCandidates();
                    break;
                case 4:
                    vote();
                    break;
                case 5:
                    viewResults();
                    break;
                case 0:
                    database.close();
                    return;
                default:
                    System.out.println("Некорректный ввод");
                    break;
            }
        }
    }

    private void registerVoter() {
        scanner.nextLine(); // Считываем символ переноса строки, оставшийся после ввода числа
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        System.out.println("Введите логин:");
        String login = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        Voter voter = new Voter(name, login, password);

        if (database.registerVoter(voter)) {
            System.out.println("Вы успешно зарегистрировались");
        } else {
            System.out.println("Ошибка регистрации");
        }
    }

    private void loginVoter() {
        scanner.nextLine(); // Считываем символ переноса строки, оставшийся после ввода числа
        System.out.println("Введите логин:");
        String login = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        // Some code for authentication
        Voter user = new Voter(login, password);
        Voter.setCurrentUser(user);

        if (database.loginVoter(login, password)) {
            voter = new Voter("", login, password);
            System.out.println("Вы успешно вошли в систему");
        } else {
            System.out.println("Неверный логин или пароль");
        }
    }

    private void viewCandidates() {
        Candidate[] candidates = database.getCandidates();
        if (candidates == null) {
            System.out.println("Ошибка получения списка кандидатов");
        } else {
            System.out.println("Список кандидатов:");
            for (Candidate candidate : candidates) {
                System.out.println(candidate);
            }
        }
    }

//    private void vote() {
//        if (voter == null) {
//            System.out.println("Сначала войдите в систему");
//            return;
//        }
//        if (database.hasVoted(voter)) {
//            return;
//        }
//        System.out.println("Введите id кандидата:");
//        int candidateId = scanner.nextInt();
//        if (!database.isValidCandidateId(candidateId)) {
//            System.out.println("Некорректный id кандидата");
//            return;
//        }
//        if (database.vote(voter, candidateId)) {
//            System.out.println("Вы успешно проголосовали");
//        } else {
//            System.out.println("Ошибка голосования");
//        }
//    }


    private void vote() {
        if (voter == null) {
            System.out.println("Сначала войдите в систему");
            return;
        }
        if (database.hasVoted(voter)) {
            return;
        }
        Candidate[] candidates = database.getCandidates();
        if (candidates == null) {
            System.out.println("Ошибка получения списка кандидатов");
            return;
        }
        for (int i = 0; i < candidates.length; i++) {
            System.out.println((i+1) + ". " + candidates[i]);
        }
        System.out.println("Выберите кандидата:");
        int candidateIndex = scanner.nextInt() - 1;
        if (candidateIndex < 0 ) {
            System.out.println("Некорректный выбор");
            return;
        }
        if (database.vote(voter, candidateIndex)) {
            System.out.println("Вы успешно проголосовали");
        } else {
            System.out.println("Ошибка голосования");
        }
    }


//    private void vote() {
//        if (voter == null) {
//            System.out.println("Сначала войдите в систему");
//            return;
//        }
//        Candidate[] candidates = database.getCandidates();
//        if (candidates == null) {
//            System.out.println("Ошибка получения списка кандидатов");
//            return;
//        }
//        for (int i = 0; i < candidates.length; i++) {
//            System.out.println((i+1) + ". " + candidates[i]);
//        }
//        System.out.println("Выберите номер кандидата:");
//        int candidateId = scanner.nextInt();
//        boolean found = false;
//        for (int i = 0; i < candidates.length; i++) {
//            if (candidates[i].getId() == candidateId) {
//                if (database.vote(voter, i)) {
//                    System.out.println("Вы успешно проголосовали");
//                } else {
//                    System.out.println("Ошибка голосования");
//                }
//                found = true;
//                break;
//            }
//        }
//        if (!found) {
//            System.out.println("Некорректный выбор");
//        }
//    }


//    private void vote() {
//        if (voter == null) {
//            System.out.println("Сначала войдите в систему");
//            return;
//        }
//        Candidate[] candidates = database.getCandidates();
//        if (candidates == null) {
//            System.out.println("Ошибка получения списка кандидатов");
//            return;
//        }
//        for (int i = 0; i < candidates.length; i++) {
//            System.out.println((i+1) + ". " + candidates[i]);
//        }
//        System.out.println("Выберите кандидата:");
//        int candidateIndex = scanner.nextInt() - 1;
//        if (candidateIndex < 0 || candidateIndex >= candidates.length) {
//            System.out.println("Некорректный выбор");
//            return;
//        }
//        if (database.vote(voter, candidateIndex)) {
//            System.out.println("Вы успешно проголосовали");
//        } else {
//            System.out.println("Ошибка голосования");
//        }
//    }

    private void viewResults() {
        int[] counts = database.countVotes();
        if (counts == null) {
            System.out.println("Ошибка получения результатов");
        } else {
            Candidate[] candidates = database.getCandidates();
            System.out.println("Результаты голосования:");
            for (int i = 0; i < candidates.length; i++) {
                int count = i < counts.length ? counts[i] : 0;
                System.out.println(candidates[i] + ": " + count);
            }
        }
    }


}



