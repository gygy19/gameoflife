package org.gameofthelife.server.network.handler.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gameofthelife.server.network.NetworkMessage;
import org.gameofthelife.server.network.handler.reflexion.MessageHandlerController;
import org.gameofthelife.server.network.messages.DefaultNetworkMessage;
import org.reflections.Reflections;

/**
 * @author jguyet
 *
 */
public class MessageFactoryImplementation {

	public static Map<Integer, Class<?>> messages = new HashMap<Integer, Class<?>>();

	/**
	 * load all class child of NetworkMessage on org.gameofthelife.server.network.messages with MessageHandlerController anotation
	 */
	static//un seul chargement
	{
		Reflections reflections = new Reflections("org.gameofthelife.server.network.messages");

		Set<Class<? extends NetworkMessage>> allClasses = reflections.getSubTypesOf(NetworkMessage.class);
		
		for (Class<?> c : allClasses)
		{
			Annotation[] annots = c.getAnnotations();
			
			if (annots.length < 0)
				continue ;
			for (Annotation a : annots)
			{
				if (!(a instanceof MessageHandlerController))
					continue ;
				messages.put(((MessageHandlerController)a).value(), c);
			}
		}
	}
	
	/**
	 * check if messages contains method with annotation value == message.getTypeId()<br>
	 * return result newInstance
	 * if Message doesn't exist return null
	 * @param message
	 * @return
	 */
	public NetworkMessage createMessage(DefaultNetworkMessage message) {

    	NetworkMessage result = null;
    	
    	try
		{
			if (messages.containsKey(message.getTypeId()))
			{
				Constructor<?>[] contructors = messages.get(message.getTypeId()).getConstructors();
				
				for (Constructor<?> c : contructors)
				{
					if (c.getGenericParameterTypes().length != 1)
						continue ;
					c.setAccessible(true);
					result = (NetworkMessage)c.newInstance(message.getData());
					break ;
				}
				System.out.println("Receive [" + result.getTypeId() + "] \"" + result.getName() + "\"");
			}
			else
			{
	    		return (null);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return (null);
		}
        return (result);
    }
	
}
