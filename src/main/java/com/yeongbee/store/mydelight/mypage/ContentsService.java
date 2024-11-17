package com.yeongbee.store.mydelight.mypage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;

    @Transactional
    public void saved(String title, String content) {
        ContentsEntity entity = new ContentsEntity(title, content);
        contentsRepository.save(entity);
    }

    @Transactional
    public void update(String title,String content) {
        ContentsEntity contents = getContents();
        contents.update(title, content);
    }

    public ContentsEntity getContents() {
        return contentsRepository.findById(1L).orElse(null);
    }

}
