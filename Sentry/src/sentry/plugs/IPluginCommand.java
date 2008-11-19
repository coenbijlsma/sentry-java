package sentry.plugs;

public interface IPluginCommand {
	
	public IPlugin getPlugin();
	
	public String getName();

	public String getHookPoint();
	
	public void execute();
	
	/**
	 * 
	 * @return An array of Strings, containing the <strong>internal</strong> 
	 * commands this command depends on.
	 */
	public String[] getDependencies();
	
	/**
	 * If execution of the command results in anything, say an socket connection,
	 * you can access it through this method.
	 * @return Object containing the result.
	 */
	public Object getResult();
	
}
