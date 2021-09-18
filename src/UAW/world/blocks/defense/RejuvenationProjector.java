package UAW.world.blocks.defense;

import UAW.graphics.UAWFxStatic;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.logic.Ranged;
import mindustry.world.blocks.defense.MendProjector;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.indexer;
import static mindustry.Vars.tilesize;

public class RejuvenationProjector extends MendProjector {
    public float boostMultiplier = 3f;
    public float boostDuration = 180f;
    public RejuvenationProjector(String name){
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.projectors;
        hasPower = true;
        hasItems = true;
        emitLight = true;
        range = 10 * 8;
        lightRadius = range / 2;
        canOverdrive = false;
        baseExplosiveness = 15;
        researchCostMultiplier = 1.5f;
    }

    @Override
    public void setStats(){
        stats.timePeriod = useTime;
        stats.add(Stat.speedIncrease, "+" + (int)(boostMultiplier * 100f - 100) + "%");
        super.setStats();

        stats.remove(Stat.boostEffect);
    }

    public class RejuvinationProjectorBuild extends Building implements Ranged {
        float heat;
        float charge = Mathf.random(reload);
        float speedStart = 60f;

        @Override
        public float range() {
            return range;
        }

        @Override
        public void updateTile(){
            heat = Mathf.lerpDelta(heat, consValid() || cheating() ? 1f : 0f, 0.08f);
            charge += heat * delta();

            if(cons.optionalValid() && timer(timerUse, useTime) && efficiency() > 0){
                consume();
            }

            if(charge >= reload){
                charge = 0f;
                indexer.eachBlock(this, range, Building::damaged, other -> {
                    other.heal(other.maxHealth() * (healPercent) / 100f * efficiency());
                    if (other.health() > (other.maxHealth / 100) * speedStart && other.health() < (other.maxHealth / 100) * (speedStart + 5)) {
                        other.applyBoost(boostMultiplier, boostDuration);
                        Fx.overdriven.at(other.x + Mathf.range(size * 6f), other.y + Mathf.range(size * 6f));
                        Sounds.shield.at(other.x, other.y);
                    }
                });
            }
        }
        @Override
        public void draw() {
            super.draw();

            float f = 1f - (Time.time / 200f) % 1f;

            Draw.color(baseColor);
            Draw.alpha(heat * Mathf.absin(Time.time, 10f, 1f) * 0.5f);
            Draw.rect(topRegion, x, y);
            Draw.alpha(1f);
            Lines.stroke((2f * f + (0.1f / 200000)) * heat);
            Lines.square(x, y, Math.min(1f + (1f - f) * size * tilesize / 2f, size * tilesize / 1.6f), 45f);
        }
    }
}
