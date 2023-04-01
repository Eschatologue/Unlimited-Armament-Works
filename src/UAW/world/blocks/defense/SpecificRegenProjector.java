package UAW.world.blocks.defense;

import arc.math.Mathf;
import arc.struct.IntFloatMap;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.world.blocks.defense.*;

import static mindustry.Vars.*;

public class SpecificRegenProjector extends RegenProjector {
	private static final IntFloatMap mendMap = new IntFloatMap();
	private static long lastUpdateFrame = -1;

	public float otherBlockHealMult = 0.5f;

	public SpecificRegenProjector(String name) {
		super(name);
	}

	public class RevitalizationProjectorBuild extends RegenProjectorBuild {

		@Override
		public void updateTile() {
			if (lastChange != world.tileChanges) {
				lastChange = world.tileChanges;
				updateTargets();
			}

			//TODO should warmup depend on didRegen?
			warmup = Mathf.approachDelta(warmup, didRegen ? 1f : 0f, 1f / 70f);
			totalTime += warmup * Time.delta;
			didRegen = false;
			anyTargets = false;

			//no healing when suppressed
			if (checkSuppression()) {
				return;
			}

			anyTargets = targets.contains(Building::damaged);

			if (efficiency > 0) {
				if ((optionalTimer += Time.delta * optionalEfficiency) >= optionalUseTime) {
					consume();
					optionalTimer = 0f;
				}

				float healAmount = Mathf.lerp(1f, optionalMultiplier, optionalEfficiency) * healPercent;

				//use Math.max to prevent stacking
				for (var build : targets) {
					if (!build.damaged() || build.isHealSuppressed()) continue;

					didRegen = true;

					int pos = build.pos();
					//TODO periodic effect
					float value = mendMap.get(pos);
					mendMap.put(pos, Math.min(Math.max(value, healAmount * edelta() * build.block.health / 100f), build.block.health - build.health));

					if (value <= 0 && Mathf.chanceDelta(effectChance * build.block.size * build.block.size)) {
						effect.at(build.x + Mathf.range(build.block.size * tilesize / 2f - 1f), build.y + Mathf.range(build.block.size * tilesize / 2f - 1f));
					}
				}
			}

			if (lastUpdateFrame != state.updateId) {
				lastUpdateFrame = state.updateId;

				for (var entry : mendMap.entries()) {
					var build = world.build(entry.key);
					// Healing is reduced on other building
					if (build != null) {
						if (build instanceof Wall.WallBuild) {
							build.heal(entry.value);
						} else {
							build.heal(entry.value * otherBlockHealMult);
						}
						build.recentlyHealed();
					}
				}
				mendMap.clear();
			}
		}
	}
}
