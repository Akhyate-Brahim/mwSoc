package client.io;

public class OutputService {

    public void printStudentNumber(int studentNumber) {
        System.out.println("Your student number: " + studentNumber);
    }

    public void printPassword(String password) {
        System.out.println("Your password: " + password); // Be careful with printing sensitive info in real applications
    }

    public void printLoginResult(boolean isSuccess) {
        if (isSuccess) {
            System.out.println("Correct info");
        } else {
            System.out.println("Incorrect info");
        }
    }
}

