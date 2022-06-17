package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.*;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Chatbot implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		MessageSendCallback.EVENT.register((MessageType type, Text message, MessageSender sender) -> {
			//Do something
			MinecraftClient mc = MinecraftClient.getInstance();
			String senderName = "Someone";
			if (sender != null)
				senderName = sender.name().getString();

			String sentMessage = message.getString();

			if (mc.player != null) {
				//This is where the chat bot goes
				mc.player.sendMessage(Text.of( senderName + " will say: " + sentMessage));
				//To send a message, use the above format
				//mc.player.sendMessage is the function, start with that
				//Then, open parentheses and insert the message in Text.of("message") style, before closing the parentheses
				//It should look like
				//mc.player.sendMessage(Text.of("Message"));
				//Try starting by just uncommenting that line (delete the two slashes behind it) and changing what Message is, to something like Hello World or Hi
			}
			return ActionResult.PASS;
		});
		MessageReceiveCallback.EVENT.register((MessageType type, Text message, MessageSender sender) -> {
			//Do something
			MinecraftClient mc = MinecraftClient.getInstance();
			String sentMessage = message.getString();
			String senderName = "Someone";
			if (sender != null)
				senderName = sender.name().getString();
			if (mc.player != null) {
				mc.player.sendMessage(Text.of( senderName + " said: " + sentMessage));
			}
			return ActionResult.PASS;
		});
		LOGGER.info("Hello Fabric world!");
	}
}
