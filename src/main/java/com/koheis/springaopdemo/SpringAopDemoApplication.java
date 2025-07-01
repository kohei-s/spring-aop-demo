package com.koheis.springaopdemo;

import com.koheis.springaopdemo.dao.AccountDAO;
import com.koheis.springaopdemo.dao.MembershipDAO;
import com.koheis.springaopdemo.service.TrafficFortuneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SpringAopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAopDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AccountDAO theAccountDAO,
                                               MembershipDAO theMembershipDAO,
                                               TrafficFortuneService theTrafficFortuneService) {

        return runner -> {
            // demoTheBeforeAdvice(theAccountDAO, theMembershipDAO);
            // demoTheAfterReturningAdvice(theAccountDAO);
            // demoTheAfterThrowingAdvice(theAccountDAO);
            // demoTheAfterAdvice(theAccountDAO);
            // demoTheAroundAdvice(theTrafficFortuneService);
            // demoTheAroundAdviceHandleException(theTrafficFortuneService);

            demoTheAroundAdviceRethrowException(theTrafficFortuneService);
        };
    }

    private void demoTheAroundAdviceRethrowException(TrafficFortuneService theTrafficFortuneService) {
        System.out.println("\n======>>> Main Program: demoTheAroundAdviceRethrowException");
        System.out.println("Calling getFortune()");

        boolean tripWire = true;

        String data = theTrafficFortuneService.getFortune(tripWire);
        System.out.println("\nMy fortune is: " + data);
        System.out.println("Finished");
    }

    private void demoTheAroundAdviceHandleException(TrafficFortuneService theTrafficFortuneService) {

        System.out.println("\n======>>> Main Program: demoTheAroundAdviceHandeException");
        System.out.println("Calling getFortune()");

        boolean tripWire = true;

        String data = theTrafficFortuneService.getFortune(tripWire);
        System.out.println("\nMy fortune is: " + data);
        System.out.println("Finished");
    }

    private void demoTheAroundAdvice(TrafficFortuneService theTrafficFortuneService) {
        System.out.println("\n======>>> Main Program: demoTheAroundAdvice");
        System.out.println("Calling getFortune()");
        String data = theTrafficFortuneService.getFortune();
        System.out.println("\nMy fortune is: " + data);
        System.out.println("Finished");
    }

    private void demoTheAfterAdvice(AccountDAO theAccountDAO) {

        // call the method to find the accounts
        List<Account> theAccounts = null;

        try {
            //  add a boolean flag to simulate exceptions
            boolean tripWire = false;
            theAccounts = theAccountDAO.findAccounts(tripWire);
        } catch (Exception ex) {
            System.out.println("\n\nMain Program: ... caught exception: " + ex);
        }

        // display the accounts
        System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
        System.out.println("----");
        System.out.println(theAccounts);
        System.out.println("\n");

    }

    private void demoTheAfterThrowingAdvice(AccountDAO theAccountDAO) {

        // call the method to find the accounts
        List<Account> theAccounts = null;

        try {
            //  add a boolean flag to simulate exceptions
            boolean tripWire = true;
            theAccounts = theAccountDAO.findAccounts(tripWire);
        } catch (Exception ex) {
            System.out.println("\n\nMain Program: ... caught exception: " + ex);
        }

        // display the accounts
        System.out.println("\n\nMain Program: demoTheAfterThrowingAdvice");
        System.out.println("----");
        System.out.println(theAccounts);
        System.out.println("\n");
    }

    private void demoTheAfterReturningAdvice(AccountDAO theAccountDAO) {

        // call the method to find the accounts
        List<Account> theAccounts = theAccountDAO.findAccounts();

        // display the accounts
        System.out.println("\n\nMain Program: demoTheAfterReturningAdvice");
        System.out.println("----");
        System.out.println(theAccounts);
        System.out.println("\n");
    }

    private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {

        // call the business method
        Account myAccount = new Account();
        myAccount.setName("Madhu");
        myAccount.setLevel("Platinum");

        theAccountDAO.addAccount(myAccount, true);
        theAccountDAO.doWork();

        // call the accountDao getter/setter methods
        theAccountDAO.setName("foober");
        theAccountDAO.setServiceCode("silver");

        String name = theAccountDAO.getName();
        String serviceCode = theAccountDAO.getServiceCode();

        // call the membership business method
        theMembershipDAO.addSillyMember();
        theMembershipDAO.goToSleep();

    }

}
