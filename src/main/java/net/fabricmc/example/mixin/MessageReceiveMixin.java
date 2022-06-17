package net.fabricmc.example.mixin;

import net.fabricmc.example.MessageReceiveCallback;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHudListener.class)
public class MessageReceiveMixin {

	@Inject(at = @At("TAIL"), method = "onChatMessage", cancellable = true)
	private void onSend(MessageType type, Text message, MessageSender sender, CallbackInfo ci) {
		ActionResult result = MessageReceiveCallback.EVENT.invoker().interact(type, message, sender);

		if(result == ActionResult.FAIL) {
			ci.cancel();
		}
	}
}
