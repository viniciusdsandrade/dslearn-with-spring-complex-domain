package com.restful.dslearn.service;

import com.restful.dslearn.dto.TopicDTO;
import com.restful.dslearn.dto.TopicRequestDTO;
import com.restful.dslearn.entity.*;
import com.restful.dslearn.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @Mock private TopicRepository topicRepository;
    @Mock private UserRepository userRepository;
    @Mock private OfferRepository offerRepository;
    @Mock private LessonRepository lessonRepository;
    @Mock private ReplyRepository replyRepository;

    @InjectMocks
    private TopicService topicService;

    private User author() { User u = new User(); u.setId(1L); return u; }
    private Offer offer() { Offer o = new Offer(); o.setId(1L); return o; }
    private Content lesson() { Content c = new Content(); c.setId(1L); return c; }

    @Test
    void shouldReturnAllTopics() {
        Topic t = new Topic();
        t.setId(1L);
        t.setTitle("Question");
        t.setAuthor(author());
        t.setOffer(offer());
        t.setLesson(lesson());
        when(topicRepository.findAll()).thenReturn(List.of(t));

        List<TopicDTO> result = topicService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).answerId()).isNull();
    }

    @Test
    void shouldFindTopicById() {
        Reply answer = new Reply();
        answer.setId(5L);
        Topic t = new Topic();
        t.setId(1L);
        t.setTitle("Q");
        t.setAuthor(author());
        t.setOffer(offer());
        t.setLesson(lesson());
        t.setAnswer(answer);
        when(topicRepository.findById(1L)).thenReturn(Optional.of(t));

        TopicDTO result = topicService.findById(1L);

        assertThat(result.answerId()).isEqualTo(5L);
    }

    @Test
    void shouldThrowNotFoundWhenTopicMissing() {
        when(topicRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> topicService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateTopicWithoutAnswer() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(author()));
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer()));
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson()));
        when(topicRepository.save(any(Topic.class))).thenAnswer(inv -> {
            Topic saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        TopicDTO result = topicService.create(
                new TopicRequestDTO("New topic", "body", 1L, 1L, 1L, null));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.answerId()).isNull();
        verify(topicRepository).save(any(Topic.class));
    }

    @Test
    void shouldCreateTopicWithAnswer() {
        Reply answer = new Reply();
        answer.setId(5L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(author()));
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer()));
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson()));
        when(replyRepository.findById(5L)).thenReturn(Optional.of(answer));
        when(topicRepository.save(any(Topic.class))).thenAnswer(inv -> {
            Topic saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        TopicDTO result = topicService.create(
                new TopicRequestDTO("New topic", "body", 1L, 1L, 1L, 5L));

        assertThat(result.answerId()).isEqualTo(5L);
    }

    @Test
    void shouldUpdateTopicClearingAnswer() {
        Topic t = new Topic();
        t.setId(1L);
        t.setTitle("Old");
        t.setAuthor(author());
        t.setOffer(offer());
        t.setLesson(lesson());
        Reply oldAnswer = new Reply();
        oldAnswer.setId(5L);
        t.setAnswer(oldAnswer);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(t));
        when(userRepository.findById(1L)).thenReturn(Optional.of(author()));
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer()));
        when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson()));

        TopicDTO result = topicService.update(1L,
                new TopicRequestDTO("Updated", "new body", 1L, 1L, 1L, null));

        assertThat(result.title()).isEqualTo("Updated");
        assertThat(result.answerId()).isNull();
    }

    @Test
    void shouldDeleteTopic() {
        when(topicRepository.existsById(1L)).thenReturn(true);

        topicService.delete(1L);

        verify(topicRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingTopic() {
        when(topicRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> topicService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
