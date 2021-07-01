package com.github.adituv.cosslayplugin;

import lombok.Getter;
import net.runelite.api.widgets.Widget;

@Getter
public class WornItemSlot
{
	private final Widget container;
	private final Widget background;
	private final Widget item;
	private final Widget icon;

	public WornItemSlot(Widget container)
	{
		this.container = container;

		if (container != null)
		{
			this.background = container.getChild(0);
			this.item = container.getChild(1);
			this.icon = container.getChild(2);
		}
		else
		{
			this.background = null;
			this.item = null;
			this.icon = null;
		}
	}

	public WornItemSlot(Widget background, Widget item)
	{
		this.container = null;
		this.background = background;
		this.item = item;
		this.icon = null;
	}
}
