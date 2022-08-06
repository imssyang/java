package jcip.examples.g3sharing_object.publication_escape;

/**
 * ThisEscape
 * <p/>
 * Implicitly allowing the this reference to escape
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThisEscape {
    // Do not allow the this reference to escape during construction.
    public ThisEscape(EventSource source) {
        // When ThisEscape publishes the EventListener, it implicitly publishes the enclosing ThisEscape instance as well,
        source.registerListener(new EventListener() {
            // inner class instances contain a hidden reference to the enclosing instance. Don't Do this.
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}

