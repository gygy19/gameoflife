package org.gameofthelife.server.network.handler.reflexion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author jguyet
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandlerController {
	public int value();
}
