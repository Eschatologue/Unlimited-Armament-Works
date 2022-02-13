package UAW.world.blocks.production;

import UAW.content.UAWItems;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.*;
import mindustry.world.blocks.production.Drill;

public class SelectiveDrill extends Drill {
	public Block specificOre = Blocks.oreCoal;
	public Item endProduct = UAWItems.anthracite;

	public SelectiveDrill(String name) {
		super(name);
		returnItem = endProduct;
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team) {
		return super.canPlaceOn(tile, team) && tile.floor() == specificOre.asFloor();
	}

}
