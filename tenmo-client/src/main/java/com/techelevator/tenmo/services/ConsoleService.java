package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("6: View a transfer by ID");
        System.out.println("7: Approve or reject a pending transfer: ");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
    public BigDecimal promptForAmount(String prompt) {
        BigDecimal amount = null;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                amount = new BigDecimal(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid amount:");
            }
        }
        return amount;
    }
    public int promptForUserId(String prompt) {
        int userId = -1;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                userId = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid user ID:");
            }
        }
        return userId;
    }
    public void printUsers(List<User> users) {
        System.out.println("Available Users:");
        for (User user : users) {
            System.out.println(user.getId() + ": " + user.getUsername());
        }
    }
    public void printTransfers(List<Transfer> transfers) {
        System.out.println("Transfer History:");
        for (Transfer transfer : transfers) {
            System.out.println(transfer.getTransferId() + ": " + transfer.getTransferTypeId()+": "+transfer.getTransferStatusId()
            +": "+transfer.getAccountFrom()+": "+transfer.getAccountTo()+": "+transfer.getAmount().doubleValue());
        }
    }
    public void printPendingTransfers(List<Transfer> transfers) {
        System.out.println("Pending Transfers:");
        for (Transfer transfer : transfers) {
            System.out.println(transfer.getTransferId() + ": " + transfer.getTransferTypeId()+": "+transfer.getTransferStatusId()
                    +": "+transfer.getAccountFrom()+": "+transfer.getAccountTo()+": "+transfer.getAmount().doubleValue());
        }
    }


}
