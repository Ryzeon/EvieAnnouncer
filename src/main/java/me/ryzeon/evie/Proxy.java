package me.ryzeon.evie;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public final class Proxy extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().registerChannel("rUHC");
        getProxy().getPluginManager().registerListener(this,this);
        getLogger().info("§b+-------------------------+");
        getLogger().info("§fEvie§bAnnouncer §7| §aEnabled");
        getLogger().info("§fAuthor: "+getDescription().getAuthor());
        getLogger().info("§fVersion: §av§b"+ getDescription().getVersion());
        getLogger().info("§b+-------------------------+");
    }
    @EventHandler
    public void onRecibeAler(PluginMessageEvent e) {
        String messages;
        if (!e.getTag().equals("rUHC")) {
            return;
        }
        Server server = (Server) e.getSender();
        ByteArrayInputStream stream = new ByteArrayInputStream(e.getData());
        DataInputStream in = new DataInputStream(stream);
        try {
            messages = in.readUTF();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            getLogger().info("§cError to read message.");
            return;
        }
        String[] strings = messages.split(";");
        String alert  = strings[0];
        int seconds = Integer.parseInt(strings[1]);
        int minutes = seconds / 60;
        String s = seconds > 60 ? (minutes + " §fminutes") : (seconds + " §fseconds");
        if (alert.equals("WHITELIST")){
            TextComponent potito = new TextComponent("");
            TextComponent pene = new TextComponent("");
            pene = new TextComponent("§b§l"+server.getInfo().getName()+ " §fUHC Game on §b"+server.getInfo().getName() + " §fopens in §b" + s + " §7[§fClick to join!§7]");
            pene.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server "+server.getInfo().getName()));
            pene.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7[§fJoin to §bEvie§fUHC§7]")).create()));
            potito.addExtra((BaseComponent) pene);
            getProxy().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.sendMessage((BaseComponent) potito));
        } else {
            TextComponent potito = new TextComponent("");
            TextComponent pene = new TextComponent("");
            pene = new TextComponent("§b§l"+server.getInfo().getName()+ " §fUHC Game on §b"+server.getInfo().getName() + " §fstarts in §b" + s + " §7[§fClick to join!§7]");
            pene.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server "+server.getInfo().getName()));
            pene.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("§7[§fJoin to §bEvie§fUHC§7]")).create()));
            potito.addExtra((BaseComponent) pene);
            getProxy().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.sendMessage((BaseComponent) potito));
        }
    }
    @Override
    public void onDisable() {
        getLogger().info("§b+-------------------------+");
        getLogger().info("§fEvie§bAnnouncer §7| §cDisabled");
        getLogger().info("§b+-------------------------+");
    }
}
