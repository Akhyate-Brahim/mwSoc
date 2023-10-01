package server.login;

import server.candidate.Candidate;
import server.candidate.TextPitch;
import server.candidate.VideoPitch;
import common.login.ILogin;
import server.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Login extends UnicastRemoteObject implements ILogin {
    private static final List<User> USERS = new ArrayList<>() {{
        add(new User(0, "password1"));
        add(new User(1, "password2"));
    }};
    private static final List<Candidate> CANDIDATES = new ArrayList<>() {{
        add(new Candidate("John", "Doe", Optional.of(new TextPitch("Hello, I am John Doe and...")) ));
        add(new Candidate("Jane", "Smith", Optional.of(new VideoPitch("http://example.com/jane"))));
    }};
    public Login() throws RemoteException {}

    @Override
    public boolean isSuccess(int studentNumber, String password) throws RemoteException {
        for (User user : USERS){
            if (studentNumber==user.getStudentNumber() && password.equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }
}
