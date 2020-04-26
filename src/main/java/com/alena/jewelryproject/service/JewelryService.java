package com.alena.jewelryproject.service;

import com.alena.jewelryproject.jpa_repositories.JewelryRepository;
import com.alena.jewelryproject.model.Jewelry;
import com.alena.jewelryproject.model.enums.JewelryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class JewelryService {
    @Autowired
    private JewelryRepository jewelryRepository;

    public List<Jewelry> getAllUnhiddenJewelries(int page, int count) {
        return jewelryRepository.getAllUnhidden(PageRequest.of(page, count));
    }

    public List<Jewelry> getAllUnhiddenJewelries() {
        return jewelryRepository.getAllUnhidden();
    }

    public List<Jewelry> getAllJewelries() {
        return jewelryRepository.findAll();
    }

    public List<Jewelry> getNewUnhiddenJewelries(int maxCount) {
        return jewelryRepository.getAllNewUnhidden(PageRequest.of(0, maxCount));
    }

    public List<Jewelry> getUnhiddenJewelries(JewelryType type, int page, int count) {
        return jewelryRepository.getAllUnhidden(type, PageRequest.of(page, count));
    }

    public int getAllJewelriesCount(JewelryType jewelryType) {
        return jewelryRepository.countJewelriesByIsHideIsFalseAndType(jewelryType);
    }

    public int getAllJewelriesCount() {
        return jewelryRepository.countJewelriesByIsHideIsFalse();
    }

    public Jewelry getJewelry(long id) {
        return jewelryRepository.findById(id).orElse(null);
    }

    public void update(Jewelry jewelry) {
        jewelryRepository.save(jewelry);
    }

    public void save(Jewelry jewelry) {
        jewelryRepository.save(jewelry);
    }

    public void saveAll(List<Jewelry> jewelries) {
        jewelryRepository.saveAll(jewelries);
    }

    public void deleteJewelry(long id) {
        jewelryRepository.deleteById(id);
    }

    public void sellJewelries(List<Jewelry> jewelries) {
        for (Jewelry jewelry : jewelries) {
            jewelry.setSold(true);
            save(jewelry);
        }
    }

    public List<String> getPages(int allJewelries, Integer page, int countInPage) {
        if (allJewelries < countInPage) {
            return Collections.singletonList("1");
        }

        int lastPage = allJewelries % countInPage == 0 ?
                allJewelries / countInPage :
                allJewelries / countInPage + 1;

        if (lastPage < 6) {
            return IntStream.rangeClosed(1, lastPage)
                    .boxed()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

        page = getCorrectPage(page, lastPage);

        if (page + 2 < lastPage) {
            if (page - 1 > 0) {
                return Arrays.asList(String.valueOf(page - 1), String.valueOf(page), String.valueOf(page + 1), "...",
                        String.valueOf(lastPage));
            } else {
                return Arrays.asList(String.valueOf(page), String.valueOf(page + 1), String.valueOf(page + 2), "...",
                        String.valueOf(lastPage));
            }
        } else {
            return Arrays.asList("1", "...", String.valueOf(lastPage - 2), String.valueOf(lastPage - 1),
                    String.valueOf(lastPage));
        }
    }

    public int getCorrectPage(Integer page, int lastPage) {
        if (page == null || page <= 0) {
            return  1;
        } else if (page > lastPage) {
            return lastPage;
        }
        return page;
    }
}
