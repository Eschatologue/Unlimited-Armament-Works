package UAW.world.blocks.defense.walls;

import mindustry.world.blocks.defense.Wall;

public class UAWWall extends Wall {
	public UAWWall(String name) {
		super(name);
	}

	public class UAWWallBuild extends WallBuild {

		public float lightningChance() {
			return lightningChance;
		}

		public float armor() {
			return armor;
		}

	}

}
