package com.github.adituv.cosslayplugin;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.SpriteID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetType;

@Singleton
@Slf4j
public final class CosslayInterface
{
	// region Widget IDs
	public static final int WORN_ITEMS_GROUP_ID = 84;
	private static final int WORN_ITEMS_ROOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 0);
	private static final int WORN_ITEMS_WINDOW_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 3);
	private static final int WORN_ITEMS_PLAYER_MODEL_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 4);
	private static final int WORN_ITEMS_MIDDLE_BAR_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 5);
	private static final int WORN_ITEMS_LEFT_BAR_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 6);
	private static final int WORN_ITEMS_RIGHT_BAR_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 7);
	private static final int WORN_ITEMS_LOWER_BAR_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 8);
	private static final int WORN_ITEMS_UPPER_BAR_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 9);
	private static final int WORN_ITEMS_HEAD_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 10);
	private static final int WORN_ITEMS_CAPE_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 11);
	private static final int WORN_ITEMS_NECK_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 12);
	private static final int WORN_ITEMS_WEAPON_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 13);
	private static final int WORN_ITEMS_TORSO_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 14);
	private static final int WORN_ITEMS_SHIELD_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 15);
	private static final int WORN_ITEMS_LEGS_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 16);
	private static final int WORN_ITEMS_GLOVES_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 17);
	private static final int WORN_ITEMS_BOOTS_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 18);
	private static final int WORN_ITEMS_RING_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 19);
	private static final int WORN_ITEMS_AMMO_SLOT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 20);
	private static final int WORN_ITEMS_STAT_BONUS_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 21);
	private static final int WORN_ITEMS_SET_BONUS_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 43);
	private static final int WORN_ITEMS_WEIGHT_SPRITE_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 48);
	private static final int WORN_ITEMS_WEIGHT_TEXT_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 49);
	private static final int WORN_ITEMS_UNKNOWN_WIDGET_ID = WidgetInfo.PACK(WORN_ITEMS_GROUP_ID, 50);
	// endregion

	private final Client client;

	private Widget root;
	private Widget titleText;
	private WornItemSlot headSlot;
	private WornItemSlot capeSlot;
	private WornItemSlot neckSlot;
	private WornItemSlot weaponSlot;
	private WornItemSlot torsoSlot;
	private WornItemSlot shieldSlot;
	private WornItemSlot legsSlot;
	private WornItemSlot glovesSlot;
	private WornItemSlot bootsSlot;
	private WornItemSlot ringSlot;
	private WornItemSlot ammoSlot;

	private WornItemSlot[] inventorySlots;

	@Inject
	protected CosslayInterface(Client client)
	{
		this.client = client;
	}

	public void createInterface()
	{
		this.root = client.getWidget(WORN_ITEMS_ROOT_ID);

		if (this.root == null || this.root.isHidden())
		{
			log.error("Worn items interface not found");
			return;
		}

		{
			Widget w = client.getWidget(WORN_ITEMS_WINDOW_ID);

			if (w == null)
			{
				log.error("Worn items window widget not found");
				return;
			}


			this.titleText = w.getChild(1);
		}

		this.headSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_HEAD_SLOT_ID));
		this.capeSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_CAPE_SLOT_ID));
		this.neckSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_NECK_SLOT_ID));
		this.weaponSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_WEAPON_SLOT_ID));
		this.torsoSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_TORSO_SLOT_ID));
		this.shieldSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_SHIELD_SLOT_ID));
		this.legsSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_LEGS_SLOT_ID));
		this.glovesSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_GLOVES_SLOT_ID));
		this.bootsSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_BOOTS_SLOT_ID));
		this.ringSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_RING_SLOT_ID));
		this.ammoSlot = new WornItemSlot(client.getWidget(WORN_ITEMS_AMMO_SLOT_ID));

		cleanupOriginalInterface();
		hideInventoryItems();

		this.inventorySlots = createInventorySlots();
	}

	public void destroyInterface()
	{
		titleText = null;
		headSlot = null;
		capeSlot = null;
		neckSlot = null;
		weaponSlot = null;
		torsoSlot = null;
		shieldSlot = null;
		legsSlot = null;
		glovesSlot = null;
		bootsSlot = null;
		ringSlot = null;
		ammoSlot = null;
		inventorySlots = null;
	}

	private void cleanupOriginalInterface()
	{
		hideWidget(client.getWidget(WORN_ITEMS_PLAYER_MODEL_ID));
		hideWidget(client.getWidget(WORN_ITEMS_STAT_BONUS_ID));
		hideWidget(client.getWidget(WORN_ITEMS_SET_BONUS_ID));
		hideWidget(client.getWidget(WORN_ITEMS_WEIGHT_SPRITE_ID));
		hideWidget(client.getWidget(WORN_ITEMS_WEIGHT_TEXT_ID));
		hideWidget(client.getWidget(WORN_ITEMS_UNKNOWN_WIDGET_ID));

		for (int widgetID = WORN_ITEMS_HEAD_SLOT_ID; widgetID <= WORN_ITEMS_AMMO_SLOT_ID; widgetID++)
		{
			emptySlot(client.getWidget(widgetID));
		}
	}

	private void hideInventoryItems()
	{
		Widget inventory = client.getWidget(WidgetInfo.EQUIPMENT_INVENTORY_ITEMS_CONTAINER);

		if (inventory == null)
		{
			log.warn("Equipment inventory items container not found");
			return;
		}

		Widget[] items = inventory.getChildren();

		if (items == null)
		{
			log.warn("Equipment inventory items children not found");
			return;
		}

		for (Widget item : items)
		{
			hideWidget(item);
		}
	}

	private void hideWidget(Widget w)
	{
		if (w != null)
		{
			w.setHidden(true);
		}
	}

	private void emptySlot(Widget slot)
	{
		if (slot == null)
		{
			return;
		}

		slot.setName("");
		slot.getChild(1).setHidden(true);
		slot.getChild(2).setHidden(false);
	}

	private WornItemSlot[] createInventorySlots()
	{
		WornItemSlot[] wornItemSlots = new WornItemSlot[4];

		Widget inventoryBar = this.root.createChild(-1, WidgetType.GRAPHIC);
		inventoryBar.setSpriteId(SpriteID.IRON_RIVETS_VERTICAL);
		inventoryBar.setSpriteTiling(true);
		inventoryBar.setOriginalX(189);
		inventoryBar.setOriginalY(104);
		inventoryBar.setOriginalWidth(36);
		inventoryBar.setOriginalHeight(124);
		inventoryBar.revalidate();

		Widget bagBackground = this.root.createChild(-1, WidgetType.GRAPHIC);
		bagBackground.setSpriteId(SpriteID.EQUIPMENT_SLOT_TILE);
		bagBackground.setOriginalX(189);
		bagBackground.setOriginalY(68);
		bagBackground.setOriginalWidth(36);
		bagBackground.setOriginalHeight(36);
		bagBackground.revalidate();

		Widget bagSprite = this.root.createChild(-1, WidgetType.GRAPHIC);
		bagSprite.setSpriteId(SpriteID.TAB_INVENTORY);
		bagSprite.setOriginalX(188);
		bagSprite.setOriginalY(70);
		bagSprite.setOriginalWidth(36);
		bagSprite.setOriginalHeight(32);
		bagSprite.revalidate();

		for (int i = 0; i < 4; i++)
		{
			// dy = 39
			Widget background = this.root.createChild(-1, WidgetType.GRAPHIC);
			background.setSpriteId(SpriteID.EQUIPMENT_SLOT_TILE);
			background.setOriginalX(189);
			background.setOriginalY(107 + (39 * i));
			background.setOriginalWidth(36);
			background.setOriginalHeight(36);
			background.revalidate();

			Widget item = this.root.createChild(-1, WidgetType.GRAPHIC);
			item.setBorderType(1);
			item.setOriginalX(191);
			item.setOriginalY(109 + (39 * i));
			item.setOriginalWidth(36);
			item.setOriginalHeight(32);
			item.setHidden(true);
			item.revalidate();

			wornItemSlots[i] = new WornItemSlot(background, item);
		}

		return wornItemSlots;
	}
}
