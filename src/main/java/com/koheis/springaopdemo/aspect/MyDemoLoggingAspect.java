package com.koheis.springaopdemo.aspect;

import com.koheis.springaopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    @Around("execution(* com.koheis.springaopdemo.service.*.getFortune(..))")
    public Object aroundLoggingAspect(ProceedingJoinPoint theProceedingjoinPoint) throws Throwable {

        // print out which method we are advising on
        String method = theProceedingjoinPoint.getSignature().toShortString();
        System.out.println("\n======>>> Executing @Around on method: " + method);

        // get begin timestamp
        // long begin = System.currentTimeMillis();
        long begin = System.nanoTime();

        // now, Let's execute the method
        Object result = null;

        try {
            result = theProceedingjoinPoint.proceed();
        } catch (Exception exc) {
            // log the exception
            System.out.println(exc.getMessage());

            // rethrow exception
            throw exc;
        }

        // get end timestamp
        // long end = System.currentTimeMillis();
        long end = System.nanoTime();

        // compute duration and display it
        long duartion = end - begin;
        System.out.println("\n======> Duration: " + duartion + " nanoseconds");

        return result;
    }

    @After("execution(* com.koheis.springaopdemo.dao.AccountDAO.findAccounts(..))")
    public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
    // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n======>>> Executing @After (finally) on method: " + method);
    }


    @AfterThrowing(
            pointcut = "execution(* com.koheis.springaopdemo.dao.AccountDAO.findAccounts(..))",
            throwing = "theExc"
    )
    public void afterThrowingFindAccountsAdvice(JoinPoint theJoinPoint, Throwable theExc) {

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n======>>> Executing @AfterThrowing on method: " + method);

        // log the exception
        System.out.println("\n======>>> The Exception is: " + theExc);

    }


    // add anew advice for @AfterReturning on the findAccounts method
    @AfterReturning(
            pointcut = "execution(* com.koheis.springaopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n======>>> Executing @AfterReturning on method: " + method);

        // print out the results of the method call
        System.out.println("\n======>>> result is:" + result);

        // let's post-process the data ... let's modify it :-)

        // convert the account names to uppercase
        convertAccountNamesToUpperCase(result);

        System.out.println("\n======>>> result is:" + result);
    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        // loop through accounts
        for (Account tempAccount : result) {

            // get uppercase version of name
            String theUpperName = tempAccount.getName().toUpperCase();

            // update the name on the account
            tempAccount.setName(theUpperName);

        }


    }


    @Before("com.koheis.springaopdemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
        System.out.println("\n=========>>> Executing @Before advice on method");

        // display the method signature
        MethodSignature methodSignature = (MethodSignature) theJoinPoint.getSignature();
        System.out.println("Method: " + methodSignature);

        // display method arguments

        // get args
        Object[] args = theJoinPoint.getArgs();

        // loop through args
        for (Object tempArg : args) {
            System.out.println(tempArg);

            if (tempArg instanceof Account) {

                // downcast and print Account specific stuff
                Account theAccount = (Account) tempArg;
                System.out.println("account name: " + theAccount.getName());
                System.out.println("account level: " + theAccount.getLevel());
            }

        }


    }

}
