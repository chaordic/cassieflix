package br.com.chaordic.cassieflix.core.util;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.datastax.driver.core.Row;
import com.google.common.base.Throwables;

public class MarshallUtils {
	
	public static final Map<String, Object> marshall(Object pojo) {
		Map<String, Object> result = null;
		try {
			result = PropertyUtils.describe(pojo);
		} catch (Throwable t) {
			Throwables.propagate(t);
		}

		result.remove("class"); //we don't want to marshall class
		return result;
	}
	
	public static final <T> T unmarshall(Row row, Class<T> clazz) {
		try {
			T result = clazz.newInstance();
			for (PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(clazz)) {
				if (pd.getReadMethod() != null && pd.getWriteMethod() != null && !"class".equals(pd.getName())) {		
					Object propertyValue = getPropertyFromRow(row, pd.getName(), pd.getPropertyType());
					if (propertyValue != null) {
						pd.getWriteMethod().invoke(result, propertyValue); //invoke property setter
					}					
				}
			}			
			return result;
		} catch (Throwable e) {
			Throwables.propagate(e);
		}
		return null;
	}

	/*
	 * Unfortunately here we have to explicitly support all CQL types,
	 * since the DataStax driver does not support retrieving a generic
	 * property
	 */
	private static Object getPropertyFromRow(Row row, String propertyName, Class<?> propertyType) {
		Object value = null;

		if (propertyType.equals(String.class)){
			value = row.getString(propertyName);
		} else if (propertyType.equals(Integer.class)) {
			value = row.getInt(propertyName);
		} else if (propertyType.equals(Date.class)) {
			value = row.getDate(propertyName);
		}
		
		return value;
	}	
}
