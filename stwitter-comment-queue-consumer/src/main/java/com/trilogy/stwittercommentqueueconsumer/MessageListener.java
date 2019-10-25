package com.trilogy.stwittercommentqueueconsumer;

import com.trilogy.stwittercommentqueueconsumer.util.feign.CommentClient;
import com.trilogy.stwittercommentqueueconsumer.util.messages.Comment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private CommentClient client;

    MessageListener(CommentClient commentClient) {
        this.client = commentClient;
    }

    @RabbitListener(queues = StwitterCommentQueueConsumerApplication.QUEUE_NAME)
    public void receiveQueueCreateComment(Comment msg) {

        // if there is no id, send msg to create a comment; else, send to update the comment
        if (msg.getCommentId() == 0) {
            Comment comment = client.createComment(msg);
        } else {
            Comment comment = client.updateComment(msg);
        }
    }
}
