package sentry.plugs;

import java.io.File;
import java.net.MalformedURLException;

/**
 * 
 * @author Coen Bijlsma
 * @since 2008-11-19
 * @version 0.1
 * 
 *
 */
public class PluginFactory {

	public static SimplePlugin loadFromFile(String filename){
		return null;
	}
	
	public static IPlugin loadFromJar(String jarFile){
		try{
			JarClassLoader classLoader = new JarClassLoader(new File(jarFile).toURI().toURL());
			Class clazz = classLoader.loadClass("Plugin");
			return (IPlugin)clazz.newInstance();
		}catch(MalformedURLException mfe){
			mfe.printStackTrace(System.err);
		}catch(ClassNotFoundException ce){
			ce.printStackTrace(System.err);
		}catch(IllegalAccessException iae){
			iae.printStackTrace(System.err);
		}catch(InstantiationException ie){
			ie.printStackTrace(System.err);
		}
		return null;
	}
}
