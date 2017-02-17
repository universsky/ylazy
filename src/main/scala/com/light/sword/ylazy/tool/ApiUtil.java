/**
 * 
 */
package com.light.sword.ylazy.tool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * @author jack
 *
 */
public class ApiUtil {
	/**
	 * 获取api方法list
	 * 
	 * @param api
	 * @return
	 */
	public static List<String> getMethodList(String api) {
		List<String> methodNames = new ArrayList<String>(3);
		try {
			Class<?> clz = Class.forName(api);
			Method[] methods = clz.getMethods();
			for (Method m : methods) {
				String name = m.getName();
				methodNames.add(name);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return methodNames;
	}

	public static String[] getParamNames(String api, String method) {
		String[] result = null;
		ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
		try {
			Class<?> clz = Class.forName(api);
			Method[] methods = clz.getMethods();
			for (Method m : methods) {
				String name = m.getName();
				// 获取被测接口的参数对象类型
				if (method.equals(name)) {
					result = pnd.getParameterNames(m);
					System.out.println("获取被测接口的参数对象类型=" + result);
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		// for (String e : result) {
		// System.out.println(e);
		// }

		return result;
	}

	/**
	 * 获取api method的所有参数的类型数组
	 * 
	 * @param api
	 * @param method
	 * @return
	 */
	public static Object[] getParamObject(String api, String method) {
		Object[] result = null;
		try {
			Class<?> clz = Class.forName(api);
			Method[] methods = clz.getMethods();
			for (Method m : methods) {
				String name = m.getName();
				// 获取被测接口的参数对象类型
				if (method.equals(name)) {
					Class<?>[] pclasses = m.getParameterTypes();
					result = new Object[pclasses.length];
					int i = 0;
					for (Class c : pclasses) {
						result[i] = JsonUtil.recursiveBuildObject(c);
						i++;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return result;
	}

}
