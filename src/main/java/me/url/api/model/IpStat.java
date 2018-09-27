package me.url.api.model;

import java.util.Objects;

/**
 * A simple wrapper for IP statistics: shows how many URLs were shortened from the given IP
 * @author Nikita R-T
 */
public final class IpStat {

    private final String ip;
    private final long count;

    public IpStat() {
        ip = "";
        count = 0;
    }

    public IpStat(String ip, long count) {
        this.ip = ip;
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpStat ipStat = (IpStat) o;
        return count == ipStat.count && Objects.equals(ip, ipStat.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, count);
    }

    @Override
    public String toString() {
        return "IpStat{" +
                "ip='" + ip + '\'' +
                ", count=" + count +
                '}';
    }
}
