package ru.mirea.UserService.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mirea.UserService.models.Industry;
import ru.mirea.UserService.models.News;
import ru.mirea.UserService.models.Quotes;
import ru.mirea.UserService.models.Security;
import ru.mirea.UserService.repositories.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class QuotesService {

    @Value("${quotesLink}")
    private String quotesLink;

    private final QuotesRepository quotesRepository;
    private final SecurityRepository securityRepository;
    private final NewsRepository newsRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    public QuotesService(SecurityRepository securityRepository, QuotesRepository quotesRepository, NewsRepository newsRepository) {
        this.quotesRepository = quotesRepository;
        this.securityRepository = securityRepository;
        this.newsRepository = newsRepository;
    }

    @Scheduled(fixedDelayString = "PT1H")
    public void listenQuotes() throws JsonProcessingException, IOException {
        List<Security> listCompanies = securityRepository.findAll();
        if(!listCompanies.isEmpty()){
            for (Security sec:listCompanies){
                String code = sec.getCode();
                String url = String.format(quotesLink, code);

                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(url);

                    doc.getDocumentElement().normalize();

                    NodeList blocksList = doc.getElementsByTagName("row");
                    Node data_prev = blocksList.item(0);
                    Element row = (Element) data_prev;
                    String dateInp = row.getAttribute("PREVDATE");
                    String priceInp = row.getAttribute("PREVLEGALCLOSEPRICE");

                    Quotes newQuote = new Quotes(sec, dateInp, Float.valueOf(priceInp));

                    List<Quotes> oldlist = quotesRepository.findAllBySecurity_Code(code);
                    if (oldlist != null && !oldlist.isEmpty()) {
                        Quotes lastold = oldlist.get(oldlist.size()-1);
                        List<News> newslist = newsRepository.findAllByResAndSecurityNews_Code(null, code);
                        if(newslist != null && !newslist.isEmpty()){
                            for(News item : newslist){
                                if(newQuote.getValue() > lastold.getValue()){item.setRes(1);}
                                else{item.setRes(0);}

                                newsRepository.save(item);
                            }
                        }

                    }
                    quotesRepository.save(newQuote);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    public List<Quotes> getQuotesBySecCode(String code){

        List<Quotes> quotesList  = quotesRepository.findAllBySecurity_Code(code);
        if (!quotesList.isEmpty()){
            for (Quotes item : quotesList){
                item.setSecurity(null);
            }
        }
        return quotesList;
    }
}
