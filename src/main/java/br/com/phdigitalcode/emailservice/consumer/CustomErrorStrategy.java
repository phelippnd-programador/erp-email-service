package br.com.phdigitalcode.emailservice.consumer;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;

import br.com.phdigitalcode.emailservice.exceptions.EmailConfiguracaoException;

public class CustomErrorStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy{
@Override
public boolean isFatal(Throwable t) {
	boolean fatal = t.getCause() instanceof EmailConfiguracaoException;
	return fatal;
}
}
