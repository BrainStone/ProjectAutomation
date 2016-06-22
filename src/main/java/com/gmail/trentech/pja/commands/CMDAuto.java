package com.gmail.trentech.pja.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList.Builder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.Main;

public class CMDAuto implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Builder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();

		pages.title(Text.builder().color(TextColors.DARK_GREEN).append(Text.of(TextColors.AQUA, "Command List")).build());

		List<Text> list = new ArrayList<>();

		if (src.hasPermission("pja.cmd.auto.create")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information "))).onClick(TextActions.runCommand("/auto help Create")).append(Text.of(" /auto create")).build());
		}
		if (src.hasPermission("pja.cmd.auto.delete")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information "))).onClick(TextActions.runCommand("/auto help Delete")).append(Text.of(" /auto delete")).build());
		}
		if (src.hasPermission("pja.cmd.auto.edit")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information "))).onClick(TextActions.runCommand("/auto help Edit")).append(Text.of(" /auto edit")).build());
		}
		if (src.hasPermission("pja.cmd.auto.reload")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information "))).onClick(TextActions.runCommand("/auto help Reload")).append(Text.of(" /auto reload")).build());
		}
		if (src.hasPermission("pja.cmd.auto.list")) {
			list.add(Text.builder().color(TextColors.GREEN).onHover(TextActions.showText(Text.of("Click command for more information "))).onClick(TextActions.runCommand("/auto help List")).append(Text.of(" /auto list")).build());
		}

		pages.contents(list);

		pages.sendTo(src);

		return CommandResult.success();
	}

}
