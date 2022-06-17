package net.fabricmc.example.mixin;

import net.fabricmc.example.MessageSendCallback;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatHudListener.class)
public class MessageSendMixin {

	@Inject(at = @At("HEAD"), method = "onChatMessage", cancellable = true)
	private void onSend(MessageType type, Text message, MessageSender sender, CallbackInfo ci) {
		ActionResult result = MessageSendCallback.EVENT.invoker().interact(type, message, sender);

		if(result == ActionResult.FAIL) {
			ci.cancel();
		}
	}
}
