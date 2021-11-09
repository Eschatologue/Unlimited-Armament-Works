package UAW.world.blocks.defense.walls;

import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;

import static mindustry.Vars.minArmorDamage;

public class ArmoredWall extends Wall {
	float armor = 50;

	public ArmoredWall(String name) {
		super(name);
		size = 1;
		solid = true;
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.armor, armor);
	}

	public class ArmoredWallBuild extends WallBuild {
		@Override
		public void damage(float amount) {
			rawDamage(Math.max(amount - armor, minArmorDamage * amount));
		}

		@Override
		public void damagePierce(float amount) {
			rawDamage(amount);
		}

		protected void rawDamage(float amount) {
			if (amount > 0) {
				health -= amount;
				if (health <= 0 && !dead()) kill();
			}
		}
	}
}
