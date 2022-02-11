package UAW.world.blocks.production;

import arc.graphics.g2d.TextureRegion;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.BlockGroup;

import static UAW.Vars.tick;

public class ThumpDrill extends GenericCrafter {
	public TextureRegion rotator, rotatorTop;
	public float rotatorSpinSpeed = -15f;
	public float drillShake = 0, drillSoundVolume = 1f;

	public float drillTime = 5 * tick;
	public Effect drillEffect = Fx.mineBig;

	/** Drill that mines stuff based on tile attribute */
	public ThumpDrill(String name) {
		super(name);
		this.craftTime = drillTime;
		this.craftEffect = drillEffect;
		update = true;
		solid = true;
		group = BlockGroup.drills;
		hasLiquids = true;
		liquidCapacity = 10f;
		hasItems = true;
		ambientSound = Sounds.drill;
		ambientSoundVolume = 0.018f;
	}

	@Override
	public void setStats() {
		super.setStats();
	}


}
