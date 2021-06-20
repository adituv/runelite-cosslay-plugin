package com.github.adituv.cosslayplugin;

import com.github.adituv.cosslayplugin.model.KitColor;
import javax.inject.Inject;
import javax.inject.Provider;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.CommandExecuted;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "CosSlay"
)
public class CosslayPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private Provider<FakeDialogInput> fakeDialogInputProvider;

	@Subscribe
	private void onCommandExecuted(CommandExecuted e)
	{

		int[] equipOverrides = new int[] {ItemID.MASK_OF_BALANCE + 512, -1, -1, -1, -1, -1, -1, -1, 256, -1, -1, -1};
		int[] colorOverrides = new int[] {-1, -1, -1, -1, KitColor.COLOR_BLACK.getSkinId()};

		if (e.getCommand().equalsIgnoreCase("cosmo"))
		{
			FakeDialogInput dialog1 = fakeDialogInputProvider.get()
				.type(DialogType.DIALOG_HEAD_LEFT)
				.player()
				.speakerName("Cosmo")
				.overrides(equipOverrides, colorOverrides)
				.message("You are not ready.  I will contact you when it is time.");

			FakeDialogInput dialog2 = fakeDialogInputProvider.get()
				.type(DialogType.DIALOG_HEAD_RIGHT)
				.player()
				.message("Well, that was strange.");

			dialog1.next(dialog2);

			dialog1.build();
		}
	}
}
