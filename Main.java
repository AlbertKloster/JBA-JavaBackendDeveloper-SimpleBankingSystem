package banking;

import banking.controller.LoginController;
import banking.data.Repository;

public class Main {
    public static void main(String[] args) {

        Repository repository = Repository.getInstance();
        repository.init(args[1]);

        try {
            LoginController.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}