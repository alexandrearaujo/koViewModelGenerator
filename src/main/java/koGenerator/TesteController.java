package koGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TesteController {
	
	private final KoViewModelGeneratorService koViewModelGeneratorService;
	
	@Autowired
	public TesteController(KoViewModelGeneratorService koViewModelGeneratorService) {
		this.koViewModelGeneratorService = koViewModelGeneratorService;
	}
	
	@RequestMapping("/")
    public String index() {
		koViewModelGeneratorService.generateViewModelForClass(TipoFrequencia.class);
		return "index";
    }

}
