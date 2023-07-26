package com.xk.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

public class JsonMap {

	/**
	 * ********************************************************
	 * @Title: jsonToMap
	 * @Description: String 类型的 json 转  Map 对象
	 * @param jsonStr
	 * @return Map
	 * @author DoDo
	 * @date 2016-9-21 下午10:22:41
	 ********************************************************
	 */
	public static Map<String,String> jsonToMap(String jsonStr){
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		Iterator<String> nameItr = jsonObj.keys();
		String name;
		Map<String, String> outMap = new HashMap<String, String>();
		
		while (nameItr.hasNext()) {
			name = nameItr.next();
			outMap.put(name, jsonObj.getString(name));
		}
		return outMap;
	}
	/**
	 * ********************************************************
	 * @Title: mapToJson
	 * @Description: String 类型的 Map 转  json 对象
	 * @param jsonStr
	 * @date 2016-9-21 下午10:22:41
	 ********************************************************
	 */
	public static String mapToJson(Map<String, Object> map) {
		Set<String> keys = map.keySet();
		String key = "";
		Object value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = (Object) map.get(key);
			jsonBuffer.append(value);
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		return jsonBuffer.toString();
	}

	public static  Map<String, String> getValue(String param) {
		Map map = new HashMap();
		String str = "";
		String key = "";
		Object value = "";
		char[] charList = param.toCharArray();
		boolean valueBegin = false;
		for (int i = 0; i < charList.length; i++) {
			char c = charList[i];
			if (c == '{') {
				if (valueBegin == true) {
					value = getValue(param.substring(i, param.length()));
					i = param.indexOf('}', i) + 1;
					map.put(key, value);
				}
			} else if (c == '=') {
				valueBegin = true;
				key = str;
				str = "";
			} else if (c == ',') {
				valueBegin = false;
				value = str;
				str = "";
				map.put(key, value);
			} else if (c == '}') {
				if (str != "") {
					value = str;
				}
				map.put(key, value);
				return map;
			} else if (c != ' ') {
				str += c;
			}
		}
		return map;
	}

}
