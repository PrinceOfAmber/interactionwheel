package mcjty.intwheel.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import mcjty.intwheel.ForgeEventHandlers;
import mcjty.intwheel.InteractionWheel;
import mcjty.intwheel.apiimp.*;
import mcjty.intwheel.config.ConfigSetup;
import mcjty.intwheel.network.PacketHandler;
import mcjty.intwheel.playerdata.PlayerWheelConfiguration;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.concurrent.Callable;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        registerCapabilities();
        PacketHandler.registerMessages("intwheel");

        InteractionWheel.interactionWheelImp.registerProvider(new DefaultWheelActionProvider());

        ConfigSetup.preInit(e);
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        NetworkRegistry.INSTANCE.registerGuiHandler(InteractionWheel.instance, new GuiProxy());

        InteractionWheel.registry.register(new RotateBlockAction());
        InteractionWheel.registry.register(new SearchWheelAction());
        InteractionWheel.registry.register(new DumpWheelAction());
        InteractionWheel.registry.register(new Dump1WheelAction());
        InteractionWheel.registry.register(new DumpSimilarWheelAction());
        InteractionWheel.registry.register(new DumpSimilarInventoryAction());
        InteractionWheel.registry.register(new DumpOresAction());
        InteractionWheel.registry.register(new DumpBlocksAction());
        InteractionWheel.registry.register(new ExtractWheelAction());
        InteractionWheel.registry.register(new PickToolWheelAction());
//        for (int i = 0 ; i < 30 ; i++) {
//            InteractionWheel.registry.register(new DummyWheelAction("std.dummy" + i));
//        }
    }

    public void postInit(FMLPostInitializationEvent e) {
        ConfigSetup.postInit();
    }

    private static void registerCapabilities(){
        CapabilityManager.INSTANCE.register(PlayerWheelConfiguration.class, new Capability.IStorage<PlayerWheelConfiguration>() {

            @Override
            public NBTBase writeNBT(Capability<PlayerWheelConfiguration> capability, PlayerWheelConfiguration instance, EnumFacing side) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void readNBT(Capability<PlayerWheelConfiguration> capability, PlayerWheelConfiguration instance, EnumFacing side, NBTBase nbt) {
                throw new UnsupportedOperationException();
            }

        }, () -> {
            throw new UnsupportedOperationException();
        });
    }

    public World getClientWorld() {
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }

    public <V> ListenableFuture<V> addScheduledTaskClient(Callable<V> callableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }


}
