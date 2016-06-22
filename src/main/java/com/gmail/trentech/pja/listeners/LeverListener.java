package com.gmail.trentech.pja.listeners;

import java.util.HashMap;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.gmail.trentech.pja.Main;
import com.gmail.trentech.pja.utils.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class LeverListener {

	public static HashMap<Player, String> creators = new HashMap<>();

	@Listener
	public void onChangeBlockEvent(ChangeBlockEvent.Modify event, @First Player player) {
		for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
			BlockSnapshot block = transaction.getFinal();
			BlockType type = block.getState().getType();

			if (!type.equals(BlockTypes.LEVER)) {
				return;
			}

			if (!block.getExtendedState().get(Keys.POWERED).isPresent()) {
				return;
			}

			Location<World> location = block.getLocation().get();
			String locationName = location.getExtent().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

			ConfigurationNode config = new ConfigManager().getConfig();

			if (config.getNode("Levers", locationName).getString() == null) {
				return;
			}
			String command = config.getNode("Levers", locationName).getString();

			if (!player.hasPermission("pja.lever.interact")) {
				player.sendMessage(Text.of(TextColors.DARK_RED, "you do not have permission to interact with command lever"));
				event.setCancelled(true);
				return;
			}

			Main.getGame().getCommandManager().process(Main.getGame().getServer().getConsole(), command);
		}
	}

	@Listener
	public void onChangeBlockEvent(ChangeBlockEvent.Break event, @First Player player) {
		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();

		for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
			Location<World> location = transaction.getFinal().getLocation().get();
			String locationName = location.getExtent().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

			if (config.getNode("Levers", locationName).getString() == null) {
				continue;
			}

			if (!player.hasPermission("pja.lever.break")) {
				player.sendMessage(Text.of(TextColors.DARK_RED, "you do not have permission to break command lever"));
				event.setCancelled(true);
				return;
			}

			config.getNode("Levers").removeChild(locationName);
			configManager.save();
			player.sendMessage(Text.of(TextColors.DARK_GREEN, "Broke command lever"));
		}
	}

	@Listener
	public void onChangeBlockEvent(ChangeBlockEvent.Place event, @First Player player) {
		if (!creators.containsKey(player)) {
			return;
		}

		for (Transaction<BlockSnapshot> transaction : event.getTransactions()) {
			BlockType type = transaction.getFinal().getState().getType();

			if (!type.equals(BlockTypes.LEVER)) {
				continue;
			}

			Location<World> location = transaction.getFinal().getLocation().get();

			if (!player.hasPermission("pja.lever.place")) {
				player.sendMessage(Text.of(TextColors.DARK_RED, "you do not have permission to place command lever"));
				creators.remove(player);
				event.setCancelled(true);
				return;
			}

			String locationName = location.getExtent().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

			ConfigManager configManager = new ConfigManager();
			ConfigurationNode config = configManager.getConfig();

			String command = creators.get(player);

			config.getNode("Levers", locationName).setValue(command);

			configManager.save();

			player.sendMessage(Text.of(TextColors.DARK_GREEN, "New command lever created"));

			creators.remove(player);
		}
	}
}
