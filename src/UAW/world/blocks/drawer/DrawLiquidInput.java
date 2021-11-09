package UAW.world.blocks.drawer;

import arc.graphics.g2d.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.*;
import mindustry.world.draw.DrawMixer;

public class DrawLiquidInput extends DrawMixer {
	public void draw(GenericCrafter.GenericCrafterBuild entity) {
		float rotation = entity.block.rotate ? entity.rotdeg() : 0.0F;
		Draw.rect(this.bottom, entity.x, entity.y, rotation);
		if (entity.liquids.total() > 0.001F) {
			Draw.color(entity.block.consumes.<ConsumeLiquid>get(ConsumeType.liquid).liquid.color, entity.liquids.get(entity.block.consumes.<ConsumeLiquid>get(ConsumeType.liquid).liquid) / entity.block.liquidCapacity);
			Draw.rect(this.liquid, entity.x, entity.y, rotation);
			Draw.color();
		}
		Draw.rect(this.top, entity.x, entity.y, rotation);
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return new TextureRegion[]{bottom, top};
	}
}
