package Admin;

import Base.Database;
import Candidate.Candidate;

import java.util.Scanner;

public class AdminInterface {
    private Database database;
    private Scanner scanner;

    public AdminInterface() {
        database = new Database();
        scanner = new Scanner(System.in);
    }


    public void run() {
        while (true) {

            System.out.println("1. Добавить кандидата");
            System.out.println("2. Просмотреть список кандидатов");
            System.out.println("3. Удалить кандидата");
            System.out.println("4. Посмотреть результаты");
            System.out.println("0. Выход");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addCandidate();
                    break;
                case 2:
                    viewCandidates();
                    break;
                case 3:
                    deleteCandidate();
                    break;
                case 4:
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

    private void addCandidate() {
        scanner.nextLine(); // Считываем символ переноса строки, оставшийся после ввода числа
        System.out.println("Введите имя кандидата:");
        String name = scanner.nextLine();
        System.out.println("Введите партию кандидата:");
        String party = scanner.nextLine();
        Candidate candidate = new Candidate(name,party);
        if (database.addCandidate(candidate)) {
            System.out.println("Кандидат добавлен");
        } else {
            System.out.println("Ошибка добавления кандидата");
        }
    }

    private void viewCandidates() {
        Candidate[] candidates = database.getCandidates();
        if (candidates == null) {
            System.out.println("Ошибка получения списка кандидатов");
        } else {
            System.out.println("Список кандидатов:");
            for (Candidate candidate : candidates) {
                System.out.println(candidate.toString());
            }
        }
    }

    private void deleteCandidate() {
        Candidate[] candidates = database.getCandidates();
        if (candidates == null) {
            System.out.println("Ошибка получения списка кандидатов");
            return;
        }
        for (int i = 0; i < candidates.length; i++) {
            System.out.println((i+1) + ". " + candidates[i]);
        }
        System.out.println("Введите номер кандидата для удаления:");
        int candidateIndex = scanner.nextInt();
        Candidate candidateToDelete = null;
        for (Candidate candidate : candidates) {
            if (candidate.getId() == candidateIndex) {
                candidateToDelete = candidate;
                break;
            }
        }
        if (candidateToDelete == null) {
            System.out.println("Кандидат не найден");
            return;
        }
        if (database.deleteCandidate(candidateIndex)) {
            System.out.println("Кандидат удален");
        } else {
            System.out.println("Ошибка удаления кандидата");
        }
    }

}


