package kw_blog.com.manifestcorp

/**
 * Created by Manifest on 3/21/2017.
 */
class SeoTagLib {

    static namespace = "seo"

    def friendlyUrlService

    /**
     * Convert the value using the SEO friendly URL service.
     *
     * @attr String value to be converted
     */
    def convert = { attr, body ->
        out << body() << friendlyUrlService.sanitizeWithDashes(attr.value)
    }
}
