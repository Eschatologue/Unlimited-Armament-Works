package UAW.content.techTree;

import UAW.audiovisual.Outliner;
import arc.Core;
import arc.graphics.Color;
import arc.util.Nullable;
import mindustry.ctype.*;
import mindustry.graphics.*;
import mindustry.type.ItemStack;

public class TechTreeNode extends UnlockableContent {
	public Color outlineColor = Pal.darkerMetal.cpy();
	public int outlineThickness = 3;

	public @Nullable
	ItemStack[] researchCost;

	@Nullable
	public String iconName;

	public TechTreeNode(String name) {
		super(name);
		hideDetails = false;
		selectionSize = 128;
		outlineThickness = 0;
	}

	@Override
	public void loadIcon() {
		fullIcon =
			Core.atlas.find(getContentType().name() + "-" + (iconName == null ? name : iconName) + "-full",
				Core.atlas.find((iconName == null ? name : iconName) + "-full",
					Core.atlas.find((iconName == null ? name : iconName),
						Core.atlas.find(getContentType().name() + "-" + (iconName == null ? name : iconName),
							Core.atlas.find((iconName == null ? name : iconName) + "1")))));

		uiIcon = Core.atlas.find(getContentType().name() + "-" + (iconName == null ? name : iconName) + "-ui", fullIcon);
	}

	@Override
	public ItemStack[] researchRequirements() {
		super.researchRequirements();
		if (researchCost != null) {
			return researchCost;
		} else return ItemStack.empty;
	}

	@Override
	public ContentType getContentType() {
		return ContentType.typeid_UNUSED;
	}

	public void init() {
		super.init();
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public void createIcons(MultiPacker packer) {
		if (outlineThickness > 0) {
			Outliner.outlineRegion(packer, fullIcon, outlineColor, (iconName == null ? name : iconName), outlineThickness);
		}
		super.createIcons(packer);
	}

}
