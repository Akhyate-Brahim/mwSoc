package com.server.main.command;

import com.server.io.InputService;
import com.server.io.OutputService;
import com.server.adminApp.AdminApp;
import com.common.candidate.Candidate;
import com.server.user.User;

public class Menu {
    private AdminApp adminApp;
    private OutputService outputService;
    private InputService inputService;

    public Menu(AdminApp adminApp, OutputService outputService, InputService inputService) {
        this.adminApp = adminApp;
        this.outputService = outputService;
        this.inputService = inputService;
    }

    public void display() {
        int choice;

        while (true) {
            outputService.printMainMenu();
            choice = inputService.getUserOption();

            switch (choice) {
                case 1:
                    outputService.printUseSerializedObjects();
                    return;
                case 2:
                    modifyDataMenu();
                    break;
                case 3:
                    outputService.printAllInformation(adminApp);
                    inputService.waitForEnter();
                    break;
                case 0:
                    inputService.close();
                    return;
                default:
                    outputService.printInvalidOption();
            }
        }
    }

    private void modifyDataMenu() {
        int modifyChoice;

        while (true) {
            outputService.printModificationMenu();
            modifyChoice = inputService.getUserOption();

            if (modifyChoice == 0) {
                return;
            }

            handleModifyChoice(modifyChoice);
        }
    }

    private void handleModifyChoice(int choice) {
        switch (choice) {
            case 1:
                User newUser = inputService.getNewUser();
                new AddUserCommand(adminApp, newUser.getStudentNumber(), newUser.getPassword()).execute();
                break;
            case 2:
                int studentNumber = inputService.getStudentNumber();
                new DeleteUserCommand(adminApp, studentNumber).execute();
                break;
            case 3:
                Candidate newCandidate = inputService.getNewCandidate();
                new AddCandidateCommand(adminApp, newCandidate).execute();
                break;
            case 4:
                int rank = inputService.getCandidateRank();
                new DeleteCandidateCommand(adminApp, rank).execute();
                break;
            case 5:
                new DeleteAllUsersCommand(adminApp).execute();
                break;
            case 6:
                new DeleteAllCandidatesCommand(adminApp).execute();
                break;
            default:
                outputService.printInvalidOption();
        }
    }
}
