package com.jellyfish.core.queue.service;

import java.util.List;

public interface IQueue<T> {
    void processData(List<T> list);
}
