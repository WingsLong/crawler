package crawler.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Handsomeli
 *
 */
public class ReflectionOperator {
	
	/**
	 * Call the method of the object by the given methodName and parameters.
	 * @param obj:It can be a class instance or a string that represent a class.
	 * @param methodName
	 * @param parameterTypes
	 * @param parameterValues
	 * @return
	 */
	public static Object methodReflect(Object obj,String methodName,Class[] parameterTypes,Object[] parameterValues)
	{
		if(obj!=null)
		{
			Class c = null;
			if(obj instanceof String)//maybe it's a class with static methods or fields
			{
				try {
					c = Class.forName((String)obj);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			else
				c = obj.getClass();
			
			try {
				Method m = c.getMethod(methodName,parameterTypes);
				try {
					return m.invoke(obj,parameterValues);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
