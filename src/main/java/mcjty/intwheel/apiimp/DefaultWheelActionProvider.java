package mcjty.intwheel.apiimp;

import mcjty.intwheel.api.IWheelActionProvider;
import mcjty.intwheel.api.IWheelBlockSupport;
import mcjty.intwheel.api.StandardWheelActions;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DefaultWheelActionProvider implements IWheelActionProvider {

    @Override
    public String getID() {
        return "intwheel:default";
    }

    @Override
    public void updateWheelActions(@Nonnull List<String> actions, @Nonnull EntityPlayer player, World world, @Nullable BlockPos pos) {
        ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
        if (ItemStackTools.isValid(heldItem)) {
            actions.add(StandardWheelActions.ID_SEARCH);
        }
        if (pos != null) {
            actions.add(StandardWheelActions.ID_ROTATE);
            Block block = world.getBlockState(pos).getBlock();
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof IInventory || (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))) {
                actions.add(StandardWheelActions.ID_DUMP);
                actions.add(StandardWheelActions.ID_EXTRACT);
            }

            if (block instanceof IWheelBlockSupport) {
                ((IWheelBlockSupport) block).updateWheelActions(actions);
            }
        }
    }
}
