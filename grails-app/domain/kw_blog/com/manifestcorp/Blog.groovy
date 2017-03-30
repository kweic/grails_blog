package kw_blog.com.manifestcorp


class Blog implements Comparable<Blog>{
    String title
    String blogEntry
    String mood
    String postBy
    Date dateCreated
    SortedSet comments
    User user

    static belongsTo = [user: User]

    static hasMany = [comments:Comment]

    static constraints = {
        title(blank: false)
        blogEntry(maxSize: 1000, widget: 'textarea', nullable: true)
        mood(inList:["", "Rocking out", "Feeling down", "Sleepy", "Busy", "Lazy", "Excited", "Pancakes and Waffles"], nullable: true)
    }

    static mapping = {
        sort dateCreated: 'desc'
        user lazy: false
    }

    @Override
    int compareTo(Blog o) {
        return o.dateCreated.compareTo(dateCreated)
    }

}
