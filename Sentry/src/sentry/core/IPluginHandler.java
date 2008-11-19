package sentry.core;

import sentry.core.exceptions.NoSuchHookPointException;
import sentry.plugs.IPlugin;

public interface IPluginHandler {

	/**
	 * 
	 * @param plugin
	 *            The <code>IPlugin</code> to attach to the
	 *            <code>HookPoint</code>.
	 * @return boolean Whether the attaching succeeded.
	 * @throws NoSuchHookPointException
	 *             If the requested <code>HookPoint</code> doesn't exist.
	 * @see HookPoint
	 */
	public boolean attachPluginCommands(IPlugin plugin)
			throws NoSuchHookPointException;
}
