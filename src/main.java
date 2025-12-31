package src;
import javax.security.auth.login.AccountException;

public class main {
    public static void main(String[] args) {
   Account a1 = new Account("Investment", "Dana", "2025-01-01");
        
        a1.requestLoan(4000);
        a1.requestLoan(6001);
        a1.withdrawCash(9000);
        a1.depositCash(10000);
        System.out.println(a1);

        
    }
}
