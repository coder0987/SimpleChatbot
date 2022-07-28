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
	public static final Logger LOGGER = LoggerFactory.getLogger("tutorialmod");

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
				String[] redirect = {"sword","stab"};
				String[] redirected = {"pointy stick","poke"};
				for (int i=0; i<redirect.length;i++) {
					while (sentMessage.contains(redirect[i])) {
						int loc = sentMessage.indexOf(redirect[i]);
						String sentMessageStart = sentMessage.substring(0,loc);
						String sentMessageEnd = redirected[i] + sentMessage.substring(loc + redirect[i].length());
						sentMessage = sentMessageStart + sentMessageEnd;
					}
				}
				if (!sentMessage.equals(message.getString())) {
					mc.player.sendMessage(Text.of("<" + senderName + "> " + sentMessage));
					return ActionResult.FAIL;//Cancel the message send
				}
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
				switch (sentMessage.toLowerCase()) {
					case "hi" -> mc.player.sendMessage(Text.of("Hello There"));
					case "hello there" -> mc.player.sendMessage(Text.of("General Kenobi"));
					case "grievous" -> mc.player.sendMessage(Text.of("So you have chosen death?"));
				}
			}
			return ActionResult.PASS;
		});
		LOGGER.info("Hello Fabric world!");
	}
}
