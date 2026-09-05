package uaw.world.draw;

import arc.graphics.g2d.Draw;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawRegion;
import uaw.audiovisual.UAWDrawf;

public class UAWDrawRegion extends DrawRegion {
    public float symmetryPeriod = 90;

    public UAWDrawRegion(String suffix) {
        this.suffix = suffix;
    }

    public UAWDrawRegion(String suffix, float rotateSpeed) {
        this.suffix = suffix;
        this.rotateSpeed = rotateSpeed;
    }

    public UAWDrawRegion(String suffix, float rotateSpeed, boolean spinSprite) {
        this.suffix = suffix;
        this.spinSprite = spinSprite;
        this.rotateSpeed = rotateSpeed;
    }

    @Override
    public void draw(Building build) {
        float z = Draw.z();
        if (layer > 0) Draw.z(layer);
        if (color != null) Draw.color(color);

        float rot = build.totalProgress() * rotateSpeed + rotation + (buildingRotate ? build.rotdeg() : 0);

        if (symmetryPeriod > 0f) {
            UAWDrawf.spinSprite(region, build.x + x, build.y + y, rot, symmetryPeriod);
        } else {
            Draw.rect(region, build.x + x, build.y + y, rot);
        }

        if (color != null) Draw.color();
        Draw.z(z);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
        if (!drawPlan) return;

        // Matches vanilla DrawRegion's own precedence quirk: buildingRotate picks between
        // plan.rotation * 90f OR rotation, never both. Kept for parity with vanilla behaviour
        float rot = buildingRotate ? plan.rotation * 90f : rotation;

        if (symmetryPeriod > 0f) {
            UAWDrawf.spinSprite(region, plan.drawx() + x, plan.drawy() + y, rot, symmetryPeriod);
        } else {
            Draw.rect(region, plan.drawx() + x, plan.drawy() + y, rot);
        }
    }

}
