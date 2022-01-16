package com.amilus.mobiquity.helpers;

import com.amilus.mobiquity.domain.dto.CommentDto;
import com.amilus.mobiquity.domain.dto.PostDto;
import com.amilus.mobiquity.domain.payloads.CommentPayload;
import com.amilus.mobiquity.domain.payloads.PostsPayload;
import com.amilus.mobiquity.resource.RestClient;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.Method;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.amilus.mobiquity.reporting.ExtentTestManager.getTest;

public class CommentHelper
{
    private CommentPayload commentPayload;
    private PostsPayload postsPayload;
    private RestClient restClient;

    @Autowired
    public CommentHelper(CommentPayload commentPayload, PostsPayload postsPayload, RestClient restClient){
        this.commentPayload = commentPayload;
        this.postsPayload = postsPayload;
        this.restClient = restClient;
    }

    public void getCommentsOfGivenPost(String method, String endPoint, String paramValue) throws JsonProcessingException
    {
        for (Integer id : postsPayload.getPostIdList()) {
            restClient.initRestAPI();
            restClient.setQueryParam(paramValue, id);
            restClient.sendHttpRequest(Method.valueOf(method), endPoint);
            commentPayload.parseJsonToComments();
        }
    }

    public int getTotalCommentCount() throws JsonProcessingException
    {
        commentPayload.parseJsonToComments();
        return commentPayload.getCommentCount();
    }

    public void verifyEmailAddressFormatInEachComment() {
        getTest().info("Verifying Email Address Format in each comment");
        Assert.assertTrue("Email Addresses are not in proper format: " + commentPayload.getInvalidEmailAddressList(),
                commentPayload.getInvalidEmailAddressList().isEmpty());
    }

    public CommentDto saveAndVerifyNewComment(CommentDto commentDto) throws JsonProcessingException
    {
        getTest().info("Saving new comment..");
        CommentDto newComment = commentPayload.postComment(commentDto);
        getTest().info("New Comment id: <b>"+newComment.getId()+"</b> Post Id: <b>"+newComment.getPostId()+"</b>");
        Assert.assertNotNull("Error occured when adding new comment !!",newComment.getId());
        return newComment;
    }

    public int getFirstPostIdFromCollection(){
        return postsPayload.getPostIdList().get(0);
    }

    public Optional<CommentDto> getFirstCommentOfPost() throws JsonProcessingException
    {
        getTest().info(MarkupHelper.createLabel("Updating Below Comment Informations...", ExtentColor.ORANGE));
        return commentPayload.getFirstCommentOfPostById(postsPayload.getPostIdList().get(0));
    }

    public Optional<CommentDto> updateAndVerifyComment(Optional<CommentDto> commentDto) throws JsonProcessingException
    {
        commentDto = Optional.ofNullable(commentPayload.updateCommentObject(commentDto.get()));
        Assert.assertNotNull(commentDto.get().getId());
        return commentDto;
    }
}
