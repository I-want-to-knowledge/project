package com.system.monitoring.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 
 *
 * @author YanZhen
 * 2018-09-14 16:51:40
 * GetProperties
 */
public class GetProperties {

	 /**
   * 获取属性配置文件的内容
   * 
   * @param name 配置文件的名称
   * @return 配置文件的内容
   */
  public static Map<String, String> getConfig(String name) {
    
    Map<String, String> result = new HashMap<>();
    
    ResourceBundle bundle = ResourceBundle.getBundle(name);
    
    if (bundle != null) {
       Set<String> keys = bundle.keySet();
       for (String key : keys) {
         result.put(key, bundle.getString(key));
       }
    }
    return result;
  }
}
