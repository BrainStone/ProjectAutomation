package com.gmail.trentech.pja.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import com.gmail.trentech.pja.Main;
import com.gmail.trentech.pja.Resource;
import com.gmail.trentech.pja.Tasks;

public class CMDReload implements CommandExecutor {

	public CommandSpec cmdReload = CommandSpec.builder().description(Texts.of("Reload plugin!")).permission("pja.cmd.auto.reload").executor(this).build();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		for(Task t : Main.getGame().getScheduler().getScheduledTasks()){
			if(t.getName().contains(Resource.NAME)){
				t.cancel();
			}
		}
		new Tasks().start();

		src.sendMessage(Texts.of(TextColors.DARK_GREEN, "Reloaded"));
		
		return CommandResult.success();
	}

}
