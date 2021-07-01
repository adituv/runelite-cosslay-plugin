package com.github.adituv.cosslayplugin;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.callback.ClientThread;
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
}
