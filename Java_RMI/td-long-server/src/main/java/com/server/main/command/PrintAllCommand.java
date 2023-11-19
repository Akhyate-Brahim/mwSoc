package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.server.io.OutputService;

public class PrintAllCommand implements Command {
    private AdminApp adminApp;
    private OutputService outputService;

    public PrintAllCommand(AdminApp adminApp, OutputService outputService) {
        this.adminApp = adminApp;
        this.outputService = outputService;
    }

    @Override
    public void execute() {
        outputService.printAllInformation(adminApp);
    }
}
