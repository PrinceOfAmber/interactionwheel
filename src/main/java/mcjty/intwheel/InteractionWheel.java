package mcjty.intwheel;


import mcjty.intwheel.api.IInteractionWheel;
import mcjty.intwheel.apiimp.InteractionWheelImp;
import mcjty.intwheel.apiimp.WheelActionRegistry;
import mcjty.intwheel.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Function;

@Mod(modid = InteractionWheel.MODID, name = InteractionWheel.MODNAME,
        dependencies =
                "after:forge@[" + InteractionWheel.MIN_FORGE11_VER + ",)",
        acceptedMinecraftVersions = "[1.12,1.13)",
        version = InteractionWheel.VERSION)
public class InteractionWheel {

    public static final String MODID = "intwheel";
    public static final String MODNAME = "Interaction Wheel";
    public static final String VERSION = "1.2.7";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";

    @SidedProxy(clientSide = "mcjty.intwheel.proxy.ClientProxy", serverSide = "mcjty.intwheel.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static InteractionWheel instance;
    public static InteractionWheelImp interactionWheelImp = new InteractionWheelImp();

    public static Logger logger;

    public static WheelActionRegistry registry = new WheelActionRegistry();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equalsIgnoreCase("getTheWheel")) {
                Optional<Function<IInteractionWheel, Void>> value = message.getFunctionValue(IInteractionWheel.class, Void.class);
                if (value.isPresent()) {
                    value.get().apply(interactionWheelImp);
                } else {
                    logger.warn("Some mod didn't return a valid result with getTheWheel!");
                }
            }
        }
    }

}
