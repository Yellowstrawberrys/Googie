package com.googie.googie.Control;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WebControl {

    @RequestMapping("/")
    public String main(ModelAndView m){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>GoogIe</title>\n" +
                "</head>\n" +
                "    <body>\n" +
                "        <center>\n" +
                "            <img src=\"/logo.png\" width=\"400px\"/><br/><br/>\n" +
                "            <form action=\"/search\" method=\"GET\">\n" +
                "                <input name=\"q\" type=\"text\" title=\"검색\" aria-label=\"검색\" autocapitalize=\"off\" autocomplete=\"off\" maxlength=\"1024\" style=\"font-size: 20px; width: 500px;\">\n" +
                "            </form>\n" +
                "        </center>\n" +
                "    </body>\n" +
                "</html>";
    }

    @RequestMapping("/search")
    public String search(Model model, HttpSession session, HttpServletRequest servlet, HttpServletResponse response) throws IOException {
        if(servlet.getParameter("q") == null || servlet.getParameter("q").isEmpty() || servlet.getParameter("q").isBlank())
            response.sendRedirect("/");
        else{
            String resultPage = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>%s - Googie</title>\n" +
                    "    <script>\n" +
                    "        function onLoad(){\n" +
                    "            document.getElementById(\"search-input\").value = '%s';\n" +
                    "        }\n" +
                    "    </script>\n" +
                    "</head>\n" +
                    "    <body onload=\"onLoad()\">\n" +
                    "        <div id=\"searchbar\">\n" +
                    "            <a href=\"/\" style=\"text-decoration: none;\"><img src=\"/logo.png\" width=\"150px\" style=\"float: left;\"></a>&nbsp;\n" +
                    "            <center>\n" +
                    "                <form action=\"/search\" method=\"GET\">\n" +
                    "                    <input name=\"q\" type=\"text\" title=\"검색\" aria-label=\"검색\" autocapitalize=\"off\" autocomplete=\"off\" maxlength=\"1024\" style=\"width: 50vw\" id=\"search-input\">\n" +
                    "                </form>\n" +
                    "            </center>\n" +
                    "        </div>\n" +
                    "        <br/>\n" +
                    "        <hr/>\n" +
                    "        %s\n" +
                    "    </body>\n" +
                    "</html>";
            String Searching = servlet.getParameter("q").replaceAll("\\+", "");
            List<String> results = new ArrayList<>();
            List<String> notSureResults = new ArrayList<>();
            for(String st : Data.titles())
                if(st.contains(Searching) || st.equals(Searching))
                    results.add(st);
            for(String st : Data.titles())
                for(String chars : Searching.split(" "))
                    for(String chars1 : st.split(" "))
                        if(chars.equals(chars1) && !notSureResults.contains(st) && !results.contains(st))
                            notSureResults.add(st);

            StringBuilder stringBuilder = new StringBuilder("");
            String template_result = "<div>\n" +
                    "    <text style=\"font-size: 12px; color: gray\">%s</text><br/>\n" +
                    "    <a href=\"%s\" style=\"font-size: 25px; text-decoration: none; color: darkblue;\">%s</a><br/>\n" +
                    "    <text style=\"font-size: 10px; color: gray\">%s</text><br/>\n" +
                    "</div><br/><br/>";
            listing(results, stringBuilder, template_result);
            listing(notSureResults, stringBuilder, template_result);
            if(stringBuilder.isEmpty())
                stringBuilder.append("No Result");

            return resultPage.formatted(Searching, Searching, stringBuilder.toString());
        }
        return null;
    }

    private void listing(List<String> notSureResults, StringBuilder stringBuilder, String template_result) {
        for(String result : notSureResults){
            List<String> values = Data.results.get(result);
            stringBuilder.append(template_result.formatted(values.get(2),
                    values.get(2),
                    values.get(0),
                    (values.get(1).isEmpty() ? "관련정보 없음" : values.get(1))));
        }
    }

}
