package org.msh.fdt.json;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

public class CustomJSONMessageConverter extends MappingJacksonHttpMessageConverter 
{
	
	private static final ThreadLocal<Map<Class<?>,Class<?>>> mixInAnnotations = new ThreadLocal<Map<Class<?>, Class<?>>>();	
	
	/**
	 * @author noor 
	 * @disclaimer 
	 * 		   By completely overriding writeInternal method I am sure that
	 *         object mapper will have the right views before JSON
	 *         serialization in multi-threaded scenario.
	 */
	 
	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		CustomJSONObjectMapper  mapper = new CustomJSONObjectMapper();
	
		if(mixInAnnotations.get()!=null)
		{
			Set<Class<?>> targets =  mixInAnnotations.get().keySet();
			for (Class<?> target : targets) {
				mapper.getSerializationConfig().addMixInAnnotations(target,mixInAnnotations.get().get(target));
			}			
		}		
		
		JsonEncoding encoding = getEncoding(outputMessage.getHeaders().getContentType());
		JsonGenerator jsonGenerator = mapper.getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
		
		try {
			mapper.writeValue(jsonGenerator, o);
		}
		catch (JsonGenerationException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
		
		if(mixInAnnotations.get()!=null)
		{
			mixInAnnotations.set(null);
			mixInAnnotations.remove();
		}
	}
	
	private JsonEncoding getEncoding(MediaType contentType) {
		if (contentType != null && contentType.getCharSet() != null) {
			Charset charset = contentType.getCharSet();
			for (JsonEncoding encoding : JsonEncoding.values()) {
				if (charset.name().equals(encoding.getJavaName())) {
					return encoding;
				}
			}
		}
		return JsonEncoding.UTF8;
	}

	public static void addMixInAnnotations(Class<?> target, Class<?> view) 
	{
		if(mixInAnnotations.get()==null)
			mixInAnnotations.set(new HashMap<Class<?>, Class<?>>(10));
		mixInAnnotations.get().put(target, view);
	}
}
