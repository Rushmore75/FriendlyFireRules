package com.friendlyfirerules.base;

import java.util.UUID;

import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.Universe;
import com.friendlyfirerules.base.init.IHasModel;
import com.friendlyfirerules.base.init.ModBlocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MonumentBlock extends BlockContainer implements IHasModel {

    private static final long TOUCH_DELAY = 2*1000*60; 

    public MonumentBlock(String name, Material material) {
        // super(material, true);
        super(material);
        /* Setting the Name of the Block */
        setTranslationKey(name);
        /* Setting the Registry Name of the Block / Item */
        setRegistryName(Base.MOD_ID, name);
        /* The Tab where the Item will be placed */
        setCreativeTab(Base.MOD_TAB);

        setHardness(60.0f);
        setResistance(Float.MAX_VALUE);
        setHarvestLevel("pickaxe", 2); // Iron level
        setLightLevel(1.0f);
        /* Light Opacity, used for Windows */
        setLightOpacity(Integer.MAX_VALUE);        
        /* Sets the Sound type when you're Placing/Walking on it */
        setSoundType(SoundType.METAL);
           /** Blocks need to be added as an Block and as an Item*/
        ModBlocks.BLOCKS.add(this);
        ModBlocks.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
    
    }
   
    @Override
    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_) {
        return false;
    }
    
    
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
        
        MonumentBlockTileEntity mte = (MonumentBlockTileEntity) world.getTileEntity(pos);
        mte.setOwner(entity.getUniqueID());
        
        super.onBlockPlacedBy(world, pos, state, entity, stack);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (!world.isRemote) {
            MonumentBlockTileEntity tileEntity = (MonumentBlockTileEntity) world.getTileEntity(pos);

            // see if the time delay is done
            long now = System.currentTimeMillis();
            if (now - tileEntity.getLastTouched() > TOUCH_DELAY) {

                // Make sure it is owned by a team, and the player is on a team 
                if (getTeamName(tileEntity.getOwner()) != null && getTeamName(player.getUniqueID()) != null) {
                    // Announce the monument has been touched
                    String msg = "§5" + getTeamName(tileEntity.getOwner()) + "'s§r monument " + "was touched by §6" + getTeamName(player.getUniqueID()) +"§r";
                    Universe.get().server.getPlayerList().sendMessage(new TextComponentString(msg));

                    tileEntity.setLastTouched(now);
                } else {
                    // Tell the player get git gud
                    player.sendMessage(new TextComponentString("Either you aren't on a team, or this monument doesn't belong to anyone..."));
                }

            } else {
                player.sendMessage(new TextComponentString("The monument is on cooldown: " + (TOUCH_DELAY - (now - tileEntity.getLastTouched())) + "ms"));
            }
        }
        return true;
    }
    
    private static String getTeamName(UUID uuid) {
        ForgePlayer player = Universe.get().getPlayer(uuid);
        if (player.hasTeam()) {
            return player.team.getId();
        }
        return null;
    }

    /**
     * Registering the Model of the Mod Blocks
     */
    @Override
    public void registerModels() {
        Base.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
        GameRegistry.registerTileEntity(MonumentBlockTileEntity.class, this.getRegistryName());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new MonumentBlockTileEntity();
    }
    
    class MonumentBlockTileEntity extends TileEntity {
        private UUID ownerUuid;
        private long lastTouched;

        public UUID getOwner() { return this.ownerUuid; }
        public long getLastTouched() { return this.lastTouched; }

        public void setLastTouched(long now) { this.lastTouched = now; }
        public void setOwner(UUID uuid) { this.ownerUuid = uuid; }

        @Override
        public NBTTagCompound writeToNBT(NBTTagCompound tag) {
            super.writeToNBT(tag);
            tag.setLong("lastTouched", lastTouched);
            tag.setString("ownerUuid", ownerUuid.toString());
            return tag;
        } 

        @Override
        public void readFromNBT(NBTTagCompound tag) {
            super.readFromNBT(tag);
            lastTouched = tag.getLong("lastTouched");
            ownerUuid = UUID.fromString(tag.getString("ownerUuid"));
        }

        // @Override
        // public NBTTagCompound getUpdateTag() {
        //     return writeToNBT(new NBTTagCompound());
        // }

	    // @Override
	    // public SPacketUpdateTileEntity getUpdatePacket() {
	    // 	return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	    // }

	    // @Override
	    // public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
	    // 	readFromNBT(pkt.getNbtCompound());
	    // }       
    }
}
