package com.utilities.cookies.filter.matcher;

import java.util.Objects;

public class CookieMatchHolder {

    private String cookieName;

    private String date;

    public String getCookieName() {
        return cookieName;
    }

    public String getDate() {
        return date;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CookieMatchHolder(String cookieName, String date) {
        this.cookieName = cookieName;
        this.date = date;
    }

    public CookieMatchHolder() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CookieMatchHolder that = (CookieMatchHolder) o;
        return Objects.equals(cookieName, that.cookieName) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookieName, date);
    }

    @Override
    public String toString() {
        return "CookieMatchHolder{" +
                "cookieName='" + cookieName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
