package org.gameofthelife.server.handler.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.client.TcpClient;
import org.gameofthelife.server.network.handler.reflexion.ClassHandlerController;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.reflections.Reflections;

public class ServerMessageHandler {

	public static Map<Integer, Method> messagesHandler = new HashMap<Integer, Method>();

	static//un seul chargement
	{
		Reflections reflections = new Reflections("org.gameofthelife.server.handler");

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
	
    public boolean handleMessage(TcpClient client, NetworkMessage message) {

    	try
    	{   
	        if (messagesHandler.containsKey(message.getTypeId()))
	        {
	        	Method m = messagesHandler.get(message.getTypeId());
	        	
	        	m.setAccessible(true);
	        	boolean result = (Boolean) m.invoke(Boolean.class, client, message);
	        	return (result);
	        }
    	} catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	return (false);
    }
}
