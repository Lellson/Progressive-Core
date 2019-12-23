package de.lellson.progressivecore.misc;

public interface ITab {
	
	public static enum Tab {
		MATERIALS, BLOCKS, COMBAT, TOOLS, ACCESSORIES
	}
	
	public Tab getTab();

	public default boolean shouldShow() {
		return true;
	}
}
