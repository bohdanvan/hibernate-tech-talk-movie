package com.bvan.movie.util;

import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class TransactionTemplateHelper {

    private final PlatformTransactionManager transactionManager;

    public TransactionTemplateHelper(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void execute(Runnable action) {
        execute(TransactionDefinition.PROPAGATION_REQUIRED, action);
    }

    public <T> T execute(Supplier<T> action) {
        return execute(TransactionDefinition.PROPAGATION_REQUIRED, action);
    }

    public void executeInNewTransaction(Runnable action) {
        execute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, action);
    }

    public <T> T executeInNewTransaction(Supplier<T> action) {
        return execute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, action);
    }

    private void execute(int propagationBehavior, Runnable action) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(propagationBehavior);
        transactionTemplate.execute(status -> {
            action.run();
            return null;
        });
    }

    private <T> T execute(int propagationBehavior, Supplier<T> action) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(propagationBehavior);
        return transactionTemplate.execute(status -> action.get());
    }
}
