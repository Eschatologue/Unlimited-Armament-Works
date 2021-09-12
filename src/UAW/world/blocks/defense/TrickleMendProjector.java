package UAW.world.blocks.defense;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.logic.Ranged;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.indexer;
import static mindustry.Vars.tilesize;

public class TrickleMendProjector extends MendProjector {
    public TrickleMendProjector(String name){
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.projectors;
        hasPower = true;
        hasItems = true;
        emitLight = true;
        range = 10 * 8;
        lightRadius = range / 2;
    }
    public class TrickleMendBuild extends Building implements Ranged {
        float heat;
        float charge = Mathf.random(reload);
        float phaseHeat;
        float smoothEfficiency;

        @Override
        public float range() {
            return range;
        }

        @Override
        public void updateTile(){
            smoothEfficiency = Mathf.lerpDelta(smoothEfficiency, efficiency(), 0.08f);
            heat = Mathf.lerpDelta(heat, consValid() || cheating() ? 1f : 0f, 0.08f);
            charge += heat * delta();

            phaseHeat = Mathf.lerpDelta(phaseHeat, Mathf.num(cons.optionalValid()), 0.1f);

            if(cons.optionalValid() && timer(timerUse, useTime) && efficiency() > 0){
                consume();
            }

            if(charge >= reload){
                float realRange = range + phaseHeat * phaseRangeBoost;
                charge = 0f;

                indexer.eachBlock(this, realRange, Building::damaged, other -> {
                    other.heal(other.maxHealth() * (healPercent + phaseHeat * phaseBoost) / 100f * efficiency());
                });
            }
        }
        @Override
        public void draw() {
            super.draw();

            float f = 1f - (Time.time / 100f) % 1f;

            Draw.color(baseColor, phaseColor, phaseHeat);
            Draw.alpha(heat * Mathf.absin(Time.time, 10f, 1f) * 0.5f);
            Draw.rect(topRegion, x, y);
            Draw.alpha(1f);
            Lines.stroke((2f * f + 0.2f) * heat);
            Lines.square(x, y, Math.min(1f + (1f - f) * size * tilesize / 2f, size * tilesize / 2.5f), 45f);
        }
    }
}
