package jcip.examples.g2thread_safety.performance;

import java.math.BigInteger;
import javax.servlet.*;

import jcip.annotations.*;

/**
 * writing correct concurrent programs is primarily about managing access to
 * shared, mutable state.
 */

/**
 * CachedFactorizer
 * <p/>
 * Servlet that caches its last request and result
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class CachedFactorizer extends GenericServlet implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    /**
     * There is frequently a tension between simplicity and performance. When implementing a synchronization policy, resist
     * the temptation to prematurely sacrifice simplicity (potentially compromising safety) for the sake of performance.
     */
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(resp, factors);
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[]{i};
    }
}
