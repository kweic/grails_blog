package com.manifestcorp.kw_blog

import grails.test.mixin.TestFor
import spock.lang.Specification
import com.manifestcorp.kw_blog.*


class LinkCodecSpec extends Specification {

    LinkCodec dateCodec = new LinkCodec();
    def setup() {
    }

    def cleanup() {
    }

    void "Test the codec returns the correct optimized string"() {
        def date= "2017-03-20 17:10:48.0"
        def title = "This is Title"
        def formatted = dateCodec.encodeWithUnderscores(date,title);
        println("date is now: "+formatted)
        expect:"strings are formatted"
            formatted == "2017-03-20_This_is_Title"
    }

}
