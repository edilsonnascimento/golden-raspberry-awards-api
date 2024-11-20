package org.enascimento.api.config;

import org.enascimento.api.service.FileService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final FileService fileService;

    public StartupApplicationListener(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
            fileService.importCsvFromResources("src/main/resources/movielist.csv");
    }
}