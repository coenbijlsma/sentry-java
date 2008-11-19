package sentry.core;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import sentry.core.exceptions.NoSuchHookPointException;
import sentry.log.Log;
import sentry.plugs.IPlugin;
import sentry.plugs.IPluginCommand;
import sentry.plugs.JarClassLoader;

public class Sentry implements IPluginHandler {

	/*
	 * The core properties file 
	 */
	private Properties _properties;
	
	/*
	 * The logfile
	 */
	private Log _log;
	
	/*
	 * The active plugins.
	 */
	private HashMap<String, IPlugin> _plugins;
	
	/*
	 * The available hook points
	 */
	private HashMap<String, HookPoint> _hookPoints;
	
	public Sentry(){
		_log = new Log("sentry.log");
		_log.log("Starting Sentry...", Log.LOG_DEBUG);
		_hookPoints = new HashMap<String, HookPoint>();
		_plugins = new HashMap<String, IPlugin>();
		_initHookPoints();
		_fetchPlugins();
		
		/* Execute the post_startup plugins*/
		_executeCommandsFrom(_getHookPointByName("post_startup"));
	}
	
	/*
	 * Creates the available hooks a plug-in can hook to.
	 */
	private void _initHookPoints(){
		_hookPoints.put("post_join_user", new HookPoint("post_join_user"));
		_hookPoints.put("post_startup", new HookPoint("post_startup"));
	}
	
	/*
	 * Tries to read all the jar files in the current directory, and retrieve
	 * the IPlugins contained in each jar file.
	 */
	private void _fetchPlugins(){
		/* Create a file that represents the current directory */
		File dir = new File(".");
		
		/* Create a filter so that only jar-files will show up */
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name){
				File file = new File(name);
				return (name.endsWith(".jar") && file.isFile());
			}
		};
		
		/* And retrieve the files */
		File[] files = dir.listFiles(filter);
		
		/*
		 * And retrieve the plugins from each file.
		 */
		for(int i = 0; i < files.length; i++){
			try{
				File f = files[i];
				JarClassLoader loader = new JarClassLoader(f.toURI().toURL());
				IPlugin[] plugins = loader.getPlugins();
				
				if(plugins != null){
					for(int j = 0; j < plugins.length; j++){
						IPlugin plugin = plugins[j];
						
						/* Add the plugin to the list of active plugins */
						_plugins.put(plugin.getName(), plugin);
						
						/* And attach its commands to the correct hook */
						if(attachPluginCommands(plugin)){
							_log.log("Succesfully loaded plugin " + plugin.getName(), Log.LOG_DEBUG);
						}else{
							_log.log("Could not load plugin " + plugin.getName(), Log.LOG_ERROR);
						}
					}
				}
			}catch(MalformedURLException mue){
				_log.log(mue.getMessage(), Log.LOG_ERROR);
			}catch(NoSuchHookPointException nshe){
				_log.log(nshe.getMessage(), Log.LOG_ERROR);
			}
		}
		
	}
	
	/*
	 * Fetches the hookpoint that is identified by the given name.
	 */
	private HookPoint _getHookPointByName(String name){
		return _hookPoints.get(name);
	}
	
	/*
	 * Executes the plug-ins that are attached to the <code>HookPoint</code>
	 */
	private void _executeCommandsFrom(HookPoint hookPoint){
		if(hookPoint == null){
			return;
		}
		Collection<IPluginCommand> commands = hookPoint.getAttachedCommands();
		
		for(IPluginCommand cmd : commands){
			cmd.execute();
		}
	}

	@Override
	/**
	 * @param hook The name of the <code>HookPoint</code> to attach to.
	 * @param plugin The IPlugin that has to be attached to the <code>HookPoint</code>.
	 */
	public boolean attachPluginCommands(IPlugin plugin) throws NoSuchHookPointException{
		Collection<IPluginCommand> commands = plugin.getCommands();
		
		for(IPluginCommand command: commands){
			HookPoint hookPoint = _getHookPointByName(command.getHookPoint());
			hookPoint.attachPluginCommand(command);
		}
		return true;
	}
}
