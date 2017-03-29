package com.manifestcorp.kw_blog

import kw_blog.com.manifestcorp.FriendlyUrlService

import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(FriendlyUrlService)
class FriendlyUrlServiceTest extends Specification {
    FriendlyUrlService friendlyUrl = new FriendlyUrlService()
    def TEXT_RAW = "entered text here"
    def TEXT_HYPHENATED = "entered-text-here"

    def TEXT_UPPERCASE = "CASED TEXT"
    def TEXT_LOWERCASE = "cased-text"


    void "url is hyphened"(){
        when: "text with spaces is sanitized"
            def sanitizedText = friendlyUrl.sanitizeWithDashes(TEXT_RAW)
        then: "the spaces are replaced with hyphens"
            sanitizedText == TEXT_HYPHENATED
    }


    void "text is returned lowercase"(){
        when: "text with uppercase letters is sanitized"
            def sanitizedText = friendlyUrl.sanitizeWithDashes(TEXT_UPPERCASE)

        then:"Uppercase letters are returned lowercase"
            sanitizedText == TEXT_LOWERCASE
    }


    void "odd characters are removed"(){
        when: "Non link-friendly letters are sanitized"
            def sanitized = friendlyUrl.sanitizeWithDashes("text%%/next")
        then: "Odd characters are removed"
            sanitized == "textnext"
    }


    void "non english characters removed"(){
        when: "Text with non english characters are sanitized"
            def sanitized = friendlyUrl.sanitizeWithDashes("话都在这里 here 这里")
        then: "Text is returned without non-english characters"
            sanitized == "here"
    }
}