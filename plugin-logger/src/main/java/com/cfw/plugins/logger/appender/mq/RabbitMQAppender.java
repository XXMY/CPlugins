package com.cfw.plugins.logger.appender.mq;

import com.cfw.plugins.mq.rabbitmq.send.Sender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>Self defined appender, can send logger messages to RabbitMQ.</p>
 *
 * Created by Duskrain on 2017/8/3.
 */
@Plugin(name = "RabbitMQ", category = "Core", elementType = "appender", printObject = true)
public class RabbitMQAppender extends AbstractAppender {

    private static Sender sender;

    private static LinkedList<String> logMessage = new LinkedList<>();

    // For spring bean initialization.
    public RabbitMQAppender(){
        super(null,null,null);
    }

    /**
     * Constructor that defaults to suppressing exceptions.
     *
     * @param name   The Appender name.
     * @param filter The Filter to associate with the Appender.
     * @param layout The layout to use to format the event.
     */
    protected RabbitMQAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    /**
     * Constructor.
     *
     * @param name             The Appender name.
     * @param filter           The Filter to associate with the Appender.
     * @param layout           The layout to use to format the event.
     * @param ignoreExceptions If true, exceptions will be logged and suppressed. If false errors will be logged and
     */
    protected RabbitMQAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);

    }

    /**
     * Logs a LogEvent using whatever logic this Appender wishes to use. It is typically recommended to use a
     * bridge pattern not only for the benefits from decoupling an Appender from its implementation, but it is also
     * handy for sharing resources which may require some form of locking.
     *
     * @param event The LogEvent.
     */
    @Override
    public void append(LogEvent event) {
        String message = new String(getLayout().toByteArray(event));
        if(sender == null){
            logMessage.add(message);
            return;
        }

        while(logMessage.size() !=0){
            sender.send(logMessage.pollFirst());
        }

        sender.send(message);
    }

    @PluginFactory
    public static RabbitMQAppender createAppender(@PluginAttribute("name") String name,
                                                  @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                  @PluginElement("Layout") Layout layout,
                                                  @PluginElement("Filters") Filter filter){
        if (name == null) {
            LOGGER.error("No name provided for StubAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new RabbitMQAppender(name,filter,layout,ignoreExceptions);
    }

    public static void setSender(Sender sender) {
        RabbitMQAppender.sender = sender;
    }
}
