package com.amilus.mobiquity.helpers;

import com.amilus.mobiquity.domain.dto.UserDto;
import com.amilus.mobiquity.domain.payloads.UserPayload;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.Method;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.amilus.mobiquity.reporting.ExtentTestManager.getTest;

public class UserHelper
{
    private UserPayload userPayload;

    @Autowired
    public UserHelper(UserPayload userPayload) {
        this.userPayload = userPayload;
    }

    // verify and validate username
    public void verifyUserByUsername(String username) {
        getTest().info("Searching Expected Username: <b>"+username+"</b> Fetching Result: <b>"+userPayload.isUserFound(username)+"</b>");
        Assert.assertTrue("Validation of existence failed!! Username: <b>"+ username + "</b> not found in system..", userPayload.isUserFound(username));
    }

    public int getUserIdByUsername(String username) {
        getTest().info("Fetching User Id by Username: "+username);
        return userPayload.fetchUserIdByUsername(username);
    }

    public List<UserDto> generateUsersFromJson() throws JsonProcessingException
    {
        getTest().info("Generating User Data Object from JSON response..");
        return userPayload.parseJsonToUsers();
    }

    public UserDto saveAndVerifyNewUser(UserDto userDto) throws JsonProcessingException
    {
        getTest().info("Saving new user..");
        UserDto newUser = userPayload.postUserObject(userDto);
        getTest().info("New user id: <b>"+newUser.getId()+"</b> New Username: <b>"+newUser.getUsername()+"</b>");
        Assert.assertNotNull("Error occured when adding new user !!",newUser.getId());
        return newUser;
    }

    public Optional<UserDto> getUserInfoByUsername(String username) {
        getTest().info("Fetching User Id by Username: "+username);
        return userPayload.fetchUserByUsername(username);
    }

    public Optional<UserDto> updateAndVerifyUser(Optional<UserDto> userDto) throws JsonProcessingException
    {
        getTest().info(MarkupHelper.createLabel("Updating User Email Information", ExtentColor.ORANGE));
        userDto = Optional.ofNullable(userPayload.updateUserObject(userDto.get()));
        Assert.assertNotNull(userDto.get().getId());
        return userDto;
    }
}
