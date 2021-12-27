package UAW.world.blocks.production;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.world.blocks.production.Separator;
import mindustry.world.draw.DrawBlock;

public class EffectSeparator extends Separator {
	public Effect updateEffect = Fx.none;
	public float updateEffectChance = 0.04f;
	public DrawBlock drawer = new DrawBlock();

	public EffectSeparator(String name) {
		super(name);
	}
	public class EffectSeparatorBuild extends SeparatorBuild {
		@Override
		public void updateTile(){
			super.updateTile();
			if(consValid()){
				if(Mathf.chanceDelta(updateEffectChance)){
					updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
				}
			}
		}

	}
}
