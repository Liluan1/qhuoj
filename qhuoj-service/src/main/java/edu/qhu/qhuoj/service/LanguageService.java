package edu.qhu.qhuoj.service;

import edu.qhu.qhuoj.entity.Language;
import edu.qhu.qhuoj.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    public Language getLanguageById(int languageId){
        boolean isPresent = languageRepository.findById(languageId).isPresent();
        if(isPresent){
            Language findLanguage = languageRepository.findById(languageId).get();
            return findLanguage;
        }
        return null;
    }

    @Autowired
    LanguageRepository languageRepository;
}
