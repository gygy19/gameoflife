package org.gameofthelife.client.network;

public interface NetworkType {

	public abstract int getTypeId();
	
	public abstract String getName();
	
	public abstract void deserialize();
	
	public abstract void serialize();
}
