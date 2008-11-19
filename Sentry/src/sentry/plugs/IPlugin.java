package sentry.plugs;

public interface IPlugin {

	public String getName();
	
	public String getHookPoint();
	
	public void execute();
}
