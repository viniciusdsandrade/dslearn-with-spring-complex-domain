package com.restful.dslearn.service;

import com.restful.dslearn.dto.ReplyDTO;
import com.restful.dslearn.dto.ReplyRequestDTO;
import com.restful.dslearn.entity.Reply;
import com.restful.dslearn.entity.Topic;
import com.restful.dslearn.entity.User;
import com.restful.dslearn.repository.ReplyRepository;
import com.restful.dslearn.repository.TopicRepository;
import com.restful.dslearn.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public ReplyService(ReplyRepository replyRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public List<ReplyDTO> findAll() {
        return replyRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReplyDTO findById(Long id) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found"));
        return toDTO(reply);
    }

    @Transactional
    public ReplyDTO create(ReplyRequestDTO request) {
        Topic topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        Reply reply = new Reply();
        reply.setBody(request.body());
        reply.setTopic(topic);
        reply.setAuthor(author);
        return toDTO(replyRepository.save(reply));
    }

    @Transactional
    public ReplyDTO update(Long id, ReplyRequestDTO request) {
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found"));
        Topic topic = topicRepository.findById(request.topicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));

        reply.setBody(request.body());
        reply.setTopic(topic);
        reply.setAuthor(author);
        return toDTO(reply);
    }

    @Transactional
    public void delete(Long id) {
        if (!replyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reply not found");
        }
        replyRepository.deleteById(id);
    }

    private ReplyDTO toDTO(Reply reply) {
        return new ReplyDTO(
                reply.getId(),
                reply.getBody(),
                reply.getMoment(),
                reply.getTopic().getId(),
                reply.getAuthor().getId()
        );
    }
}
