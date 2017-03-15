package kw_blog

class Blog {
    String title;
    String blogEntry;
    String mood;
    String postBy= "Kevin";
    Date dateCreated = new Date();

//    static belongsTo = [group: Group]

    static constraints = {
        title(blank: false)
        blogEntry(maxSize: 1000, widget: 'textarea', nullable: true)
        mood(inList:["Rocking out", "Feeling down", "Sleepy", "Busy", "Lazy", "Excited", "Pancakes and Waffles"])
    }
}
