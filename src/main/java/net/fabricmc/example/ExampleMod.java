package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.*;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		MessageSendCallback.EVENT.register((MessageType type, Text message, MessageSender sender) -> {
			//Do something
			MinecraftClient mc = MinecraftClient.getInstance();
			if (mc.player != null && sender != null)
				mc.player.sendMessage(Text.of(sender.name().getString() + " will say: " + message.getString()));
			else if (mc.player != null)
				mc.player.sendMessage(Text.of( "Someone will say: " + message.getString()));
			return ActionResult.PASS;
		});
		MessageReceiveCallback.EVENT.register((MessageType type, Text message, MessageSender sender) -> {
			//Do something
			MinecraftClient mc = MinecraftClient.getInstance();
			if (mc.player != null && sender != null)
				mc.player.sendMessage(Text.of(sender.name().getString() + " said: " + message.getString()));
			else if (mc.player != null)
				mc.player.sendMessage(Text.of( "Someone said: " + message.getString()));
			return ActionResult.PASS;
		});
		LOGGER.info("Hello Fabric world!");
	}
}
