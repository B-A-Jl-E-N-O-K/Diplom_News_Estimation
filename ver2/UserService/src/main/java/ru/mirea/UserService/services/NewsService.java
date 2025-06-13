package ru.mirea.UserService.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.mirea.UserService.dao.KafkaObj;
import ru.mirea.UserService.dao.NewsDAO;
import ru.mirea.UserService.dao.SecDAO;
import ru.mirea.UserService.models.*;
import ru.mirea.UserService.repositories.IndustryRepository;
import ru.mirea.UserService.repositories.NewsRepository;
import ru.mirea.UserService.repositories.SecurityRepository;
import ru.mirea.UserService.repositories.SourceRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final SecurityRepository securityRepository;
    private final IndustryRepository industryRepository;
    private final SourceRepository sourceRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    public NewsService(NewsRepository newsRepository, SecurityRepository securityRepository,
                       IndustryRepository industryRepository, SourceRepository sourceRepository) {
        this.newsRepository = newsRepository;
        this.securityRepository = securityRepository;
        this.industryRepository = industryRepository;
        this.sourceRepository = sourceRepository;
    }


  @KafkaListener(groupId = "group1", topicPartitions = @TopicPartition(topic = "news", partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0")}))
    public void listenNews(@Payload String message) throws JsonProcessingException, IOException {
        List<KafkaObj> mes = objectMapper.readValue(message, new TypeReference<>(){});

        for(KafkaObj kafkaMes : mes){
            String sec = kafkaMes.getCompany();
            String ind = kafkaMes.getIndustry();
            if (sec != null && !(sec.isEmpty())){
                String[] arr_sec = sec.split(" ");
                for(String comp : arr_sec){
                    Security related_company = securityRepository.findOneByCode(comp);
                    if(related_company != null){
                        News new_news = new News(related_company, kafkaMes.getLink(),
                                kafkaMes.getTitle_out(), kafkaMes.getClass_news() == 1, kafkaMes.getDatetime(), true);
                        newsRepository.save(new_news);
                    }
                    else{
                        System.out.println("Error in create news line: No security type found");
                    }

                }

            }
            else if (ind != null && !(ind.isEmpty())){
                String[] arr_ind = ind.split(" ");
                for(String comp : arr_ind){
                    Industry related_ind = industryRepository.findOneByName(comp);
                    List<Security> newsec = related_ind.getSecurityList();
                    if(newsec != null && !newsec.isEmpty()){
                        Security secc = newsec.get(0);
                        if(secc != null){
                            News new_news = new News(secc, kafkaMes.getLink(),
                                    kafkaMes.getTitle_out(), kafkaMes.getClass_news() == 1, kafkaMes.getDatetime(), false);
                            newsRepository.save(new_news);
                        }
                        else{
                            System.out.println("Error in create news line: No security type found");
                        }
                    }


                }
            }
            else{
                News new_news = new News(null, kafkaMes.getLink(),
                        kafkaMes.getTitle_out(), kafkaMes.getClass_news() == 1, kafkaMes.getDatetime(), true);
                newsRepository.save(new_news);
            }

        }
        System.out.println("News delivery saved with: " + mes.size() + " news");

    }


    public Source getSource(int id){

        Optional<Source> source = sourceRepository.findById(id);
        Source res;
        if (source.isPresent()){
            res = source.get();
            res.setIndustryList(null);
        }
        else {res = null;}

        return res;

    }

    public List<Industry> getAllIndustries(){

        List<Industry> ind = industryRepository.findAll();
        if (!ind.isEmpty()){
            for (Industry item : ind){
                item.setSecurityList(null);
                item.setSource(null);
            }
        }
        return ind;
    }

    public List<SecDAO> getAllSec(){

        List<Security> sec = securityRepository.findAll();
        List<SecDAO> daolist = new ArrayList<SecDAO>();
        if (!sec.isEmpty()){
            for (Security item : sec){
                SecDAO newsec = new SecDAO(item.getIndustry().getName(), item.getCode(), item.getName());
                daolist.add(newsec);
            }
        }

        return daolist;
    }

    public List<NewsDAO> getAllNews(){

        List<News> sec = newsRepository.findAll();
        List<NewsDAO> daolist = new ArrayList<NewsDAO>();
        if (!sec.isEmpty()){
            for (News item : sec){
                NewsDAO newsec;
                if(item.isIscomp()){
                    if(item.getSecurityNews() == null){
                        newsec = new NewsDAO(null, item.getLink(), item.getTitle(), item.isEst(),item.getTime(), item.getRes());
                    }
                    else{
                        newsec = new NewsDAO(item.getSecurityNews().getName(), item.getLink(), item.getTitle(), item.isEst(),item.getTime(), item.getRes());
                    }

                }
                else{
                    newsec = new NewsDAO(item.getSecurityNews().getIndustry().getName(), item.getLink(), item.getTitle(), item.isEst(),item.getTime(), item.getRes());

                }
                daolist.add(newsec);

            }
        }

        return daolist;
    }

    public List<Security> getSecByIndName(String name){

        List<Security> sec = securityRepository.findAllByIndustry_Name(name);
        if (!sec.isEmpty()){
            for (Security item : sec){
                item.setQuotesList(null);
                item.setNewsList(null);
                item.setIndustry(null);
            }
        }
        return sec;

    }

//    public List<News> getNewsByIndName(String name){
//
//        List<News> news = newsRepository.findAllByIndustryNews_Name(name);
//        if (!news.isEmpty()){
//            for (News item : news){
//                item.setIndustryNews(null);
//                item.setSecurityNews(null);
//            }
//        }
//        return news;
//
//    }

//    public List<News> getNewsBySecCode(String code){
//
//        List<News> news = newsRepository.findAllBySecurityNews_Code(code);
//        if (!news.isEmpty()){
//            for (News item : news){
//                item.setIndustryNews(null);
//                item.setSecurityNews(null);
//            }
//        }
//        return news;
//
//    }
//
//    public List<News> getNewsBySource(){
//
//        List<News> news = newsRepository.findByIndustryNews_NameAndSecurityNews_Code(null, null);
//        return news;
//
//    }

}
