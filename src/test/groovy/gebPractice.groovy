import spock.lang.Specification

//import geb.Browser
//
//Browser.drive {
//    go "http://gebish.org"
//
//    assert title == "Geb - Very Groovy Browser Automation"
//
//    $("#sidebar .sidemenu a", text: "jQuery-like API").click()
//
//    assert $("#main h1")*.text() == ["Navigating Content", "Form Control Shortcuts"]
//    assert $("#sidebar .sidemenu a", text: "jQuery-like API").parent().hasClass("selected")
//}
import geb.spock.GebSpec
import grails.test.mixin.integration.Integration

@Integration
class GebPracticeSpec extends GebSpec {


    def "show off the awesomeness of google"() {
        given:
        go "http://www.google.com/"

        expect: "make sure we actually got to the page"
        title == "Google"

        when: "enter wikipedia into the search field"
        $("input", name: "q").value("wikipedia")

        then: "wait for the change to results page to happen and (google updates the page dynamically without a new request)"
        waitFor { title.endsWith("Google Search") }
        // is the first link to wikipedia?
        def firstLink = $("li.g", 0).find("a.l")

        and:
        firstLink.text() == "Wikipedia"

        when: "click the link"
        firstLink.click()

        then: "wait for Google's javascript to redirect to Wikipedia"
        waitFor { title == "Wikipedia" }
    }
}