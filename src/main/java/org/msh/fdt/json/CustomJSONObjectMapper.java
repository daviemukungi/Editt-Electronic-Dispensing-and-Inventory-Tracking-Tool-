package org.msh.fdt.json;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class CustomJSONObjectMapper extends ObjectMapper
{		
	public CustomJSONObjectMapper()
	{
		setSerializationInclusion(org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_EMPTY);
		configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
	}
}