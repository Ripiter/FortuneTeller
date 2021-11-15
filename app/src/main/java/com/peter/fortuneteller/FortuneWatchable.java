package com.peter.fortuneteller;

public interface FortuneWatchable {
    public void update(Fortune fortune);
    public void errorUpdate(String errorMessage);
}
