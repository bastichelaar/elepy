package com.elepy.admin.models;

import com.elepy.admin.services.*;
import com.elepy.annotations.*;
import com.elepy.dao.SortOption;
import com.elepy.id.HexIdProvider;
import com.elepy.models.RestModelAccessType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


@RestModel(
        //The only 2 required properties in RestModels are are slug and name
        slug = "/users",
        name = "Users",

        //Fontawesome icon from the free cdn
        icon = "users",

        description = "",

        //Custom route classes these must have an empty constructor and implement one of the Crud Operations: Create, FindOne, Find, Update or Delete.
        deleteRoute = UserDelete.class,
        createRoute = UserCreate.class,
        updateRoute = UserUpdate.class,

        //Access type on each of the setup, these can be: ADMIN, PUBLIC or DISABLED. If disabled, the route won't be created. If public, anyone can access it.
        //If admin it will run through all the hooked admin filters
        findAll = RestModelAccessType.ADMIN,
        findOne = RestModelAccessType.ADMIN,
        create = RestModelAccessType.ADMIN,

        //Sort
        //The default sorted mongo field. default is "_id"
        defaultSortField = "username",
        //Ascending sort or descending sort
        defaultSortDirection = SortOption.ASCENDING,


        //Specifies the class that will handle ID creation for this resource
        idProvider = HexIdProvider.class,

        //Array of ObjectEvaluators that evaluates an object on Create and Update operations
        objectEvaluators = {UserEvaluator.class}
)
public class User {

    //The only MUST-HAVE annotation is atleast one @Identifier used by Elepy to generate ID's for resources
    @Identifier
    private final String id;

    //This specifies that the property must be unique
    @Unique
    //Only this is necessary to search for users with a with a username
    @Searchable
    //How the username gets saved in the database
    @JsonProperty("username")
    //A nice looking name for the admin UI :)
    @PrettyName("Username")
    @Text(maximumLength = 30)
    private final String username;

    @PrettyName("Password")
    @JsonProperty("password")
    @Importance(-1)
    private final String password;

    @Searchable
    @JsonProperty("email")
    @PrettyName("E-mail address")
    @Unique
    private final String email;


    @Searchable
    @JsonProperty("user_type")
    @PrettyName("User role")
    private final UserType userType;


    @JsonCreator
    public User(@JsonProperty("_id") String id, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("email") String email, @JsonProperty("user_type") UserType userType) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userType = userType == null ? UserType.USER : userType;
    }

    public User hashWord() {
        return new User(id, username, BCrypt.hashpw(password, BCrypt.gensalt()), email, userType);
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public UserType getUserType() {
        return userType;
    }
}
