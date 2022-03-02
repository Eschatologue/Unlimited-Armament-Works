package UAW.world.blocks.production;

import gas.world.blocks.production.GasDrill;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.world.*;

public class ThumperDrill extends GasDrill {
	public Block specificOre = Blocks.oreCoal;

	public ThumperDrill(String name) {
		super(name);
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (isMultiblock() && tile.overlay() == specificOre) {
			for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
				if (canMine(other)) {
					return true;
				}
			}
			return false;
		} else {
			return canMine(tile);
		}
	}
}
