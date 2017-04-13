package com.manifestcorp.kw_blog

import kw_blog.com.manifestcorp.Blog
import kw_blog.com.manifestcorp.User

class UserActiveDateComparator implements Comparator<User>{

        @Override
        public int compare(User o1, User o2) {
            if(!o1.blogs.isEmpty() && !o2.blogs.isEmpty()) {
                return ((Blog) o1.blogs.first()).dateCreated.compareTo(((Blog) o2.blogs.first()).dateCreated)
            }else if(!o1.blogs.isEmpty()){
                return -1;
            }else if(!o2.blogs.isEmpty()){
                return 1;
            }
            println "in compare, names: "+o1.username+" "+o2.username
            println "in compare, ids: "+o1.id+" "+o2.id
            return o1.id - o2.id;
        }
}
