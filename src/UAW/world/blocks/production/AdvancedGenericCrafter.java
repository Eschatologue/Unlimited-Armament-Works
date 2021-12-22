package UAW.world.blocks.production;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.g2d.*;
import arc.struct.EnumSet;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BlockFlag;

public class AdvancedGenericCrafter extends GenericCrafter {
	public TextureRegion rotator, rotatorTop;
	public float rotatorSpinSpeed = -15f;
	public float craftShake = 0, craftSoundVolume = 0f;
	public Sound craftSound = Sounds.plasmaboom;

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

	public class AdvancedGenericCrafterBuild extends GenericCrafterBuild {
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
			}
			if (craftSoundVolume > 0) {
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
	}
}

