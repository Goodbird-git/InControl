package mcjty.incontrol.events.mob;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import noppes.npcs.CustomEntities;
import noppes.npcs.api.NpcAPI;

import java.util.Objects;

public class CNPCMob extends DefaultMob {
    private int cloneTab;
    private String cloneName;

    public CNPCMob(int cloneTab, String cloneName) {
        this.cloneName = cloneName;
        this.cloneTab = cloneTab;
    }

    public int getCloneTab() {
        return cloneTab;
    }

    public void setCloneTab(int cloneTab) {
        this.cloneTab = cloneTab;
    }

    public String getCloneName() {
        return cloneName;
    }

    public void setCloneName(String cloneName) {
        this.cloneName = cloneName;
    }

    public EntityType<?> getType() {
        return CustomEntities.entityCustomNpc;
    }

    public boolean matches(Entity entity) {
        if (entity == null) return false;
        if (!entity.getPersistentData().contains("InControlNatSpawnTab") || !entity.getPersistentData().contains("InControlNatSpawnName"))
            return false;
        return entity.getPersistentData().getInt("InControlNatSpawnTab") == cloneTab && entity.getPersistentData().getString("InControlNatSpawnName").equals(cloneName);
    }

    @Override
    public Entity getEntity(ServerLevel level) {
        try {
            Entity entity = NpcAPI.Instance().getClones().get(cloneTab, cloneName, NpcAPI.Instance().getIWorld(level)).getMCEntity();
            entity.getPersistentData().putInt("InControlNatSpawnTab", cloneTab);
            entity.getPersistentData().putString("InControlNatSpawnName", cloneName);
            return entity;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CNPCMob cnpcMob)) return false;
        return cloneTab == cnpcMob.cloneTab && Objects.equals(cloneName, cnpcMob.cloneName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloneTab, cloneName);
    }
}
