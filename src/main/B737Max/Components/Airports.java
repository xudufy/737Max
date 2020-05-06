package B737Max.Components;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class aggregates a number of Airport. The aggregate is implemented as an TreeMap.
 *
 *
 * @author xudufy
 * @version 2.0 2020-05-03
 * @since 2020-03-01
 */
public class Airports {
    private static Airports instance=null;
    private final TreeMap<String, Airport> codeMap;
    private final TreeMap<String, Airport> nameMap;
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock rLock = rwl.readLock();
    private final Lock wLock = rwl.writeLock();
    private static final Lock singletonLock = new ReentrantLock();

    private Airports() {
        codeMap = new TreeMap<>();
        nameMap = new TreeMap<>();
    }

    /**
     *
     * @return
     */
    public static Airports getInstance() {
        singletonLock.lock();
        if (instance==null) {
            instance = new Airports();
        }
        singletonLock.unlock();
        return instance;
    }

    /**
     * @return
     */
    public Airport[] getList() {
        rLock.lock();
        try {
            return nameMap.values().toArray(new Airport[0]);
        } finally {
            rLock.unlock();
        }
    }

    /**
     * set a list of airport including name and code
     * @param list
     */
    public void setList(Airport[] list) {
        wLock.lock();
        nameMap.clear();
        codeMap.clear();
        for (Airport a:list) {
            nameMap.put(a.getName(), a);
            codeMap.put(a.getCode(), a);
        }
        wLock.unlock();
    }

    /**
     * using airport code to get the name
     * @param code
     * @return
     */
    public Airport selectByCode(String code) {
        rLock.lock();
        try {
            return codeMap.get(code);
        } finally {
            rLock.unlock();
        }
    }

    /**
     * using airport name to get the code
     * @param name
     * @return
     */
    public Airport selectByName(String name) {
        rLock.lock();
        try {
            return nameMap.get(name);
        } finally {
          rLock.unlock();
        }
    }


}
