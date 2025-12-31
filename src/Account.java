import java.util.function.DoubleBinaryOperator;

import javax.print.attribute.standard.OutputBin;
import javax.swing.event.DocumentEvent;
import javax.swing.plaf.basic.BasicScrollPaneUI.ViewportChangeHandler;

public class Account {
    public static int noOfAccounts = 0;

    private double maxLoanLimit; // kullanicinin maximum sahip olabilecegi kredi miktari
    private double maxDebtLimit; // kullanicinin maximum sahip olabilecegi borc miktari
    private String accountType; // kullanicinin sahip olabilecegi hesap tipleri ornegin ogrenci hesabi
    private int ID;
    private String owner;
    private double balance;
    private String creationDate;
    private double loanAmount; // kullanicinin mevcut kredi miktari
    private int numberOfLoans; // kullanicinin aldigi kredi sayisi
    private double debt; // kullanicinin mevcut borc miktari
    private boolean isActive;
    

    public Account(String accountType, String owner, String creationDate){
        this.accountType = accountType;
        this.owner = owner;
        this.creationDate = creationDate;
        this.ID = ++noOfAccounts;
        this.balance = 0.0;
        this.loanAmount = 0.0;
        this.numberOfLoans = 0;
        this.debt = 0.0;
        this.isActive = true;

        if (accountType.equals("Student")){ // Equals yapma sebebimiz burası string değerlerinin eşit olup olmadığına baktığımız için.
            this.maxLoanLimit = 4000.0;
        }
        else if (accountType.equals("Personal")){
            this.maxLoanLimit = 2000.0;
        }
        else if (accountType.equals("Business")){
            this.maxLoanLimit = 100000.0;
        }
        else if (accountType.equals("Investment")){
            this.maxLoanLimit = 10000.0;
        }
        else {
            this.maxLoanLimit = 0.0;
        }

        this.maxDebtLimit = this.maxLoanLimit / 2;
    }

     public static int getNoOfAccounts() {
        return noOfAccounts;
    }

    public double getMaxLoanLimit() {
        return maxLoanLimit;
    }

    public double getMaxDebtLimit() {
        return maxDebtLimit;
    }

    public String getAccountType() {
        return accountType;
    }

    public int getID() {
        return ID;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getNumberOfLoans() {
        return numberOfLoans;
    }

    public double getDebt() {
        return debt;
    }

    public boolean isActive() {
        return isActive;
    } 

    // Para çekme işlemi
    public void withdrawCash(int amount){

        if(isActive == false){
            return;
        }

        if (balance >= amount){
            balance -= amount;
        }
        else
        {
                if ((amount - balance) + debt <= maxDebtLimit){
                    debt += (amount - balance);
                    balance = 0;
                }
                else {
                    System.out.println("Withdrawal failed. Insufficient balance.");
                    return;
                }
        }

        System.out.println("Cash Withdrawl of " + amount + " into account " + ID);
    }

    // para yatirma işlemi
    public void depositCash(int amount){
        if(isActive == false){
            return;
        }

        if(debt >= amount){
            debt -= amount;
        }
        else {
            balance += (amount - debt);
            debt = 0;
        }

        System.out.println("Cash Deposit of " + amount + " into account " + ID);
    }

    public void deactivate(){
        this.isActive = false;
        System.out.println("Account " + ID + " has been deactivated.");
    }

    // kullanici borcunun olup olmadiğini kontrol eder
    public boolean hasDebt(){
        return this.debt > 0;
    }


    // Hesaplar arasi para transferi
    public void transfer(Account destAccount, int amount){
        if(isActive == false || destAccount.isActive == false){
            return;
        }

        if (balance >= amount){
            balance -= amount;
        }
        else
        {
                if ((amount - balance) + debt < maxDebtLimit){
                    debt += ((amount - balance));
                    balance = 0;
                }        
                else 
                {
                    System.out.println("Withdrawal failed. Insufficient balance.");
                    return;
                }
        }

        if (destAccount.debt >= amount){
            destAccount.debt -= amount;
        }
        else {
            destAccount.balance += (amount - destAccount.debt);
            destAccount.debt = 0;
        }

        System.out.println("Transferred " + amount + " from account " + ID + " to account " + destAccount.ID);
    }

    // kredi talep etme islemi
    public void requestLoan(double loanAmount){
        if (isActive == false){
            return;
        }

        if (this.loanAmount + loanAmount <= maxLoanLimit){
            this.loanAmount += loanAmount;
            this.numberOfLoans++;
            this.balance += loanAmount;
            System.out.println("Loan of " + loanAmount + " approved for account " + ID);
        }
        else {
            System.out.println("Loan request denied. Exceeds maximum loan limit.");
        }
    }

    @Override
    public String toString() {
        return "*".repeat(30) + "\n" +
                "Account Information:\n" +
               "ID: " + ID + "\n" +
               "Owner: " + owner + "\n" +
               "Account Type: " + accountType + "\n" +
               "Creation Date: " + creationDate + "\n" +
               "Balance: " + balance + "\n" +
               "Loan Amount: " + loanAmount + "\n" +
               "Number of Loans: " + numberOfLoans + "\n" +
               "Debt: " + debt + "\n" +
               "Account Active: " + isActive + "\n" +
               "*".repeat(30);
    }

    public void activate(){
        this.isActive = true;
    }
}
