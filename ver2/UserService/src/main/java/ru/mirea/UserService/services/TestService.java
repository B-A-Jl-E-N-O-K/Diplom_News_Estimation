package ru.mirea.UserService.services;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import ru.mirea.UserService.dao.KafkaObj;
//import ru.mirea.UserService.models.Industry;
//import ru.mirea.UserService.models.News;
//import ru.mirea.UserService.models.Quotes;
//import ru.mirea.UserService.models.Security;
//import ru.mirea.UserService.repositories.IndustryRepository;
//import ru.mirea.UserService.repositories.NewsRepository;
//import ru.mirea.UserService.repositories.SecurityRepository;
//import ru.mirea.UserService.repositories.SourceRepository;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestService {
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//
//    private String quotesLink = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities/%s.xml?iss.meta=off&securities.columns=PREVDATE,PREVLEGALCLOSEPRICE&marketdata.columns=LCLOSEPRICE";
//
//
//
//
//    public List<News> listenNews(String message) throws JsonProcessingException, IOException {
//        List<KafkaObj> mes = objectMapper.readValue(message, new TypeReference<>(){});
//        List<News> res = new ArrayList<>();
//        for(KafkaObj kafkaMes : mes){
//            String sec = kafkaMes.getCompany();
//            String ind = kafkaMes.getIndustry();
//            if (sec != null && !(sec.isEmpty())){
//                String[] arr_sec = sec.split(" ");
//                for(String comp : arr_sec){
//                    Security related_company = new Security(null, "AFLT", "Aeroflot");
//                    if(related_company != null){
//                        News new_news = new News(null, kafkaMes.getLink(),
//                                kafkaMes.getTitle_out(), kafkaMes.getClass_news() == 1, kafkaMes.getDatetime(), true);
//                        res.add(new_news);
//                    }
//                    else{
//                        System.out.println("Error in create news line: No security type found");
//                    }
//
//                }
//
//            }
////            else if (ind != null && !(ind.isEmpty())){
////                String[] arr_ind = ind.split(" ");
////                for(String comp : arr_ind){
////                    Industry related_ind = industryRepository.findOneByName(comp);
////                    List<Security> newsec = related_ind.getSecurityList();
////                    if(newsec != null && !newsec.isEmpty()){
////                        Security secc = newsec.get(0);
////                        if(secc != null){
////                            News new_news = new News(secc, kafkaMes.getLink(),
////                                    kafkaMes.getTitle_out(), kafkaMes.getClass_news() == 1, kafkaMes.getDatetime(), false);
////                            res.add(new_news);
////                        }
////                        else{
////                            System.out.println("Error in create news line: No security type found");
////                        }
////                    }
////
////
////                }
////            }
////            else{
////                News new_news = new News(null, kafkaMes.getLink(),
////                        kafkaMes.getTitle_out(), kafkaMes.getClass_news() == 1, kafkaMes.getDatetime(), true);
////                res.add(new_news);
////            }
//
//        }
//        return res;
//
//    }
//
//    public String listenQuotes() throws JsonProcessingException, IOException {
//
//            String code = "AFLT";
//            String url = String.format(quotesLink, code);
//
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document doc = builder.parse(url);
//
//            doc.getDocumentElement().normalize();
//
//            NodeList blocksList = doc.getElementsByTagName("row");
//            Node data_prev = blocksList.item(0);
//            Element row = (Element) data_prev;
//            String dateInp = row.getAttribute("PREVDATE");
//            String priceInp = row.getAttribute("PREVLEGALCLOSEPRICE");
//
//            return(priceInp);
//
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return "None";
//
//    }
//}
