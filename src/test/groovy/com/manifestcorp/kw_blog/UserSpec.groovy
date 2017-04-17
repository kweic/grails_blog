package com.manifestcorp.kw_blog

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.transaction.Transactional
import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.User
import org.hibernate.collection.internal.PersistentSortedSet
import spock.lang.Specification


class UserSpec extends Specification{

    def makeStubBlogsList(date, title, user){
        println "setting "+user+" date to: "+date
        Blog blog = new Blog(dateCreated: date, title: title, postBy: user)
        def blogListStub = Stub(PersistentSortedSet);
        blogListStub.first() >> blog
        blogListStub.size() >> 1
        return blogListStub;
    }

    def stubEmptyBlogs(){
        def emptyBlogs = Stub(PersistentSortedSet)
        emptyBlogs.isEmpty() >> true
        return emptyBlogs
    }

    def makeUserList(){
        User user0 = new User(username: "u0", password: "a")
        User user1 = new User(username: "u1", password: "a")
        User user2 = new User(username: "u2", password: "a")
        User user3 = new User(username: "u3", password: "a")
        User user4 = new User(username: "u4", password: "a")
        User user5 = new User(username: "u5", password: "a")
        user0.id = 0;
        user1.id = 1;
        user2.id = 2;
        user3.id = 3;
        user4.id = 4;
        user5.id = 5;

        user0.blogs = stubEmptyBlogs();
        user1.blogs = makeStubBlogsList(new Date(10000000), "title1", "user1")
        user2.blogs = makeStubBlogsList(new Date(20000000), "title2", "user2")
        user3.blogs = makeStubBlogsList(new Date(30000000), "title3", "user3")
        user4.blogs = makeStubBlogsList(new Date(40000000), "title4", "user4")
        user5.blogs = stubEmptyBlogs()

        [user0, user1, user2, user3, user4, user5]
    }


    void "Users are sorted first by active date (last post) and secondly by id"() {
        setup:
            def users = makeUserList()
            println "users size: "+users.size()
            println "user blog size: "+users.get(0).blogs.size()


        when:"The users are sorted by date"
            Collections.sort(users, new UserActiveDateComparator());


        then:"The most recently active user is at the top"
            !users.isEmpty()
            users.get(0).blogs.size() > 0
            users.size() == 6
            users.get(0).username == "u4";
            users.get(1).username == "u3";
            users.get(2).username == "u2";
            users.get(3).username == "u1";
            users.get(4).username == "u0";
            users.get(5).username == "u5";
    }
}
