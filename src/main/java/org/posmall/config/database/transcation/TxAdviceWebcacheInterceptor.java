package org.posmall.config.database.transcation;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Collections;

/**
 * Created by USER on 2017-12-28.
 */
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class TxAdviceWebcacheInterceptor {
    private static final String TX_METHOD_NAME = "*";
    private static final int TX_METHOD_TIMEOUT = 1000 * 10;
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* org.posmall.service.*Service.insert*(..)) || execution(* org.posmall.service.*Service.update*(..)) || execution(* org.posmall.service.*Service.save*(..))  || execution(* org.posmall.service.*Service.delete*(..))";

    @Qualifier("webcacheTransactionManager")
    private PlatformTransactionManager webcacheTransactionManager;

    @Bean(name = "txWebcacheAdvice")
    public TransactionInterceptor txWebcacheAdvice() {
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(TX_METHOD_NAME);
        transactionAttribute.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        transactionAttribute.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
        transactionAttribute.setTimeout(TX_METHOD_TIMEOUT);
        source.setTransactionAttribute(transactionAttribute);

        TransactionInterceptor txAdvice = new TransactionInterceptor(webcacheTransactionManager, source);
        return txAdvice;
    }

    @Bean(name = "txWebcacheAdviceAdvisor")
    public Advisor txWebcacheAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txWebcacheAdvice());
    }
}
