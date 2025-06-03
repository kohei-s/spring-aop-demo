package com.koheis.springaopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

    // this is where we add all of our related advices for logging

    // an @Before advice
    // @Before("execution(public void com.koheis.springaopdemo.dao.AccountDAO.addAccount())")
    @Before("execution(public void add*())")
    public void beforeAddAccountAdvice() {
        System.out.println("\n=========>>> Executing @Before advice on method");
    }
}
