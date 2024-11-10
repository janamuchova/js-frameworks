package cz.eg.hr.service;

import cz.eg.hr.repository.JavascriptFrameworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JavascriptFrameworkServiceImpl implements JavascriptFrameworkService {
    @Autowired
    private JavascriptFrameworkRepository javascriptFrameworkRepository;
}
