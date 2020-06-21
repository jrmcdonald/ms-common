package com.jrmcdonald.common.ext.spring.reactive.context.lifter;

import org.reactivestreams.Subscription;
import org.slf4j.MDC;

import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

import static com.jrmcdonald.common.ext.spring.reactive.context.model.ReactorContextKeys.CONTEXT_KEYS;

public class ReactiveContextLifter<T> implements CoreSubscriber<T> {

    CoreSubscriber<T> coreSubscriber;

    public ReactiveContextLifter(CoreSubscriber<T> coreSubscriber) {
        this.coreSubscriber = coreSubscriber;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        coreSubscriber.onSubscribe(subscription);
    }

    @Override
    public void onNext(T obj) {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onNext(obj);
    }

    @Override
    public void onError(Throwable t) {
        coreSubscriber.onError(t);
    }

    @Override
    public void onComplete() {
        coreSubscriber.onComplete();
    }

    @Override
    public Context currentContext() {
        return coreSubscriber.currentContext();
    }

    private void copyToMdc(Context context) {
        if (context.isEmpty()) {
            CONTEXT_KEYS.forEach(MDC::remove);
        } else {
            context.stream()
                   .filter(e -> CONTEXT_KEYS.contains(e.getKey().toString()))
                   .forEach(e -> MDC.put(e.getKey().toString(), e.getValue().toString()));
        }
    }

}