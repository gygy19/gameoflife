package org.gameofthelife.client.handler.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gameofthelife.client.network.NetworkMessage;
import org.gameofthelife.client.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.client.network.handler.reflexion.MessageHandlerController;
import org.reflections.Reflections;

/**
 * @author jguyet
 * Handle Server Message
 */
public class ClientMessageHandler {

	public static Map<Integer, Method> messagesHandler = new HashMap<Integer, Method>();

	/**
	 * load all method on directory org.gameofthelife.client.handler with MessageHandlerController anotation
	 */
	static//un seul chargement
	{
		Reflections reflections = new Reflections("org.gameofthelife.client.handler");

		Set<Class<?>> allclass = reflections.getTypesAnnotatedWith(ClassHandlerController.class);
		
		for (Class<?> c : allclass)
		{
			Method[] allMethod = c.getDeclaredMethods();
			
			for (Method m : allMethod)
			{
				Annotation[] annots = m.getAnnotations();
				
				if (annots.length < 0)
					continue ;
				for (Annotation a : annots)
				{
					if (!(a instanceof MessageHandlerController))
						continue ;
					messagesHandler.put(((MessageHandlerController)a).value(), m);
				}
			}
		}
	}
	
	/**
	 * check if messagesHandler contains method with annotation value == message.getTypeId()<br>
	 * return result on execution method
	 * if method doesn't exist return false
	 * @param message
	 * @return
	 */
    public boolean handleMessage(NetworkMessage message) {

    	try
    	{   
	        if (messagesHandler.containsKey(message.getTypeId()))
	        {
	        	Method m = messagesHandler.get(message.getTypeId());
	        	
	        	m.setAccessible(true);
	        	boolean result = (Boolean)m.invoke(Boolean.class, message);
	        	return (result);
	        }
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return (false);
    }
}
