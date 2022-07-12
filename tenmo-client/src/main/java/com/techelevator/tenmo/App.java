package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private AuthenticatedUser currentUser;

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService = new UserService();


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
       // userService.setAuthToken(currentUser.getToken());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            userService.setAuthToken(currentUser.getToken());
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
            }  else if (menuSelection == 6) {
                viewTransferDetails();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		BigDecimal balance = userService.getBalance();
        System.out.println("Your current balance is: $" + balance);
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		TenmoTransfer[] transfers = userService.getAllTransfersByUser();

        if(transfers != null){
            System.out.println("-------------------------------------------");
            System.out.println("Transfers");
            System.out.println("TransferId          From/To          Amount");
            System.out.println("-------------------------------------------");
            for(TenmoTransfer transfer : transfers){
                if(userService.getCurrentUserAccountId() == transfer.getAccountTo()) {
                    System.out.println(transfer.getTransferId() + "          From: " + userService.getUserByAccountId(transfer.getAccountFrom()).getUsername() +
                            "          $" + transfer.getAmount());
                } else if(userService.getCurrentUserAccountId() == transfer.getAccountFrom()) {
                    System.out.println(transfer.getTransferId() + "          To: " + userService.getUserByAccountId(transfer.getAccountTo()).getUsername() +
                            "          $" + transfer.getAmount());
                }
            }
        } else {
            System.out.println("You have no transactions to display");
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
        System.out.println("Not completed");
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        User[] allUsers = userService.getAllUsers();
        for(User user: allUsers){
            System.out.println(user.getId() + " " + user.getUsername());
        }
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSenderAcct(userService.getTenmoAccount());
        int id = consoleService.promptForInt("Please enter a user id: ");
        BigDecimal amount = consoleService.promptForBigDecimal("Please enter an amount you'd like to send: ");
		TenmoAccount receiverAccount = userService.getAccountByUserId(id);
        if(id != currentUser.getUser().getId()) {
            transferDTO.setReceiverAcct(receiverAccount);
        }else{
            System.out.println("You can't send money to yourself. Select another user id");
        }
        if((amount.compareTo(new BigDecimal("0")) > 0) && (userService.getTenmoAccount().getBalance().compareTo(amount) > 0))  {
            transferDTO.setAmount(amount);
        }
        else {
            System.out.println("Amount must be positive");
        }
        userService.transfer(transferDTO);


	}

	private void requestBucks() {
		// TODO Auto-generated method stub
        System.out.println("Not completed");
	}

    private void viewTransferDetails() {

       int id = consoleService.promptForInt("Please enter a transaction id: ");
        TenmoTransfer transfer = userService.getTransferById(id);


        if(transfer != null) {
            System.out.println("-------------------------------------------");
            System.out.println("Transfer Details");
            System.out.println("-------------------------------------------");
            System.out.println("Id: " + transfer.getTransferId() );

            System.out.println("From: " + userService.getUserByAccountId(transfer.getAccountFrom()).getUsername());
            System.out.println("To: " + userService.getUserByAccountId(transfer.getAccountTo()).getUsername());
            System.out.println("Type: " + transfer.getTransferTypeId());
            System.out.println("Status: " + transfer.getTransferStatusId());
            System.out.println("Amount: " + transfer.getAmount());

        }
        else{
            System.out.println("Transaction id not valid");
        }

    }

}
