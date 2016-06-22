package com.gmail.trentech.pja.commands;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import com.gmail.trentech.pja.commands.create.CMDButton;
import com.gmail.trentech.pja.commands.create.CMDCreate;
import com.gmail.trentech.pja.commands.create.CMDDoor;
import com.gmail.trentech.pja.commands.create.CMDLever;
import com.gmail.trentech.pja.commands.create.CMDPlate;
import com.gmail.trentech.pja.commands.create.CMDSign;

public class CommandManager {

	private CommandSpec cmdButton = CommandSpec.builder().description(Text.of("Create new command button")).permission("pja.cmd.auto.create.button").arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("command")))).executor(new CMDButton()).build();

	private CommandSpec cmdDoor = CommandSpec.builder().description(Text.of("Create new command door")).permission("pja.cmd.auto.create.door").arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("command")))).executor(new CMDDoor()).build();

	private CommandSpec cmdLever = CommandSpec.builder().description(Text.of("Create new command lever")).permission("pja.cmd.auto.create.lever").arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("command")))).executor(new CMDLever()).build();

	private CommandSpec cmdPlate = CommandSpec.builder().description(Text.of("Create new command pressure plate")).permission("pja.cmd.auto.create.plate").arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("command")))).executor(new CMDPlate()).build();

	private CommandSpec cmdSign = CommandSpec.builder().description(Text.of("Create new command sign")).permission("pja.cmd.auto.create.sign").arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("command")))).executor(new CMDSign()).build();
	private CommandSpec cmdCreate = CommandSpec.builder().description(Text.of("Create new scheduled command")).permission("pja.cmd.auto.create").child(cmdButton, "button", "b").child(cmdDoor, "door", "d").child(cmdLever, "lever", "l").child(cmdPlate, "plate", "p").child(cmdSign, "sign", "s").arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))), GenericArguments.optional(GenericArguments.string(Text.of("time"))), GenericArguments.optional(GenericArguments.string(Text.of("command"))), GenericArguments.optional(GenericArguments.string(Text.of("repeat")))).executor(new CMDCreate()).build();

	private CommandSpec cmdDelete = CommandSpec.builder().description(Text.of("delete scheduled command")).permission("pja.cmd.auto.delete").arguments(GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("name")))).executor(new CMDDelete()).build();

	private CommandSpec cmdEdit = CommandSpec.builder().description(Text.of("Edit a scheduled command")).permission("pja.cmd.auto.edit").arguments(GenericArguments.optional(GenericArguments.string(Text.of("name"))), GenericArguments.optional(GenericArguments.string(Text.of("arg0"))), GenericArguments.optional(GenericArguments.string(Text.of("arg1"))), GenericArguments.optional(GenericArguments.string(Text.of("arg2")))).executor(new CMDEdit()).build();

	private CommandSpec cmdReload = CommandSpec.builder().description(Text.of("reload schedulers")).permission("pja.cmd.auto.reload").executor(new CMDReload()).build();

	private CommandSpec cmdList = CommandSpec.builder().description(Text.of("list all tasks")).permission("pja.cmd.auto.list").executor(new CMDList()).build();

	private CommandSpec cmdHelp = CommandSpec.builder().description(Text.of("i need help")).permission("pja.cmd.help").arguments(GenericArguments.optional(GenericArguments.string(Text.of("command")))).executor(new CMDHelp()).build();

	public CommandSpec cmdAuto = CommandSpec.builder().description(Text.of("Base command")).permission("pja.cmd.auto").child(cmdCreate, "create", "c").child(cmdDelete, "delete", "d").child(cmdEdit, "edit", "e").child(cmdReload, "reload", "r").child(cmdList, "list", "l").child(cmdHelp, "help").executor(new CMDAuto()).build();
}
