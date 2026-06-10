package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ExampleMod implements ModInitializer {
    public static final String MOD_ID = "modid";
    
    private static KeyBinding toggleKeyBinding;
    private static boolean isEnabled = true;

    @Override
    public void onInitialize() {
        toggleKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.autosprint.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V, 
                "category.autosprint"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (toggleKeyBinding.wasPressed()) {
                isEnabled = !isEnabled;
                if (isEnabled) {
                    client.player.sendMessage(Text.literal("§aAutoSprint: Включен"), true);
                } else {
                    client.player.sendMessage(Text.literal("§cAutoSprint: Выключен"), true);
                }
            }

            if (isEnabled && client.options.forwardKey.isPressed()) {
                client.options.sprintKey.setPressed(true);
            }
        });
    }
}
