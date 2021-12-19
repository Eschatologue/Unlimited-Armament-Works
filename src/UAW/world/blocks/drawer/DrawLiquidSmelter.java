package UAW.world.blocks.drawer;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.graphics.*;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.*;
import mindustry.world.draw.DrawBlock;

public class DrawLiquidSmelter extends DrawBlock {
	public Color flameColor = Color.valueOf("ffc999");
	public TextureRegion smelterTop, liquidTop, inLiquid, outLiquid;
	public float lightRadius = 60f, lightAlpha = 0.65f, lightSinScl = 10f, lightSinMag = 5;
	public float flameRadius = 3f, flameRadiusIn = 1.9f, flameRadiusScl = 5f, flameRadiusMag = 2f, flameRadiusInMag = 1f;
	public boolean useOutputSprite = false;

	public DrawLiquidSmelter() {
	}

	public DrawLiquidSmelter(Color flameColor, boolean useOutputSprite) {
		this.flameColor = flameColor;
		this.useOutputSprite = useOutputSprite;
	}

	@Override
	public void load(Block block) {
		smelterTop = Core.atlas.find(block.name + "-top-smelter");
		liquidTop = Core.atlas.find(block.name + "-top-liquid");
		inLiquid = Core.atlas.find(block.name + "-liquid-input");
		outLiquid = Core.atlas.find(block.name + "-liquid-output");
		block.clipSize = Math.max(block.clipSize, (lightRadius + lightSinMag) * 2f * block.size);
	}

	@Override
	public void draw(GenericCrafter.GenericCrafterBuild build) {
		Draw.rect(build.block.region, build.x, build.y, build.block.rotate ? build.rotdeg() : 0);
		GenericCrafter type = (GenericCrafter) build.block;

		if (build.warmup > 0f && flameColor.a > 0.001f) {
			float g = 0.3f;
			float r = 0.06f;
			float cr = Mathf.random(0.1f);

			Draw.z(Layer.block + 0.01f);

			Draw.alpha(build.warmup);
			Draw.rect(smelterTop, build.x, build.y);

			Draw.alpha(((1f - g) + Mathf.absin(Time.time, 8f, g) + Mathf.random(r) - r) * build.warmup);

			Draw.tint(flameColor);
			Fill.circle(build.x, build.y, flameRadius + Mathf.absin(Time.time, flameRadiusScl, flameRadiusMag) + cr);
			Draw.color(1f, 1f, 1f, build.warmup);
			Fill.circle(build.x, build.y, flameRadiusIn + Mathf.absin(Time.time, flameRadiusScl, flameRadiusInMag) + cr);

			Draw.color();
		}
		if ((inLiquid.found() || useOutputSprite) && type.consumes.has(ConsumeType.liquid)) {
			Liquid input = type.consumes.<ConsumeLiquid>get(ConsumeType.liquid).liquid;
			Drawf.liquid(useOutputSprite ? outLiquid : inLiquid, build.x, build.y,
				build.liquids.get(input) / type.liquidCapacity,
				input.color
			);
		}

		if (type.outputLiquid != null && build.liquids.get(type.outputLiquid.liquid) > 0) {
			Drawf.liquid(outLiquid, build.x, build.y,
				build.liquids.get(type.outputLiquid.liquid) / type.liquidCapacity,
				type.outputLiquid.liquid.color
			);
		}

		if (liquidTop.found()) Draw.rect(liquidTop, build.x, build.y);
	}

	@Override
	public void drawLight(GenericCrafter.GenericCrafterBuild build) {
		Drawf.light(build.team, build.x, build.y, (lightRadius + Mathf.absin(lightSinScl, lightSinMag)) * build.warmup * build.block.size, flameColor, lightAlpha);
	}
}
