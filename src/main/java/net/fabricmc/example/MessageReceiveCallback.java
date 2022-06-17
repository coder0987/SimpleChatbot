package net.fabricmc.example;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

/**
 *  Callback for players sending messages
 *  Called before the message is processed
 *  Upon return:
 *  - SUCCESS cancels further processing and continues with normal messaging behavior.
 *  - PASS falls back to further processing and defaults to SUCCESS if no other listeners are available
 *  - FAIL cancels further processing and does not send the message.
 */
public interface MessageReceiveCallback {
    Event<MessageReceiveCallback> EVENT = EventFactory.createArrayBacked(MessageReceiveCallback.class,
            (listeners) -> (messageType, message, sender) -> {
                for (MessageReceiveCallback listener : listeners) {
                    ActionResult result = listener.interact(messageType, message, sender);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }
                return ActionResult.PASS;
            });
    ActionResult interact(MessageType type, Text message, MessageSender sender);
}
