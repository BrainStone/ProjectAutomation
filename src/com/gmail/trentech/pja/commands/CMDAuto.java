package com.gmail.trentech.pja.commands;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.Main;

public class CMDAuto implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		PaginationBuilder pages = Main.getGame().getServiceManager().provide(PaginationService.class).get().builder();
		
		pages.title(Texts.builder().color(TextColors.DARK_GREEN).append(Texts.of(TextColors.AQUA, "Command List")).build());
		
		List<Text> list = new ArrayList<>();
		
		if(src.hasPermission("pja.cmd.auto.create")) {
			list.add(Texts.builder().color(TextColors.GREEN).onHover(TextActions.showText(Texts.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help Create")).append(Texts.of(" /auto create")).build());
		}
		if(src.hasPermission("pja.cmd.auto.delete")) {
			list.add(Texts.builder().color(TextColors.GREEN).onHover(TextActions.showText(Texts.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help Delete")).append(Texts.of(" /auto delete")).build());
		}
		if(src.hasPermission("pja.cmd.auto.edit")) {
			list.add(Texts.builder().color(TextColors.GREEN).onHover(TextActions.showText(Texts.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help Edit")).append(Texts.of(" /auto edit")).build());
		}
		if(src.hasPermission("pja.cmd.auto.reload")) {
			list.add(Texts.builder().color(TextColors.GREEN).onHover(TextActions.showText(Texts.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help Reload")).append(Texts.of(" /auto reload")).build());
		}
		if(src.hasPermission("pja.cmd.auto.list")) {
			list.add(Texts.builder().color(TextColors.GREEN).onHover(TextActions.showText(Texts.of("Click command for more information ")))
					.onClick(TextActions.runCommand("/auto help List")).append(Texts.of(" /auto list")).build());
		}

		pages.contents(list);
		
		pages.sendTo(src);
		
		return CommandResult.success();		
	}

}
