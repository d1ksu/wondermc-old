package pl.wondermc.boxpvp.helper;

import dev.triumphteam.gui.components.util.Legacy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ChatHelper {

    private ChatHelper(){
    }

    public static String fixColor(String text){
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", "»"));
    }

    public static List<String> fixColor(final List<String> messages) {
        List<String> list = new ArrayList<>();
        for (String message : messages) {
            list.add(fixColor(message));
        }
        return list;
    }



}
