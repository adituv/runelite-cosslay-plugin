package com.github.adituv.cosslayplugin;

import com.github.adituv.cosslayplugin.model.KitColor;
import javax.inject.Inject;
import javax.inject.Provider;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import static net.runelite.api.KeyCode.KC_SHIFT;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.ItemQuantityMode;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
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
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private Provider<FakeDialogInput> fakeDialogInputProvider;

	private boolean shouldOverrideDialog = true;
	private FakeDialogChain cosmoDialogChain;
	private boolean equipmentInterfaceOverridden = false;

	@Override
	protected void startUp() throws Exception
	{
		cosmoDialogChain = createCosmoDialogChain();
	}

	private FakeDialogChain createCosmoDialogChain()
	{
		final int FAKEHASH_COSMO = 2;

		int[] cosmoEquips = new int[]{ItemID.MASK_OF_BALANCE + 512, -1, -1, -1, -1, -1, -1, -1, 256, -1, -1, -1};
		int[] cosmoColors = new int[]{-1, -1, -1, -1, KitColor.COLOR_BLACK.getSkinId()};

		FakeDialogInput dialog1 = fakeDialogInputProvider.get()
			.type(DialogType.DIALOG_HEAD_LEFT)
			.player()
			.speakerName("Cosmo")
			.overrides(cosmoEquips, cosmoColors)
			.fakePlayerHash(FAKEHASH_COSMO)
			.message("You are not ready.  I will contact you when it is<br>time.");

		FakeDialogInput dialog2 = fakeDialogInputProvider.get()
			.type(DialogType.DIALOG_HEAD_RIGHT)
			.player()
			.message("Well, that was strange.");

		FakeDialogChain cosmoChain = new FakeDialogChain(chatboxPanelManager);
		cosmoChain.append(dialog1);
		cosmoChain.append(dialog2);

		return cosmoChain;
	}

	@Subscribe
	private void onInteractingChanged(InteractingChanged e)
	{
		if (e.getSource() == client.getLocalPlayer() && e.getTarget() != null)
		{
			log.debug("Setting dialog override trigger");
			shouldOverrideDialog = true;
		}
	}

	@Subscribe
	private void onWidgetLoaded(WidgetLoaded e)
	{
		final int WORN_EQUIPMENT_GROUP_ID = 84;

		if (e.getGroupId() == WidgetID.DIALOG_NPC_GROUP_ID
			|| e.getGroupId() == WidgetID.DIALOG_PLAYER_GROUP_ID
			|| e.getGroupId() == WidgetID.DIALOG_OPTION_GROUP_ID)
		{
			if (shouldOverrideDialog)
			{
				shouldOverrideDialog = false;

				cosmoDialogChain.show();
			}
		}
		else if (equipmentInterfaceOverridden && e.getGroupId() == WORN_EQUIPMENT_GROUP_ID)
		{
			clientThread.invokeLater(this::setupCosslayInterface);
		}
	}

	private void setupCosslayInterface()
	{
		client.getWidget(84, 4).setHidden(true);
		client.getWidget(84, 21).setHidden(true);
		client.getWidget(84, 43).setHidden(true);
		client.getWidget(84, 48).setHidden(true);
		client.getWidget(84, 49).setHidden(true);

		{
			Widget head = client.getWidget(84, 10);
			head.setName("");
			head.getChild(1).setHidden(true);
			head.getChild(2).setHidden(false);
		}
		{
			Widget cape = client.getWidget(84, 11);
			cape.setName("<col=ff9040>Red cape</col>");
			cape.getChild(1).setHidden(false);
			cape.getChild(1).setItemId(ItemID.RED_CAPE);
			cape.getChild(1).setItemQuantityMode(ItemQuantityMode.NEVER);
			cape.getChild(2).setHidden(true);
		}
		{
			Widget neck = client.getWidget(84, 12);
			neck.setName("");
			neck.getChild(1).setHidden(true);
			neck.getChild(2).setHidden(false);
		}
		{
			Widget weapon = client.getWidget(84, 13);
			weapon.setName("<col=ff9040>Black 2h sword</col>");
			weapon.getChild(1).setHidden(false);
			weapon.getChild(1).setItemId(ItemID.BLACK_2H_SWORD);
			weapon.getChild(1).setItemQuantityMode(ItemQuantityMode.NEVER);
			weapon.getChild(2).setHidden(true);
			weapon.revalidate();
		}
		{
			Widget torso = client.getWidget(84, 14);
			torso.setName("<col=ff9040>Khazard armour</col>");
			torso.getChild(0).setSpriteId(179);
			torso.getChild(1).setHidden(false);
			torso.getChild(1).setItemId(ItemID.KHAZARD_ARMOUR);
			torso.getChild(1).setItemQuantityMode(ItemQuantityMode.NEVER);
			torso.getChild(2).setHidden(true);
		}
		{
			Widget shield = client.getWidget(84, 15);
			shield.setName("");
			shield.getChild(1).setHidden(true);
			shield.getChild(2).setHidden(false);
		}
		{
			Widget legs = client.getWidget(84, 16);
			legs.setName("<col=ff9040>Iron platelegs</col>");
			legs.getChild(1).setHidden(false);
			legs.getChild(1).setItemId(ItemID.IRON_PLATELEGS);
			legs.getChild(1).setItemQuantityMode(ItemQuantityMode.NEVER);
			legs.getChild(2).setHidden(true);
		}
		{
			Widget gloves = client.getWidget(84, 17);
			gloves.setName("<col=ff9040>Leather gloves</col>");
			gloves.getChild(1).setHidden(false);
			gloves.getChild(1).setItemId(ItemID.LEATHER_GLOVES);
			gloves.getChild(1).setItemQuantityMode(ItemQuantityMode.NEVER);
			gloves.getChild(2).setHidden(true);
		}
		{
			Widget boots = client.getWidget(84, 18);
			boots.setName("<col=ff9040>Leather boots</col>");
			boots.getChild(1).setHidden(false);
			boots.getChild(1).setItemId(ItemID.LEATHER_BOOTS);
			boots.getChild(1).setItemQuantityMode(ItemQuantityMode.NEVER);
			boots.getChild(2).setHidden(true);
		}
		{
			Widget ring = client.getWidget(84, 19);
			ring.setName("");
			ring.getChild(1).setHidden(true);
			ring.getChild(2).setHidden(false);
		}
		{
			Widget ammo = client.getWidget(84, 20);
			ammo.setName("");
			ammo.getChild(1).setHidden(true);
			ammo.getChild(2).setHidden(false);
		}

		// Inventory - skull

		client.getWidget(84, 3).getChild(1).setText("Easy Tier - General Khazard");

		{
			Widget w = client.getWidget(84, 8);
			w.setOriginalWidth(135);
			w.revalidate();
		}


	}

	@Subscribe
	private void onMenuOptionClicked(MenuOptionClicked e)
	{
		int widgetGroupId = WidgetInfo.TO_GROUP(e.getWidgetId());
		int widgetChildId = WidgetInfo.TO_CHILD(e.getWidgetId());
		String option = e.getMenuOption();

		if (equipmentInterfaceOverridden && widgetGroupId == 84 && widgetChildId >= 10 && widgetChildId <= 20)
		{
			if (e.getMenuAction() == MenuAction.CC_OP)
			{
				e.consume();
			}
		}

		if (option.equals("View equipment stats"))
		{
			equipmentInterfaceOverridden = client.isKeyPressed(KC_SHIFT);
		}

		// Close custom chatbox panels when the player does anything requiring navigation.  This is to
		// emulate the OnDialogAbortListener which doesn't trigger in all situations
		int VIEWPORT_GROUP_ID = 0;
		if (widgetGroupId == VIEWPORT_GROUP_ID && !option.equals("Cancel") && !option.equals("Examine"))
		{
			cosmoDialogChain.abort();
		}
	}

	@Subscribe
	private void onGameTick(GameTick e)
	{
		cosmoDialogChain.update();
	}

	private boolean shiftPressedLast;

	@Subscribe
	private void onClientTick(ClientTick e)
	{
		if (client.getVar(VarClientInt.INVENTORY_TAB) == 4)
		{
			boolean shiftPressed = client.isKeyPressed(KC_SHIFT);

			Widget button = client.getWidget(387, 2);

			if (button == null)
			{
				log.error("onClientTick: button is null");
				return;
			}

			if (shiftPressed && !shiftPressedLast)
			{
				shiftPressedLast = true;
				button.setSpriteId(216);
				button.setOriginalWidth(24);
				button.setOriginalHeight(24);
				button.setOriginalX(button.getOriginalX() + 4);
				button.setOriginalY(button.getOriginalY() + 4);
				button.revalidate();
			}
			else if (!shiftPressed && shiftPressedLast)
			{
				shiftPressedLast = false;
				button.setSpriteId(675);
				button.setOriginalWidth(32);
				button.setOriginalHeight(32);
				button.setOriginalX(button.getOriginalX() - 4);
				button.setOriginalY(button.getOriginalY() - 4);
				button.revalidate();
			}
		}
	}

	@Subscribe
	private void onMenuEntryAdded(MenuEntryAdded e)
	{
		if (!equipmentInterfaceOverridden
			|| e.getActionParam1() < WidgetInfo.PACK(84, 10)
			|| e.getActionParam1() > WidgetInfo.PACK(84, 20))
		{
			return;
		}

		if (e.getOption().equals("Remove"))
		{
			MenuEntry[] entries = client.getMenuEntries();
			MenuEntry[] newEntries = new MenuEntry[entries.length - 1];
			System.arraycopy(entries, 0, newEntries, 0, entries.length - 1);
			client.setMenuEntries(newEntries);
		}
	}
}
