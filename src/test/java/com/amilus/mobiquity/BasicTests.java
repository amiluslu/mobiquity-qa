package com.amilus.mobiquity;

import com.amilus.mobiquity.base.Testbase;
import com.amilus.mobiquity.domain.dto.UserDto;
import com.amilus.mobiquity.domain.payloads.CommentPayload;
import com.amilus.mobiquity.domain.payloads.PostsPayload;
import com.amilus.mobiquity.domain.payloads.UserPayload;
import com.amilus.mobiquity.helpers.BasicHelper;
import com.amilus.mobiquity.helpers.CommentHelper;
import com.amilus.mobiquity.helpers.PostHelper;
import com.amilus.mobiquity.helpers.UserHelper;
import com.amilus.mobiquity.resource.RestClient;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.http.Method;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.List;

import static com.amilus.mobiquity.reporting.ExtentTestManager.getTest;

@SpringBootTest
@ExtendWith(Testbase.class)
public class BasicTests
{
    @Autowired
    RestClient restClient;

    @Autowired
    UserPayload userPayload;

    @Autowired
    PostsPayload postsPayload;

    @Autowired
    CommentPayload commentPayload;

    private BasicHelper basicHelper;
    private UserHelper userHelper;
    private PostHelper postHelper;
    private CommentHelper commentHelper;

    @SneakyThrows
    @Test
    @Description("Method Type: GET EndPoint: /amil")
    void testGetWrongEndpointGivenByUser(){
        getTest().getModel().setDescription("Testing <b>Endpoint which does not exist</b> when user calls");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/amil");
        basicHelper.verifyResponseCode(404);
    }

    @SneakyThrows
    @Test
    @Description("Method Type: GET EndPoint: /comments")
    void testGetCommentEndpointIsUp()
    {
        getTest().getModel().setDescription("Testing <b>/comments</b> endpoint is up and returns 200");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/comments");
        basicHelper.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Method Type: GET EndPoint: /users")
    void testGetUsersEndpointIsUp()
    {
        getTest().getModel().setDescription("Testing <b>/users</b> endpoint is up and returns 200");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/users");
        basicHelper.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Method Type: GET EndPoint: /posts")
    void testGetPostsEndpointIsUp()
    {
        getTest().getModel().setDescription("Testing <b>/posts</b> endpoint is up and returns 200");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/posts");
        basicHelper.verifyResponseCode(200);
    }

    @SneakyThrows
    @Test
    @Description("Method Type: DELETE EndPoint: /users")
    void testDeleteUser()
    {
        getTest().getModel().setDescription("Testing Delete USERS Endpoint which USER ID is 9");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/users");
        basicHelper.verifyResponseCode(200);
        userHelper = new UserHelper(userPayload);
        List<UserDto> initialUserList = userHelper.generateUsersFromJson();
        getTest().info(MarkupHelper.createLabel("Before deletion, Total Number of users is: "+initialUserList.size(),ExtentColor.LIME));

        basicHelper.callTheEndPoint(Method.DELETE.name(), "/users/9");
        basicHelper.verifyResponseCode(200);
        getTest().info("User ID: 9 is deleted...");

        basicHelper.callTheEndPoint(Method.GET.name(), "/users");
        basicHelper.verifyResponseCode(200);
        userHelper = new UserHelper(userPayload);
        List<UserDto> lastUserList = userHelper.generateUsersFromJson();
        getTest().info(MarkupHelper.createLabel("After deletion, Total Number of user is: "+lastUserList.size(), ExtentColor.CYAN));
        Assert.assertEquals("Validation Of User Deletion failed !!",1,initialUserList.size()-lastUserList.size());
    }

    @SneakyThrows
    @Test
    @Description("Method Type: DELETE EndPoint: /posts")
    void testDeletePosts()
    {
        getTest().getModel().setDescription("Testing Delete POSTS Endpoint which POST ID is 82");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/posts");
        postHelper = new PostHelper(postsPayload,userPayload,restClient);
        postHelper.getPostsCollection();
        int initialPostCount = postHelper.getTotalPostCount();
        getTest().info(MarkupHelper.createLabel("Before deletion, Total Number of posts is: "+initialPostCount,ExtentColor.LIME));

        basicHelper.callTheEndPoint(Method.DELETE.name(), "/posts/82");
        basicHelper.verifyResponseCode(200);
        getTest().info("POSTS ID: 82 is deleted...");

        basicHelper.callTheEndPoint(Method.GET.name(), "/posts");
        postHelper = new PostHelper(postsPayload,userPayload,restClient);
        postHelper.getPostsCollection();
        int lastPostCount = postHelper.getTotalPostCount();
        getTest().info(MarkupHelper.createLabel("After deletion, Total Number of posts is: "+lastPostCount, ExtentColor.CYAN));
        Assert.assertEquals("Validation Of Posts Deletion failed !!",1,initialPostCount-lastPostCount);
    }

    @SneakyThrows
    @Test
    @Description("Method Type: DELETE EndPoint: /comments")
    void testDeleteComments()
    {
        getTest().getModel().setDescription("Testing Delete COMMENTS Endpoint which COMMENT ID is 58");
        getTest().assignCategory("BasicTest");
        basicHelper = new BasicHelper(restClient);
        basicHelper.callTheEndPoint(Method.GET.name(), "/comments");
        basicHelper.verifyResponseCode(200);
        commentHelper = new CommentHelper(commentPayload,postsPayload,restClient);
        int initialCommentCount = commentHelper.getTotalCommentCount();
        getTest().info(MarkupHelper.createLabel("Before deletion, Total Number of comments is: "+initialCommentCount,ExtentColor.LIME));

        basicHelper.callTheEndPoint(Method.DELETE.name(), "/comments/58");
        basicHelper.verifyResponseCode(200);
        getTest().info("COMMENT ID: 58 is deleted...");

        basicHelper.callTheEndPoint(Method.GET.name(), "/comments");
        basicHelper.verifyResponseCode(200);
        commentHelper = new CommentHelper(commentPayload,postsPayload,restClient);
        int lastCommentCount = commentHelper.getTotalCommentCount();
        getTest().info(MarkupHelper.createLabel("After deletion, Total Number of Comments is: "+lastCommentCount, ExtentColor.CYAN));
        Assert.assertEquals("Validation Of Comment Deletion failed !!",1,initialCommentCount-lastCommentCount);
    }
}
