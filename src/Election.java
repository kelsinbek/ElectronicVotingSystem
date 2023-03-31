import Admin.AdminInterface;
import Vote.UserInterface;


import java.util.Scanner;

//Main функция нашей программы
public class Election {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пажаловать в программу!");

                System.out.println("1. Аккаунт администратора");
                System.out.println("2. Аккаунт пользователя");
                System.out.println("0. Выйти из программы");;
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Добро падаловать в аккаунт АДМИНА");
                        AdminInterface adminInterface = new AdminInterface();
                        Thread adminThread = new Thread(adminInterface::run);
                        adminThread.start();
                        break;
                    case 2:
                        System.out.println("Добро падаловать в аккаунт ПОЛЗОВАТЕЛЯ");
                        UserInterface userInterface = new UserInterface();
                        Thread voterThread = new Thread(userInterface::run);
                        voterThread.start();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Некорректный ввод");
                        break;
                }
            }



    }
