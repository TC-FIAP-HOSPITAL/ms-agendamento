package com.ms.agendamento.entrypoints.controllers.handler;

import com.ms.agendamento.domain.domainService.exception.AgendamentoExistenteException;
import com.ms.agendamento.domain.domainService.exception.AgendamentoNaoExisteException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof AgendamentoNaoExisteException || ex instanceof AgendamentoExistenteException) {
            return GraphqlErrorBuilder.newError()
                    .message(ex.getMessage())
                    .errorType(ErrorType.BAD_REQUEST)
                    .path(env.getExecutionStepInfo().getPath())
                    .build();
        }
        return null; // outras exceções seguem o comportamento default
    }
}
