package UAW.entities.part;

import arc.graphics.g2d.Draw;
import mindustry.entities.part.RegionPart;

public class ModRegionPart extends RegionPart {
	boolean alpha;

	@Override
	public void draw(PartParams params) {
		Draw.alpha(progress.getClamp(params));
		super.draw(params);
	}
}
