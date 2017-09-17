package org.gameofthelife.client.network;

/**
 * @author jguyet
 *
 */
public interface NetworkType {

	/**
	 * return id from message
	 */
	public abstract int getTypeId();
	
	/**
	 * return name from message
	 */
	public abstract String getName();
	
	/**
	 * Deserialize message after reception
	 */
	public abstract void deserialize();
	
	/**
	 * serialize message before send
	 */
	public abstract void serialize();
}
