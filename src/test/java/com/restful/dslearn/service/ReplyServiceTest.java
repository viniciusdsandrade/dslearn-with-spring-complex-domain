package com.restful.dslearn.service;

import com.restful.dslearn.dto.ReplyDTO;
import com.restful.dslearn.dto.ReplyRequestDTO;
import com.restful.dslearn.entity.Reply;
import com.restful.dslearn.entity.Topic;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.ReplyRepository;
import com.restful.dslearn.repository.TopicRepository;
import com.restful.dslearn.repository.UserRepository;
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
class ReplyServiceTest {

    @Mock private ReplyRepository replyRepository;
    @Mock private TopicRepository topicRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private ReplyService replyService;

    @Test
    void shouldReturnAllReplies() {
        Topic topic = new Topic();
        topic.setId(1L);
        User author = new User();
        author.setId(1L);
        Reply reply = new Reply();
        reply.setId(1L);
        reply.setBody("Answer text");
        reply.setTopic(topic);
        reply.setAuthor(author);
        when(replyRepository.findAll()).thenReturn(List.of(reply));

        List<ReplyDTO> result = replyService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).body()).isEqualTo("Answer text");
    }

    @Test
    void shouldFindReplyById() {
        Topic topic = new Topic();
        topic.setId(1L);
        User author = new User();
        author.setId(2L);
        Reply reply = new Reply();
        reply.setId(1L);
        reply.setBody("Text");
        reply.setTopic(topic);
        reply.setAuthor(author);
        when(replyRepository.findById(1L)).thenReturn(Optional.of(reply));

        ReplyDTO result = replyService.findById(1L);

        assertThat(result.authorId()).isEqualTo(2L);
    }

    @Test
    void shouldThrowNotFoundWhenReplyMissing() {
        when(replyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> replyService.findById(99L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldCreateReply() {
        Topic topic = new Topic();
        topic.setId(1L);
        User author = new User();
        author.setId(1L);
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(userRepository.findById(1L)).thenReturn(Optional.of(author));
        when(replyRepository.save(any(Reply.class))).thenAnswer(inv -> {
            Reply saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        ReplyDTO result = replyService.create(new ReplyRequestDTO("My reply", 1L, 1L));

        assertThat(result.id()).isEqualTo(10L);
        assertThat(result.body()).isEqualTo("My reply");
        verify(replyRepository).save(any(Reply.class));
    }

    @Test
    void shouldUpdateReply() {
        Topic topic = new Topic();
        topic.setId(1L);
        User author = new User();
        author.setId(2L);
        Reply reply = new Reply();
        reply.setId(1L);
        reply.setBody("Old");
        reply.setTopic(topic);
        reply.setAuthor(author);
        when(replyRepository.findById(1L)).thenReturn(Optional.of(reply));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(userRepository.findById(2L)).thenReturn(Optional.of(author));

        ReplyDTO result = replyService.update(1L, new ReplyRequestDTO("Updated", 1L, 2L));

        assertThat(result.body()).isEqualTo("Updated");
    }

    @Test
    void shouldDeleteReply() {
        when(replyRepository.existsById(1L)).thenReturn(true);

        replyService.delete(1L);

        verify(replyRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNotFoundWhenDeletingMissingReply() {
        when(replyRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> replyService.delete(1L))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }
}
