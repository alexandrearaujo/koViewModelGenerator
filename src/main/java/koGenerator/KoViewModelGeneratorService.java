package koGenerator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class KoViewModelGeneratorService {
	
	@Autowired
    ResourceLoader resourceLoader;
	
	public <T> void generateViewModelForClass(Class<T> clazz) {
		List<String> lines = new ArrayList<String>();
		String className = clazz.getSimpleName();
    	String viewModelName = "ViewModel" + className;
    	lines.add("function " + viewModelName + "() {");
    	lines.add("	var self = this;");
    	
    	for (Field field : clazz.getDeclaredFields()) {
    		if (field.isAnnotationPresent(Column.class)) {
    			lines.add("	self." + field.getName() + " = ko.observable();");
    		}
    	}
    	
    	lines.add("};");
    	lines.add("");
    	lines.add("window.view" + className + " = new " + viewModelName + "();");
    	lines.add("ko.applyBindings(window.view" + className + ");");
    	
        try {
        	String path = resourceLoader.getResource("classpath:/static/").getURL().getPath();
			Files.write(Paths.get(viewModelName + ".js"),
					lines,
					StandardCharsets.UTF_8,
			        StandardOpenOption.CREATE,
			        StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
