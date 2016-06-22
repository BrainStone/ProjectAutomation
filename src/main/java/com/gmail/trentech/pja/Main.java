package com.gmail.trentech.pja;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.gmail.trentech.pja.commands.CommandManager;
import com.gmail.trentech.pja.listeners.ButtonListener;
import com.gmail.trentech.pja.listeners.DoorListener;
import com.gmail.trentech.pja.listeners.LeverListener;
import com.gmail.trentech.pja.listeners.PlateListener;
import com.gmail.trentech.pja.listeners.SignListener;
import com.gmail.trentech.pja.utils.Resource;
import com.gmail.trentech.pja.utils.Tasks;

import me.flibio.updatifier.Updatifier;

@Updatifier(repoName = "ProjectAutomation", repoOwner = "TrenTech", version = Resource.VERSION)
@Plugin(id = Resource.ID, name = Resource.NAME, authors = Resource.AUTHOR, url = Resource.URL, dependencies = { @Dependency(id = "Updatifier", optional = true) })
public class Main {

	private static Game game;
	private static Logger log;
	private static PluginContainer plugin;

	@Listener
	public void onPreInitialization(GamePreInitializationEvent event) {
		game = Sponge.getGame();
		plugin = getGame().getPluginManager().getPlugin(Resource.ID).get();
		log = getPlugin().getLogger();
	}

	@Listener
	public void onInitialization(GameInitializationEvent event) {
		getGame().getCommandManager().register(this, new CommandManager().cmdAuto, "auto", "a");

		getGame().getEventManager().registerListeners(this, new ButtonListener());
		getGame().getEventManager().registerListeners(this, new DoorListener());
		getGame().getEventManager().registerListeners(this, new LeverListener());
		getGame().getEventManager().registerListeners(this, new PlateListener());
		getGame().getEventManager().registerListeners(this, new SignListener());
	}

	@Listener
	public void onStartedServer(GameStartedServerEvent event) {
		new Tasks().start();
	}

	public static Logger getLog() {
		return log;
	}

	public static Game getGame() {
		return game;
	}

	public static PluginContainer getPlugin() {
		return plugin;
	}
}