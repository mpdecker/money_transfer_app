package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 6){
                viewTransfer();
            }else if (menuSelection ==7){
                approveTransfer();
            }else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }
    private void viewTransfer(){
        TransferService transferService = new TransferService(currentUser);
        int transferId = consoleService.promptForInt("Please enter a valid transfer ID: ");
        Transfer transfer = transferService.getTransferById(transferId);
        System.out.println(transfer.getTransferId()+": "+transfer.getTransferTypeId()+": "+transfer.getTransferStatusId()
        +": "+transfer.getAccountFrom()+": "+transfer.getAccountTo()+": "+transfer.getAmount());
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        AccountService accountService = new AccountService(currentUser);
        BigDecimal balance = accountService.getBalance();
        System.out.println(balance);
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        TransferService transferService = new TransferService(currentUser);
        AccountService accountService = new AccountService(currentUser);
        Account userAccount = accountService.getAccount(currentUser.getUser().getId());
        List<Transfer> transfers = transferService.getSentOrReceivedTransfers(userAccount.getAccountId());
        if(transfers!=null){
            consoleService.printTransfers(transfers);
        }
		
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
        TransferService transferService = new TransferService(currentUser);
        List<Transfer> transfers = transferService.getPendingTransfers();
        if(transfers!=null){
            consoleService.printPendingTransfers(transfers);
        }
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        List<User> users = accountService.getAllUsers();
        if(users!=null){
            consoleService.printUsers(users);
            int toUser = consoleService.promptForUserId("Enter destination user ID: ");
            BigDecimal amount = consoleService.promptForAmount("Enter the amount to send: ");
            accountService.makeTransfer(currentUser.getUser().getId(), amount, toUser);
            TransferDto transferDto = new TransferDto();
            transferDto.setFromUserId(currentUser.getUser().getId());
            transferDto.setToUserId(toUser);
            transferDto.setAmount(amount);
            transferDto.setTransferStatusId(2);
            transferDto.setTransferTypeId(2);
            transferService.sendTransfer(transferDto);
        }
		
	}

	private void requestBucks() {
        // TODO Auto-generated method stub
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        List<User> users = accountService.getAllUsers();
        if (users != null) {
            consoleService.printUsers(users);
            int toUser = consoleService.promptForUserId("Enter destination user ID: ");
            BigDecimal amount = consoleService.promptForAmount("Enter the amount to request: ");
            TransferDto transferDto = new TransferDto();
            transferDto.setFromUserId(currentUser.getUser().getId());
            transferDto.setToUserId(toUser);
            transferDto.setAmount(amount);
            transferDto.setTransferStatusId(1);
            transferDto.setTransferTypeId(1);
            transferService.sendTransfer(transferDto);

        }
    }
    private void approveTransfer(){
        AccountService accountService = new AccountService(currentUser);
        TransferService transferService = new TransferService(currentUser);
        List<Transfer> pendingTransfers = transferService.getPendingTransfers();
        if(pendingTransfers!=null){
            consoleService.printPendingTransfers(pendingTransfers);
            int pendingTransfer = consoleService.promptForInt("Select transfer to approve/deny by transfer ID: ");
            Transfer transfer = transferService.getTransferById(pendingTransfer);
            User requester = accountService.getUserByAccountId(transfer.getAccountFrom());
            int approveOrDeny = consoleService.promptForInt("Enter 1 to approve transfer or 0 to deny: ");
            if(approveOrDeny==1){
                accountService.makeTransfer(currentUser.getUser().getId(), transfer.getAmount(), requester.getId());
                TransferDto transferDto = new TransferDto();
                transferDto.setFromUserId(currentUser.getUser().getId());
                transferDto.setToUserId(requester.getId());
                transferDto.setAmount(transfer.getAmount());
                transferDto.setTransferStatusId(2);
                transferDto.setTransferTypeId(2);
                transferService.sendTransfer(transferDto);
                System.out.println("Transfer Approved.");

            }else if(approveOrDeny==0){
                TransferDto transferDto = new TransferDto();
                transferDto.setFromUserId(currentUser.getUser().getId());
                transferDto.setToUserId(requester.getId());
                transferDto.setAmount(transfer.getAmount());
                transferDto.setTransferStatusId(3);
                transferDto.setTransferTypeId(1);
                transferService.sendTransfer(transferDto);
                System.out.println("Transfer Rejected.");

            }
        }
    }

}
