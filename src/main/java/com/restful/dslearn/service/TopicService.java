package com.restful.dslearn.service;

import com.restful.dslearn.dto.TopicDTO;
import com.restful.dslearn.dto.TopicRequestDTO;
import com.restful.dslearn.entity.Lesson;
import com.restful.dslearn.entity.Offer;
import com.restful.dslearn.entity.Reply;
import com.restful.dslearn.entity.Topic;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.LessonRepository;
import com.restful.dslearn.repository.OfferRepository;
import com.restful.dslearn.repository.ReplyRepository;
import com.restful.dslearn.repository.TopicRepository;
import com.restful.dslearn.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final LessonRepository lessonRepository;
    private final ReplyRepository replyRepository;

    public TopicService(TopicRepository topicRepository,
                        UserRepository userRepository,
                        OfferRepository offerRepository,
                        LessonRepository lessonRepository,
                        ReplyRepository replyRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.lessonRepository = lessonRepository;
        this.replyRepository = replyRepository;
    }

    public List<TopicDTO> findAll() {
        return topicRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TopicDTO findById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        return toDTO(topic);
    }

    @Transactional
    public TopicDTO create(TopicRequestDTO request) {
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
        Offer offer = offerRepository.findById(request.offerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        Topic topic = new Topic();
        topic.setTitle(request.title());
        topic.setBody(request.body());
        topic.setAuthor(author);
        topic.setOffer(offer);
        topic.setLesson(lesson);

        if (request.answerId() != null) {
            Reply answer = replyRepository.findById(request.answerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found"));
            topic.setAnswer(answer);
        }

        return toDTO(topicRepository.save(topic));
    }

    @Transactional
    public TopicDTO update(Long id, TopicRequestDTO request) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
        Offer offer = offerRepository.findById(request.offerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found"));
        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));

        topic.setTitle(request.title());
        topic.setBody(request.body());
        topic.setAuthor(author);
        topic.setOffer(offer);
        topic.setLesson(lesson);

        if (request.answerId() != null) {
            Reply answer = replyRepository.findById(request.answerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found"));
            topic.setAnswer(answer);
        } else {
            topic.setAnswer(null);
        }
        return toDTO(topic);
    }

    @Transactional
    public void delete(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found");
        }
        topicRepository.deleteById(id);
    }

    private TopicDTO toDTO(Topic topic) {
        return new TopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getBody(),
                topic.getMoment(),
                topic.getAuthor().getId(),
                topic.getOffer().getId(),
                topic.getLesson().getId(),
                topic.getAnswer() != null ? topic.getAnswer().getId() : null
        );
    }
}
