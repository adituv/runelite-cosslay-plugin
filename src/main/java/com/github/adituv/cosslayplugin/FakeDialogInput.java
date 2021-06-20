package com.github.adituv.cosslayplugin;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.FontID;
import net.runelite.api.FontTypeFace;
import net.runelite.api.Player;
import net.runelite.api.PlayerComposition;
import net.runelite.api.widgets.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.chatbox.ChatboxInput;
import net.runelite.client.game.chatbox.ChatboxPanelManager;

@Slf4j
public class FakeDialogInput extends ChatboxInput
{
	public static final int CHATHEAD_MODEL_ZOOM = 796;
	public static final int CHATHEAD_WIDTH = 32;
	public static final int CHATHEAD_HEIGHT = 32;
	public static final int TEXT_WIDTH = 380;
	public static final int TEXT_LINE_HEIGHT = 17;
	public static final int TEXT_BLOCK_HEIGHT = 67;

	private final ChatboxPanelManager chatboxPanelManager;
	private final Client client;
	private final ClientThread clientThread;
	private final EventBus eventBus;

	@Getter
	private int npcId;

	@Getter
	private boolean isPlayer;

	@Getter
	private int[] equipmentOverrides;

	@Getter
	private int[] colorOverrides;

	@Getter
	private DialogType dialogType;

	@Getter
	private String speakerName;

	@Getter
	private String message;

	@Getter
	private FakeDialogInput next;

	@Getter
	private Runnable onClose;

	private Widget chatHeadWidget;
	private Widget speakerNameWidget;
	private Widget messageWidget;
	private Widget continueWidget;

	private int[] storedEquipmentIds;
	private int[] storedColors;

	@Inject
	protected FakeDialogInput(Client client, ClientThread clientThread, ChatboxPanelManager chatboxPanelManager, EventBus eventBus)
	{
		this.chatboxPanelManager = chatboxPanelManager;
		this.client = client;
		this.clientThread = clientThread;
		this.eventBus = eventBus;

		this.npcId = -1;
		this.isPlayer = false;
		this.equipmentOverrides = null;
		this.colorOverrides = null;
		this.dialogType = null;
		this.speakerName = null;
		this.message = null;
		this.onClose = null;
	}

	public FakeDialogInput npc(int npcId)
	{
		this.npcId = npcId;
		this.isPlayer = false;
		return this;
	}

	public FakeDialogInput player()
	{
		this.isPlayer = true;
		this.npcId = -1;
		return this;
	}

	public FakeDialogInput speakerName(String speakerName)
	{
		this.speakerName = speakerName;
		return this;
	}

	public FakeDialogInput message(String message)
	{
		this.message = message;
		return this;
	}

	public FakeDialogInput type(DialogType type)
	{
		this.dialogType = type;
		return this;
	}

	public FakeDialogInput overrides(int[] equipmentOverrides, int[] colorOverrides)
	{
		this.equipmentOverrides = equipmentOverrides;
		this.colorOverrides = colorOverrides;
		return this;
	}

	public FakeDialogInput next(FakeDialogInput next)
	{
		this.next = next;
		return this;
	}

	public FakeDialogInput onClose(Runnable onClose)
	{
		this.onClose = onClose;
		return this;
	}

	public FakeDialogInput build()
	{
		if (this.npcId == -1 ^ this.isPlayer)
		{
			throw new IllegalStateException("Exactly one of NPC or Player must be set");
		}
		if (this.message == null)
		{
			throw new IllegalStateException("Message must be set");
		}
		if (this.dialogType == null)
		{
			throw new IllegalStateException("Dialog type must be set");
		}
		if (this.equipmentOverrides != null && this.equipmentOverrides.length != 12)
		{
			throw new IllegalStateException("Equipment overrides must be null or of length 12");
		}
		if (this.colorOverrides != null && this.colorOverrides.length != 5)
		{
			throw new IllegalStateException("Color overrides must be null or of length 5");
		}

		chatboxPanelManager.openInput(this);
		return this;
	}

	@Override
	protected void open()
	{
		Widget container = chatboxPanelManager.getContainerWidget();

		createChatHead(container);
		createSpeakerName(container);
		createContinueButton(container);
		createDialogText(container);
	}

	private void createDialogText(Widget container)
	{
		messageWidget = container.createChild(-1, WidgetType.TEXT);
		messageWidget.setText(this.message);
		messageWidget.setXTextAlignment(WidgetTextAlignment.CENTER);
		messageWidget.setYTextAlignment(WidgetTextAlignment.CENTER);
		messageWidget.setFontId(FontID.QUILL_8);

		messageWidget.setOriginalX(dialogType.getTextX());
		messageWidget.setOriginalY(dialogType.getTextY());
		messageWidget.setOriginalWidth(TEXT_WIDTH);
		messageWidget.setOriginalHeight(TEXT_BLOCK_HEIGHT);

		int lineHeight = getLineHeightForText(messageWidget);
		messageWidget.setLineHeight(lineHeight);

		messageWidget.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		messageWidget.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		messageWidget.setWidthMode(WidgetSizeMode.ABSOLUTE);
		messageWidget.setHeightMode(WidgetSizeMode.ABSOLUTE);


		messageWidget.revalidate();
	}

	private void createContinueButton(Widget container)
	{
		continueWidget = container.createChild(-1, WidgetType.TEXT);
		continueWidget.setText("Click here to continue");
		continueWidget.setFontId(FontID.QUILL_8);
		continueWidget.setXTextAlignment(WidgetTextAlignment.CENTER);
		continueWidget.setYTextAlignment(WidgetTextAlignment.TOP);
		continueWidget.setTextColor(0x0000FF);

		continueWidget.setOriginalX(dialogType.getTextX());
		continueWidget.setOriginalY(dialogType.getContinueY());
		continueWidget.setOriginalWidth(TEXT_WIDTH);
		continueWidget.setOriginalHeight(TEXT_LINE_HEIGHT);

		continueWidget.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		continueWidget.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		continueWidget.setWidthMode(WidgetSizeMode.ABSOLUTE);
		continueWidget.setHeightMode(WidgetSizeMode.ABSOLUTE);

		continueWidget.setAction(0, "Continue");

		continueWidget.setOnOpListener((JavaScriptCallback) e ->
		{
			log.debug("\"Click here to continue\" clicked.");

			this.handleContinue(container);
		});
		continueWidget.setOnMouseOverListener((JavaScriptCallback) e ->
			continueWidget.setTextColor(0xFFFFFF)
		);
		continueWidget.setOnMouseLeaveListener((JavaScriptCallback) e ->
			continueWidget.setTextColor(0xFF)
		);
		continueWidget.setOnKeyListener((JavaScriptCallback) e ->
			handleContinue(container)
		);
		continueWidget.setHasListener(true);

		continueWidget.revalidate();
	}

	private void handleContinue(Widget container)
	{
		if (next != null)
		{
			this.chatHeadWidget.setHidden(true);
			this.messageWidget.setHidden(true);
			this.speakerNameWidget.setHidden(true);
			this.continueWidget.setHidden(true);

			next.open();
			chatboxPanelManager.openInput(next);
			eventBus.unregister(this);
		}
		else
		{
			chatboxPanelManager.close();
		}
	}

	private void createSpeakerName(Widget container)
	{
		if (this.speakerName == null)
		{
			if (this.isPlayer)
			{
				Player p = client.getLocalPlayer();

				if (p == null)
				{
					this.speakerName = "<PLAYER>";
				}
				else
				{
					this.speakerName = p.getName();
				}
			}
			else
			{
				this.speakerName = client.getNpcDefinition(this.npcId).getName();
			}
		}

		speakerNameWidget = container.createChild(-1, WidgetType.TEXT);
		speakerNameWidget.setText(this.speakerName);
		speakerNameWidget.setTextColor(0x800000);
		speakerNameWidget.setFontId(FontID.QUILL_8);
		speakerNameWidget.setXTextAlignment(WidgetTextAlignment.CENTER);
		speakerNameWidget.setYTextAlignment(WidgetTextAlignment.TOP);

		speakerNameWidget.setOriginalX(dialogType.getTextX());
		speakerNameWidget.setOriginalY(dialogType.getNameY());
		speakerNameWidget.setOriginalWidth(TEXT_WIDTH);
		speakerNameWidget.setOriginalHeight(TEXT_LINE_HEIGHT);

		speakerNameWidget.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		speakerNameWidget.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		speakerNameWidget.setWidthMode(WidgetSizeMode.ABSOLUTE);
		speakerNameWidget.setHeightMode(WidgetSizeMode.ABSOLUTE);

		speakerNameWidget.revalidate();
	}

	private void createChatHead(Widget container)
	{
		final int TALKING_ANIMATION_ID = 568;

		chatHeadWidget = container.createChild(-1, WidgetType.MODEL);

		if (this.isPlayer)
		{
			chatHeadWidget.setModelType(WidgetModelType.LOCAL_PLAYER_CHATHEAD);
		}
		else
		{
			chatHeadWidget.setModelType(WidgetModelType.NPC_CHATHEAD);
			chatHeadWidget.setModelId(npcId);
		}

		chatHeadWidget.setModelZoom(CHATHEAD_MODEL_ZOOM);
		chatHeadWidget.setAnimationId(TALKING_ANIMATION_ID);

		chatHeadWidget.setRotationX(dialogType.getHeadRotX());
		chatHeadWidget.setRotationY(dialogType.getHeadRotY());
		chatHeadWidget.setRotationZ(dialogType.getHeadRotZ());

		chatHeadWidget.setOriginalX(dialogType.getHeadX());
		chatHeadWidget.setOriginalY(dialogType.getHeadY());
		chatHeadWidget.setOriginalWidth(CHATHEAD_WIDTH);
		chatHeadWidget.setOriginalHeight(CHATHEAD_HEIGHT);

		chatHeadWidget.setXPositionMode(WidgetPositionMode.ABSOLUTE_LEFT);
		chatHeadWidget.setYPositionMode(WidgetPositionMode.ABSOLUTE_TOP);
		chatHeadWidget.setWidthMode(WidgetSizeMode.ABSOLUTE);
		chatHeadWidget.setHeightMode(WidgetSizeMode.ABSOLUTE);

		if (this.isPlayer)
		{
			Player p = client.getLocalPlayer();

			if (p == null)
			{
				chatHeadWidget.revalidate();
				return;
			}

			PlayerComposition playerDef = client.getLocalPlayer().getPlayerComposition();
			final int[] storedEquipmentIds = playerDef.getEquipmentIds().clone();
			final int[] storedColors = playerDef.getColors().clone();

			if (equipmentOverrides != null)
			{
				setArrayContents(playerDef.getEquipmentIds(), equipmentOverrides);
			}

			if (colorOverrides != null)
			{
				setArrayContents(playerDef.getColors(), colorOverrides);
			}

			clientThread.invokeLater(() ->
			{
				setArrayContents(playerDef.getEquipmentIds(), storedEquipmentIds);
				setArrayContents(playerDef.getColors(), storedColors);
			});
		}

		chatHeadWidget.revalidate();
	}

	private void setArrayContents(int[] originalArray, int[] newContents)
	{
		for (int i = 0; i < newContents.length; i++)
		{
			if (newContents[i] > 0)
			{
				originalArray[i] = newContents[i];
			}
		}
	}

	@Override
	protected void close()
	{
		if (onClose != null)
		{
			onClose.run();
		}
	}

	private int getLineHeightForText(Widget w)
	{
		FontTypeFace widgetFont = w.getFont();
		int width = w.getOriginalWidth();
		String message = w.getText();
		String[] forcedLines = message.split("<br>");
		int numLines = 0;
		for (String s : forcedLines)
		{
			int numWraps = widgetFont.getTextWidth(s) / width;
			numLines += numWraps + 1;
		}

		int lineHeight;

		switch (numLines)
		{
			case 2:
				lineHeight = 28;
				break;
			case 3:
				lineHeight = 20;
				break;
			default:
				lineHeight = 16;
		}

		return lineHeight;
	}
}
