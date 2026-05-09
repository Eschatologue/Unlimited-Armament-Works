package uaw.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.content.Blocks;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.OverlayFloor;
import mindustry.world.meta.Stat;
import uaw.content.UAWItems;

import static mindustry.Vars.*;

@Deprecated
public class LegacyConversionDrill extends ThumperDrill {

    /**
     * The floor or overlay block this drill must be placed on
     */
    public Block tileRequirement = Blocks.oreCoal;
    /**
     * The item this drill produces, regardless of what it mines
     */
    public Item drilledItem = UAWItems.anthracite;
    /**
     * How many output items are produced per ore tile per drill cycle
     */
    public int outputMult = 1;

    public LegacyConversionDrill(String name) {
        super(name);
        drawMineItem = false;
    }

    protected boolean tileMatchesRequirement(Tile tile) {
        return tileRequirement instanceof OverlayFloor
                ? tile.overlay() == tileRequirement
                : tile.floor() == tileRequirement;
    }

    /**
     * Removes Drill tier Stat
     */
    @Override
    public void setStats() {
        super.setStats();
        stats.remove(Stat.drillTier);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (isMultiblock()) {
            for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
                if (canMine(other) && tileMatchesRequirement(other)) return true;
            }
            return false;
        }
        return canMine(tile) && tileMatchesRequirement(tile);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        drawPotentialLinks(x, y);

        Tile tile = world.tile(x, y);
        if (tile == null) return;

        countOre(tile);

        boolean anyMatch = false;
        for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
            if (tileMatchesRequirement(other)) {
                anyMatch = true;
                break;
            }
        }
        if (returnItem != null && anyMatch) {
            // Show drill speed for the converted output item
            float width = drawPlaceText(
                    Core.bundle.formatFloat(
                            "bar.drillspeed",
                            60f / getDrillTime(returnItem) * returnCount,
                            2),
                    x, y, valid);

            float dx = x * tilesize + offset - width / 2f - 4f;
            float dy = y * tilesize + offset + size * tilesize / 2f + 5;
            float s = iconSmall / 4f;

            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(drilledItem.fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(drilledItem.fullIcon, dx, dy, s, s);
        } else {
            // Check whether any linked tile actually fails the requirement
            boolean anyMismatch = tile.getLinkedTilesAs(this, tempTiles)
                    .contains(t -> !tileMatchesRequirement(t));

            if (anyMismatch || !valid) {
                String barName = "bar.conversion-drill-inoperative-" + tileRequirement.name;
                drawPlaceText(Core.bundle.get(barName), x, y, valid);
            }
        }
    }

    public class ConversionDrillBuild extends ThumperDrillBuild {

        /**
         * The item this build outputs. Override for specialised variants
         */
        protected Item outputItem() {
            return drilledItem;
        }

        @Override
        public void drawSelect() {
            if (dominantItem == null) return;
            float dx = x - size * tilesize / 2f;
            float dy = y + size * tilesize / 2f;
            float s = iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(outputItem().fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(outputItem().fullIcon, dx, dy, s, s);
        }

        @Override
        public void updateTile() {
            if (dominantItem == null) return;

            if (invertTime > 0f) invertTime -= delta() / invertedTime;

            if (timer(timerDump, dumpTime / timeScale)) {
                dump(items.has(outputItem()) ? outputItem() : null);
            }

            float drillTime = getDrillTime(dominantItem);
            smoothProgress = Mathf.lerpDelta(smoothProgress, progress / (drillTime - 20f), 0.1f);

            if (items.total() <= itemCapacity - dominantItems * outputMult && dominantItems > 0 && efficiency > 0) {
                warmup = Mathf.approachDelta(warmup, progress / drillTime, 0.01f);
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;
                timeDrilled += speedCurve.apply(progress / drillTime) * speed;
                lastDrillSpeed = 1f / drillTime * speed * dominantItems;
                progress += delta() * speed;
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, 0.01f);
                lastDrillSpeed = 0f;
                return;
            }

            if (dominantItems > 0 && progress >= drillTime && items.total() < itemCapacity) {
                for (int i = 0; i < dominantItems * outputMult; i++) offload(outputItem());
                invertTime = 1f;
                progress %= drillTime;
                if (wasVisible) {
                    Effect.shake(shake, shake, this);
                    drillSound.at(x, y, 1f + Mathf.range(drillSoundPitchRand), drillSoundVolume);
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), outputItem().color);
                }
            }
        }
    }
}
