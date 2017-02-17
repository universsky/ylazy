/**
 * 
 */
package com.light.sword.ylazy.tool;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author jack
 *
 */
public class JsonUtil {
	public static String toJsonString(String api) {
		Class<?> clazz = getClass(api);
		Object object = null;
		try {
			object = buildObject(clazz);
		} catch (SecurityException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return toJsonString(object);
	}

	public static String toJsonString(Class<?> api) {
		Object object = null;
		try {
			object = buildObject(api);
		} catch (SecurityException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return toJsonString(object);
	}

	/**
	 * 递归嵌套build对象
	 * 
	 * @param api
	 * @return
	 */
	public static Object recursiveBuildObject(Class<?> api) {
		Object object = null;
		try {
			object = buildObject(api);
		} catch (SecurityException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return object;
	}

	private static Class<?> getClass(String api) {
		try {
			Class<?> clz = Class.forName(api);
			return clz;
		} catch (Exception e) {
		}
		return null;
	}

	private static final String MAP_KEY_PLACE_HOLDER = "MAP_KEY_PLACE_HOLDER";

	private static String toJsonString(Object obj) {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		return JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty,
				SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullNumberAsZero, SerializerFeature.QuoteFieldNames,
				SerializerFeature.DisableCheckSpecialChar, SerializerFeature.WriteEnumUsingToString);
	}

	private static Object buildObject(Class<?> clazz)
			throws SecurityException, InstantiationException, IllegalAccessException {

		if (isBaseType(clazz))
			return buildBaseType(clazz);

		Object ob = clazz.newInstance();

		Field[] fields = clazz.getDeclaredFields();
		List<Field> selfFields = Arrays.asList(fields);
		List<Field> pFields = new ArrayList<Field>();
		getParentClassFields(pFields, clazz);
		pFields.addAll(selfFields);

		for (Field field : pFields) {
			Class<?> fieldType = field.getType();
			if (isArrayType(fieldType)) {
				Class<?> trueTypeOfArray = fieldType.getComponentType();
				setFieldValue(clazz, field, ob, Array.newInstance(trueTypeOfArray, 0));
			} else if (isListType(fieldType)) {
				Class<?> trueTypeOfList = null;
				Type type = field.getGenericType();
				if (type instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) type;
					trueTypeOfList = (Class<?>) pt.getActualTypeArguments()[0]; // 得到泛型里的class类型对象
				}
				Object object = buildObject(trueTypeOfList);
				List<Object> objectlist = Arrays.asList(new Object[] { object }); // 这里用的arrayList，如果有用linkedlist可能会有问题
				setFieldValue(clazz, field, ob, objectlist);
			} else if (isMapType(fieldType)) {
				ParameterizedType pType = (ParameterizedType) field.getGenericType();
				Class<?> trueTypeOfMap = (Class<?>) pType.getActualTypeArguments()[1];
				Object object = buildObject(trueTypeOfMap);
				Map<String, Object> objectMap = new HashMap<String, Object>();
				objectMap.put(MAP_KEY_PLACE_HOLDER, object);
				setFieldValue(clazz, field, ob, objectMap);
			} else if (!isBaseType(fieldType)) { // 如果是不是基础类型,集合类型，递归调用创建对象
				Object object = buildObject(fieldType);
				setFieldValue(clazz, field, ob, object);
			}

		}
		return ob;
	}


	/**
	 * set field
	 * 
	 * @param clazz
	 * @param field
	 * @param classObject
	 * @param fieldObject
	 */
	private static void setFieldValue(Class<?> clazz, Field field, Object classObject, Object fieldObject) {
		String setMethod = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
		try {
			if (!isArrayType(field.getType())) {
				clazz.getMethod(setMethod, field.getType()).invoke(classObject, fieldObject);
			} else {
				clazz.getMethod(setMethod, field.getType()).invoke(classObject, new Object[] { fieldObject });
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	private static boolean isArrayType(Class<?> type) {
		return type.isArray();
	}

	private static boolean isMapType(Class<?> type) {
		return type.getName().equals("java.util.Map");
	}

	private static boolean isListType(Class<?> type) {
		return type.getName().equals("java.util.List");
	}

	private static boolean isBaseType(Class<?> type) {
		if (type.isPrimitive()) {
			return true;
		} else if (type.isEnum()) {
			return true;
		} else if (type.getTypeName().equals("java.lang.String")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.StringBuffer")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.StringBuilder")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Integer")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Long")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Short")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Byte")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Character")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Double")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Float")) {
			return true;
		} else if (type.getTypeName().equals("java.math.BigDecimal")) {
			return true;
		} else if (type.getTypeName().equals("java.math.BigInteger")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Byte")) {
			return true;
		} else if (type.getTypeName().equals("java.lang.Boolean")) {
			return true;
		} else if (type.getTypeName().equals("java.util.Date")) {
			return true;
		} else if (type.getTypeName().equals("java.io.InputStream")) {
			return true;
		}
		return false;
	}


	private static Object buildBaseType(Class<?> type) {
		if (type.isPrimitive()) {
			return 0;
		} else if (type.isEnum()) {
			return null;
		} else if (type.getTypeName().equals("java.lang.String")) {
			return "";
		} else if (type.getTypeName().equals("java.lang.StringBuffer")) {
			return "";
		} else if (type.getTypeName().equals("java.lang.StringBuilder")) {
			return "";
		} else if (type.getTypeName().equals("java.lang.Integer")) {
			return 0;
		} else if (type.getTypeName().equals("java.lang.Long")) {
			return 0;
		} else if (type.getTypeName().equals("java.lang.Short")) {
			return 0;
		} else if (type.getTypeName().equals("java.lang.Byte")) {
			return 0;
		} else if (type.getTypeName().equals("java.lang.Character")) {
			return "";
		} else if (type.getTypeName().equals("java.lang.Double")) {
			return 0.0;
		} else if (type.getTypeName().equals("java.lang.Float")) {
			return 0.0;
		} else if (type.getTypeName().equals("java.math.BigDecimal")) {
			return 0.0;
		} else if (type.getTypeName().equals("java.math.BigInteger")) {
			return 0;
		} else if (type.getTypeName().equals("java.lang.Byte")) {
			return 0;
		} else if (type.getTypeName().equals("java.lang.Boolean")) {
			return false;
		} else if (type.getTypeName().equals("java.util.Date")) {
			return new Date();
		} else if (type.getTypeName().equals("java.io.InputStream")) {
			return true;
		}
		return null;
	}
	
	/**
	 * 获取一个类及其父类的成员变量类型
	 * 
	 * @param fieldClassList
	 * @param clazz
	 * @return
	 */
	private static List<Field> getParentClassFields(List<Field> fieldClassList, Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			fieldClassList.add(field);
		}
		if (clazz.getSuperclass() == null) {
			return fieldClassList;
		}
		getParentClassFields(fieldClassList, clazz.getSuperclass());
		return fieldClassList;
	}
}
