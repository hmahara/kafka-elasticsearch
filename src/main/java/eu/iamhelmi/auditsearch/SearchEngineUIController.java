package eu.iamhelmi.auditsearch;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import eu.iamhelmi.auditsearch.document.UserAccountDocument;
import eu.iamhelmi.auditsearch.repository.ElasticSearchQuery;

@Controller
public class SearchEngineUIController {

	@Autowired
    private ElasticSearchQuery elasticSearchQuery;

    @GetMapping("/")
    public String viewHomePage(Model model) throws IOException {
        model.addAttribute("listUserAccountDocuments",elasticSearchQuery.searchAllDocuments());
        return "index";
    }

    

    @GetMapping("/showUser")
    public String showNewEmployeeForm(Model model) {
        // create model attribute to bind form data
    	UserAccountDocument userAccount = new UserAccountDocument();
        model.addAttribute("userAccount", userAccount);
        return "newProductDocument";
    }

    
}
