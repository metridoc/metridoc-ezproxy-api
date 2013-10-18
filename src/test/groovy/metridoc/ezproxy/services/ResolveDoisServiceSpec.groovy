package metridoc.ezproxy.services

import metridoc.ezproxy.entities.EzDoi
import metridoc.ezproxy.entities.EzDoiJournal
import org.slf4j.impl.SimpleLogger
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA on 10/15/13
 * @author Tommy Barker
 */
class ResolveDoisServiceSpec extends Specification{

    void "test ingest from CrossRefObject"() {
        given:
        def response = new CrossRefResponse(
                printYear: 1,
                issue: "foo",
                onlineYear: 50
        )

        def instance = new EzDoiJournal()

        when:
        ResolveDoisService.ingestResponse(instance, response)

        then:
        1 == instance.printYear
        "foo" == instance.issue
        50 == instance.onlineYear
    }

    void "test process response on CrossRefResponseException"() {
        when:
        ResolveDoisService service = new ResolveDoisService()
        EzDoi ezDoi = new EzDoi(doi: "foo")
        service.processResponse(
                new CrossRefResponse(
                        statusException: new CrossRefResponseException(400, new URL("http://foo.bar"))
                ),
                ezDoi
        )

        then:
        !ezDoi.resolvableDoi
    }
}
