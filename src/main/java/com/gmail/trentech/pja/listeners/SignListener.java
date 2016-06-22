package com.gmail.trentech.pja.listeners;

import java.util.HashMap;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.gmail.trentech.pja.Main;
import com.gmail.trentech.pja.utils.ConfigManager;

import ninja.leaping.configurate.ConfigurationNode;

public class SignListener {

	public static HashMap<Player, String> creators = new HashMap<>();

	@Listener
	public void onChangeSignEvent(ChangeSignEvent event, @First Player player) {
		if (!creators.containsKey(player)) {
			return;
		}
		String command = creators.get(player);

		if (!player.hasPermission("pja.sign.place")) {
			player.sendMessage(Text.of(TextColors.DARK_RED, "You do not have permission to place command sign"));
			creators.remove(player);
			event.setCancelled(true);
			return;
		}

		Location<World> location = event.getTargetTile().getLocation();
		String locationName = location.getExtent().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

		ConfigManager configManager = new ConfigManager();
		ConfigurationNode config = configManager.getConfig();

		config.getNode("Signs", locationName).setValue(command);

		configManager.save();

		creators.remove(player);

		player.sendMessage(Text.of(TextColors.DARK_GREEN, "New command sign created"));
	}

	@Listener
	public void onInteractBlockEvent(InteractBlockEvent.Secondary event, @First Player player) {
		if (!(event.getTargetBlock().getState().getType().equals(BlockTypes.WALL_SIGN) || event.getTargetBlock().getState().getType().equals(BlockTypes.STANDING_SIGN))) {
			return;
		}

		Location<World> location = event.getTargetBlock().getLocation().get();
		String locationName = location.getExtent().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

		ConfigurationNode config = new ConfigManager().getConfig();

		if (config.getNode("Signs", locationName).getString() == null) {
			return;
		}

		String command = config.getNode("Signs", locationName).getString();

		if (!player.hasPermission("pja.sign.interact")) {
			player.sendMessage(Text.of(TextColors.DARK_RED, "you do not have permission to interact with command sign"));
			event.setCancelled(true);
			return;
		}

		CommandManager commandManager = Main.getGame().getCommandManager();

		commandManager.process(Main.getGame().getServer().getConsole(), command);
	}

	@Listener
	public void onChangeBlockEvent(ChangeBlockEvent.Break event, @First Player player) {
		for (Transaction<BlockSnapshot> blockTransaction : event.getTransactions()) {
			BlockState block = blockTransaction.getOriginal().getState();

			Location<World> location = blockTransaction.getOriginal().getLocation().get();

			String locationName = location.getExtent().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ();

			if (!block.getType().equals(BlockTypes.WALL_SIGN) && !block.getType().equals(BlockTypes.STANDING_SIGN)) {
				return;
			}

			ConfigManager configManager = new ConfigManager();
			ConfigurationNode config = configManager.getConfig();

			if (config.getNode("Signs", locationName).getString() == null) {
				return;
			}

			if (!player.hasPermission("pja.sign.break")) {
				player.sendMessage(Text.of(TextColors.DARK_RED, "You do not have permission to break command sign"));
				event.setCancelled(true);
				return;
			}

			config.getNode("Signs").removeChild(locationName);
			configManager.save();
			player.sendMessage(Text.of(TextColors.DARK_GREEN, "Broke command pressure plate"));
		}
	}
}
