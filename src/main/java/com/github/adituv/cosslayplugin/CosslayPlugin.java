package com.github.adituv.cosslayplugin;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "CosSlay"
)
public final class CosslayPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private CosslayInterface cosslayInterface;

	@Subscribe
	private void onWidgetLoaded(WidgetLoaded e)
	{
		clientThread.invokeLater(() ->
		{
			if (e.getGroupId() == CosslayInterface.WORN_ITEMS_GROUP_ID)
			{
				cosslayInterface.createInterface();
			}
		});
	}
}
