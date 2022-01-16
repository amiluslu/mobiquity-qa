package com.amilus.mobiquity.helpers;

import com.amilus.mobiquity.domain.dto.PostDto;
import com.amilus.mobiquity.domain.dto.UserDto;
import com.amilus.mobiquity.domain.payloads.PostsPayload;
import com.amilus.mobiquity.domain.payloads.UserPayload;
import com.amilus.mobiquity.resource.RestClient;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.Method;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.amilus.mobiquity.reporting.ExtentTestManager.getTest;

public class PostHelper
{
    private PostsPayload postsPayload;
    private UserPayload userPayload;
    private RestClient restClient;

    @Autowired
    public PostHelper(PostsPayload postsPayload, UserPayload userPayload, RestClient restClient) {
        this.postsPayload = postsPayload;
        this.userPayload = userPayload;
        this.restClient = restClient;
    }

    // verify and validate of user's posts count
    public void verifyCountOfUserPosts(int count) {
        getTest().info("Expected Posts Count: "+count +" Actual Posts Count: "+ postsPayload.getPostCount());
        Assert.assertEquals("Count of user's post is not " + count, count, postsPayload.getPostCount());
    }

    public int getTotalPostCount(){
        return postsPayload.getPostCount();
    }
    //It collects all posts of Id's in a list
    public void getPostsCollection() throws JsonProcessingException
    {
        getTest().info("Generating Posts Data Object from JSON response..");
        postsPayload.parseJsonToPosts();
        postsPayload.collectPostIds();
    }

    // It takes userId parameter and gets all posts
    public void callPostsOfUserById(String method, String endPoint, String parameterValue) {
        restClient.initRestAPI();
        restClient.setQueryParam(parameterValue, String.valueOf(userPayload.getUserId()));
        restClient.sendHttpRequest(Method.valueOf(method), endPoint);
    }

    public PostDto saveAndVerifyNewPost(PostDto postDto) throws JsonProcessingException
    {
        getTest().info("Saving new posts..");
        PostDto newPost = postsPayload.createPosts(postDto);
        getTest().info("New posts id: <b>"+newPost.getId()+"</b> New Posts User Id: <b>"+newPost.getUserId()+"</b>");
        Assert.assertNotNull("Error occured when adding new posts !!",newPost.getId());
        return newPost;
    }

    public Optional<PostDto> getFirstPostOfUserById(int userId) {
        getTest().info("Fetching First Post of User By Id: "+userId);
        return postsPayload.getFirstPostOfUserById(userId);
    }

    public Optional<PostDto> updateAndVerifyPost(Optional<PostDto> postDto) throws JsonProcessingException
    {
        getTest().info(MarkupHelper.createLabel("Updating Posts Title & Body..", ExtentColor.ORANGE));
        postDto = Optional.ofNullable(postsPayload.updatePostObject(postDto.get()));
        Assert.assertNotNull(postDto.get().getId());
        return postDto;
    }

}
