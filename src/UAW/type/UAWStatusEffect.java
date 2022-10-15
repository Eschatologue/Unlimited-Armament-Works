package UAW.type;

import UAW.audiovisual.Outliner;
import arc.graphics.Color;
import mindustry.graphics.*;
import mindustry.type.StatusEffect;

public class UAWStatusEffect extends StatusEffect {
	public Color outlineColor = Pal.darkerMetal;
	public int outlineThickness = 3;
	public float affinityResultTime = 60f;

	public UAWStatusEffect(String name) {
		super(name);
	}

	@Override
	public void createIcons(MultiPacker packer) {
		Outliner.outlineRegion(packer, fullIcon, outlineColor, name, outlineThickness);

		super.createIcons(packer);
	}
}
