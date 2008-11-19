package sentry.core;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

import sentry.core.exceptions.NoSuchHookPointException;
import sentry.plugs.IPlugin;
import sentry.plugs.JarClassLoader;

public class Sentry implements IPluginHandler {

	/*
	 * The core properties file 
	 */
	private Properties _properties;
	
	/*
	 * The available hook points
	 */
	private HashMap<String, HookPoint> _hookPoints;
	
	public Sentry(){
		_hookPoints = new HashMap<String, HookPoint>();
		_initHookPoints();
		_fetchPlugins();
		
		/* Execute the post_startup plugins*/
		_executePluginsFrom(_getHookPointByName("post_startup"));
	}
	
	/*
	 * Creates the available hooks a plug-in can hook to.
	 */
	private void _initHookPoints(){
		_hookPoints.put("post_join_user", new HookPoint("post_join_user"));
		_hookPoints.put("post_startup", new HookPoint("post_startup"));
	}
	
	private void _fetchPlugins(){
		try{
			File f = new File("sentry_irc_plugin.jar");
			JarClassLoader loader = new JarClassLoader(f.toURI().toURL());
			IPlugin[] plugins = loader.getPlugins();
			
			if(plugins != null){
				for(int i = 0; i < plugins.length; i++){
					IPlugin plugin = plugins[i];
					if(attachPluginTo(plugin.getHookPoint(), plugin)){
						System.out.println("Succes loading plugin " + plugin.getName());
					}else{
						System.err.println("Could not load plugin " + plugin.getName());
					}
				}
			}
		}catch(MalformedURLException mue){
			mue.printStackTrace(System.err);
		}catch(NoSuchHookPointException nshe){
			nshe.printStackTrace(System.err);
		}
	}
	
	private HookPoint _getHookPointByName(String name){
		return _hookPoints.get(name);
	}
	
	private void _executePluginsFrom(HookPoint hookPoint){
		if(hookPoint == null){
			return;
		}
		Collection<IPlugin> plugs = hookPoint.getAttachedPlugins();
		
		for(IPlugin plug : plugs){
			plug.execute();
		}
	}

	@Override
	public boolean attachPluginTo(String hook, IPlugin plugin) throws NoSuchHookPointException{
		/* First, check if the hookPoint does exist */
		HookPoint hookPoint = _hookPoints.get(hook);
		
		if(hookPoint == null){
			throw new NoSuchHookPointException("No such hookpoint: " + hook);
		}
		
		return hookPoint.attachPlugin(plugin);
	}
}
