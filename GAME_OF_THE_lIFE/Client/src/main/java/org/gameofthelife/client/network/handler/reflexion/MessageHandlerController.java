package org.gameofthelife.client.network.handler.reflexion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MessageHandlerController {
	public int value();
}
