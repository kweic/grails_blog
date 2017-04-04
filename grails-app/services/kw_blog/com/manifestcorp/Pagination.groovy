package kw_blog.com.manifestcorp

/**
 * Created by Manifest on 3/31/2017.
 */
class Pagination {

    def paginateResults(array, params){
        def begin = 0
        def end = 10
        println "in paginate, size: "+array.size()
        println "params in paginate: "+params
        if(params.offset != null) {
            def pageSize = Integer.parseInt(params.offset)
            end += pageSize
            begin += pageSize
        }

        if(end > array.size()){
            end = array.size()
        }

        println "calling sublist, begin: "+begin+" end; "+end
        array.subList(begin, end)
    }
}
