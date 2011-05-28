/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package genie;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import org.jboss.netty.util.internal.ConcurrentIdentityWeakKeyHashMap;

/**
 * A global variable that is local to a {@link Channel}.  Think of this as a
 * variation of {@link ThreadLocal} whose key is a {@link Channel} rather than
 * a {@link Thread#currentThread()}.  One difference is that you always have to
 * specify the {@link Channel} to access the variable.
 * <p>
 * Alternatively, you might want to use the
 * {@link ChannelHandlerContext#setAttachment(Object) ChannelHandlerContext.attachment}
 * property, which performs better.
 *
 * @author <a href="http://www.jboss.org/netty/">The Netty Project</a>
 * @author <a href="http://gleamynode.net/">Trustin Lee</a>
 * @version $Rev: 2080 $, $Date: 2010-01-26 18:04:19 +0900 (Tue, 26 Jan 2010) $
 *
 * @apiviz.stereotype utility
 */
public class PlayerLocal<T> {

    private final ConcurrentMap<Channel, T> map =
        new ConcurrentIdentityWeakKeyHashMap<Channel, T>();

    /**
     * Creates a {@link Channel} local variable.
     */
    public PlayerLocal() {
        super();
    }

    public Set<Channel> getChannels() {
        return map.keySet();
    }

    public Collection<T> getValues() {
        return map.values();
    }

    /**
     * Returns the initial value of the variable.  By default, it returns
     * {@code null}.  Override it to change the initial value.
     */
    protected T initialValue(@SuppressWarnings("unused") Channel channel) {
        return null;
    }

    /**
     * Returns the value of this variable.
     */
    public T get(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }

        T value = map.get(channel);
        if (value == null) {
            value = initialValue(channel);
            if (value != null) {
                T oldValue = setIfAbsent(channel, value);
                if (oldValue != null) {
                    value = oldValue;
                }
            }
        }
        return value;
    }

    /**
     * Sets the value of this variable.
     *
     * @return the old value. {@code null} if there was no old value.
     */
    public T set(Channel channel, T value) {
        if (value == null) {
            return remove(channel);
        } else {
            if (channel == null) {
                throw new NullPointerException("channel");
            }
            return map.put(channel, value);
        }
    }

    /**
     * Sets the value of this variable only when no value was set.
     *
     * @return {@code null} if the specified value was set.
     *         An existing value if failed to set.
     */
    public T setIfAbsent(Channel channel, T value) {
        if (value == null) {
            return get(channel);
        } else {
            if (channel == null) {
                throw new NullPointerException("channel");
            }
            return map.putIfAbsent(channel, value);
        }
    }

    /**
     * Removes the variable.
     *
     * @return the removed value. {@code null} if no value was set.
     */
    public T remove(Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        return map.remove(channel);
    }
}
