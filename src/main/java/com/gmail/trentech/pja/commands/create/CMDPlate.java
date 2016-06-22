package com.gmail.trentech.pja.commands.create;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.listeners.PlateListener;

public class CMDPlate implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!(src instanceof Player)) {
			src.sendMessage(Text.of(TextColors.DARK_RED, "Must be a player"));
			return CommandResult.empty();
		}
		Player player = (Player) src;

		if (!args.hasAny("command")) {
			src.sendMessage(Text.of(TextColors.YELLOW, "/auto create plate <command>"));
			return CommandResult.empty();
		}
		String command = args.<String> getOne("command").get();

		PlateListener.creators.put(player, command);

		src.sendMessage(Text.of(TextColors.DARK_GREEN, "Place pressure plate to create command pressure plate"));

		return CommandResult.success();
	}

}
