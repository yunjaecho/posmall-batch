package org.posmall.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

/**
 * Created by USER on 2017-12-26.
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class TxAdvicePosmallInterceptor {
    private static final String TX_METHOD_NAME = "*";
    private static final int TX_METHOD_TIMEOUT = 1000 * 10;
    private static final String AOP_POINTCUT_EXPRESSION_POSMALL = "execution(* org.posmall.service.*Service.insert*(..)) || execution(* org.posmall.service.*Service.update*(..)) || execution(* org.posmall.service.*Service.save*(..))  || execution(* org.posmall.service.*Service.delete*(..))";
    //private static final String AOP_POINTCUT_EXPRESSION_WEBCACGE = "execution(* org.posmall.service.*Service.*Webcache(..))";

    @Qualifier("posmallTransactionManager")
    private PlatformTransactionManager posmallTransactionManager;

    @Qualifier("webcacheTransactionManager")
    private PlatformTransactionManager webcacheTransactionManager;

    @Bean(name = "txPosmallAdvice")
    public TransactionInterceptor txPosmallAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(TX_METHOD_NAME);
        transactionAttribute.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        transactionAttribute.setTimeout(TX_METHOD_TIMEOUT);
        source.setTransactionAttribute(transactionAttribute);
        TransactionInterceptor txAdvice = new TransactionInterceptor(posmallTransactionManager, source);
        return txAdvice;
    }

    @Bean(name = "txPosmallAdviceAdvisor")
    public Advisor txPosmallAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION_POSMALL);
        return new DefaultPointcutAdvisor(pointcut, txPosmallAdvice());
    }

    @Bean(name = "txWebcacheAdvice")
    public TransactionInterceptor txWebcacheAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(TX_METHOD_NAME);
        transactionAttribute.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        transactionAttribute.setTimeout(TX_METHOD_TIMEOUT);
        source.setTransactionAttribute(transactionAttribute);
        TransactionInterceptor txAdvice = new TransactionInterceptor(webcacheTransactionManager, source);
        return txAdvice;
    }

    @Bean(name = "txWebcacheAdviceAdvisor")
    public Advisor txWebcacheAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION_POSMALL);
        return new DefaultPointcutAdvisor(pointcut, txWebcacheAdvice());
    }
}
