package org.gameofthelife.client.network.handler.reflexion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author jguyet
 * annotation for reflexion loaded on runtime
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandlerController {
	public int value();
}
