package com.github.adituv.cosslayplugin.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;

@Getter
@Slf4j
public enum KitPart
{
	// region Hair
	// region Male
	HAIRSTYLE_MALE_BALD(0, 0, "Bald"),
	HAIRSTYLE_MALE_DREADLOCKS(1, 1, "Dreadlocks"),
	HAIRSTYLE_MALE_LONG(2, 2, "Long"),
	HAIRSTYLE_MALE_MEDIUM(3, 3, "Medium"),
	HAIRSTYLE_MALE_TONSURE(4, 4, "Tonsure"),
	HAIRSTYLE_MALE_SHORT(5, 5, "Short"),
	HAIRSTYLE_MALE_CROPPED(6, 6, "Cropped"),
	HAIRSTYLE_MALE_WILD_SPIKES(7, 7, "Wild spikes"),
	HAIRSTYLE_MALE_SPIKES(8, 8, "Spikes"),
	HAIRSTYLE_MALE_MOHAWK(9, 9, "Mohawk"),
	HAIRSTYLE_MALE_WIND_BRAIDS(129, 10, "Wind braids"),
	HAIRSTYLE_MALE_QUIFF(130, 11, "Quiff"),
	HAIRSTYLE_MALE_SAMURAI(131, 12, "Samurai"),
	HAIRSTYLE_MALE_PRINCELY(132, 13, "Princely"),
	HAIRSTYLE_MALE_CURTAINS(133, 14, "Curtains"),
	HAIRSTYLE_MALE_LONG_CURTAINS(134, 15, "Long curtains"),
	HAIRSTYLE_MALE_FRONT_SPLIT(151, 16, "Front split"),
	HAIRSTYLE_MALE_TOUSLED(144, 17, "Tousled"),
	HAIRSTYLE_MALE_SIDE_WEDGE(145, 18, "Side wedge"),
	HAIRSTYLE_MALE_FRONT_WEDGE(146, 19, "Front wedge"),
	HAIRSTYLE_MALE_FRONT_SPIKES(147, 20, "Front spikes"),
	HAIRSTYLE_MALE_FROHAWK(148, 21, "Frohawk"),
	HAIRSTYLE_MALE_REAR_SKIRT(149, 22, "Rear skirt"),
	HAIRSTYLE_MALE_QUEUE(150, 23, "Queue"),
	// endregion Male

	// region Female
	HAIRSTYLE_FEMALE_BALD(45, 0, "Bald"),
	HAIRSTYLE_FEMALE_BUN(46, 1, "Bun"),
	HAIRSTYLE_FEMALE_DREADLOCKS(47, 2, "Dreadlocks"),
	HAIRSTYLE_FEMALE_LONG(48, 3, "Long"),
	HAIRSTYLE_FEMALE_MEDIUM(49, 4, "Medium"),
	HAIRSTYLE_FEMALE_PIGTAILS(50, 5, "Pigtails"),
	HAIRSTYLE_FEMALE_SHORT(51, 6, "Short"),
	HAIRSTYLE_FEMALE_CROPPED(52, 7, "Cropped"),
	HAIRSTYLE_FEMALE_WILD_SPIKES(43, 8, "Wild spikes"),
	HAIRSTYLE_FEMALE_SPIKY(54, 9, "Spiky"),
	HAIRSTYLE_FEMALE_EARMUFFS(55, 10, "Earmuffs"),
	HAIRSTYLE_FEMALE_SIDE_PONY(118, 11, "Side pony"),
	HAIRSTYLE_FEMALE_CURLS(119, 12, "Curls"),
	HAIRSTYLE_FEMALE_WIND_BRAIDS(120, 13, "Wind braids"),
	HAIRSTYLE_FEMALE_PONYTAIL(121, 14, "Ponytail"),
	HAIRSTYLE_FEMALE_BRAIDS(122, 15, "Braids"),
	HAIRSTYLE_FEMALE_BUNCHES(123, 16, "Bunches"),
	HAIRSTYLE_FEMALE_BOB(124, 17, "Bob"),
	HAIRSTYLE_FEMALE_LAYERED(125, 18, "Layered"),
	HAIRSTYLE_FEMALE_STRAIGHT(126, 19, "Straight"),
	HAIRSTYLE_FEMALE_STRAIGHT_BRAIDS(127, 20, "Straight braids"),
	HAIRSTYLE_FEMALE_CURTAINS(128, 21, "Curtains"),
	HAIRSTYLE_FEMALE_FRONT_SPLIT(141, 22, "Front split"),
	HAIRSTYLE_FEMALE_TWO_BACK(143, 23, "Two-back"),
	// endregion Female
	// endregion Hair

	// region Beards
	BEARD_CLEAN_SHAVEN(14, 0, "Clean-shaven"),
	BEARD_GOATEE(10, 1, "Goatee"),
	BEARD_LONG(11, 2, "Long"),
	BEARD_MEDIUM(12, 3, "Medium"),
	BEARD_SMALL_MOUSTACHE(13, 4, "Small moustache"),
	BEARD_SHORT(15, 5, "Short"),
	BEARD_POINTY(16, 6, "Pointy"),
	BEARD_SPLIT(17, 7, "Split"),
	BEARD_HANDLEBAR(111, 8, "Handlebar"),
	BEARD_MUTTON(112, 9, "Mutton"),
	BEARD_FULL_MUTTON(113, 10, "Full mutton"),
	BEARD_BIG_MOUSTACHE(114, 11, "Big moustache"),
	BEARD_WAXED_MOUSTACHE(115, 12, "Waxed moustache"),
	BEARD_DALI(116, 13, "Dali"),
	BEARD_VIZIER(117, 14, "Vizier"),
	// endregion Beards

	// region Torso
	// region Male
	TORSO_MALE_PLAIN(18, 0, "Plain"),
	TORSO_MALE_LIGHT_BUTTONS(19, 1, "Light buttons"),
	TORSO_MALE_DARK_BUTTONS(20, 2, "Dark buttons"),
	TORSO_MALE_JACKET(21, 3, "Jacket"),
	TORSO_MALE_SHIRT(22, 4, "Shirt"),
	TORSO_MALE_STITCHING(23, 5, "Stitching"),
	TORSO_MALE_TORN(24, 6, "Torn"),
	TORSO_MALE_TWO_TONED(25, 7, "Two-toned"),
	TORSO_MALE_SWEATER(105, 8, "Sweater"),
	TORSO_MALE_BUTTONED_SHIRT(106, 9, "Buttoned shirt"),
	TORSO_MALE_VEST(107, 10, "Vest"),
	TORSO_MALE_PRINCELY(108, 11, "Princely"),
	TORSO_MALE_RIPPED_WESKIT(109, 12, "Ripped weskit"),
	TORSO_MALE_TORN_WESKIT(110, 13, "Torn weskit"),
	// endregion Male

	// region Female
	TORSO_FEMALE_PLAIN(56, 0, "Plain"),
	TORSO_FEMALE_CROP_TOP(57, 1, "Crop-top"),
	TORSO_FEMALE_POLO_NECK(58, 2, "Polo-neck"),
	TORSO_FEMALE_SIMPLE(59, 3, "Simple"),
	TORSO_FEMALE_TORN(60, 4, "Torn"),
	TORSO_FEMALE_SWEATER(89, 5, "Sweater"),
	TORSO_FEMALE_SHIRT(90, 6, "Shirt"),
	TORSO_FEMALE_VEST(91, 7, "Vest"),
	TORSO_FEMALE_FRILLY(92, 8, "Frilly"),
	TORSO_FEMALE_CORSETRY(93, 9, "Corsetry"),
	TORSO_FEMALE_BODICE(94, 10, "Bodice"),
	// endregion Female
	// endregion Torso

	// region Arms
	// region Male
	ARMS_MALE_REGULAR(26, 0, "Regular"),
	ARMS_MALE_MUSCLEBOUND(27, 1, "Musclebound"),
	ARMS_MALE_LOOSE_SLEEVED(28, 2, "Loose sleeved"),
	ARMS_MALE_LARGE_CUFFED(29, 3, "Large cuffed"),
	ARMS_MALE_THIN(30, 4, "Thin"),
	ARMS_MALE_SHOULDER_PADS(31, 5, "Shoulder pads"),
	ARMS_MALE_THIN_STRIPE(32, 6, "Thin stripe"),
	ARMS_MALE_THICK_STRIPE(84, 7, "Thick stripe"),
	ARMS_MALE_WHITE_CUFFS(85, 8, "White cuffs"),
	ARMS_MALE_PRINCELY(86, 9, "Princely"),
	ARMS_MALE_TATTY(87, 10, "Tatty"),
	ARMS_MALE_RIPPED(88, 11, "Ripped"),
	// endregion Male

	// region Female
	ARMS_FEMALE_SHORT_SLEEVES(61, 0, "Short sleeves"),
	ARMS_FEMALE_BARE_ARMS(62, 1, "Bare arms"),
	ARMS_FEMALE_MUSCLEY(63, 2, "Muscley"),
	ARMS_FEMALE_LONG_SLEEVED(64, 3, "Long sleeved"),
	ARMS_FEMALE_LARGE_CUFFS(65, 4, "Large cuffs"),
	ARMS_FEMALE_FRILLY(66, 5, "Frilly"),
	ARMS_FEMALE_SWEATER(95, 6, "Sweater"),
	ARMS_FEMALE_WHITE_CUFFS(96, 7, "White cuffs"),
	ARMS_FEMALE_THIN_STRIPE(97, 8, "Thin stripe"),
	ARMS_FEMALE_TATTY(98, 9, "Tatty"),
	ARMS_FEMALE_BARE_SHOULDERS(99, 10, "Bare shoulders"),
	// endregion Female
	// endregion Arms

	// region Legs
	// region Male
	LEGS_MALE_PLAIN(36, 0, "Plain"),
	LEGS_MALE_SHORTS(37, 1, "Shorts"),
	LEGS_MALE_FLARES(38, 2, "Flares"),
	LEGS_MALE_TURN_UPS(39, 3, "Turn-ups"),
	LEGS_MALE_TATTY(40, 4, "Tatty"),
	LEGS_MALE_BEACH(41, 5, "Beach"),
	LEGS_MALE_PRINCELY(100, 6, "Princely"),
	LEGS_MALE_LEGGINGS(101, 7, "Leggings"),
	LEGS_MALE_SIDE_STRIPES(102, 8, "Side-stripes"),
	LEGS_MALE_RIPPED(103, 9, "Ripped"),
	LEGS_MALE_PATCHED(104, 10, "Patched"),
	// endregion Male

	// region Female
	LEGS_FEMALE_PLAIN(70, 0, "Plain"),
	LEGS_FEMALE_SKIRT(71, 1, "Skirt"),
	LEGS_FEMALE_FLARES(72, 2, "Flares"),
	LEGS_FEMALE_LONG_SKIRT(73, 3, "Long skirt"),
	LEGS_FEMALE_LONG_NARROW_SKIRT(74, 4, "Long narrow skirt"),
	LEGS_FEMALE_TATTY(75, 5, "Tatty"),
	LEGS_FEMALE_TURN_UPS(76, 6, "Turn-ups"),
	LEGS_FEMALE_SHORT_SKIRT(77, 7, "Short skirt"),
	LEGS_FEMALE_LAYERED(78, 8, "Layered"),
	LEGS_FEMALE_SASH_AND_DOTS(135, 9, "Sash & dots"),
	LEGS_FEMALE_BIG_HEM(136, 10, "Big hem"),
	LEGS_FEMALE_SASH_AND_TROUSERS(137, 11, "Sash & trousers"),
	LEGS_FEMALE_PATTERNED(138, 12, "Patterned"),
	LEGS_FEMALE_TORN_SKIRT(139, 13, "Torn skirt"),
	LEGS_FEMALE_PATCHED_SKIRT(140, 14, "Patched skirt"),
	// endregion Female
	// endregion Legs

	// region Hands
	HANDS_MALE_PLAIN(34, 0, "Plain"),
	HANDS_MALE_BRACERS(35, 1, "Bracers"),

	HANDS_FEMALE_PLAIN(68, 0, "Plain"),
	HANDS_FEMALE_BRACERS(69, 1, "Bracers"),
	// endregion Hands

	// region Boots
	BOOTS_MALE_SMALL(42, 0, "Small"),
	BOOTS_MALE_LARGE(43, 0, "Large"),

	BOOTS_FEMALE_SMALL(79, 0, "Small"),
	BOOTS_FEMALE_LARGE(80, 0, "Large"),
	// endregion Boots
	;

	private final String id;
	private final int partId;
	private final int menuIdx;
	private final String name;

	KitPart(int partId, int menuIdx, String name)
	{
		this.id = this.toString().toLowerCase();
		this.partId = partId;
		this.menuIdx = menuIdx;
		this.name = name;
	}

	public int getEquipmentId()
	{
		return this.partId + 256;
	}

	@Nullable
	public static KitPart getByPartId(int partId)
	{
		if (partId < 0)
		{
			return null;
		}
		if (partId >= 256)
		{
			log.warn("Attempted to get KitPart by id>=256.  Maybe getByEquipmentId?  partId={}", partId);
			return null;
		}

		for (KitPart part : KitPart.values())
		{
			if (part.getPartId() == partId)
			{
				return part;
			}
		}

		return null;
	}

	@Nullable
	public static KitPart getByEquipmentId(int equipId)
	{
		if (equipId < 0)
		{
			return null;
		}
		if (equipId < 256)
		{
			log.warn("Attempted to get KitPart by equipId<256.  Maybe getByPartId()?  equipId={}", equipId);
			return null;
		}
		if (equipId > 512)
		{
			// Not a kit part - there is an item in this slot
			return null;
		}

		int partId = equipId - 256;

		for (KitPart part : KitPart.values())
		{
			if (part.getPartId() == partId)
			{
				return part;
			}
		}

		return null;
	}

	@Nullable
	public static KitPart getById(String id)
	{
		for (KitPart part : KitPart.values())
		{
			if (part.getId().equals(id))
			{
				return part;
			}
		}

		return null;
	}
}
