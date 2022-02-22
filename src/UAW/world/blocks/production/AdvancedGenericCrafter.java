package UAW.world.blocks.production;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.g2d.*;
import arc.util.Nullable;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.*;

/** GenericCrafter with craft shakes and attributes when enabled */
public class AdvancedGenericCrafter extends GenericCrafter {
	public TextureRegion rotator, rotatorTop;
	public float rotatorSpinSpeed = -15f;
	public float craftShake = 0, craftSoundVolume = 1f;
	public Sound craftSound = Sounds.plasmaboom;

	public @Nullable
	Attribute attribute;

	public float baseEfficiency = 1f;
	public float boostScale = 1f;
	public float maxBoost = 1f;

	public AdvancedGenericCrafter(String name) {
		super(name);
		update = true;
		solid = true;
		hasItems = true;
		ambientSound = Sounds.machine;
		sync = true;
		ambientSoundVolume = 0.05f;
	}

	@Override
	public void load() {
		super.load();
		rotator = Core.atlas.find(name + "-rotator");
		rotatorTop = Core.atlas.find(name + "-rotator-top");
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		if (attribute != null) {
			drawPlaceText(Core.bundle.format("bar.efficiency",
				(int) ((baseEfficiency + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f)), x, y, valid);
		}
	}

	@Override
	public void setBars() {
		super.setBars();
		if (attribute != null) {
			bars.add("efficiency", (AdvancedGenericCrafterBuild entity) ->
				new Bar(() ->
					Core.bundle.format("bar.efficiency", (int) (entity.efficiencyScale() * 100)),
					() -> Pal.lightOrange,
					entity::efficiencyScale));
		}
	}

	@Override
	public void setStats() {
		super.setStats();
		if (attribute != null) stats.add(Stat.affinities, attribute, boostScale * size * size);
	}

	public class AdvancedGenericCrafterBuild extends GenericCrafterBuild {
		public float attrsum;
		float rotatorRot;

		@Override
		public void update() {
			super.update();
			rotatorRot += warmup * edelta();
		}

		public void craft() {
			super.craft();
			if (craftShake > 0) {
				Effect.shake(craftShake, craftShake, x, y);
				craftSound.at(x, y, craftSoundVolume);
			}
		}

		@Override
		public void draw() {
			super.draw();
			if (rotator.found()) {
				Drawf.spinSprite(rotator, x, y, rotatorSpinSpeed * rotatorRot);
			}
			if (rotatorTop.found()) {
				Draw.rect(rotatorTop, x, y);
			}
		}

		@Override
		public float getProgressIncrease(float base) {
			if (attribute != null) {
				return super.getProgressIncrease(base) * efficiencyScale();
			} else return super.getProgressIncrease(base);
		}

		public float efficiencyScale() {
			return baseEfficiency + Math.min(maxBoost, boostScale * attrsum) + attribute.env();
		}

		@Override
		public void pickedUp() {
			attrsum = 0f;
		}

		@Override
		public void onProximityUpdate() {
			super.onProximityUpdate();
			attrsum = sumAttribute(attribute, tile.x, tile.y);
		}
	}
}

