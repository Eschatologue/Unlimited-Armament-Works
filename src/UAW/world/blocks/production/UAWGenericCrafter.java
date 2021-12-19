package UAW.world.blocks.production;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.g2d.*;
import arc.struct.EnumSet;
import arc.util.Time;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BlockFlag;

public class UAWGenericCrafter extends GenericCrafter {
	public TextureRegion rotator, rotatorTop;
	public float rotatorSpinSpeed = 15f;
	public float craftShake = 14, craftSoundVolume = 1.2f;
	public Sound craftSound = Sounds.plasmaboom;

	public UAWGenericCrafter(String name) {
		super(name);
		update = true;
		solid = true;
		hasItems = true;
		ambientSound = Sounds.machine;
		sync = true;
		ambientSoundVolume = 0.03f;
		flags = EnumSet.of(BlockFlag.factory);
	}

	@Override
	public void load() {
		super.load();
		rotator = Core.atlas.find(name + "-rotator");
		rotatorTop = Core.atlas.find(name + "-rotator-top");
	}

	public class UAWGenericCrafterBuild extends GenericCrafterBuild {

		@Override
		public void draw() {
			super.draw();
			if (rotator.found()) {
				Draw.rect(rotator, x, y, Time.time * (rotatorSpinSpeed * warmup));
			}
			if (rotatorTop.found()) {
				Draw.rect(rotatorTop, x, y);
			}
		}

		@Override
		public void craft() {
			super.craft();
			if (craftShake > 0) {
				Effect.shake(craftShake, craftShake, x, y);
			}
			craftSound.at(x, y, craftSoundVolume);
		}
	}
}
