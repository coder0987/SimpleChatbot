package net.fabricmc.example;

import com.mojang.brigadier.Message;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.event.GameEvent;

/**
 *  Callback for players sending messages
 *  Called before the message is processed
 *  Upon return:
 *  - SUCCESS cancels further processing and continues with normal messaging behavior.
 *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 *  - FAIL cancels further processing and does not send the message.
 */
public interface MessageSendCallback {
    Event<MessageSendCallback> EVENT = EventFactory.createArrayBacked(MessageSendCallback.class,
            (listeners) -> (messageType, message, sender) -> {
                for (MessageSendCallback listener : listeners) {
                    ActionResult result = listener.interact(messageType, message, sender);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });

    ActionResult interact(MessageType type, Text message, MessageSender sender);
}
