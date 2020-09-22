package ru.jpixel.personaldiaryservice.services;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ru.jpixel.models.dtos.open.PageDto;
import ru.jpixel.personaldiaryservice.domain.open.Page;

public class PageConverter implements Converter<Page, PageDto> {
    @Override
    @NonNull
    public PageDto convert(Page page) {
        var result = new PageDto();
        result.setId(page.getId());
        result.setRecordingSummary(page.getRecordingSummary());
        result.setNotificationDate(page.getNotificationDate());
        result.setCreateDate(page.getCreateDate());
        result.setTag(new DirectoryConverter().convert(page.getTag()));
        result.setConfidential(page.isConfidential());
        if (!page.isConfidential()) {
            result.setContent(page.getContent());
        }
        return result;
    }
}
