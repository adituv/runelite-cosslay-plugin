package com.github.adituv.cosslayplugin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DialogType
{
	DIALOG_HEAD_LEFT(46, 52, 40, 0, 1882, 109),
	DIALOG_HEAD_RIGHT(426, 52, 40, 0, 166, 17);

	private int headX;
	private int headY;
	private int headRotX;
	private int headRotY;
	private int headRotZ;

	private int textX;

	public int getNameY()
	{
		return 16;
	}

	public int getTextY()
	{
		return 32;
	}

	public int getContinueY()
	{
		return 96;
	}
}
