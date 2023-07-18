package UAW.entities.part;

import arc.graphics.Color;
import arc.math.Interp;
import mindustry.entities.part.RegionPart;
import mindustry.graphics.Pal;

public class MissilePart extends RegionPart {
	public MissilePart(String region) {
		super.suffix = region;
		progress = PartProgress.recoil.curve(Interp.pow2In);
		colorTo = new Color(1f, 1f, 1f, 0f);
		color = Color.white;
		mixColorTo = Pal.accent;
		mixColor = new Color(1f, 1f, 1f, 0f);
		outline = false;
		under = true;
	}

	public MissilePart() {
		this("-missile");
	}
}
