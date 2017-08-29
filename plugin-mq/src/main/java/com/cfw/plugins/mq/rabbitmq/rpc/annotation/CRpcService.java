package com.cfw.plugins.mq.rabbitmq.rpc.annotation;

/**
 * Created by Duskrain on 2017/8/27.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CRpcService {
}
